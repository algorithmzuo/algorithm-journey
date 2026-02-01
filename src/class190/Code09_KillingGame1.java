package class190;

// 杀人游戏，java版
// 一共n个人，只有一个杀手，每个人是杀手的概率均等，其他人都是平民
// 给定m个知晓关系，如果x知晓y，那么y是不是杀手，x就知道情况了
// 知晓关系是单向且可传递的，比如a知晓b，b知晓c，那么a知晓c
// 你可以盘问任何人，不仅能知道对方身份，并且对方知晓的所有情况都能获得
// 但是如果你直接盘问到杀手的话，杀手会原地爆炸，炸死所有人
// 你一定要确定所有人的身份，而且你充分了解知晓关系网，会用最优的盘问策略
// 返回最优盘问策略下，杀手不爆炸还能被揪出来的概率，保留小数点后面6位
// 1 <= n <= 10^5
// 0 <= m <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4819
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code09_KillingGame1 {

	public static int MAXN = 100001;
	public static int MAXM = 300001;
	public static int n, m;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] sccSiz = new int[MAXN];
	public static int sccCnt;

	public static long[] edgeArr = new long[MAXM];
	public static int cnte;

	public static int[] indegree = new int[MAXN];

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][3];
	public static int u, status, e;
	public static int stacksize;

	public static void push(int u, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = status;
		stack[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		status = stack[stacksize][1];
		e = stack[stacksize][2];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，java会爆栈，C++可以通过
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			sccSiz[sccCnt] = 0;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				sccSiz[sccCnt]++;
			} while (pop != u);
		}
	}

	// 迭代版
	public static void tarjan2(int node) {
		stacksize = 0;
		push(node, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
				}
				if (status == 1 && belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, 0, e);
					push(v, -1, -1);
				} else {
					push(u, 1, e);
				}
			} else {
				if (dfn[u] == low[u]) {
					sccCnt++;
					sccSiz[sccCnt] = 0;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
						sccSiz[sccCnt]++;
					} while (pop != u);
				}
			}
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= sccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			if (scc1 != scc2) {
				edgeArr[++cnte] = ((long) scc1 << 32) | scc2;
			}
		}
		Arrays.sort(edgeArr, 1, cnte + 1);
		long pre = 0, cur;
		for (int i = 1; i <= cnte; i++) {
			cur = edgeArr[i];
			if (cur != pre) {
				int scc1 = (int) (cur >>> 32);
				int scc2 = (int) (cur & 0xffffffffL);
				indegree[scc2]++;
				addEdge(scc1, scc2);
				pre = cur;
			}
		}
	}

	public static double compute() {
		int inZero = 0;
		for (int i = 1; i <= sccCnt; i++) {
			if (indegree[i] == 0) {
				inZero++;
			}
		}
		for (int i = 1; i <= sccCnt; i++) {
			if (sccSiz[i] == 1 && indegree[i] == 0) {
				boolean unique = false;
				for (int e = head[i]; e > 0; e = nxt[e]) {
					int v = to[e];
					if (indegree[v] == 1) {
						unique = true;
						break;
					}
				}
				if (!unique) {
					inZero--;
					break;
				}
			}
		}
		return 1.0 - (double) inZero / n;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i);
				tarjan2(i);
			}
		}
		condense();
		double ans = compute();
		out.printf("%.6f\n", ans);
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
