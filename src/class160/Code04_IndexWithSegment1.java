package class160;

// 树状数组套线段树，java版
// 给定一个长度为n的数组arr，下标1~n，每条操作都是如下5种类型中的一种，一共进行m次操作
// 操作 1 x y z : 查询数字z在arr[x..y]中的排名
// 操作 2 x y z : 查询arr[x..y]中排第z名的数字
// 操作 3 x y   : arr中x位置的数字改成y
// 操作 4 x y z : 查询数字z在arr[x..y]中的前驱，不存在返回-2147483647
// 操作 5 x y z : 查询数字z在arr[x..y]中的后继，不存在返回+2147483647
// 1 <= n、m <= 5 * 10^4
// 数组中的值永远在[0, 10^8]范围内
// 测试链接 : https://www.luogu.com.cn/problem/P3380
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_IndexWithSegment1 {

	public static int MAXN = 50001;

	public static int MAXT = MAXN * 160;

	public static int INF = Integer.MAX_VALUE;

	public static int n, m, s;

	public static int[] arr = new int[MAXN];

	public static int[][] ques = new int[MAXN][4];

	public static int[] sorted = new int[MAXN * 2];

	public static int[] root = new int[MAXN];

	public static int[] sum = new int[MAXT];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static int cntt = 0;

	public static int[] addTree = new int[MAXN];

	public static int[] minusTree = new int[MAXN];

	public static int cntadd;

	public static int cntminus;

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

	public static int lowbit(int i) {
		return i & -i;
	}

	// 第jobi小的数字，增加jobv的计数，数字范围l~r，节点编号i，返回头节点编号
	public static int innerAdd(int jobi, int jobv, int l, int r, int i) {
		if (i == 0) {
			i = ++cntt;
		}
		if (l == r) {
			sum[i] += jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[i] = innerAdd(jobi, jobv, l, mid, left[i]);
			} else {
				right[i] = innerAdd(jobi, jobv, mid + 1, r, right[i]);
			}
			sum[i] = sum[left[i]] + sum[right[i]];
		}
		return i;
	}

	// 查询比jobi小的数字个数，数字范围l~r
	// 不需要头节点编号，因为有多棵树，所有的头节点记录在addTree、minusTree
	public static int innerSmall(int jobi, int l, int r) {
		if (l == r) {
			return 0;
		}
		int mid = (l + r) / 2;
		if (jobi <= mid) {
			for (int i = 1; i <= cntadd; i++) {
				addTree[i] = left[addTree[i]];
			}
			for (int i = 1; i <= cntminus; i++) {
				minusTree[i] = left[minusTree[i]];
			}
			return innerSmall(jobi, l, mid);
		} else {
			int leftsum = 0;
			for (int i = 1; i <= cntadd; i++) {
				leftsum += sum[left[addTree[i]]];
				addTree[i] = right[addTree[i]];
			}
			for (int i = 1; i <= cntminus; i++) {
				leftsum -= sum[left[minusTree[i]]];
				minusTree[i] = right[minusTree[i]];
			}
			return leftsum + innerSmall(jobi, mid + 1, r);
		}
	}

	// 查询第jobk小的数字，数字范围l~r
	// 不需要头节点编号，因为有多棵树，所有的头节点记录在addTree、minusTree
	public static int innerQuery(int jobk, int l, int r) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		int leftsum = 0;
		for (int i = 1; i <= cntadd; i++) {
			leftsum += sum[left[addTree[i]]];
		}
		for (int i = 1; i <= cntminus; i++) {
			leftsum -= sum[left[minusTree[i]]];
		}
		if (jobk <= leftsum) {
			for (int i = 1; i <= cntadd; i++) {
				addTree[i] = left[addTree[i]];
			}
			for (int i = 1; i <= cntminus; i++) {
				minusTree[i] = left[minusTree[i]];
			}
			return innerQuery(jobk, l, mid);
		} else {
			for (int i = 1; i <= cntadd; i++) {
				addTree[i] = right[addTree[i]];
			}
			for (int i = 1; i <= cntminus; i++) {
				minusTree[i] = right[minusTree[i]];
			}
			return innerQuery(jobk - leftsum, mid + 1, r);
		}
	}

	// arr中i下标的数字，增加cnt的计数
	public static void add(int i, int cnt) {
		for (int j = i; j <= n; j += lowbit(j)) {
			root[j] = innerAdd(arr[i], cnt, 1, s, root[j]);
		}
	}

	// arr中i下标的数字，改成v
	public static void update(int i, int v) {
		add(i, -1);
		arr[i] = kth(v);
		add(i, 1);
	}

	// arr[l..r]范围上，比v小的数字个数
	public static int small(int l, int r, int v) {
		cntadd = cntminus = 0;
		for (int i = r; i > 0; i -= lowbit(i)) {
			addTree[++cntadd] = root[i];
		}
		for (int i = l - 1; i > 0; i -= lowbit(i)) {
			minusTree[++cntminus] = root[i];
		}
		return innerSmall(v, 1, s);
	}

	// arr[l..r]范围上，查询第k小的数字是什么
	public static int number(int l, int r, int k) {
		cntadd = cntminus = 0;
		for (int i = r; i > 0; i -= lowbit(i)) {
			addTree[++cntadd] = root[i];
		}
		for (int i = l - 1; i > 0; i -= lowbit(i)) {
			minusTree[++cntminus] = root[i];
		}
		return sorted[innerQuery(k, 1, s)];
	}

	// arr[l..r]范围上，查询v的前驱
	public static int pre(int l, int r, int v) {
		int rank = small(l, r, v) + 1;
		if (rank == 1) {
			return -INF;
		}
		return number(l, r, rank - 1);
	}

	// arr[l..r]范围上，查询v的后继
	public static int post(int l, int r, int v) {
		if (v == s) {
			return INF;
		}
		int sml = small(l, r, v + 1);
		if (sml == r - l + 1) {
			return INF;
		}
		return number(l, r, sml + 1);
	}

	public static void prepare() {
		s = 0;
		for (int i = 1; i <= n; i++) {
			sorted[++s] = arr[i];
		}
		for (int i = 1; i <= m; i++) {
			if (ques[i][0] == 3) {
				sorted[++s] = ques[i][2];
			} else if (ques[i][0] != 2) {
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
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(arr[i]);
			add(i, 1); // arr中i位置的数字，增加1个词频
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
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			ques[i][0] = (int) in.nval;
			in.nextToken();
			ques[i][1] = (int) in.nval;
			in.nextToken();
			ques[i][2] = (int) in.nval;
			if (ques[i][0] != 3) {
				in.nextToken();
				ques[i][3] = (int) in.nval;
			}
		}
		prepare();
		for (int i = 1, op, x, y, z; i <= m; i++) {
			op = ques[i][0];
			x = ques[i][1];
			y = ques[i][2];
			if (op == 3) {
				update(x, y);
			} else {
				z = ques[i][3];
				if (op == 1) {
					out.println(small(x, y, kth(z)) + 1);
				} else if (op == 2) {
					out.println(number(x, y, z));
				} else if (op == 4) {
					out.println(pre(x, y, kth(z)));
				} else {
					out.println(post(x, y, kth(z)));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
