package class137;

// 幸运数字(迭代版)
// 测试链接 : https://www.luogu.com.cn/problem/P3292
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code02_LuckyNumber2 {

	public static int MAXN = 20002;

	public static int LIMIT = 16;

	public static int BIT = 60;

	public static int n, q;

	public static long[] arr = new long[MAXN];

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	// 树上倍增
	public static int[] deep = new int[MAXN];

	public static int[][] stjump = new int[MAXN][LIMIT];

	public static int power;

	// bs[i][j]表示：
	// 头节点到i节点路径上的数字，建立异或空间线性基，其中j位的线性基是哪个数字
	public static long[][] bs = new long[MAXN][BIT + 1];

	// ps[i][j]表示：
	// 头节点到i节点路径上的数字，建立异或空间线性基，其中j位的线性基来自哪一层
	public static int[][] ps = new int[MAXN][BIT + 1];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		power = log2(n);
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

	// dfs迭代版
	// ufe是为了实现迭代版而准备的栈
	public static int[][] ufe = new int[MAXN][3];

	public static int stackSize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stackSize][0] = u;
		ufe[stackSize][1] = f;
		ufe[stackSize][2] = e;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufe[stackSize][0];
		f = ufe[stackSize][1];
		e = ufe[stackSize][2];
	}

	public static void dfs(int root) {
		stackSize = 0;
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				deep[u] = deep[f] + 1;
				stjump[u][0] = f;
				for (int p = 1; p <= power; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				for (int i = 0; i <= BIT; i++) {
					bs[u][i] = bs[f][i];
					ps[u][i] = ps[f][i];
				}
				insert(u, bs[u], ps[u]);
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			}
		}
	}

	public static void insert(int p, long[] basis, int[] pos) {
		long num = arr[p];
		for (int i = BIT; i >= 0; i--) {
			if (num >> i == 1) {
				if (basis[i] == 0) {
					basis[i] = num;
					pos[i] = p;
					break;
				}
				if (deep[p] > deep[pos[i]]) {
					long tmp1 = num;
					num = basis[i];
					basis[i] = tmp1;
					int tmp2 = pos[i];
					pos[i] = p;
					p = tmp2;
				}
				num ^= basis[i];
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = power; p >= 0; p--) {
			if (deep[stjump[a][p]] >= deep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = power; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static long[] base = new long[BIT + 1];

	public static long query(int x, int y) {
		int lca = lca(x, y);
		long[] basisx = bs[x];
		int[] posx = ps[x];
		long[] basisy = bs[y];
		int[] posy = ps[y];
		Arrays.fill(base, 0);
		for (int i = BIT; i >= 0; i--) {
			if (deep[posx[i]] >= deep[lca]) {
				base[i] = basisx[i];
			}
		}
		for (int i = BIT; i >= 0; i--) {
			long num = basisy[i];
			if (deep[posy[i]] >= deep[lca] && num != 0) {
				for (int j = i; j >= 0; j--) {
					if (num >> j == 1) {
						if (base[j] == 0) {
							base[j] = num;
							break;
						}
						num ^= base[j];
					}
				}
			}
		}
		long ans = 0;
		for (int i = BIT; i >= 0; i--) {
			ans = Math.max(ans, ans ^ base[i]);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		n = io.nextInt();
		q = io.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = io.nextLong();
		}
		build();
		for (int i = 1, u, v; i < n; i++) {
			u = io.nextInt();
			v = io.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(1);
		for (int i = 1, x, y; i <= q; i++) {
			x = io.nextInt();
			y = io.nextInt();
			io.println(query(x, y));
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
