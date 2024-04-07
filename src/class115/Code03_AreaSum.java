package class115;

// 矩形面积并
// 测试链接 : https://www.luogu.com.cn/problem/P5490
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_AreaSum {

	public static int MAXN = 300001;

	public static int[][] rec = new int[MAXN][4];

	public static int[][] line = new int[MAXN][4];

	public static int[] y = new int[MAXN];

	public static int[] left = new int[MAXN << 2];

	public static int[] right = new int[MAXN << 2];

	public static int[] cover = new int[MAXN << 2];

	public static int[] len = new int[MAXN << 2];

	private static void build(int l, int r, int i) {
		if (r - l > 1) {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid, r, i << 1 | 1);
		}
		left[i] = y[l];
		right[i] = y[r];
	}

	public static void up(int i) {
		if (cover[i] > 0) {
			len[i] = right[i] - left[i];
		} else {
			len[i] = len[i << 1] + len[i << 1 | 1];
		}
	}

	private static void add(int jobl, int jobr, int jobv, int i) {
		int l = left[i];
		int r = right[i];
		if (jobl <= l && jobr >= r) {
			cover[i] += jobv;
		} else {
			if (jobl < right[i << 1]) {
				add(jobl, jobr, jobv, i << 1);
			}
			if (jobr > left[i << 1 | 1]) {
				add(jobl, jobr, jobv, i << 1 | 1);
			}
		}
		up(i);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken(); rec[i][0] = (int) in.nval;
			in.nextToken(); rec[i][1] = (int) in.nval;
			in.nextToken(); rec[i][2] = (int) in.nval;
			in.nextToken(); rec[i][3] = (int) in.nval;
		}
		out.println(compute(n));
		out.flush();
		out.close();
		br.close();
	}

	public static long compute(int n) {
		for (int i = 1, j = 1 + n, x1, y1, x2, y2; i <= n; i++, j++) {
			x1 = rec[i][0]; y1 = rec[i][1];
			x2 = rec[i][2]; y2 = rec[i][3];
			y[i] = y1; y[j] = y2;
			line[i][0] = x1; line[i][1] = y1; line[i][2] = y2; line[i][3] = 1;
			line[j][0] = x2; line[j][1] = y1; line[j][2] = y2; line[j][3] = -1;
		}
		n <<= 1;
		Arrays.sort(y, 1, n + 1);
		Arrays.sort(line, 1, n + 1, (a, b) -> a[0] - b[0]);
		build(1, n, 1);
		long ans = 0;
		for (int i = 1, pre = 0; i <= n; i++) {
			ans += (long) len[1] * (line[i][0] - pre);
			pre = line[i][0];
			add(line[i][1], line[i][2], line[i][3], 1);
		}
		return ans;
	}

}