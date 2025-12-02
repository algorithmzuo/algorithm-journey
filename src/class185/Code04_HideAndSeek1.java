package class185;

// 捉迷藏，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code04_HideAndSeek2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Code04_HideAndSeek1 {

	static class Set {
		PriorityQueue<Integer> addHeap;
		PriorityQueue<Integer> delHeap;

		public Set() {
			addHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
			delHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
		}

		void clean() {
			while (!delHeap.isEmpty() && delHeap.peek().equals(addHeap.peek())) {
				addHeap.poll();
				delHeap.poll();
			}
		}

		int size() {
			return addHeap.size() - delHeap.size();
		}

		void push(int v) {
			addHeap.add(v);
		}

		void del(int v) {
			delHeap.add(v);
		}

		int pop() {
			clean();
			return addHeap.poll();
		}

		int top() {
			clean();
			return addHeap.peek();
		}

		int second() {
			int a = pop();
			int b = top();
			push(a);
			return b;
		}
	}

	public static int MAXN = 100001;
	public static int n, m;
	public static boolean[] status = new boolean[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] centfa = new int[MAXN];

	// distSet[u]收集 : 点分树中，子树u的每个节点，到u的父亲的距离
	// maxdSet[u]收集 : 点分树中，子树u的所有儿子，dist的最大值
	// maxdTop2收集 : 每个点的maxvSet里的最大 + 次大
	public static Set[] distSet = new Set[MAXN];
	public static Set[] maxdSet = new Set[MAXN];
	public static Set maxdTop2 = new Set();

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][4];
	public static int u, f, t, e;
	public static int stacksize;

	public static void push(int u, int f, int t, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = t;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		t = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 重链剖分收集信息递归版，java会爆栈，C++不会
	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head[u], v; e > 0; e = nxt[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int ei = head[u], v; ei > 0; ei = nxt[ei]) {
			v = to[ei];
			if (v != f) {
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	// 根据重儿子剖分的递归版，java会爆栈，C++不会
	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = nxt[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	// dfs1改成迭代版
	public static void dfs3(int cur, int father) {
		stacksize = 0;
		push(cur, father, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				fa[u] = f;
				dep[u] = dep[f] + 1;
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = to[e];
				if (v != f) {
					push(v, u, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f) {
						siz[u] += siz[v];
						if (son[u] == 0 || siz[son[u]] < siz[v]) {
							son[u] = v;
						}
					}
				}
			}
		}
	}

	// dfs2改成迭代版
	public static void dfs4(int cur, int tag) {
		stacksize = 0;
		push(cur, 0, tag, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				top[u] = t;
				if (son[u] == 0) {
					continue;
				}
				push(u, 0, t, -2);
				push(son[u], 0, t, -1);
				continue;
			} else if (e == -2) {
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, t, e);
				int v = to[e];
				if (v != fa[u] && v != son[u]) {
					push(v, 0, v, -1);
				}
			}
		}
	}

	// 找重心需要计算子树大小的递归版，java会爆栈，C++不会
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// getSize1的迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getLca(int a, int b) {
		while (top[a] != top[b]) {
			if (dep[top[a]] <= dep[top[b]]) {
				b = fa[top[b]];
			} else {
				a = fa[top[a]];
			}
		}
		return dep[a] <= dep[b] ? a : b;
	}

	public static int getDist(int x, int y) {
		return dep[x] + dep[y] - (dep[getLca(x, y)] << 1);
	}

	public static int getCentroid(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	public static void centroidTree(int u, int fa) {
		centfa[u] = fa;
		vis[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				centroidTree(getCentroid(v, u), u);
			}
		}
	}

	public static void add(int x) {
		if (maxdSet[x].size() >= 2) {
			maxdTop2.push(maxdSet[x].top() + maxdSet[x].second());
		}
	}

	public static void del(int x) {
		if (maxdSet[x].size() >= 2) {
			maxdTop2.del(maxdSet[x].top() + maxdSet[x].second());
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			maxdSet[i] = new Set();
			distSet[i] = new Set();
		}
		for (int i = 1; i <= n; i++) {
			int u = i;
			while (u > 0) {
				int fa = centfa[u];
				if (fa > 0) {
					distSet[u].push(getDist(i, fa));
				}
				u = fa;
			}
		}
		for (int i = 1; i <= n; i++) {
			maxdSet[i].push(0);
			int fa = centfa[i];
			if (fa > 0) {
				maxdSet[fa].push(distSet[i].top());
			}
		}
		for (int i = 1; i <= n; i++) {
			add(i);
		}
	}

	public static void on(int x) {
		del(x);
		maxdSet[x].del(0);
		add(x);
		for (int u = x, fa = centfa[u]; fa > 0; u = fa, fa = centfa[u]) {
			del(fa);
			maxdSet[fa].del(distSet[u].top());
			distSet[u].del(getDist(x, fa));
			if (distSet[u].size() > 0) {
				maxdSet[fa].push(distSet[u].top());
			}
			add(fa);
		}
	}

	public static void off(int x) {
		del(x);
		maxdSet[x].push(0);
		add(x);
		for (int u = x, fa = centfa[u]; fa > 0; u = fa, fa = centfa[u]) {
			del(fa);
			if (distSet[u].size() > 0) {
				maxdSet[fa].del(distSet[u].top());
			}
			distSet[u].push(getDist(x, fa));
			maxdSet[fa].push(distSet[u].top());
			add(fa);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// dfs1(1, 0);
		// dfs2(1, 1);
		dfs3(1, 0);
		dfs4(1, 1);
		centroidTree(getCentroid(1, 0), 0);
		prepare();
		int offCnt = n;
		m = in.nextInt();
		char op;
		int x;
		for (int i = 1; i <= m; i++) {
			op = in.nextChar();
			if (op == 'G') {
				if (offCnt <= 1) {
					out.println(offCnt - 1);
				} else {
					out.println(maxdTop2.top());
				}
			} else {
				x = in.nextInt();
				if (status[x]) {
					off(x);
					offCnt++;
				} else {
					on(x);
					offCnt--;
				}
				status[x] = !status[x];
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
		}

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		public char nextChar() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (c <= ' ');
			char ans = 0;
			while (c > ' ') {
				ans = (char) c;
				c = readByte();
			}
			return ans;
		}

		public int nextInt() throws IOException {
			int num = 0;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
