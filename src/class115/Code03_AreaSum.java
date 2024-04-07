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

	public static long[][] arr = new long[MAXN][4];

	public static long[] y = new long[MAXN];

	public static long[] left = new long[MAXN << 2];

	public static long[] right = new long[MAXN << 2];

	public static long[] cover = new long[MAXN << 2];

	public static long[] len = new long[MAXN << 2];

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

	private static void add(long jobl, long jobr, long jobv, int i) {
		long l = left[i];
		long r = right[i];
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
			in.nextToken();
			int x1 = (int) in.nval;
			in.nextToken();
			int y1 = (int) in.nval;
			in.nextToken();
			int x2 = (int) in.nval;
			in.nextToken();
			int y2 = (int) in.nval;
			y[i] = y1;
			y[i + n] = y2;
			arr[i][0] = x1;
			arr[i][1] = y1;
			arr[i][2] = y2;
			arr[i][3] = 1;
			arr[i + n][0] = x2;
			arr[i + n][1] = y1;
			arr[i + n][2] = y2;
			arr[i + n][3] = -1;
		}
		out.println(compute(n << 1));
		out.flush();
		out.close();
		br.close();
	}

	public static long compute(int n) {
		Arrays.sort(y, 1, n + 1);
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[0] <= b[0] ? -1 : 1);
		build(1, n, 1);
		long pre = 0, ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += len[1] * (arr[i][0] - pre);
			pre = arr[i][0];
			add(arr[i][1], arr[i][2], (int) arr[i][3], 1);
		}
		return ans;
	}

}