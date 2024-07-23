package class136;

// 返回最大异或和
// 测试链接 : https://www.luogu.com.cn/problem/P3812
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_MaximumXor {

	public static int MAXN = 51;

	public static int MAXM = 64;

	public static long[] arr = new long[MAXN];

	public static int n, m;

	public static long[] basis = new long[MAXM];

	public static boolean zero;

	public static void maxbit() {
		long max = arr[1];
		for (int i = 2; i <= n; i++) {
			max = Math.max(max, arr[i]);
		}
		m = 0;
		while ((max >> (m + 1)) != 0) {
			m++;
		}
	}

	// 普通消元
	public static void compute() {
		for (int i = 1; i <= n; i++) {
			boolean pick = false;
			for (int j = m; j >= 0; j--) {
				if (arr[i] >> j == 1) {
					if (basis[j] == 0) {
						basis[j] = arr[i];
						pick = true;
						break;
					}
					arr[i] ^= basis[j];
				}
			}
			if (!pick) {
				zero = true;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (long) in.nval;
		}
		maxbit();
		compute();
		long ans = 0;
		for (int i = m; i >= 0; i--) {
			ans = Math.max(ans, ans ^ basis[i]);
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
