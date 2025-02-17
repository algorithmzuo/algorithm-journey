package class160;

// 树状数组套线段树，java版
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

	// 注意这不是主席树！而是若干棵动态开点权值线段树！
	public static int[] root = new int[MAXN];

	public static int[] sum = new int[MAXT];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static int cntt = 0;

	public static int[] pos = new int[MAXN];

	public static int[] pre = new int[MAXN];

	public static int cntpos;

	public static int cntpre;

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

	public static int add(int jobi, int jobv, int l, int r, int i) {
		if (i == 0) {
			i = ++cntt;
		}
		if (l == r) {
			sum[i] += jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[i] = add(jobi, jobv, l, mid, left[i]);
			} else {
				right[i] = add(jobi, jobv, mid + 1, r, right[i]);
			}
			sum[i] = sum[left[i]] + sum[right[i]];
		}
		return i;
	}

	public static void add(int i, int v) {
		for (int j = i; j <= n; j += lowbit(j)) {
			root[j] = add(arr[i], v, 1, s, root[j]);
		}
	}

	public static int queryNumber(int jobk, int l, int r) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		int leftsum = 0;
		for (int i = 1; i <= cntpos; i++) {
			leftsum += sum[left[pos[i]]];
		}
		for (int i = 1; i <= cntpre; i++) {
			leftsum -= sum[left[pre[i]]];
		}
		if (jobk <= leftsum) {
			for (int i = 1; i <= cntpos; i++) {
				pos[i] = left[pos[i]];
			}
			for (int i = 1; i <= cntpre; i++) {
				pre[i] = left[pre[i]];
			}
			return queryNumber(jobk, l, mid);
		} else {
			for (int i = 1; i <= cntpos; i++) {
				pos[i] = right[pos[i]];
			}
			for (int i = 1; i <= cntpre; i++) {
				pre[i] = right[pre[i]];
			}
			return queryNumber(jobk - leftsum, mid + 1, r);
		}
	}

	public static int findNumber(int l, int r, int k) {
		cntpos = cntpre = 0;
		for (int i = r; i > 0; i -= lowbit(i)) {
			pos[++cntpos] = root[i];
		}
		for (int i = l - 1; i > 0; i -= lowbit(i)) {
			pre[++cntpre] = root[i];
		}
		return sorted[queryNumber(k, 1, s)];
	}

	public static int queryRank(int jobk, int l, int r) {
		if (l == r) {
			return 0;
		}
		int mid = (l + r) / 2;
		if (jobk <= mid) {
			for (int i = 1; i <= cntpos; i++) {
				pos[i] = left[pos[i]];
			}
			for (int i = 1; i <= cntpre; i++) {
				pre[i] = left[pre[i]];
			}
			return queryRank(jobk, l, mid);
		} else {
			int leftsum = 0;
			for (int i = 1; i <= cntpos; i++) {
				leftsum += sum[left[pos[i]]];
				pos[i] = right[pos[i]];
			}
			for (int i = 1; i <= cntpre; i++) {
				leftsum -= sum[left[pre[i]]];
				pre[i] = right[pre[i]];
			}
			return leftsum + queryRank(jobk, mid + 1, r);
		}
	}

	public static int findRank(int l, int r, int k) {
		cntpos = cntpre = 0;
		for (int i = r; i > 0; i -= lowbit(i)) {
			pos[++cntpos] = root[i];
		}
		for (int i = l - 1; i > 0; i -= lowbit(i)) {
			pre[++cntpre] = root[i];
		}
		return queryRank(k, 1, s) + 1;
	}

	public static int findLast(int l, int r, int k) {
		int rank = findRank(l, r, k);
		if (rank == 1) {
			return -INF;
		}
		return findNumber(l, r, rank - 1);
	}

	public static int findNext(int l, int r, int k) {
		if (k == s) {
			return INF;
		}
		int rank = findRank(l, r, k + 1);
		if (rank == r - l + 2) {
			return INF;
		}
		return findNumber(l, r, rank);
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
			add(i, 1);
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
		for (int i = 1; i <= m; i++) {
			if (ques[i][0] == 1) {
				out.println(findRank(ques[i][1], ques[i][2], kth(ques[i][3])));
			} else if (ques[i][0] == 2) {
				out.println(findNumber(ques[i][1], ques[i][2], ques[i][3]));
			} else if (ques[i][0] == 3) {
				add(ques[i][1], -1);
				arr[ques[i][1]] = kth(ques[i][2]);
				add(ques[i][1], 1);
			} else if (ques[i][0] == 4) {
				out.println(findLast(ques[i][1], ques[i][2], kth(ques[i][3])));
			} else {
				out.println(findNext(ques[i][1], ques[i][2], kth(ques[i][3])));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
