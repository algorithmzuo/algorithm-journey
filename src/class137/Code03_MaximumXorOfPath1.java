package class137;

// 路径最大异或和(递归版)
// 测试链接 : https://www.luogu.com.cn/problem/P4151
// 提交以下的code，提交时请把类名改成"Main"
// C++这么写能通过，java会因为递归层数太多而爆栈
// java能通过的写法参考本节课Code03_MaximumXorOfPath2文件

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code03_MaximumXorOfPath1 {

	public static int MAXN = 50001;

	public static int MAXM = 200002;

	public static int BIT = 62;

	// 链式前向星建图
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static long[] weight = new long[MAXM];

	public static int cnt;

	// 空间线性基
	public static long[] basis = new long[BIT + 1];

	// dfs需要
	public static boolean[] visited = new boolean[MAXN];

	public static long[] path = new long[MAXN];

	public static void build(int n) {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(basis, 0);
	}

	public static void addEdge(int u, int v, long w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static boolean insert(long num) {
		for (int i = BIT; i >= 0; i--)
			if (num >> i == 1) {
				if (basis[i] == 0) {
					basis[i] = num;
					return true;
				}
				num ^= basis[i];
			}
		return false;
	}

	public static void dfs(int u, long p) {
		path[u] = p;
		visited[u] = true;
		for (int e = head[u]; e != 0; e = next[e]) {
			int v = to[e];
			long w = weight[e];
			if (visited[v]) {
				insert(p ^ w ^ path[v]);
			} else {
				dfs(v, p ^ w);
			}
		}
	}

	public static long query(long num) {
		long ans = num;
		for (int i = BIT; i >= 0; i--) {
			ans = Math.max(ans, ans ^ basis[i]);
		}
		return ans;
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		int n = io.nextInt();
		int m = io.nextInt();
		build(n);
		for (int i = 1; i <= m; i++) {
			int u = io.nextInt();
			int v = io.nextInt();
			long w = io.nextLong();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		dfs(1, 0);
		io.println(query(path[n]));
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
