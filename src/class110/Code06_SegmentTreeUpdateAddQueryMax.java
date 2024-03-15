package class110;

// 线段树同时支持范围更新、范围增加、范围查询
// 维护最大值
// 测试链接 : https://www.luogu.com.cn/problem/P1253
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_SegmentTreeUpdateAddQueryMax {

	public static int MAXN = 1000001;

	public static long[] arr = new long[MAXN];

	public static long[] max = new long[MAXN << 2];

	public static long[] lazy = new long[MAXN << 2];

	public static long[] change = new long[MAXN << 2];

	public static boolean[] update = new boolean[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			max[rt] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			up(rt);
		}
		lazy[rt] = 0;
		change[rt] = 0;
		update[rt] = false;
	}

	public static void up(int rt) {
		max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
	}

	public static void down(int rt) {
		if (update[rt]) {
			max[rt << 1] = change[rt];
			lazy[rt << 1] = 0;
			change[rt << 1] = change[rt];
			update[rt << 1] = true;
			max[rt << 1 | 1] = change[rt];
			lazy[rt << 1 | 1] = 0;
			change[rt << 1 | 1] = change[rt];
			update[rt << 1 | 1] = true;
			update[rt] = false;
		}
		if (lazy[rt] != 0) {
			max[rt << 1] += lazy[rt];
			lazy[rt << 1] += lazy[rt];
			max[rt << 1 | 1] += lazy[rt];
			lazy[rt << 1 | 1] += lazy[rt];
			lazy[rt] = 0;
		}
	}

	public static void update(int jobl, int jobr, long jobv, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			max[rt] = jobv;
			lazy[rt] = 0;
			change[rt] = jobv;
			update[rt] = true;
		} else {
			int mid = (l + r) >> 1;
			down(rt);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, rt << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, rt << 1 | 1);
			}
			up(rt);
		}
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			max[rt] += jobv;
			lazy[rt] += jobv;
		} else {
			int mid = (l + r) >> 1;
			down(rt);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, rt << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, rt << 1 | 1);
			}
			up(rt);
		}
	}

	public static long query(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return max[rt];
		}
		int mid = (l + r) >> 1;
		down(rt);
		long ans = Long.MIN_VALUE;
		if (jobl <= mid) {
			ans = Math.max(ans, query(jobl, jobr, l, mid, rt << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(jobl, jobr, mid + 1, r, rt << 1 | 1));
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (long) in.nval;
			}
			build(1, n, 1);
			long jobv;
			for (int i = 1, op, jobl, jobr; i <= m; i++) {
				in.nextToken();
				op = (int) in.nval;
				if (op == 1) {
					in.nextToken();
					jobl = (int) in.nval;
					in.nextToken();
					jobr = (int) in.nval;
					in.nextToken();
					jobv = (long) in.nval;
					update(jobl, jobr, jobv, 1, n, 1);
				} else if (op == 2) {
					in.nextToken();
					jobl = (int) in.nval;
					in.nextToken();
					jobr = (int) in.nval;
					in.nextToken();
					jobv = (long) in.nval;
					add(jobl, jobr, jobv, 1, n, 1);
				} else {
					in.nextToken();
					jobl = (int) in.nval;
					in.nextToken();
					jobr = (int) in.nval;
					out.println(query(jobl, jobr, 1, n, 1));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
