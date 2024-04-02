package class113;

// 序列操作
// 给定一个长度为n的数组arr，内部只有01两种值，下标从0开始
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

	public static void up(int i, int ln, int rn) {
		int l = i << 1;
		int r = i << 1 | 1;
		sum[i] = sum[l] + sum[r];
		len0[i] = Math.max(Math.max(len0[l], len0[r]), suf0[l] + pre0[r]);
		pre0[i] = pre0[l] < ln ? pre0[l] : (pre0[l] + pre0[r]);
		suf0[i] = suf0[r] < rn ? suf0[r] : (suf0[l] + suf0[r]);
		len1[i] = Math.max(Math.max(len1[l], len1[r]), suf1[l] + pre1[r]);
		pre1[i] = pre1[l] < ln ? pre1[l] : (pre1[l] + pre1[r]);
		suf1[i] = suf1[r] < rn ? suf1[r] : (suf1[l] + suf1[r]);
	}

	public static void down(int i, int ln, int rn) {
		if (update[i]) {
			updateLazy(i << 1, change[i], ln);
			updateLazy(i << 1 | 1, change[i], rn);
			update[i] = false;
		}
		if (reverse[i]) {
			reverseLazy(i << 1, ln);
			reverseLazy(i << 1 | 1, rn);
			reverse[i] = false;
		}
	}

	public static void updateLazy(int i, int v, int n) {
		sum[i] = v * n;
		len0[i] = pre0[i] = suf0[i] = v == 0 ? n : 0;
		len1[i] = pre1[i] = suf1[i] = v == 1 ? n : 0;
		change[i] = v;
		update[i] = true;
		reverse[i] = false;
	}

	public static void reverseLazy(int i, int n) {
		sum[i] = n - sum[i];
		int tmp;
		tmp = len0[i];
		len0[i] = len1[i];
		len1[i] = tmp;
		tmp = pre0[i];
		pre0[i] = pre1[i];
		pre1[i] = tmp;
		tmp = suf0[i];
		suf0[i] = suf1[i];
		suf1[i] = tmp;
		reverse[i] = !reverse[i];
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = arr[l];
			len0[i] = pre0[i] = suf0[i] = arr[l] ^ 1;
			len1[i] = pre1[i] = suf1[i] = arr[l];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i, mid - l + 1, r - mid);
		}
		update[i] = false;
		reverse[i] = false;
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			updateLazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) / 2;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i, mid - l + 1, r - mid);
		}
	}

	public static void reverse(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			reverseLazy(i, r - l + 1);
		} else {
			int mid = (l + r) / 2;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				reverse(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				reverse(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			up(i, mid - l + 1, r - mid);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) / 2;
		down(i, mid - l + 1, r - mid);
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static int[] longest(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return new int[] { len1[i], pre1[i], suf1[i] };
		} else {
			int mid = (l + r) / 2;
			int ln = mid - l + 1;
			int rn = r - mid;
			down(i, ln, rn);
			if (jobr <= mid) {
				return longest(jobl, jobr, l, mid, i << 1);
			}
			if (jobl > mid) {
				return longest(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			int[] linfo = longest(jobl, jobr, l, mid, i << 1);
			int[] rinfo = longest(jobl, jobr, mid + 1, r, i << 1 | 1);
			int llen = linfo[0], lpre = linfo[1], lsuf = linfo[2];
			int rlen = rinfo[0], rpre = rinfo[1], rsuf = rinfo[2];
			int len = Math.max(Math.max(llen, rlen), lsuf + rpre);
			int pre = lpre < mid - Math.max(jobl, l) + 1 ? lpre : (lpre + rpre);
			int suf = rsuf < Math.min(r, jobr) - mid ? rsuf : (lsuf + rsuf);
			return new int[] { len, pre, suf };
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
			in.nextToken();
			jobl = (int) in.nval + 1;
			in.nextToken();
			jobr = (int) in.nval + 1;
			if (op == 0) {
				update(jobl, jobr, 0, 1, n, 1);
			} else if (op == 1) {
				update(jobl, jobr, 1, 1, n, 1);
			} else if (op == 2) {
				reverse(jobl, jobr, 1, n, 1);
			} else if (op == 3) {
				out.println(query(jobl, jobr, 1, n, 1));
			} else {
				out.println(longest(jobl, jobr, 1, n, 1)[0]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
