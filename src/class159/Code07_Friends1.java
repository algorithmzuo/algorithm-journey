package class159;

// 前m大两两异或值的和，java版
// 本题只用到了经典前缀树，没有用到可持久化前缀树
// 给定一个长度为n的数组arr，下标1~n
// 你可以随意选两个不同位置的数字进行异或，得到两两异或值，顺序不同的话，算做一个两两异或值
// 那么，两两异或值，就有第1大、第2大...
// 返回前k大两两异或值的累加和，答案对1000000007取模
// 1 <= n <= 5 * 10^4
// 0 <= k <= n * (n-1) / 2
// 0 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF241B
// 测试链接 : https://codeforces.com/problemset/problem/241/B
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code07_Friends1 {

	public static int MAXN = 50001;

	public static int MAXT = MAXN * 20;

	public static int BIT = 30;

	public static int MOD = 1000000007;

	public static int INV2 = 500000004;

	public static int n, k;

	public static int[] arr = new int[MAXN];

	public static int[][] tree = new int[MAXT][2];

	public static int[] pass = new int[MAXT];

	public static int cnt = 1;

	public static int[][] sum = new int[MAXT][BIT + 1];

	public static void insert(int num) {
		int cur = 1;
		pass[1]++;
		for (int b = BIT, path; b >= 0; b--) {
			path = (num >> b) & 1;
			if (tree[cur][path] == 0) {
				tree[cur][path] = ++cnt;
			}
			cur = tree[cur][path];
			pass[cur]++;
		}
	}

	public static void dfs(int i, int h, int s) {
		if (i == 0) {
			return;
		}
		if (h == 0) {
			for (int j = 0; j <= BIT; j++) {
				if (((s >> j) & 1) == 1) {
					sum[i][j] = pass[i];
				}
			}
		} else {
			dfs(tree[i][0], h - 1, s);
			dfs(tree[i][1], h - 1, s | (1 << (h - 1)));
			for (int j = 0; j <= BIT; j++) {
				sum[i][j] = sum[tree[i][0]][j] + sum[tree[i][1]][j];
			}
		}
	}

	public static long moreEqual(int x) {
		long ans = 0;
		for (int i = 1, num, cur; i <= n; i++) {
			num = arr[i];
			cur = 1;
			for (int b = BIT, path, best, xpath; b >= 0; b--) {
				path = (num >> b) & 1;
				best = path ^ 1;
				xpath = (x >> b) & 1;
				if (xpath == 0) {
					ans += pass[tree[cur][best]];
					cur = tree[cur][path];
				} else {
					cur = tree[cur][best];
				}
				if (cur == 0) {
					break;
				}
			}
			ans += pass[cur];
		}
		if (x == 0) {
			ans -= n;
		}
		return ans / 2;
	}

	public static int maxKth() {
		int l = 0, r = 1 << BIT, m;
		int ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (moreEqual(m) >= k) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static long compute() {
		int kth = maxKth();
		long ans = 0;
		for (int i = 1, cur; i <= n; i++) {
			cur = 1;
			for (int b = BIT, path, best, kpath; b >= 0; b--) {
				path = (arr[i] >> b) & 1;
				best = path ^ 1;
				kpath = (kth >> b) & 1;
				if (kpath == 0) {
					for (int j = 0; j <= BIT; j++) {
						if (((arr[i] >> j) & 1) == 1) {
							ans = (ans + ((long) pass[tree[cur][best]] - sum[tree[cur][best]][j]) * (1L << j)) % MOD;
						} else {
							ans = (ans + (long) sum[tree[cur][best]][j] * (1L << j)) % MOD;
						}
					}
					cur = tree[cur][path];
				} else {
					cur = tree[cur][best];
				}
				if (cur == 0) {
					break;
				}
			}
			ans = (ans + (long) pass[cur] * kth) % MOD;
		}
		ans = ans * INV2 % MOD;
		ans = ((ans - (moreEqual(kth) - k) * kth % MOD) % MOD + MOD) % MOD;
		return ans;
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			insert(arr[i]);
		}
		dfs(tree[1][0], BIT, 0);
		dfs(tree[1][1], BIT, 1 << BIT);
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
		if (k == 0) {
			out.println(0);
		} else {
			prepare();
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

}
