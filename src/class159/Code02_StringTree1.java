package class159;

// 字符串树，java版
// 一共有n个节点，n-1条边，组成一棵树，每条边的边权为字符串
// 一共有m条查询，每条查询的格式为
// u v s : 查询节点u到节点v的路径中，有多少边的字符串以字符串s作为前缀
// 1 <= n、m <= 10^5
// 所有字符串长度不超过10，并且都由字符a~z组成
// 测试链接 : https://www.luogu.com.cn/problem/P6088
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code02_StringTree1 {

	public static int MAXN = 100001;

	public static int MAXT = 1000001;

	public static int MAXH = 20;

	public static int n, m;

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static String[] weight = new String[MAXN << 1];

	public static int cntg = 0;

	// 可持久化前缀树需要
	public static int[] root = new int[MAXN];

	public static int[][] tree = new int[MAXT][27];

	public static int[] pass = new int[MAXT];

	public static int cntt = 0;

	// 树上倍增和LCA需要
	public static int[] deep = new int[MAXN];

	public static int[][] stjump = new int[MAXN][MAXH];

	public static void addEdge(int u, int v, String w) {
		next[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static int num(char cha) {
		return cha - 'a' + 1;
	}

	public static int clone(int i) {
		int rt = ++cntt;
		for (int cha = 1; cha <= 26; cha++) {
			tree[rt][cha] = tree[i][cha];
		}
		pass[rt] = pass[i];
		return rt;
	}

	public static int insert(String str, int i) {
		int rt = clone(i);
		pass[rt]++;
		for (int j = 0, path, pre = rt, cur; j < str.length(); j++, pre = cur) {
			path = num(str.charAt(j));
			i = tree[i][path];
			cur = clone(i);
			pass[cur]++;
			tree[pre][path] = cur;
		}
		return rt;
	}

	public static int query(String str, int i) {
		for (int j = 0, path; j < str.length(); j++) {
			path = num(str.charAt(j));
			i = tree[i][path];
			if (i == 0) {
				return 0;
			}
		}
		return pass[i];
	}

	// 递归版，C++可以通过，java无法通过，递归会爆栈
	public static void dfs1(int u, int fa, String path) {
		root[u] = insert(path, root[fa]);
		deep[u] = deep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			if (to[e] != fa) {
				dfs1(to[e], u, weight[e]);
			}
		}
	}

	// 迭代版，都可以通过
	// 讲解118，详解了从递归版改迭代版
	public static int[] us = new int[MAXN];
	public static int[] fs = new int[MAXN];
	public static int[] es = new int[MAXN];
	public static String[] ps = new String[MAXN];
	public static int stackSize;
	public static int u;
	public static int f;
	public static int e;
	public static String p;

	public static void push(int u, int f, int e, String p) {
		us[stackSize] = u;
		fs[stackSize] = f;
		es[stackSize] = e;
		ps[stackSize] = p;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = us[stackSize];
		f = fs[stackSize];
		e = es[stackSize];
		p = ps[stackSize];
	}

	// dfs1方法改成迭代版
	public static void dfs2() {
		stackSize = 0;
		push(1, 0, -1, "");
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				root[u] = insert(p, root[f]);
				deep[u] = deep[f] + 1;
				stjump[u][0] = f;
				for (int p = 1; p < MAXH; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e, p);
				if (to[e] != f) {
					push(to[e], u, -1, weight[e]);
				}
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (deep[stjump[a][p]] >= deep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static int compute(int u, int v, String s) {
		return query(s, root[u]) + query(s, root[v]) - 2 * query(s, root[lca(u, v)]);
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		n = io.nextInt();
		int u, v;
		String s;
		for (int i = 1; i < n; i++) {
			u = io.nextInt();
			v = io.nextInt();
			s = io.next();
			addEdge(u, v, s);
			addEdge(v, u, s);
		}
		dfs2(); // 使用迭代版防止爆栈
		m = io.nextInt();
		for (int i = 1; i <= m; i++) {
			u = io.nextInt();
			v = io.nextInt();
			s = io.next();
			io.println(compute(u, v, s));
		}
		io.flush();
		io.close();
	}

	// 读写工具类
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
