package class145;

// 分特产
// 一共有m种特产，arr[i]表示i种特产有几个
// 一共有n个同学，每个同学至少要得到一个特产
// 返回分配特产的方法数，答案对 1000000007 取模
// 0 <= n、m <= 1000
// 0 <= arr[i] <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P5505
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_DistributeSpecialties {

	public static int MAXN = 1001;

	public static int MAXK = MAXN * 2;

	public static int MOD = 1000000007;

	public static int[] arr = new int[MAXN];

	public static long[][] c = new long[MAXK][MAXK];

	public static long[] g = new long[MAXN];

	public static int n, k, m;

	public static long compute() {
		for (int i = 0; i <= k; i++) {
			c[i][0] = 1;
			for (int j = 1; j <= i; j++) {
				c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
			}
		}
		for (int i = 0; i < n; i++) {
			g[i] = c[n][i];
			for (int j = 1; j <= m; j++) {
				g[i] = (int) ((g[i] * c[arr[j] + n - i - 1][n - i - 1]) % MOD);
			}
		}
		g[n] = 0;
		long ans = 0;
		for (int i = 0; i <= n; i++) {
			if ((i & 1) == 0) {
				ans = (ans + g[i]) % MOD;
			} else {
				// -1 和 (MOD-1) 同余
				ans = (ans + g[i] * (MOD - 1) % MOD) % MOD;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		k = n * 2;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
