package class112;

// 无聊的数列
// 给定一个长度为n的数组arr，实现如下两种操作
// 操作 1 l r k d : arr[l..r]范围上的数依次加上等差数列，首项k，公差d
// 操作 2 p       : 查询arr[p]的值
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

public class Code03_BoringSequence {

	public static int MAXN = 100001;

	public static int[] diff = new int[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static long[] add = new long[MAXN << 2];

	public static void up(int i) {
		sum[i] = sum[i << 1] + sum[i << 1 | 1];
	}

	public static void down(int i, int ln, int rn) {
		if (add[i] != 0) {
			lazy(i << 1, add[i], ln);
			lazy(i << 1 | 1, add[i], rn);
			add[i] = 0;
		}
	}

	public static void lazy(int i, long v, int n) {
		add[i] += v;
		sum[i] += v * n;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = diff[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		add[i] = 0;
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
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

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		down(i, mid - l + 1, r - mid);
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1, pre = 0, cur; i <= n; i++) {
			in.nextToken();
			cur = (int) in.nval;
			diff[i] = cur - pre;
			pre = cur;
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
		out.flush();
		out.close();
		br.close();
	}

}
