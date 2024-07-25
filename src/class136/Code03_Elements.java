package class136;

// 元素
// 给定n个魔法矿石，每个矿石有状态和魔力，都是整数
// 若干矿石组成的组合能否有效，根据状态异或的结果来决定
// 如果一个矿石组合内部会产生异或和为0的子集，那么这个组合无效
// 返回有效的矿石组合中，最大的魔力和是多少
// 1 <= n <= 1000
// 1 <= 状态 <= 10^18
// 1 <= 魔力 <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P4570
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code03_Elements {

	public static int MAXN = 1001;

	public static int BIT = 60;

	public static long[][] arr = new long[MAXN][2];

	public static long[] basis = new long[BIT + 1];

	public static int n;

	// 普通消元
	// 计算得到最大线性基的最少花费
	public static long compute() {
		long ans = 0;
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[1] >= b[1] ? -1 : 1);
		for (int i = 1; i <= n; i++) {
			if (insert(arr[i][0])) {
				ans += arr[i][1];
			}
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
		// 题目会读取10^18范围内的long类型数字
		// 用StreamTokenizer可能无法正确读取，因为先变成double再转成long
		// 这里用Kattio类，具体看讲解019的代码中，Code05_Kattio文件
		// 有详细的说明
		Kattio io = new Kattio();
		n = io.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = io.nextLong();
			arr[i][1] = io.nextInt();
		}
		io.println(compute());
		io.flush();
		io.close();
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
