package class119;

// 检查树上两节点间的路径是否是回文
// 一颗树上有n个节点，编号1~n
// 给定长度为n的数组parent, parent[i]表示节点i的父节点编号
// 给定长度为n的数组s, s[i]表示节点i上是什么字符
// 从节点a到节点b经过节点最少的路，叫做a和b的路径
// 一共有m条查询，每条查询(a,b)，a和b的路径字符串是否是回文
// 是回文打印"YES"，不是回文打印"NO"
// 1 <= n <= 10^5
// 1 <= m <= 10^5
// parent[1] = 0，即整棵树的头节点一定是1号节点
// 每个节点上的字符一定是小写字母a~z
// 测试链接 : https://ac.nowcoder.com/acm/contest/78807/G
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code05_PathPalindrome {

	public static int MAXN = 100001;

	public static int LIMIT = 17;

	public static int power;

	public static int[] s = new int[MAXN];

	public static int[] head = new int[MAXN];

	public static int[] to = new int[MAXN << 1];

	public static int[] next = new int[MAXN << 1];

	public static int cnt;

	public static int[] deep = new int[MAXN];

	public static int[][] jump = new int[MAXN][LIMIT];

	public static long K = 499;

	// kpow[i] = k的i次方
	public static long[] kpow = new long[MAXN];

	public static long[][] stup = new long[MAXN][LIMIT];

	public static long[][] stdown = new long[MAXN][LIMIT];

	public static void build(int n) {
		power = log2(n);
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		kpow[0] = 1;
		for (int i = 1; i <= n; i++) {
			kpow[i] = kpow[i - 1] * K;
		}
	}

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs(int u, int f) {
		deep[u] = deep[f] + 1;
		jump[u][0] = f;
		stup[u][0] = stdown[u][0] = s[f];
		for (int p = 1, v; p <= power; p++) {
			v = jump[u][p - 1];
			jump[u][p] = jump[v][p - 1];
			stup[u][p] = stup[u][p - 1] * kpow[1 << (p - 1)] + stup[v][p - 1];
			stdown[u][p] = stdown[v][p - 1] * kpow[1 << (p - 1)] + stdown[u][p - 1];
		}
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
			}
		}
	}

	public static boolean isPalindrome(int a, int b) {
		int lca = lca(a, b);
		long hash1 = hash(a, lca, b);
		long hash2 = hash(b, lca, a);
		return hash1 == hash2;
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = power; p >= 0; p--) {
			if (deep[jump[a][p]] >= deep[b]) {
				a = jump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = power; p >= 0; p--) {
			if (jump[a][p] != jump[b][p]) {
				a = jump[a][p];
				b = jump[b][p];
			}
		}
		return jump[a][0];
	}

	public static long hash(int from, int lca, int to) {
		// up是上坡hash值
		long up = s[from];
		for (int p = power; p >= 0; p--) {
			if (deep[jump[from][p]] >= deep[lca]) {
				up = up * kpow[1 << p] + stup[from][p];
				from = jump[from][p];
			}
		}
		if (to == lca) {
			return up;
		}
		// down是下坡hash值
		long down = s[to];
		// height是目前下坡的总高度
		int height = 1;
		for (int p = power; p >= 0; p--) {
			if (deep[jump[to][p]] > deep[lca]) {
				down = stdown[to][p] * kpow[height] + down;
				height += 1 << p;
				to = jump[to][p];
			}
		}
		return up * kpow[height] + down;
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		int n = io.nextInt();
		build(n);
		int si = 1;
		for (char c : io.next().toCharArray()) {
			s[si++] = c - 'a' + 1;
		}
		for (int u = 1, v; u <= n; u++) {
			v = io.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(1, 0);
		int m = io.nextInt();
		for (int i = 1, a, b; i <= m; i++) {
			a = io.nextInt();
			b = io.nextInt();
			io.println(isPalindrome(a, b) ? "YES" : "NO");
		}
		io.flush();
		io.close();
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
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
