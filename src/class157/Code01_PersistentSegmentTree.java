package class157;

// 可持久化线段树模版题
// 测试链接 : https://www.luogu.com.cn/problem/P3834
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_PersistentSegmentTree {

	public static int MAXN = 200001;

	public static int MAXM = MAXN * 22;

	public static int n, m;

	public static int[] arr = new int[MAXN];

	public static int[] sort = new int[MAXN];

	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	public static int[] count = new int[MAXM];

	public static int cnt;

	public static void prepare() {
		cnt = 0;
		for (int i = 1; i <= n; i++) {
			sort[i] = arr[i];
		}
		Arrays.sort(sort, 1, n + 1);
		root[0] = build(1, n);
	}

	public static int build(int l, int r) {
		int rt = ++cnt;
		count[rt] = 0;
		if (l < r) {
			int mid = (l + r) / 2;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
		}
		return rt;
	}

	public static int rank(int v) {
		int l = 1;
		int r = n;
		int m = 0;
		int ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (sort[m] <= v) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static int insert(int p, int l, int r, int x) {
		int rt = ++cnt;
		left[rt] = left[p];
		right[rt] = right[p];
		count[rt] = count[p] + 1;
		if (l < r) {
			int mid = (l + r) / 2;
			if (x <= mid) {
				left[rt] = insert(left[p], l, mid, x);
			} else {
				right[rt] = insert(right[p], mid + 1, r, x);
			}
		}
		return rt;
	}

	public static int query(int u, int v, int k, int l, int r) {
		if (l == r) {
			return l;
		}
		int lcount = count[left[v]] - count[left[u]];
		int mid = (l + r) / 2;
		if (lcount >= k) {
			return query(left[u], left[v], k, l, mid);
		} else {
			return query(right[u], right[v], k - lcount, mid + 1, r);
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
		prepare();
		for (int i = 1, x; i <= n; i++) {
			x = rank(arr[i]);
			root[i] = insert(root[i - 1], 1, n, x);
		}
		for (int i = 1, l, r, k; i <= m; i++) {
			in.nextToken();
			l = (int) in.nval;
			in.nextToken();
			r = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
			int ans = query(root[l - 1], root[r], k, 1, n);
			out.println(sort[ans]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
