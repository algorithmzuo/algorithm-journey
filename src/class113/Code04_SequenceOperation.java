package class113;

// 序列操作
// 给定一个arr，内部只有01两种值，长度为n
// 对于这个序列有五种变换操作和询问操作
// 操作0, l, r : 把l~r范围上所有数字全改成0
// 操作1, l, r : 把l~r范围上所有数字全改成1
// 操作2, l, r : 把l~r范围上所有数字全取反
// 操作3, l, r : 询问l~r范围上有多少个1
// 操作4, l, r : 询问l~r范围上最长的连续1字符串的长度
// 测试链接 : https://www.luogu.com.cn/problem/P2572
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_SequenceOperation {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] sum = new int[MAXN << 2];

	public static int[] len0 = new int[MAXN << 2];

	public static int[] pre0 = new int[MAXN << 2];

	public static int[] suf0 = new int[MAXN << 2];

	public static int[] len1 = new int[MAXN << 2];

	public static int[] pre1 = new int[MAXN << 2];

	public static int[] suf1 = new int[MAXN << 2];

	public static int[] change = new int[MAXN << 2];

	public static boolean[] update = new boolean[MAXN << 2];

	public static boolean[] reverse = new boolean[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			sum[rt] = arr[l];
			len0[rt] = pre0[rt] = suf0[rt] = arr[l] ^ 1;
			len1[rt] = pre1[rt] = suf1[rt] = arr[l];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			up(rt, mid - l + 1, r - mid);
		}
		update[rt] = false;
		reverse[rt] = false;
	}

	public static void up(int rt, int ln, int rn) {
		sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		len0[rt] = Math.max(Math.max(len0[rt << 1], len0[rt << 1 | 1]), suf0[rt << 1] + pre0[rt << 1 | 1]);
		pre0[rt] = pre0[rt << 1];
		if (pre0[rt << 1] == ln) {
			pre0[rt] += pre0[rt << 1 | 1];
		}
		suf0[rt] = suf0[rt << 1 | 1];
		if (suf0[rt << 1 | 1] == rn) {
			suf0[rt] += suf0[rt << 1];
		}
		len1[rt] = Math.max(Math.max(len1[rt << 1], len1[rt << 1 | 1]), suf1[rt << 1] + pre1[rt << 1 | 1]);
		pre1[rt] = pre1[rt << 1];
		if (pre1[rt << 1] == ln) {
			pre1[rt] += pre1[rt << 1 | 1];
		}
		suf1[rt] = suf1[rt << 1 | 1];
		if (suf1[rt << 1 | 1] == rn) {
			suf1[rt] += suf1[rt << 1];
		}
	}

	public static void down(int rt, int ln, int rn) {
		if (update[rt]) {
			int v = change[rt];
			sum[rt << 1] = v * ln;
			sum[rt << 1 | 1] = v * rn;
			change[rt << 1] = change[rt << 1 | 1] = v;
			update[rt << 1] = update[rt << 1 | 1] = true;
			reverse[rt << 1] = reverse[rt << 1 | 1] = false;
			len0[rt << 1] = pre0[rt << 1] = suf0[rt << 1] = v == 0 ? ln : 0;
			len1[rt << 1] = pre1[rt << 1] = suf1[rt << 1] = v == 1 ? ln : 0;
			len0[rt << 1 | 1] = pre0[rt << 1 | 1] = suf0[rt << 1 | 1] = v == 0 ? rn : 0;
			len1[rt << 1 | 1] = pre1[rt << 1 | 1] = suf1[rt << 1 | 1] = v == 1 ? rn : 0;
			update[rt] = false;
		}
		if (reverse[rt]) {
			reverse(rt << 1, ln);
			reverse(rt << 1 | 1, rn);
			reverse[rt] = false;
		}
	}

	public static void reverse(int rt, int n) {
		sum[rt] = n - sum[rt];
		int tmp;
		tmp = len0[rt]; len0[rt] = len1[rt]; len1[rt] = tmp;
		tmp = pre0[rt]; pre0[rt] = pre1[rt]; pre1[rt] = tmp;
		tmp = suf0[rt]; suf0[rt] = suf1[rt]; suf1[rt] = tmp;
		reverse[rt] = !reverse[rt];
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			sum[rt] = jobv * (r - l + 1);
			len0[rt] = pre0[rt] = suf0[rt] = jobv == 0 ? (r - l + 1) : 0;
			len1[rt] = pre1[rt] = suf1[rt] = jobv == 1 ? (r - l + 1) : 0;
			change[rt] = jobv;
			update[rt] = true;
			reverse[rt] = false;
		} else {
			int mid = (l + r) / 2;
			down(rt, mid - l + 1, r - mid);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, rt << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, rt << 1 | 1);
			}
			up(rt, mid - l + 1, r - mid);
		}
	}

	public static void flip(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			reverse(rt, r - l + 1);
		} else {
			int mid = (l + r) / 2;
			down(rt, mid - l + 1, r - mid);
			if (jobl <= mid) {
				flip(jobl, jobr, l, mid, rt << 1);
			}
			if (jobr > mid) {
				flip(jobl, jobr, mid + 1, r, rt << 1 | 1);
			}
			up(rt, mid - l + 1, r - mid);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return sum[rt];
		}
		int mid = (l + r) / 2;
		down(rt, mid - l + 1, r - mid);
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, rt << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, rt << 1 | 1);
		}
		return ans;
	}

	public static int pre, suf, len;

	public static void longest(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			len = len1[rt];
			pre = pre1[rt];
			suf = suf1[rt];
		} else {
			int mid = (l + r) / 2;
			int ln = mid - l + 1;
			int rn = r - mid;
			down(rt, ln, rn);
			if (jobr <= mid) {
				longest(jobl, jobr, l, mid, rt << 1);
			} else if (jobl > mid) {
				longest(jobl, jobr, mid + 1, r, rt << 1 | 1);
			} else {
				longest(jobl, jobr, l, mid, rt << 1);
				int llen = len; int lp = pre; int ls = suf;
				longest(jobl, jobr, mid + 1, r, rt << 1 | 1);
				int rlen = len; int rp = pre; int rs = suf;
				len = Math.max(Math.max(llen, rlen), ls + rp);
				pre = lp;
				if (lp == mid - Math.max(jobl, l) + 1) {
					pre += rp;
				}
				suf = rs;
				if (rs == Math.min(r, jobr) - mid) {
					suf += ls;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		build(1, n, 1);

		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			// 注意题目给的下标从0开始
			// 线段树下标从1开始
			in.nextToken(); jobl = (int) in.nval + 1;
			in.nextToken(); jobr = (int) in.nval + 1;
			if (op == 0) {
				update(jobl, jobr, 0, 1, n, 1);
			} else if (op == 1) {
				update(jobl, jobr, 1, 1, n, 1);
			} else if (op == 2) {
				flip(jobl, jobr, 1, n, 1);
			} else if (op == 3) {
				out.println(query(jobl, jobr, 1, n, 1));
			} else {
				longest(jobl, jobr, 1, n, 1);
				out.println(len);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
