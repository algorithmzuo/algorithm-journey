package class186;

// 欧拉序求LCA，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_EulerLCA1 {

	public static int MAXN = 500001;
	public static int MAXH = 20;
	public static int n, m, root;

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	public static int[] first = new int[MAXN];
	public static int[] lg2 = new int[MAXN << 1];
	public static int[][] rmq = new int[MAXN << 1][MAXH];
	public static int cntEuler;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][3];
	public static int u, f, e;
	public static int stacksize;

	public static void push(int u, int f, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		e = stack[stacksize][2];
	}

	public static void addEdge(int u, int v) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static int getUp(int x, int y) {
		return first[x] < first[y] ? x : y;
	}

	// 欧拉序递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa) {
		first[u] = ++cntEuler;
		rmq[first[u]][0] = u;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			int v = tog[e];
			if (v != fa) {
				dfs1(v, u);
				rmq[++cntEuler][0] = u;
			}
		}
	}

	// dfs1迭代版
	public static void dfs2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				first[u] = ++cntEuler;
				rmq[first[u]][0] = u;
				e = headg[u];
			} else {
				if (tog[e] != f) {
					rmq[++cntEuler][0] = u;
				}
				e = nextg[e];
			}
			if (e != 0) {
				push(u, f, e);
				int v = tog[e];
				if (v != f) {
					push(v, u, -1);
				}
			}
		}
	}

	public static void buildRmq() {
		// dfs1(root, 0);
		dfs2(root, 0);
		for (int i = 2; i <= cntEuler; i++) {
			lg2[i] = lg2[i >> 1] + 1;
		}
		for (int pre = 0, cur = 1; cur <= lg2[cntEuler]; pre++, cur++) {
			for (int i = 1; i + (1 << cur) - 1 <= cntEuler; i++) {
				rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
			}
		}
	}

	public static int getLCA(int x, int y) {
		x = first[x];
		y = first[y];
		if (x > y) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		int k = lg2[y - x + 1];
		return getUp(rmq[x][k], rmq[y - (1 << k) + 1][k]);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		root = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		buildRmq();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			out.println(getLCA(u, v));
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
