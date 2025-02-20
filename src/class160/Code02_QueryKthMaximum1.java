package class160;

// k大数查询，java版
// 初始时有n个空集合，编号1~n，实现如下两种类型的操作，操作一共发生m次
// 操作 1 l r v : 数字v放入编号范围[l,r]的每一个集合中
// 操作 2 l r k : 编号范围[l,r]的所有集合，如果生成不去重的并集，返回第k大的数字
// 1 <= n、m <= 5 * 10^4
// 1 <= k < 2^63，题目保证第k大的数字一定存在
// 注意，测试用例里增加了Hack数据，v可能是很大的数字
// 测试链接 : https://www.luogu.com.cn/problem/P3332
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_QueryKthMaximum1 {

	// 外部线段树的范围，一共只有m个操作，所以最多有m种数字
	public static int MAXM = 50001;

	// 内部线段树的节点数上限
	public static int MAXT = MAXM * 230;

	public static int n, m, s;

	// 所有操作收集起来，因为牵扯到数字离散化
	public static int[][] ques = new int[MAXM][4];

	// 所有可能的数字，收集起来去重，方便得到数字排名
	public static int[] sorted = new int[MAXM];

	// 外部(a~b) + 内部(c~d)表示：数字排名范围a~b，集合范围c~d，数字的个数
	// 外部线段树的下标表示数字的排名
	// 外部(a~b)，假设对应的节点编号为i，那么root[i]就是内部线段树的头节点编号
	public static int[] root = new int[MAXM << 2];

	// 内部线段树是开点线段树，所以需要cnt来获得节点计数
	// 内部线段树的下标表示集合的编号
	// 内部(c~d)，假设对应的节点编号为i
	// sum[i]表示集合范围c~d，一共收集了多少数字
	// lazy[i]懒更新信息，集合范围c~d，增加了几个数字，等待懒更新的下发
	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static long[] sum = new long[MAXT];

	public static int[] lazy = new int[MAXT];

	public static int cnt;

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
				left[i] = ++cnt;
			}
			if (right[i] == 0) {
				right[i] = ++cnt;
			}
			sum[left[i]] += lazy[i] * ln;
			lazy[left[i]] += lazy[i];
			sum[right[i]] += lazy[i] * rn;
			lazy[right[i]] += lazy[i];
			lazy[i] = 0;
		}
	}

	public static int innerAdd(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			i = ++cnt;
		}
		if (jobl <= l && r <= jobr) {
			sum[i] += r - l + 1;
			lazy[i]++;
		} else {
			int mid = (l + r) / 2;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				left[i] = innerAdd(jobl, jobr, l, mid, left[i]);
			}
			if (jobr > mid) {
				right[i] = innerAdd(jobl, jobr, mid + 1, r, right[i]);
			}
			up(i);
		}
		return i;
	}

	public static long innerQuery(int jobl, int jobr, int l, int r, int i) {
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
			ans += innerQuery(jobl, jobr, l, mid, left[i]);
		}
		if (jobr > mid) {
			ans += innerQuery(jobl, jobr, mid + 1, r, right[i]);
		}
		return ans;
	}

	public static void outerAdd(int jobl, int jobr, int jobv, int l, int r, int i) {
		root[i] = innerAdd(jobl, jobr, 1, n, root[i]);
		if (l < r) {
			int mid = (l + r) / 2;
			if (jobv <= mid) {
				outerAdd(jobl, jobr, jobv, l, mid, i << 1);
			} else {
				outerAdd(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static int outerQuery(int jobl, int jobr, long jobk, int l, int r, int i) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		long rightsum = innerQuery(jobl, jobr, 1, n, root[i << 1 | 1]);
		if (jobk > rightsum) {
			return outerQuery(jobl, jobr, jobk - rightsum, l, mid, i << 1);
		} else {
			return outerQuery(jobl, jobr, jobk, mid + 1, r, i << 1 | 1);
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
				outerAdd(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
			} else {
				int idx = outerQuery(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
				out.println(sorted[idx]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
