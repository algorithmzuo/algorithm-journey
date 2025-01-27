package class159;

// 美味，java版
// 给定一个长度为n的数组arr，一共有m条查询，查询格式如下
// b x l r : 从arr[l..r]中选一个数字，希望b ^ (该数字 + x)的值最大，打印这个值
// 1 <= n <= 2 * 10^5
// 1 <= m <= 10^5
// 0 <= arr[i]、b、x < 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3293
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_Yummy1 {

	public static int MAXN = 200001;

	public static int MAXT = 4000001;

	public static int BIT = 18;

	public static int n, m, s;

	public static int[] arr = new int[MAXN];

	// 可持久化线段树需要
	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static int[] size = new int[MAXT];

	public static int cnt;

	public static int build(int l, int r) {
		int rt = ++cnt;
		size[rt] = 0;
		if (l < r) {
			int mid = (l + r) / 2;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
		}
		return rt;
	}

	public static int insert(int jobi, int l, int r, int i) {
		int rt = ++cnt;
		left[rt] = left[i];
		right[rt] = right[i];
		size[rt] = size[i] + 1;
		if (l < r) {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[rt] = insert(jobi, l, mid, left[rt]);
			} else {
				right[rt] = insert(jobi, mid + 1, r, right[rt]);
			}
		}
		return rt;
	}

	public static int query(int jobl, int jobr, int l, int r, int u, int v) {
		if (jobr < l || jobl > r) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return size[v] - size[u];
		}
		int mid = (l + r) / 2;
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, left[u], left[v]);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, right[u], right[v]);
		}
		return ans;
	}

	public static void prepare() {
		cnt = 0;
		s = 0;
		for (int i = 1; i <= n; i++) {
			s = Math.max(s, arr[i]);
		}
		root[0] = build(0, s);
		for (int i = 1; i <= n; i++) {
			root[i] = insert(arr[i], 0, s, root[i - 1]);
		}
	}

	public static int compute(int b, int x, int l, int r) {
		int best = 0;
		for (int i = BIT; i >= 0; i--) {
			if (((b >> i) & 1) == 1) {
				if (query(best - x, best + (1 << i) - 1 - x, 0, s, root[l - 1], root[r]) == 0) {
					best += 1 << i;
				}
			} else {
				if (query(best + (1 << i) - x, best + (1 << (i + 1)) - 1 - x, 0, s, root[l - 1], root[r]) != 0) {
					best += 1 << i;
				}
			}
		}
		return best ^ b;
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
		for (int i = 1, b, x, l, r; i <= m; i++) {
			in.nextToken();
			b = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			in.nextToken();
			l = (int) in.nval;
			in.nextToken();
			r = (int) in.nval;
			out.println(compute(b, x, l, r));
		}
		out.flush();
		out.close();
		br.close();
	}

}
