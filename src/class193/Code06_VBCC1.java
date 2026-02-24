package class193;

// 点双连通分量模版题2，java版
// 测试链接 : https://www.luogu.com.cn/problem/B3610
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Code06_VBCC1 {

	public static int MAXN = 50001;
	public static int MAXM = 300001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static List<List<Integer>> vbccArr = new ArrayList<>();

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN][4];
	public static int u, root, status, e;
	public static int stacksize;

	public static void push(int u, int root, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = root;
		stack[stacksize][2] = status;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		root = stack[stacksize][1];
		status = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static class VbccCmp implements Comparator<List<Integer>> {

		@Override
		public int compare(List<Integer> o1, List<Integer> o2) {
			int size = Math.min(o1.size(), o2.size());
			for (int i = 0; i < size; i++) {
				if (!o1.get(i).equals(o2.get(i))) {
					return o1.get(i).compareTo(o2.get(i));
				}
			}
			return o1.size() - o2.size();
		}

	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void tarjan1(int u, boolean root) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		if (root && head[u] == 0) {
			ArrayList<Integer> list = new ArrayList<>();
			list.add(u);
			vbccArr.add(list);
			return;
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v, false);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					ArrayList<Integer> list = new ArrayList<>();
					list.add(u);
					int pop;
					do {
						pop = sta[top--];
						list.add(pop);
					} while (pop != v);
					vbccArr.add(list);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	// 迭代版
	public static void tarjan2(int node, boolean rt) {
		stacksize = 0;
		push(node, rt ? 1 : 0, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				if (root == 1 && head[u] == 0) {
					ArrayList<Integer> list = new ArrayList<>();
					list.add(u);
					vbccArr.add(list);
					continue;
				} else {
					e = head[u];
				}
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (low[v] >= dfn[u]) {
						ArrayList<Integer> list = new ArrayList<>();
						list.add(u);
						int pop;
						do {
							pop = sta[top--];
							list.add(pop);
						} while (pop != v);
						vbccArr.add(list);
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, root, 0, e);
					push(v, 0, -1, -1);
				} else {
					push(u, root, 1, e);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			if (u != v) {
				addEdge(u, v);
				addEdge(v, u);
			}
		}
		for (int i = 1; i <= n; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i, true);
				tarjan2(i, true);
			}
		}
		int ansCnt = 0;
		for (int i = 0; i < vbccArr.size(); i++) {
			if (vbccArr.get(i).size() > 1) {
				ansCnt++;
				vbccArr.get(i).sort((a, b) -> a.compareTo(b));
			}
		}
		out.println(ansCnt);
		vbccArr.sort(new VbccCmp());
		for (int i = 0; i < vbccArr.size(); i++) {
			if (vbccArr.get(i).size() > 1) {
				for (int node : vbccArr.get(i)) {
					out.print(node + " ");
				}
				out.println();
			}
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
