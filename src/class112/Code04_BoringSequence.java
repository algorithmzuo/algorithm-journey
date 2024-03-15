package class112;

// 无聊的数列
// 给定一个长度为n的数组arr，实现如下两种操作
// 操作1 : l r k d，arr[l..r]范围上的数依次加上等差数列，首项k，公差d
// 操作2 : p，查询arr[p]的值
// 测试链接 : https://www.luogu.com.cn/problem/P1438
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_BoringSequence {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static long[] lazy = new long[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			sum[rt] = arr[l];
		} else {
			int mid = (l + r) / 2;
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
			lazy[rt] += jobv;
			sum[rt] += jobv * (r - l + 1);
		} else {
			int mid = (l + r) / 2;
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
		int mid = (l + r) / 2;
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
			for (int i = n; i >= 2; i--) {
				arr[i] -= arr[i - 1];
			}
			build(1, n, 1);
			for (int i = 1, op; i <= m; i++) {
				in.nextToken();
				op = (int) in.nval;
				if (op == 1) {
					in.nextToken();
					int jobl = (int) in.nval;
					in.nextToken();
					int jobr = (int) in.nval;
					in.nextToken();
					long k = (long) in.nval;
					in.nextToken();
					long d = (long) in.nval;
					long e = k + d * (jobr - jobl);
					add(jobl, jobl, k, 1, n, 1);
					if (jobl + 1 <= jobr) {
						add(jobl + 1, jobr, d, 1, n, 1);
					}
					if (jobr < n) {
						add(jobr + 1, jobr + 1, -e, 1, n, 1);
					}
				} else {
					in.nextToken();
					int p = (int) in.nval;
					out.println(query(1, p, 1, n, 1));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
