package class136;

// 最大异或和
// 给定一个长度为n的数组arr，arr中都是long类型的非负数，可能有重复值
// 在这些数中选取任意个，使得异或和最大，返回最大的异或和
// 1 <= n <= 50
// 0 <= arr[i] <= 2^50
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

	public static int BIT = 50;

	public static long[] arr = new long[MAXN];

	public static long[] basis = new long[BIT + 1];

	public static int n;

	// 普通消元
	// 计算最大异或和
	public static long compute() {
		for (int i = 1; i <= n; i++) {
			insert(arr[i]);
		}
		long ans = 0;
		for (int i = BIT; i >= 0; i--) {
			ans = Math.max(ans, ans ^ basis[i]);
		}
		return ans;
	}

	// 线性基里插入num，如果线性基增加了返回true，否则返回false
	public static boolean insert(long num) {
		for (int i = BIT; i >= 0; i--) {
			if (num >> i == 1) {
				if (basis[i] == 0) {
					basis[i] = num;
					return true;
				}
				num ^= basis[i];
			}
		}
		return false;
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
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
