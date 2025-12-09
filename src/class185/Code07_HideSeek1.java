package class185;

// 捉迷藏，点分树的解，java版
// 树上有n个点，点的初始颜色为黑，给定n-1条边，边权都是1
// 一共有m条操作，每条操作是如下两种类型中的一种
// 操作 C x : 改变点x的颜色，黑变成白，白变成黑
// 操作 G   : 打印最远的两个黑点的距离，只有一个黑点打印0，无黑点打印-1
// 1 <= n <= 10^5    1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 提交以下的code，提交时请把类名改成"Main"，一个测试用例空间超标
// 本节课Code07_HideSeek3文件是最优解的实现，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Code07_HideSeek1 {

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

		int popHead() {
			int ans = addHeap.poll();
			clean();
			return ans;
		}

		int size() {
			return addHeap.size() - delHeap.size();
		}

		void add(int v) {
			addHeap.add(v);
		}

		void del(int v) {
			delHeap.add(v);
			clean();
		}

		int first() {
			return addHeap.peek();
		}

		int second() {
			int a = popHead();
			int b = first();
			add(a);
			return b;
		}
	}

	public static int MAXN = 100001;
	public static int n, m;
	public static boolean[] black = new boolean[MAXN];

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

	// distFa[u] : u的连通区里，每个黑点到上级重心的距离，组成的集合
	// sonMax[u] : u的每个儿子连通区里，都选一个最远的黑点，到u的最大距离，组成的集合
	//             如果u自己是黑点，那么0要加入集合
	// maxDist   : 每个重心在连通区里，选两个最远黑点的最大距离，作为自己的答案，放入全局集合
	public static Set[] distFa = new Set[MAXN];
	public static Set[] sonMax = new Set[MAXN];
	public static Set maxDist = new Set();

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

	public static void addAns(int x) {
		if (sonMax[x].size() >= 2) {
			maxDist.add(sonMax[x].first() + sonMax[x].second());
		}
	}

	public static void delAns(int x) {
		if (sonMax[x].size() >= 2) {
			maxDist.del(sonMax[x].first() + sonMax[x].second());
		}
	}

	public static void on(int x) {
		delAns(x);
		sonMax[x].del(0);
		addAns(x);
		for (int u = x, fa = centfa[u]; fa > 0; u = fa, fa = centfa[u]) {
			delAns(fa);
			sonMax[fa].del(distFa[u].first());
			distFa[u].del(getDist(x, fa));
			if (distFa[u].size() > 0) {
				sonMax[fa].add(distFa[u].first());
			}
			addAns(fa);
		}
	}

	public static void off(int x) {
		delAns(x);
		sonMax[x].add(0);
		addAns(x);
		for (int u = x, fa = centfa[u]; fa > 0; u = fa, fa = centfa[u]) {
			delAns(fa);
			if (distFa[u].size() > 0) {
				sonMax[fa].del(distFa[u].first());
			}
			distFa[u].add(getDist(x, fa));
			sonMax[fa].add(distFa[u].first());
			addAns(fa);
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			black[i] = true;
		}
		for (int i = 1; i <= n; i++) {
			distFa[i] = new Set();
			sonMax[i] = new Set();
		}
		for (int i = 1; i <= n; i++) {
			for (int u = i, fa = centfa[u]; fa > 0; u = fa, fa = centfa[u]) {
				distFa[u].add(getDist(i, fa));
			}
		}
		for (int i = 1; i <= n; i++) {
			sonMax[i].add(0);
			if (centfa[i] > 0) {
				sonMax[centfa[i]].add(distFa[i].first());
			}
		}
		for (int i = 1; i <= n; i++) {
			addAns(i);
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
		m = in.nextInt();
		int blackCnt = n;
		char op;
		for (int i = 1, x; i <= m; i++) {
			op = in.nextChar();
			if (op == 'C') {
				x = in.nextInt();
				black[x] = !black[x];
				if (black[x]) {
					off(x);
					blackCnt++;
				} else {
					on(x);
					blackCnt--;
				}
			} else {
				if (blackCnt <= 1) {
					out.println(blackCnt - 1);
				} else {
					out.println(maxDist.first());
				}
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