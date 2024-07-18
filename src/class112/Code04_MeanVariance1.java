package class112;

// 平均数和方差
// 给定一个长度为n的数组arr，操作分为三种类型，一共调用m次
// 操作 1 l r : arr数组中[l, r]范围上每个数字加上k，k为double类型
// 操作 2 l r : 查询arr数组中[l, r]范围上所有数字的平均数，返回double类型
// 操作 3 l r : 查询arr数组中[l, r]范围上所有数字的方差，返回double类型
// 测试链接 : https://www.luogu.com.cn/problem/P1471
// 如下实现是正确的，但是洛谷平台对空间卡的很严，只有使用C++能全部通过
// C++版本就是本节代码中的Code04_MeanVariance2文件
// C++版本和java版本逻辑完全一样，但只有C++版本可以通过所有测试用例
// java的版本就是无法完全通过，空间会超过限制，主要是IO空间占用大
// 这是洛谷平台没有照顾各种语言的实现所导致的
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code04_MeanVariance1 {

	public static int MAXN = 100001;

	public static double[] arr = new double[MAXN];

	// 区间累加和
	public static double[] sum1 = new double[MAXN << 2];

	// 区间平方和
	public static double[] sum2 = new double[MAXN << 2];

	// 懒更新信息(范围内每个数字增加多少)
	public static double[] addv = new double[MAXN << 2];

	public static void up(int i) {
		sum1[i] = sum1[i << 1] + sum1[i << 1 | 1];
		sum2[i] = sum2[i << 1] + sum2[i << 1 | 1];
	}

	public static void down(int i, int ln, int rn) {
		if (addv[i] != 0) {
			lazy(i << 1, addv[i], ln);
			lazy(i << 1 | 1, addv[i], rn);
			addv[i] = 0;
		}
	}

	public static void lazy(int i, double v, int n) {
		sum2[i] += sum1[i] * v * 2 + v * v * n;
		sum1[i] += v * n;
		addv[i] += v;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum1[i] = arr[l];
			sum2[i] = arr[l] * arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		addv[i] = 0;
	}

	public static void add(int jobl, int jobr, double jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static double query(double[] sum, int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		down(i, mid - l + 1, r - mid);
		double ans = 0;
		if (jobl <= mid) {
			ans += query(sum, jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += query(sum, jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		int n = io.nextInt();
		int m = io.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = io.nextDouble();
		}
		build(1, n, 1);
		double jobv;
		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			op = io.nextInt();
			if (op == 1) {
				jobl = io.nextInt();
				jobr = io.nextInt();
				jobv = io.nextDouble();
				add(jobl, jobr, jobv, 1, n, 1);
			} else if (op == 2) {
				jobl = io.nextInt();
				jobr = io.nextInt();
				double ans = query(sum1, jobl, jobr, 1, n, 1) / (jobr - jobl + 1);
				io.println(String.format("%.4f", ans));
			} else {
				jobl = io.nextInt();
				jobr = io.nextInt();
				double a = query(sum1, jobl, jobr, 1, n, 1);
				double b = query(sum2, jobl, jobr, 1, n, 1);
				double size = jobr - jobl + 1;
				double ans = b / size - (a / size) * (a / size);
				io.println(String.format("%.4f", ans));
			}
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