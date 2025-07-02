package class173;

// 王室联邦，java版
// 一共有n个城市，编号1~n，给定n-1条边，所有城市连成一棵树
// 给定数值b，希望你把树划分成若干个联通区，也叫省，划分要求如下
// 每个省至少要有b个城市，最多有3 * b个城市，每个省必须有一个省会
// 省会可在省内也可在省外，一个城市可以是多个省的省会
// 一个省里，任何城市到达省会的路径上，除了省会之外的其他城市，必须都在省内
// 根据要求完成一种有效划分即可，先打印你划分了多少个省，假设数量为k
// 然后打印n个数字，范围[1, k]，表示每个城市被划分给了哪个省
// 最后打印k个数字，表示每个省会的城市编号
// 1 <= n、b <= 10^3
// 测试链接 : https://www.luogu.com.cn/problem/P2325
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_Royal1 {

	public static int MAXN = 1001;
	public static int n, b;

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] capital = new int[MAXN];
	public static int[] belong = new int[MAXN];
	public static int cntb;

	public static int[] stack = new int[MAXN];
	public static int siz;

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs(int u, int f) {
		int tmp = siz;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
				if (siz >= tmp + b) {
					capital[++cntb] = u;
					while (siz != tmp) {
						belong[stack[siz--]] = cntb;
					}
				}
			}
		}
		stack[++siz] = u;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		b = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(1, 0);
		if (cntb == 0) {
			capital[++cntb] = 1;
		}
		while (siz > 0) {
			belong[stack[siz--]] = cntb;
		}
		out.println(cntb);
		for (int i = 1; i <= n; i++) {
			out.print(belong[i] + " ");
		}
		out.println();
		for (int i = 1; i <= cntb; i++) {
			out.print(capital[i] + " ");
		}
		out.println();
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
