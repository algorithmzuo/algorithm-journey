package class160;

// 线段树套线段树，java版
// 人有三种属性，身高、活泼度、缘分值
// 身高为int类型，活泼度和缘分值为小数点后最多1位的double类型
// 实现一种结构，提供如下两种类型的操作
// 操作 I a b c   : 加入一个人，身高为a，活泼度为b，缘分值为c
// 操作 Q a b c d : 查询身高范围[a,b]，活泼度范围[c,d]，所有人中的缘分最大值
// 注意操作Q，如果a > b需要交换，如果c > d需要交换
// 100 <= 身高 <= 200
// 0.0 <= 活泼度、缘分值 <= 100.0
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=1823
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code01_SegmentWithSegment1 {

	public static int n = 101;

	public static int m = 1001;

	public static int MINX = 100, MAXX = 200, MINY = 0, MAXY = 1000;

	public static int[][] tree = new int[n << 2][m << 2];

	public static void innerBuild(int yl, int yr, int xi, int yi) {
		tree[xi][yi] = -1;
		if (yl < yr) {
			int mid = (yl + yr) / 2;
			innerBuild(yl, mid, xi, yi << 1);
			innerBuild(mid + 1, yr, xi, yi << 1 | 1);
		}
	}

	public static void innerUpdate(int joby, int jobv, int yl, int yr, int xi, int yi) {
		if (yl == yr) {
			tree[xi][yi] = Math.max(tree[xi][yi], jobv);
		} else {
			int mid = (yl + yr) / 2;
			if (joby <= mid) {
				innerUpdate(joby, jobv, yl, mid, xi, yi << 1);
			} else {
				innerUpdate(joby, jobv, mid + 1, yr, xi, yi << 1 | 1);
			}
			tree[xi][yi] = Math.max(tree[xi][yi << 1], tree[xi][yi << 1 | 1]);
		}
	}

	public static int innerQuery(int jobyl, int jobyr, int yl, int yr, int xi, int yi) {
		if (jobyl <= yl && yr <= jobyr) {
			return tree[xi][yi];
		}
		int mid = (yl + yr) / 2;
		int ans = -1;
		if (jobyl <= mid) {
			ans = innerQuery(jobyl, jobyr, yl, mid, xi, yi << 1);

		}
		if (jobyr > mid) {
			ans = Math.max(ans, innerQuery(jobyl, jobyr, mid + 1, yr, xi, yi << 1 | 1));
		}
		return ans;
	}

	public static void outerBuild(int xl, int xr, int xi) {
		innerBuild(MINY, MAXY, xi, 1);
		if (xl < xr) {
			int mid = (xl + xr) / 2;
			outerBuild(xl, mid, xi << 1);
			outerBuild(mid + 1, xr, xi << 1 | 1);
		}
	}

	public static void outerUpdate(int jobx, int joby, int jobv, int xl, int xr, int xi) {
		innerUpdate(joby, jobv, MINY, MAXY, xi, 1);
		if (xl < xr) {
			int mid = (xl + xr) / 2;
			if (jobx <= mid) {
				outerUpdate(jobx, joby, jobv, xl, mid, xi << 1);
			} else {
				outerUpdate(jobx, joby, jobv, mid + 1, xr, xi << 1 | 1);
			}
		}
	}

	public static int outerQuery(int jobxl, int jobxr, int jobyl, int jobyr, int xl, int xr, int xi) {
		if (jobxl <= xl && xr <= jobxr) {
			return innerQuery(jobyl, jobyr, MINY, MAXY, xi, 1);
		}
		int mid = (xl + xr) / 2;
		int ans = -1;
		if (jobxl <= mid) {
			ans = outerQuery(jobxl, jobxr, jobyl, jobyr, xl, mid, xi << 1);
		}
		if (jobxr > mid) {
			ans = Math.max(ans, outerQuery(jobxl, jobxr, jobyl, jobyr, mid + 1, xr, xi << 1 | 1));
		}
		return ans;
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		int q = io.nextInt();
		String op;
		int a, b, c, d;
		while (q != 0) {
			outerBuild(MINX, MAXX, 1);
			for (int i = 1; i <= q; i++) {
				op = io.next();
				if (op.equals("I")) {
					a = io.nextInt();
					b = (int) (io.nextDouble() * 10);
					c = (int) (io.nextDouble() * 10);
					outerUpdate(a, b, c, MINX, MAXX, 1);
				} else {
					a = io.nextInt();
					b = io.nextInt();
					c = (int) (io.nextDouble() * 10);
					d = (int) (io.nextDouble() * 10);
					int xl = Math.min(a, b);
					int xr = Math.max(a, b);
					int yl = Math.min(c, d);
					int yr = Math.max(c, d);
					int ans = outerQuery(xl, xr, yl, yr, MINX, MAXX, 1);
					if (ans == -1) {
						io.println(ans);
					} else {
						io.println(((double) ans) / 10);
					}
				}
			}
			q = io.nextInt();
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
