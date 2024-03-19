package class114;

// 动态开点线段树
// 一共有n个位置，编号从1~n，一开始所有位置的值为0
// 实现如下两个操作，一共会调用m次
// 操作1 : 将l~r范围的每个数增加v
// 操作2 : 返回l~r范围的累加和
// 1 <= n <= 10^9
// 1 <= m <= 10^3
// 测试链接 : https://www.luogu.com.cn/problem/P2781
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

public class Code01_DynamicSegmentTree {

	// 范围10^9，所以线段树高度为log 10^9，数量差不多30
	// 查询次数1000，每次查询都可能新建区间，数量差不多1000 * 30
	// 每个区间有左右两个孩子，所以总数量差不多1000 * 30 * 2 = 60000
	// 所以将空间设置在10^5规模就够用了
	public static int LIMIT = 100001;

	public static int cnt;

	public static int[] left = new int[LIMIT];

	public static int[] right = new int[LIMIT];

	public static long[] sum = new long[LIMIT];

	public static long[] lazy = new long[LIMIT];

	public static void up(int hi, int li, int ri) {
		sum[hi] = sum[li] + sum[ri];
	}

	public static void down(int hi, int li, int ri, int ln, int rn) {
		if (lazy[hi] != 0) {
			sum[li] += lazy[hi] * ln;
			sum[ri] += lazy[hi] * rn;
			lazy[li] += lazy[hi];
			lazy[ri] += lazy[hi];
			lazy[hi] = 0;
		}
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			sum[i] += jobv * (r - l + 1);
			lazy[i] += jobv;
		} else {
			if (left[i] == 0) {
				left[i] = ++cnt;
			}
			if (right[i] == 0) {
				right[i] = ++cnt;
			}
			int mid = (l + r) >> 1;
			down(i, left[i], right[i], mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, left[i]);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, right[i]);
			}
			up(i, left[i], right[i]);
		}
	}

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		if (left[i] == 0) {
			left[i] = ++cnt;
		}
		if (right[i] == 0) {
			right[i] = ++cnt;
		}
		int mid = (l + r) >> 1;
		down(i, left[i], right[i], mid - l + 1, r - mid);
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, left[i]);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, right[i]);
		}
		return ans;
	}

	// 如果有多组测试数据都要执行
	// 每组测试完成后要clear空间
	public static void clear() {
		Arrays.fill(left, 1, cnt + 1, 0);
		Arrays.fill(right, 1, cnt + 1, 0);
		Arrays.fill(sum, 1, cnt + 1, 0);
		Arrays.fill(lazy, 1, cnt + 1, 0);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		cnt = 1;
		long jobv;
		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken(); jobl = (int) in.nval;
				in.nextToken(); jobr = (int) in.nval;
				in.nextToken(); jobv = (long) in.nval;
				add(jobl, jobr, jobv, 1, n, 1);
			} else {
				in.nextToken(); jobl = (int) in.nval;
				in.nextToken(); jobr = (int) in.nval;
				out.println(query(jobl, jobr, 1, n, 1));
			}
		}
		// 本题每组测试数据都单独运行
		// 可以不写clear方法
		// 但是如果多组测试数据串行调用
		// 就需要加上清空逻辑
		clear();
		out.flush();
		out.close();
		br.close();
	}

}
