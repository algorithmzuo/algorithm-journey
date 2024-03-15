package class110;

// 线段树支持范围增加、范围查询
// 维护累加和
// 测试链接 : https://www.luogu.com.cn/problem/P3372
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_SegmentTreeAddQuerySum {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static long[] lazy = new long[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			sum[rt] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			up(rt);
		}
		lazy[rt] = 0;
	}

	public static void up(int rt) {
		sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
	}

	public static void down(int rt, int ln, int rn) {
		if (lazy[rt] != 0) {
			sum[rt << 1] += lazy[rt] * ln;
			sum[rt << 1 | 1] += lazy[rt] * rn;
			lazy[rt << 1] += lazy[rt];
			lazy[rt << 1 | 1] += lazy[rt];
			lazy[rt] = 0;
		}
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			sum[rt] += jobv * (r - l + 1);
			lazy[rt] += jobv;
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

	public static long query(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return sum[rt];
		}
		int mid = (l + r) >> 1;
		down(rt, mid - l + 1, r - mid);
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, rt << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, rt << 1 | 1);
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
