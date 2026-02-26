package class194;

// 铁人两项，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4630
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Duathlon1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;
	public static int n, m, cntn;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXM << 1];
	public static int[] to1 = new int[MAXM << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXN << 1];
	public static int[] next2 = new int[MAXM << 2];
	public static int[] to2 = new int[MAXM << 2];
	public static int cnt2;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] val = new int[MAXN << 1];
	public static int[] siz = new int[MAXN << 1];
	public static int num;
	public static long ans;

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN << 1][4];
	public static int u, status, fa, e;
	public static int stacksize;

	public static void push(int u, int status, int fa, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = status;
		stack[stacksize][2] = fa;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		status = stack[stacksize][1];
		fa = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge1(int u, int v) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		head2[u] = cnt2;
	}

	// 递归版
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		num++;
		val[u] = -1;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					cntn++;
					addEdge2(cntn, u);
					addEdge2(u, cntn);
					val[cntn]++;
					int pop;
					do {
						pop = sta[top--];
						addEdge2(cntn, pop);
						addEdge2(pop, cntn);
						val[cntn]++;
					} while (pop != v);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	// 迭代版
	public static void tarjan2(int node) {
		stacksize = 0;
		push(node, -1, 0, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				num++;
				val[u] = -1;
				e = head1[u];
			} else {
				v = to1[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (low[v] >= dfn[u]) {
						cntn++;
						addEdge2(cntn, u);
						addEdge2(u, cntn);
						val[cntn]++;
						int pop;
						do {
							pop = sta[top--];
							addEdge2(cntn, pop);
							addEdge2(pop, cntn);
							val[cntn]++;
						} while (pop != v);
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = next1[e];
			}
			if (e != 0) {
				v = to1[e];
				if (dfn[v] == 0) {
					push(u, 0, 0, e);
					push(v, -1, 0, -1);
				} else {
					push(u, 1, 0, e);
				}
			}
		}
	}

	// 递归版
	public static void dfs1(int u, int fa) {
		if (u <= n) {
			siz[u] = 1;
		} else {
			siz[u] = 0;
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				dfs1(v, u);
				ans += 2L * siz[u] * siz[v] * val[u];
				siz[u] += siz[v];
			}
		}
		ans += 2L * siz[u] * (num - siz[u]) * val[u];
	}

	// 迭代版
	public static void dfs2(int cur, int father) {
		stacksize = 0;
		push(cur, 0, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				if (u <= n) {
					siz[u] = 1;
				} else {
					siz[u] = 0;
				}
				e = head2[u];
			} else {
				e = next2[e];
			}
			if (e != 0) {
				push(u, 0, fa, e);
				if (to2[e] != fa) {
					push(to2[e], 0, u, -1);
				}
			} else {
				for (int ei = head2[u]; ei > 0; ei = next2[ei]) {
					int v = to2[ei];
					if (v != fa) {
						ans += 2L * siz[u] * siz[v] * val[u];
						siz[u] += siz[v];
					}
				}
				ans += 2L * siz[u] * (num - siz[u]) * val[u];
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntn = n;
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge1(u, v);
			addEdge1(v, u);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				num = 0;
				// tarjan1(i);
				tarjan2(i);
				// dfs1(i, 0);
				dfs2(i, 0);
			}
		}
		out.println(ans);
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
