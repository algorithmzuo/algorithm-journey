package class156;

// 团伙
// 注意洛谷关于本题的描述有问题，请按照如下的描述来理解题意
// 一共有n个黑帮成员，编号1 ~ n，发现了m条事实，每条事实一定属于如下两种类型中的一种
// F l r : l号成员和r号成员是朋友
// E l r : l号成员和r号成员是敌人
// 黑帮遵守如下的约定，敌人的敌人一定是朋友，朋友都来自同一个黑帮，敌人一定不是同一个黑帮
// 如果根据事实无法推断出一个成员有哪些朋友，那么该成员自己是一个黑帮
// 输入数据不存在矛盾，也就是任何两人不会推出既是朋友又是敌人的结论
// 遵守上面的约定，根据m条事实，计算黑帮有多少个
// 1 <= n <= 1000    1 <= m <= 5000
// 测试链接 : https://www.luogu.com.cn/problem/P1892
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code08_Gangster {

	public static int MAXN = 1001;

	public static int n, m;

	public static int[] father = new int[MAXN];

	public static int[] enemy = new int[MAXN];

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			enemy[i] = 0;
		}
	}

	public static int find(int i) {
		father[i] = father[i] == i ? i : find(father[i]);
		return father[i];
	}

	public static void union(int l, int r) {
		father[find(l)] = find(r);
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		n = io.nextInt();
		m = io.nextInt();
		prepare();
		String op;
		int l, r;
		for (int i = 1; i <= m; i++) {
			op = io.next();
			l = io.nextInt();
			r = io.nextInt();
			if (op.equals("F")) {
				union(l, r);
			} else {
				if (enemy[l] == 0) {
					enemy[l] = r;
				} else {
					union(enemy[l], r);
				}
				if (enemy[r] == 0) {
					enemy[r] = l;
				} else {
					union(l, enemy[r]);
				}
			}
		}
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			if (i == father[i]) {
				ans++;
			}
		}
		io.println(ans);
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
