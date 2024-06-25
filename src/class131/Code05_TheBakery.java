package class131;

// 测试链接 : https://www.luogu.com.cn/problem/CF833B
// 测试链接 : https://codeforces.com/problemset/problem/833/B
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_TheBakery {

	public static int MAXN = 35001;

	public static int[] arr = new int[MAXN];

	public static int[] dp = new int[MAXN];

	public static int[] pre = new int[MAXN];

	// 线段树
	// max数组维护最大值信息
	// add数组维护加的懒更新
	// 注意这里是有错位的
	// 线段树1...k范围的值
	// 对应dp[0...k-1]范围的值
	public static int[] max = new int[MAXN << 2];

	public static int[] add = new int[MAXN << 2];

	public static int n, k;

	public static void up(int i) {
		max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
	}

	public static void down(int i) {
		if (add[i] != 0) {
			lazy(i << 1, add[i]);
			lazy(i << 1 | 1, add[i]);
			add[i] = 0;
		}
	}

	public static void lazy(int i, int v) {
		max[i] += v;
		add[i] += v;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			// 注意错位 : 线段树1...k范围的值，对应，dp[0...k-1]的值
			max[i] = dp[l - 1];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		add[i] = 0;
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		int ans = Integer.MIN_VALUE;
		if (jobl <= mid) {
			ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static int compute() {
		Arrays.fill(dp, 1, n + 1, 0);
		for (int i = 1; i <= k; i++) {
			build(1, n, 1);
			Arrays.fill(pre, 1, n + 1, 0);
			for (int j = 1; j <= n; j++) {
				// dp[当前值上次出现的位置...j-1]这些枚举值，所增加的部分，都有提升
				add(pre[arr[j]] + 1, j, 1, 1, n, 1);
				// dp[0...j-1]的枚举值中最大的作为dp[j]的答案
				dp[j] = query(1, j, 1, n, 1);
				pre[arr[j]] = j;
			}
		}
		return dp[n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
