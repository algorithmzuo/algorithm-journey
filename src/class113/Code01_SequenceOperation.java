package class113;

// 序列操作
// 给定一个长度为n的数组arr，内部只有01两种值，下标从0开始
// 对于这个序列有五种变换操作和询问操作
// 操作 0 l r : 把l~r范围上所有数字全改成0
// 操作 1 l r : 把l~r范围上所有数字全改成1
// 操作 2 l r : 把l~r范围上所有数字全取反
// 操作 3 l r : 询问l~r范围上有多少个1
// 操作 4 l r : 询问l~r范围上连续1的最长子串长度
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

public class Code01_SequenceOperation {

	public static int MAXN = 100001;

	// 原始数组
	public static int[] arr = new int[MAXN];

	// 累加和用来统计1的数量
	public static int[] sum = new int[MAXN << 2];

	// 连续0的最长子串长度
	public static int[] len0 = new int[MAXN << 2];

	// 连续0的最长前缀长度
	public static int[] pre0 = new int[MAXN << 2];

	// 连续0的最长后缀长度
	public static int[] suf0 = new int[MAXN << 2];

	// 连续1的最长子串长度
	public static int[] len1 = new int[MAXN << 2];

	// 连续1的最长前缀长度
	public static int[] pre1 = new int[MAXN << 2];

	// 连续1的最长后缀长度
	public static int[] suf1 = new int[MAXN << 2];

	// 懒更新信息，范围上所有数字被重置成了什么
	public static int[] change = new int[MAXN << 2];

	// 懒更新信息，范围上有没有重置任务
	public static boolean[] update = new boolean[MAXN << 2];

	// 懒更新信息，范围上有没有翻转任务
	public static boolean[] reverse = new boolean[MAXN << 2];

	public static void up(int i, int ln, int rn) {
		int l = i << 1;
		int r = i << 1 | 1;
		sum[i] = sum[l] + sum[r];
		len0[i] = Math.max(Math.max(len0[l], len0[r]), suf0[l] + pre0[r]);
		pre0[i] = len0[l] < ln ? pre0[l] : (pre0[l] + pre0[r]);
		suf0[i] = len0[r] < rn ? suf0[r] : (suf0[l] + suf0[r]);
		len1[i] = Math.max(Math.max(len1[l], len1[r]), suf1[l] + pre1[r]);
		pre1[i] = len1[l] < ln ? pre1[l] : (pre1[l] + pre1[r]);
		suf1[i] = len1[r] < rn ? suf1[r] : (suf1[l] + suf1[r]);
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
		tmp = len0[i]; len0[i] = len1[i]; len1[i] = tmp;
		tmp = pre0[i]; pre0[i] = pre1[i]; pre1[i] = tmp;
		tmp = suf0[i]; suf0[i] = suf1[i]; suf1[i] = tmp;
		reverse[i] = !reverse[i];
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = arr[l];
			len0[i] = pre0[i] = suf0[i] = arr[l] == 0 ? 1 : 0;
			len1[i] = pre1[i] = suf1[i] = arr[l] == 1 ? 1 : 0;
		} else {
			int mid = (l + r) >> 1;
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
			int mid = (l + r) >> 1;
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
			int mid = (l + r) >> 1;
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

	// 线段树范围l~r上，被jobl~jobr影响的区域里，返回1的数量
	public static int querySum(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		down(i, mid - l + 1, r - mid);
		int ans = 0;
		if (jobl <= mid) {
			ans += querySum(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	// 返回一个长度为3的数组ans，代表结果，具体含义如下：
	// ans[0] : 线段树范围l~r上，被jobl~jobr影响的区域里，连续1的最长子串长度
	// ans[1] : 线段树范围l~r上，被jobl~jobr影响的区域里，连续1的最长前缀长度
	// ans[2] : 线段树范围l~r上，被jobl~jobr影响的区域里，连续1的最长后缀长度
	public static int[] queryLongest(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return new int[] { len1[i], pre1[i], suf1[i] };
		} else {
			int mid = (l + r) >> 1;
			int ln = mid - l + 1;
			int rn = r - mid;
			down(i, ln, rn);
			if (jobr <= mid) {
				return queryLongest(jobl, jobr, l, mid, i << 1);
			}
			if (jobl > mid) {
				return queryLongest(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			int[] l3 = queryLongest(jobl, jobr, l, mid, i << 1);
			int[] r3 = queryLongest(jobl, jobr, mid + 1, r, i << 1 | 1);
			int llen = l3[0], lpre = l3[1], lsuf = l3[2];
			int rlen = r3[0], rpre = r3[1], rsuf = r3[2];
			int len = Math.max(Math.max(llen, rlen), lsuf + rpre);
			// 任务实际影响了左侧范围的几个点 -> mid - Math.max(jobl, l) + 1
			int pre = llen < mid - Math.max(jobl, l) + 1 ? lpre : (lpre + rpre);
			// 任务实际影响了右侧范围的几个点 -> Math.min(r, jobr) - mid
			int suf = rlen < Math.min(r, jobr) - mid ? rsuf : (lsuf + rsuf);
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
			in.nextToken();
			jobl = (int) in.nval + 1; // 注意题目给的下标从0开始，线段树下标从1开始
			in.nextToken();
			jobr = (int) in.nval + 1; // 注意题目给的下标从0开始，线段树下标从1开始
			if (op == 0) {
				update(jobl, jobr, 0, 1, n, 1);
			} else if (op == 1) {
				update(jobl, jobr, 1, 1, n, 1);
			} else if (op == 2) {
				reverse(jobl, jobr, 1, n, 1);
			} else if (op == 3) {
				out.println(querySum(jobl, jobr, 1, n, 1));
			} else {
				out.println(queryLongest(jobl, jobr, 1, n, 1)[0]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
