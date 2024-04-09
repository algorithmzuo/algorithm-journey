package class114;

// 线段树的区间最值操作(hdu测试)
// 给定一个长度为n的数组arr，实现支持以下三种操作的结构
// 操作 0 l r x : 把arr[l..r]范围的每个数v，更新成min(v, x)
// 操作 1 l r   : 查询arr[l..r]范围上的最大值
// 操作 2 l r   : 查询arr[l..r]范围上的累加和
// 三种操作一共调用m次，做到时间复杂度O(n * log n + m * log n)
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5306
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_SegmentTreeSetminQueryMaxSum2 {

	public static int MAXN = 1000001;

	public static int LOWEST = -1;

	public static long[] sum = new long[MAXN << 2];

	public static int[] max = new int[MAXN << 2];

	public static int[] cnt = new int[MAXN << 2];

	public static int[] sem = new int[MAXN << 2];

	public static void up(int i) {
		int l = i << 1;
		int r = i << 1 | 1;
		sum[i] = sum[l] + sum[r];
		max[i] = Math.max(max[l], max[r]);
		if (max[l] > max[r]) {
			cnt[i] = cnt[l];
			sem[i] = Math.max(sem[l], max[r]);
		} else if (max[l] < max[r]) {
			cnt[i] = cnt[r];
			sem[i] = Math.max(max[l], sem[r]);
		} else {
			cnt[i] = cnt[l] + cnt[r];
			sem[i] = Math.max(sem[l], sem[r]);
		}
	}

	public static void down(int i) {
		lazy(i << 1, max[i]);
		lazy(i << 1 | 1, max[i]);
	}

	// 一定是没有颠覆掉次大值的懒更新信息下发，也就是说：
	// 最大值被压成v，并且v > 严格次大值的情况下
	// sum和max怎么调整
	public static void lazy(int i, int v) {
		if (v < max[i]) {
			sum[i] -= ((long) max[i] - v) * cnt[i];
			max[i] = v;
		}
	}

	public static void build(int l, int r, int i) throws IOException {
		if (l == r) {
			// 不能生成原始数组然后build
			// 因为这道题空间非常极限
			// 生成原始数组然后build
			// 空间就是会超过限制
			// 所以build的过程直接从输入流读入
			// 一般情况下不会这么极限的
			in.nextToken();
			sum[i] = max[i] = (int) in.nval;
			cnt[i] = 1;
			sem[i] = LOWEST;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void setMin(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobv >= max[i]) {
			return;
		}
		if (jobl <= l && r <= jobr && sem[i] < jobv) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				setMin(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				setMin(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int queryMax(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		int ans = Integer.MIN_VALUE;
		if (jobl <= mid) {
			ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static long querySum(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		long ans = 0;
		if (jobl <= mid) {
			ans += querySum(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	// 为了不生成原始数组
	// 让build函数可以直接从输入流拿数据
	// 所以把输入输出流定义成全局静态变量
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static StreamTokenizer in = new StreamTokenizer(br);

	public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws IOException {
		in.nextToken();
		int testCases = (int) in.nval;
		for (int t = 1; t <= testCases; t++) {
			in.nextToken();
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			build(1, n, 1);
			for (int i = 1, op, jobl, jobr, jobv; i <= m; i++) {
				in.nextToken();
				op = (int) in.nval;
				if (op == 0) {
					in.nextToken();
					jobl = (int) in.nval;
					in.nextToken();
					jobr = (int) in.nval;
					in.nextToken();
					jobv = (int) in.nval;
					setMin(jobl, jobr, jobv, 1, n, 1);
				} else if (op == 1) {
					in.nextToken();
					jobl = (int) in.nval;
					in.nextToken();
					jobr = (int) in.nval;
					out.println(queryMax(jobl, jobr, 1, n, 1));
				} else {
					in.nextToken();
					jobl = (int) in.nval;
					in.nextToken();
					jobr = (int) in.nval;
					out.println(querySum(jobl, jobr, 1, n, 1));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}