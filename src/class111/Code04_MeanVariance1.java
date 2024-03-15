package class111;

// 平均数和方差
// 给定一个长度为n的数组arr，进行m次操作，操作分为三种类型
// 操作1 : arr数组中[l, r]范围上每个数字加上k，k为double类型
// 操作2 : 查询arr数组中[l, r]范围上所有数字的平均数，返回double类型
// 操作3 : 查询arr数组中[l, r]范围上所有数字的方差，返回double类型
// 测试链接 : https://www.luogu.com.cn/problem/P1471
// 如下实现是正确的，但是洛谷平台对空间卡的很严，只有使用C++能全部通过
// java的版本就是无法完全通过的，空间会超过限制，主要是IO空间占用大
// 这是洛谷平台没有照顾各种语言的实现所导致的
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的
// C++版本就是本节代码中的Code04_MeanVariance2文件
// C++版本和java版本逻辑完全一样，但只有C++版本可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

public class Code04_MeanVariance1 {

	public static int MAXN = 100001;

	public static double[] arr = new double[MAXN];

	public static double[] sum1 = new double[MAXN << 2];

	public static double[] lazy1 = new double[MAXN << 2];

	public static double[] sum2 = new double[MAXN << 2];

	public static double[] lazy2 = new double[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			sum1[rt] = arr[l];
			sum2[rt] = arr[l] * arr[l];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			up(rt);
		}
		lazy1[rt] = 0;
		lazy2[rt] = 0;
	}

	public static void up(int rt) {
		sum1[rt] = sum1[rt << 1] + sum1[rt << 1 | 1];
		sum2[rt] = sum2[rt << 1] + sum2[rt << 1 | 1];
	}

	public static void down(int rt, int ln, int rn) {
		if (lazy1[rt] != 0 || lazy2[rt] != 0) {
			lazy2[rt << 1] += lazy2[rt];
			sum2[rt << 1] += sum1[rt << 1] * lazy2[rt] * 2 + lazy2[rt] * lazy2[rt] * ln;
			lazy2[rt << 1 | 1] += lazy2[rt];
			sum2[rt << 1 | 1] += sum1[rt << 1 | 1] * lazy2[rt] * 2 + lazy2[rt] * lazy2[rt] * rn;
			lazy2[rt] = 0;
			lazy1[rt << 1] += lazy1[rt];
			sum1[rt << 1] += lazy1[rt] * ln;
			lazy1[rt << 1 | 1] += lazy1[rt];
			sum1[rt << 1 | 1] += lazy1[rt] * rn;
			lazy1[rt] = 0;
		}
	}

	public static void add(int jobl, int jobr, double jobv, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			lazy2[rt] += jobv;
			sum2[rt] += sum1[rt] * jobv * 2 + jobv * jobv * (r - l + 1);
			lazy1[rt] += jobv;
			sum1[rt] += jobv * (r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(rt, mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, rt << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, rt << 1 | 1);
			}
			up(rt);
		}
	}

	public static double query(double[] sum, int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return sum[rt];
		}
		int mid = (l + r) / 2;
		down(rt, mid - l + 1, r - mid);
		double ans = 0;
		if (jobl <= mid) {
			ans += query(sum, jobl, jobr, l, mid, rt << 1);
		}
		if (jobr > mid) {
			ans += query(sum, jobl, jobr, mid + 1, r, rt << 1 | 1);
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

	// 本题double类型的变量精度较高
	// 用StreamTokenizer读取会出错
	// 有兴趣的同学可以调用test1、test2方法，一试便知
	// 高精度需要用到StringTokenizer，但是这个结构会比较费空间
	// 以下的写法已经是oi-wiki最推荐的StringTokenizer用法了，空间依然超出限制
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

	/*
读取8个double类型的数字输入如下
8
-2.7566713364794850E+0000
2.1308819339610636E+0000
1.5912831262685359E+0000
-2.7779214559122920E+0000
-6.5134523715823889E-0001
-8.5440817382186651E-0001
-1.8737916438840330E+0000
2.5193137815222144E+0000
	 */

	// StreamTokenizer无法正确读取
	public static void test1() throws IOException {
		System.out.println("测试StreamTokenizer");
		System.out.println("输入 : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		double num;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			num = (double) in.nval;
			out.println(num);
		}
		out.flush();
		out.close();
		br.close();
	}

	// StringTokenizer可以正确读取
	public static void test2() throws IOException {
		System.out.println("测试StringTokenizer");
		System.out.println("输入 : ");
		Kattio io = new Kattio();
		int n = io.nextInt();
		double num;
		for (int i = 1; i <= n; i++) {
			num = io.nextDouble();
			io.println(num);
		}
		io.flush();
		io.close();
	}

}