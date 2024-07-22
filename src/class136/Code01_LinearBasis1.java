package class136;

// 异或空间线性基模版
// 返回第k小的异或和
// 测试链接 : https://loj.ac/p/114
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_LinearBasis1 {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static int n;

	public static int len;

	public static boolean zero;

	// 利用高斯消元生成异或空间线性基
	// 因为不需要维护主元和自由元的依赖关系
	// 所以高斯消元的写法可以得到简化
	public static void basis() {
		long max = arr[1];
		for (int i = 2; i <= n; i++) {
			max = Math.max(max, arr[i]);
		}
		int m = 0;
		while ((max >> (m + 1)) != 0) {
			m++;
		}
		len = 1;
		for (long bit = 1L << m; bit != 0; bit >>= 1) {
			for (int i = len; i <= n; i++) {
				if ((arr[i] & bit) != 0) {
					swap(i, len);
					break;
				}
			}
			if ((arr[len] & bit) != 0) {
				for (int i = 1; i <= n; i++) {
					if (i != len && (arr[i] & bit) != 0) {
						arr[i] ^= arr[len];
					}
				}
				len++;
			}
		}
		len--;
		zero = len != n;
	}

	public static void swap(int a, int b) {
		long tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
	}

	// 返回第k小的异或和
	public static long query(long k) {
		if (zero) {
			k--;
		}
		if (k == 0) {
			return 0;
		}
		if (k >= 1L << len) {
			return -1;
		}
		long ans = 0;
		for (int i = len; i >= 1; i--) {
			if ((k & 1) != 0) {
				ans ^= arr[i];
			}
			k >>= 1;
		}
		return ans;
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
		basis();
		in.nextToken();
		int q = (int) in.nval;
		for (int i = 1; i <= q; i++) {
			in.nextToken();
			long k = (long) in.nval;
			out.println(query(k));
		}
		out.flush();
		out.close();
		br.close();
	}

}
