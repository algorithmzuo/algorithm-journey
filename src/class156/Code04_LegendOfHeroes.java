package class156;

// 银河英雄传说
// 一共有30000搜战舰，编号1~30000，一开始每艘战舰各自成一队
// 如果若干战舰变成一队，那么队伍里的所有战舰竖直地排成一列
// 实现如下两种操作，操作一共调用t次
// M l r : 合并l号战舰所在队伍和r号战舰所在队伍
//         l号战舰的队伍，整体移动到，r号战舰所在队伍的最末尾战舰的后面
//         如果l号战舰和r号战舰已经是一队，不进行任何操作
// C l r : 如果l号战舰和r号战舰不在一个队伍，打印-1
//         如果l号战舰和r号战舰在一个队伍，打印它俩中间隔着几艘战舰
// 1 <= t <= 5 * 10^5
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

public class Code04_LegendOfHeroes {

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
		int t = io.nextInt();
		String op;
		for (int i = 1, l, r; i <= t; i++) {
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
