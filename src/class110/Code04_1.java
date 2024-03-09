package class110;

// 测试链接 : https://www.luogu.com.cn/problem/P1471
// 如下实现是正确的，但是洛谷平台对空间卡的很严，只有使用C++能全部通过
// java的版本就是无法完全通过的，空间会超过限制
// 这是洛谷平台没有照顾各种语言的实现所导致的
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的
// C++版本就是Code04_2文件
// C++版本和java版本逻辑完全一致，但只有C++版本可以通过所有测试用例

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Code04_1 {

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
			return;
		}
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
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = in.nextInt();
		int m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextDouble();
		}
		build(1, n, 1);
		double jobv;
		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			op = in.nextInt();
			if (op == 1) {
				jobl = in.nextInt();
				jobr = in.nextInt();
				jobv = in.nextDouble();
				add(jobl, jobr, jobv, 1, n, 1);
			} else if (op == 2) {
				jobl = in.nextInt();
				jobr = in.nextInt();
				double ans = query(sum1, jobl, jobr, 1, n, 1) / (jobr - jobl + 1);
				out.println(String.format("%.4f", ans));
			} else {
				jobl = in.nextInt();
				jobr = in.nextInt();
				double a = query(sum1, jobl, jobr, 1, n, 1);
				double b = query(sum2, jobl, jobr, 1, n, 1);
				double size = jobr - jobl + 1;
				double ans = b / size - (a / size) * (a / size);
				out.println(String.format("%.4f", ans));
			}
		}
		out.flush();
		out.close();
		in.close();
	}

}