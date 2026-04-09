package class196;

// 宇航员，java版
// 一共n个宇航员，给定每个宇航员的年龄，假设平均年龄为x
// 任务有三个，A任务、B任务、C任务，所有宇航员都可以执行C任务
// 年龄 >= x 的宇航员可以执行A任务，年龄 < x 的宇航员可以执行B任务
// 给定m个厌恶关系，格式 x y，代表x和y两个编号的宇航员相互讨厌
// 允许某个任务无人执行，但是每个宇航员必须选择一个任务
// 相互讨厌的宇航员不能分配相同的任务，请问是否存在分配方案
// 如果不存在分配方案，打印"No solution."
// 如果存在，打印每个宇航员分配了什么任务，任何一种方案都可以
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/UVA1391
// 测试链接 : https://vjudge.net/problem/UVA-1391
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Astronauts1 {

	public static int MAXN = 200001;
	public static int MAXM = 500001;
	public static int n, m;
	public static int[] age = new int[MAXN];
	public static int sumAge;

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
	public static int sccCnt;

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

	public static void prepare() {
		sumAge = cntg = cntd = sccCnt = 0;
		for (int i = 1; i <= n << 1; i++) {
			head[i] = dfn[i] = low[i] = belong[i] = 0;
		}
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static boolean older(int x) {
		return age[x] * n >= sumAge;
	}

	// 递归版
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
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
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
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
					} while (pop != u);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		while (n != 0 && m != 0) {
			prepare();
			for (int i = 1; i <= n; i++) {
				age[i] = in.nextInt();
				sumAge += age[i];
			}
			for (int i = 1, x, y; i <= m; i++) {
				x = in.nextInt();
				y = in.nextInt();
				addEdge(x + n, y);
				addEdge(y + n, x);
				if (older(x) == older(y)) {
					addEdge(x, y + n);
					addEdge(y, x + n);
				}
			}
			for (int i = 1; i <= n << 1; i++) {
				if (dfn[i] == 0) {
					// tarjan1(i);
					tarjan2(i);
				}
			}
			boolean check = true;
			for (int i = 1; i <= n; i++) {
				if (belong[i] == belong[i + n]) {
					check = false;
					break;
				}
			}
			if (check) {
				for (int i = 1; i <= n; i++) {
					if (belong[i] < belong[i + n]) {
						if (older(i)) {
							out.println("A");
						} else {
							out.println("B");
						}
					} else {
						out.println("C");
					}
				}
			} else {
				out.println("No solution.");
			}
			n = in.nextInt();
			m = in.nextInt();
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
