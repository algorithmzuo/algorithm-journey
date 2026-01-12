package class188;

// 所有可能的密码串，java版
// 给定正数n，表示密码有n位，每一位可能的数字是[0..9]
// 密码有(10^n)个可能性，构造一个字符串，其中的连续子串包含所有可能的密码
// 先保证字符串的长度最短，然后保证字典序尽量的小，返回这个字符串
// 1 <= n <= 6
// 测试链接 : http://poj.org/problem?id=1780
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Password1 {

	public static int MAXN = 1000002;
	public static int n, k, m;
	public static int[] cur = new int[MAXN];
	public static int[] path = new int[MAXN];
	public static int cntp;

	public static int[][] sta = new int[MAXN][2];
	public static int u, e;
	public static int stacksize;

	public static void push(int u, int e) {
		sta[stacksize][0] = u;
		sta[stacksize][1] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = sta[stacksize][0];
		e = sta[stacksize][1];
	}

	public static void prepare(int len, int num) {
		n = len;
		k = num;
		m = 1;
		for (int i = 1; i <= n - 1; i++) {
			m *= k;
		}
		for (int i = 0; i < m; i++) {
			cur[i] = 0;
		}
		cntp = 0;
	}

	// 本题的递归深度很大，递归版会爆栈，java和C++都只有迭代版可以通过
	public static void euler(int node, int edge) {
		stacksize = 0;
		push(node, edge);
		while (stacksize > 0) {
			pop();
			if (cur[u] < k) {
				int ne = cur[u]++;
				push(u, e);
				push((u * k + ne) % m, ne);
			} else {
				path[++cntp] = e;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int len = in.nextInt();
		int num = 10;
		while (len != 0) {
			prepare(len, num);
			euler(0, 0);
			for (int i = 1; i <= n - 1; i++) {
				out.print("0");
			}
			for (int i = cntp - 1; i >= 1; i--) {
				out.print(path[i]);
			}
			out.println();
			len = in.nextInt();
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}
	}

}
