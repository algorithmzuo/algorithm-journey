package class157;

// 区间内最大中位数
// 测试链接 : https://www.luogu.com.cn/problem/P2839
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_LargestMedian {

	public static int MAXN = 20001;

	public static int MAXM = MAXN * 20;

	public static int INF = 10000001;

	public static int n, q;

	// 原始位置、数值
	public static int[][] arr = new int[MAXN][2];

	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	// 区间内最大前缀和，前缀不能为空
	public static int[] pre = new int[MAXM];

	// 区间内最大后缀和，后缀不能为空
	public static int[] suf = new int[MAXM];

	// 区间内累加和，区间为空认为累加和是0
	public static int[] sum = new int[MAXM];

	public static int cnt;

	// 查询的问题，a、b、c、d
	public static int[] ques = new int[4];

	// 收集区间信息，pre、suf、sum
	public static int[] info = new int[3];

	public static int build(int l, int r) {
		int rt = ++cnt;
		pre[rt] = suf[rt] = sum[rt] = r - l + 1;
		if (l < r) {
			int mid = (l + r) / 2;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
		}
		return rt;
	}

	public static void up(int i) {
		pre[i] = Math.max(pre[left[i]], sum[left[i]] + pre[right[i]]);
		suf[i] = Math.max(suf[right[i]], suf[left[i]] + sum[right[i]]);
		sum[i] = sum[left[i]] + sum[right[i]];
	}

	public static int update(int jobi, int l, int r, int i) {
		int rt = ++cnt;
		left[rt] = left[i];
		right[rt] = right[i];
		pre[rt] = pre[i];
		suf[rt] = suf[i];
		sum[rt] = sum[i];
		if (l == r) {
			pre[rt] = suf[rt] = sum[rt] = -1;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[rt] = update(jobi, l, mid, left[rt]);
			} else {
				right[rt] = update(jobi, mid + 1, r, right[rt]);
			}
			up(rt);
		}
		return rt;
	}

	public static void initInfo() {
		info[0] = info[1] = -INF;
		info[2] = 0;
	}

	public static void mergeRight(int r) {
		info[0] = Math.max(info[0], info[2] + pre[r]);
		info[1] = Math.max(suf[r], info[1] + sum[r]);
		info[2] += sum[r];
	}

	public static void query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			mergeRight(i);
		} else {
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				query(jobl, jobr, l, mid, left[i]);
			}
			if (jobr > mid) {
				query(jobl, jobr, mid + 1, r, right[i]);
			}
		}
	}

	public static void prepare() {
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[1] - b[1]);
		cnt = 0;
		root[1] = build(1, n);
		for (int i = 2; i <= n; i++) {
			root[i] = update(arr[i - 1][0], 1, n, root[i - 1]);
		}
	}

	public static boolean check(int i) {
		int a = ques[0] + 1, b = ques[1] + 1, c = ques[2] + 1, d = ques[3] + 1, best;
		initInfo();
		query(a, b, 1, n, root[i]);
		best = info[1];
		initInfo();
		query(c, d, 1, n, root[i]);
		best += info[0];
		if (b + 1 <= c - 1) {
			initInfo();
			query(b + 1, c - 1, 1, n, root[i]);
			best += info[2];
		}
		return best >= 0;
	}

	public static int compute() {
		int l = 1, r = n, m, ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (check(m)) {
				ans = arr[m][1];
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			arr[i][0] = i;
			in.nextToken();
			arr[i][1] = (int) in.nval;
		}
		prepare();
		in.nextToken();
		q = (int) in.nval;
		for (int i = 1, lastAns = 0; i <= q; i++) {
			in.nextToken();
			ques[0] = ((int) in.nval + lastAns) % n;
			in.nextToken();
			ques[1] = ((int) in.nval + lastAns) % n;
			in.nextToken();
			ques[2] = ((int) in.nval + lastAns) % n;
			in.nextToken();
			ques[3] = ((int) in.nval + lastAns) % n;
			Arrays.sort(ques);
			lastAns = compute();
			out.println(lastAns);
		}
		out.flush();
		out.close();
		br.close();
	}

}
