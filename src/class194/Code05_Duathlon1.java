package class194;

// 铁人两项，java版
// 给定一张无向图，一共n个点、m条边，图中可能存在多个连通区
// 有效的点对三元组(a, b, c)，首先要求a、b、c是不同的点
// 其次要求存在一条从a出发，经过b，最终到达c的路径，沿途无重复节点
// 打印有效的点对三元组的数量
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4630
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Duathlon1 {

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

	// 方点才有deg，点双连通分量的节点数，也就是度
	public static int[] deg = new int[MAXN << 1];

	// dfs过程中，子树中的圆点数量
	public static int[] siz = new int[MAXN << 1];

	// 当前连通区的总节点数
	public static int total;

	// 答案
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
		total++;
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					cntn++;
					addEdge2(cntn, u);
					addEdge2(u, cntn);
					deg[cntn]++;
					int pop;
					do {
						pop = sta[top--];
						addEdge2(cntn, pop);
						addEdge2(pop, cntn);
						deg[cntn]++;
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
				total++;
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				e = head1[u];
			} else {
				v = to1[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (low[v] >= dfn[u]) {
						cntn++;
						addEdge2(cntn, u);
						addEdge2(u, cntn);
						deg[cntn]++;
						int pop;
						do {
							pop = sta[top--];
							addEdge2(cntn, pop);
							addEdge2(pop, cntn);
							deg[cntn]++;
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
	public static void dpOnTree1(int u, int fa) {
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				dpOnTree1(v, u);
				if (u <= n) {
					ans += 1L * siz[v] * (total - siz[v] - 1);
				} else {
					ans += 1L * siz[v] * (deg[u] - 2) * (total - siz[v]);
				}
				siz[u] += siz[v];
			}
		}
		siz[u] += u <= n ? 1 : 0;
		if (u <= n) {
			ans += 1L * (siz[u] - 1) * (total - siz[u]);
		} else {
			ans += 1L * siz[u] * (deg[u] - 2) * (total - siz[u]);
		}
	}

	// 迭代版
	public static void dpOnTree2(int cur, int father) {
		stacksize = 0;
		push(cur, 0, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
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
						if (u <= n) {
							ans += 1L * siz[v] * (total - siz[v] - 1);
						} else {
							ans += 1L * siz[v] * (deg[u] - 2) * (total - siz[v]);
						}
						siz[u] += siz[v];
					}
				}
				siz[u] += u <= n ? 1 : 0;
				if (u <= n) {
					ans += 1L * (siz[u] - 1) * (total - siz[u]);
				} else {
					ans += 1L * siz[u] * (deg[u] - 2) * (total - siz[u]);
				}
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
				total = 0;
				// tarjan1(i);
				tarjan2(i);
				// dpOnTree1(i, 0);
				dpOnTree2(i, 0);
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
