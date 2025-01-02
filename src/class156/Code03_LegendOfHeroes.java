package class156;

// 银河英雄传说
// 测试链接 : https://www.luogu.com.cn/problem/P1196
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code03_LegendOfHeroes {

	public static int MAXN = 30001;

	public static int n = 30000;

	public static int[] father = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static int[] size = new int[MAXN];

	// 递归会爆栈，所以用迭代来寻找并查集代表节点
	public static int[] stack = new int[MAXN];

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			dist[i] = 0;
			size[i] = 1;
		}
	}

	// 迭代的方式实现find，递归方式实现会爆栈
	public static int find(int i) {
		int si = 0;
		while (i != father[i]) {
			stack[++si] = i;
			i = father[i];
		}
		stack[si + 1] = i;
		for (int j = si; j >= 1; j--) {
			father[stack[j]] = i;
			dist[stack[j]] += dist[stack[j + 1]];
		}
		return i;
	}

	public static void union(int l, int r) {
		int lf = find(l), rf = find(r);
		if (lf != rf) {
			father[lf] = rf;
			dist[lf] += size[rf];
			size[rf] += size[lf];
		}
	}

	public static int query(int l, int r) {
		if (find(l) != find(r)) {
			return -1;
		}
		return Math.abs(dist[l] - dist[r]) - 1;
	}

	public static void main(String[] args) {
		prepare();
		Kattio io = new Kattio();
		int m = io.nextInt();
		String op;
		for (int i = 1, l, r; i <= m; i++) {
			op = io.next();
			l = io.nextInt();
			r = io.nextInt();
			if (op.equals("M")) {
				union(l, r);
			} else {
				io.println(query(l, r));
			}
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
