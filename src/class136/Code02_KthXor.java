package class136;

// 第k小的异或和
// 给定一个长度为n的数组arr，arr中都是long类型的非负数，可能有重复值
// 在这些数中选取任意个，至少要选一个数字
// 可以得到很多异或和，假设异或和的结果去重
// 返回第k小的异或和
// 1 <= n <= 10^5
// 0 <= arr[i] <= 2^50
// 1 <= k <= 2^50
// 测试链接 : https://loj.ac/p/114
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_KthXor {

	public static int MAXN = 100001;

	public static int BIT = 50;

	public static long[] arr = new long[MAXN];

	public static int len;

	public static boolean zero;

	public static int n;

	// 高斯消元
	public static void compute() {
		len = 1;
		for (long i = BIT; i >= 0; i--) {
			for (int j = len; j <= n; j++) {
				if ((arr[j] & (1L << i)) != 0) {
					swap(j, len);
					break;
				}
			}
			if ((arr[len] & (1L << i)) != 0) {
				for (int j = 1; j <= n; j++) {
					if (j != len && (arr[j] & (1L << i)) != 0) {
						arr[j] ^= arr[len];
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
		if (zero && k == 1) {
			return 0;
		}
		if (zero) {
			k--;
		}
		if (k >= 1L << len) {
			return -1;
		}
		long ans = 0;
		for (int i = len, j = 0; i >= 1; i--, j++) {
			if ((k & (1L << j)) != 0) {
				ans ^= arr[i];
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
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (long) in.nval;
		}
		compute();
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
