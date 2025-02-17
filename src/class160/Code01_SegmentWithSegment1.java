package class160;

// 线段树套线段树，java版
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

	public static int MAXH = 101;

	public static int MAXA = 1001;

	public static int n = 1000, m;

	public static int[][] tree = new int[MAXH << 2][MAXA << 2];

	public static void ybuild(int yl, int yr, int xi, int yi) {
		tree[xi][yi] = -1;
		if (yl < yr) {
			int mid = (yl + yr) / 2;
			ybuild(yl, mid, xi, yi << 1);
			ybuild(mid + 1, yr, xi, yi << 1 | 1);
		}
	}

	public static void build(int xl, int xr, int xi) {
		ybuild(0, n, xi, 1);
		if (xl < xr) {
			int mid = (xl + xr) / 2;
			build(xl, mid, xi << 1);
			build(mid + 1, xr, xi << 1 | 1);
		}
	}

	public static void yupdate(int joby, int jobv, int yl, int yr, int xi, int yi) {
		if (yl == yr) {
			tree[xi][yi] = Math.max(tree[xi][yi], jobv);
		} else {
			int mid = (yl + yr) / 2;
			if (joby <= mid) {
				yupdate(joby, jobv, yl, mid, xi, yi << 1);
			} else {
				yupdate(joby, jobv, mid + 1, yr, xi, yi << 1 | 1);
			}
			tree[xi][yi] = Math.max(tree[xi][yi << 1], tree[xi][yi << 1 | 1]);
		}
	}

	public static void update(int jobx, int joby, int jobv, int xl, int xr, int xi) {
		yupdate(joby, jobv, 0, n, xi, 1);
		if (xl < xr) {
			int mid = (xl + xr) / 2;
			if (jobx <= mid) {
				update(jobx, joby, jobv, xl, mid, xi << 1);
			} else {
				update(jobx, joby, jobv, mid + 1, xr, xi << 1 | 1);
			}
		}
	}

	public static int yquery(int jobyl, int jobyr, int yl, int yr, int xi, int yi) {
		if (jobyl <= yl && yr <= jobyr) {
			return tree[xi][yi];
		}
		int mid = (yl + yr) / 2;
		int ans = -1;
		if (jobyl <= mid) {
			ans = yquery(jobyl, jobyr, yl, mid, xi, yi << 1);

		}
		if (jobyr > mid) {
			ans = Math.max(ans, yquery(jobyl, jobyr, mid + 1, yr, xi, yi << 1 | 1));
		}
		return ans;
	}

	public static int query(int jobxl, int jobxr, int jobyl, int jobyr, int xl, int xr, int xi) {
		if (jobxl <= xl && xr <= jobxr) {
			return yquery(jobyl, jobyr, 0, n, xi, 1);
		}
		int mid = (xl + xr) / 2;
		int ans = -1;
		if (jobxl <= mid) {
			ans = query(jobxl, jobxr, jobyl, jobyr, xl, mid, xi << 1);
		}
		if (jobxr > mid) {
			ans = Math.max(ans, query(jobxl, jobxr, jobyl, jobyr, mid + 1, xr, xi << 1 | 1));
		}
		return ans;
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		m = io.nextInt();
		String op;
		int a, b, c, d;
		while (m != 0) {
			build(100, 200, 1);
			for (int i = 1; i <= m; i++) {
				op = io.next();
				if (op.equals("I")) {
					a = io.nextInt();
					b = (int) (io.nextDouble() * 10);
					c = (int) (io.nextDouble() * 10);
					update(a, b, c, 100, 200, 1);
				} else {
					a = io.nextInt();
					b = io.nextInt();
					c = (int) (io.nextDouble() * 10);
					d = (int) (io.nextDouble() * 10);
					int xl = Math.min(a, b);
					int xr = Math.max(a, b);
					int yl = Math.min(c, d);
					int yr = Math.max(c, d);
					int ans = query(xl, xr, yl, yr, 100, 200, 1);
					if (ans == -1) {
						io.println(ans);
					} else {
						io.println(((double) ans) / 10);
					}
				}
			}
			m = io.nextInt();
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
