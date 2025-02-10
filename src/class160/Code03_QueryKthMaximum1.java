package class160;

// K大数查询，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3332
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_QueryKthMaximum1 {

	public static int MAXN = 50001;

	public static int MAXT = MAXN * 230;

	public static int n, m, s;

	public static int[][] ques = new int[MAXN][4];

	public static int[] sorted = new int[MAXN];

	public static int[] root = new int[MAXN << 2];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static long[] sum = new long[MAXT];

	public static int[] lazy = new int[MAXT];

	public static int cntt;

	public static int kth(int num) {
		int left = 1, right = s, mid;
		while (left <= right) {
			mid = (left + right) / 2;
			if (sorted[mid] == num) {
				return mid;
			} else if (sorted[mid] < num) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return -1;
	}

	public static void up(int i) {
		sum[i] = sum[left[i]] + sum[right[i]];
	}

	public static void down(int i, int ln, int rn) {
		if (lazy[i] != 0) {
			if (left[i] == 0) {
				left[i] = ++cntt;
			}
			if (right[i] == 0) {
				right[i] = ++cntt;
			}
			sum[left[i]] += lazy[i] * ln;
			lazy[left[i]] += lazy[i];
			sum[right[i]] += lazy[i] * rn;
			lazy[right[i]] += lazy[i];
			lazy[i] = 0;
		}
	}

	public static int update(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			i = ++cntt;
		}
		if (jobl <= l && r <= jobr) {
			sum[i] += r - l + 1;
			lazy[i]++;
		} else {
			int mid = (l + r) / 2;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				left[i] = update(jobl, jobr, l, mid, left[i]);
			}
			if (jobr > mid) {
				right[i] = update(jobl, jobr, mid + 1, r, right[i]);
			}
			up(i);
		}
		return i;
	}

	public static long querySum(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) / 2;
		down(i, mid - l + 1, r - mid);
		long ans = 0;
		if (jobl <= mid) {
			ans += querySum(jobl, jobr, l, mid, left[i]);
		}
		if (jobr > mid) {
			ans += querySum(jobl, jobr, mid + 1, r, right[i]);
		}
		return ans;
	}

	public static void add(int jobl, int jobr, int jobk, int l, int r, int i) {
		root[i] = update(jobl, jobr, 1, n, root[i]);
		if (l < r) {
			int mid = (l + r) / 2;
			if (jobk <= mid) {
				add(jobl, jobr, jobk, l, mid, i << 1);
			} else {
				add(jobl, jobr, jobk, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static int query(int jobl, int jobr, long jobk, int l, int r, int i) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		long rightsum = querySum(jobl, jobr, 1, n, root[i << 1 | 1]);
		if (jobk > rightsum) {
			return query(jobl, jobr, jobk - rightsum, l, mid, i << 1);
		} else {
			return query(jobl, jobr, jobk, mid + 1, r, i << 1 | 1);
		}
	}

	public static void prepare() {
		s = 0;
		for (int i = 1; i <= m; i++) {
			if (ques[i][0] == 1) {
				sorted[++s] = ques[i][3];
			}
		}
		Arrays.sort(sorted, 1, s + 1);
		int len = 1;
		for (int i = 2; i <= s; i++) {
			if (sorted[len] != sorted[i]) {
				sorted[++len] = sorted[i];
			}
		}
		s = len;
		for (int i = 1; i <= m; i++) {
			if (ques[i][0] == 1) {
				ques[i][3] = kth(ques[i][3]);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			ques[i][0] = (int) in.nval;
			in.nextToken();
			ques[i][1] = (int) in.nval;
			in.nextToken();
			ques[i][2] = (int) in.nval;
			in.nextToken();
			ques[i][3] = (int) in.nval;
		}
		prepare();
		for (int i = 1; i <= m; i++) {
			if (ques[i][0] == 1) {
				add(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
			} else {
				int idx = query(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
				out.println(sorted[idx]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
