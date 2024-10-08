package class146;

// 火星人plus
// 测试链接 : https://www.luogu.com.cn/problem/U72177
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_AlienPlus {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static int[] sum = new int[MAXN << 2];

	public static int n;

	public static long m;

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = 1;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			sum[i] = sum[i << 1] + sum[i << 1 | 1];
		}
	}

	public static void add(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			sum[i] += jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				add(jobi, jobv, l, mid, i << 1);
			} else {
				add(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			sum[i] = sum[i << 1] + sum[i << 1 | 1];
		}
	}

	public static int sum(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		int ans = 0;
		if (jobl <= mid) {
			ans += sum(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += sum(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static int get(int k, int l, int r, int i) {
		int ans;
		if (l == r) {
			ans = l;
		} else {
			int mid = (l + r) >> 1;
			if (sum[i << 1] >= k) {
				ans = get(k, l, mid, i << 1);
			} else {
				ans = get(k - sum[i << 1], mid + 1, r, i << 1 | 1);
			}
		}
		return ans;
	}

	public static void compute() {
		build(1, n, 1);
		for (int i = 1, x; i <= n; i++) {
			x = (int) arr[i];
			if (x == 1) {
				arr[i] = 0;
			} else {
				arr[i] = sum(1, x - 1, 1, n, 1);
			}
			add(x, -1, 1, n, 1);
		}
		arr[n] += m;
		for (int i = n; i >= 1; i--) {
			arr[i - 1] += arr[i] / (n - i + 1);
			arr[i] %= n - i + 1;
		}
		build(1, n, 1);
		for (int i = 1; i <= n; i++) {
			arr[i] = get((int) arr[i] + 1, 1, n, 1);
			add((int) arr[i], -1, 1, n, 1);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (long) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		compute();
		for (int i = 1; i <= n; i++) {
			out.print(arr[i] + " ");
		}
		out.flush();
		out.close();
		br.close();
	}

}