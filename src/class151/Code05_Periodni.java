package class151;

// 表格填数
// 测试链接 : https://www.luogu.com.cn/problem/P6453
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_Periodni {

	public static int MOD = 1000000007;

	public static int MAXN = 501;

	public static int MAXH = 1000000;

	public static int[] fac = new int[MAXH + 1];

	public static int[] inv = new int[MAXH + 1];

	public static int[] arr = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[][] dp = new int[MAXN][MAXN];

	public static int[][] tmp = new int[MAXN][MAXN];

	public static int n, k;

	public static int power(long x, long p) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * x) % MOD;
			}
			x = (x * x) % MOD;
			p >>= 1;
		}
		return (int) ans;
	}

	public static int c(int n, int k) {
		return k > n ? 0 : (int) ((long) fac[n] * inv[k] % MOD * inv[n - k] % MOD);
	}

	public static void build() {
		fac[0] = fac[1] = inv[0] = 1;
		for (int i = 2; i <= MAXH; i++) {
			fac[i] = (int) ((long) fac[i - 1] * i % MOD);
		}
		inv[MAXH] = power(fac[MAXH], MOD - 2);
		for (int i = MAXH - 1; i >= 1; i--) {
			inv[i] = (int) ((long) inv[i + 1] * (i + 1) % MOD);
		}
		for (int i = 1, top = 0, pos; i <= n; i++) {
			pos = top;
			while (pos > 0 && arr[stack[pos]] > arr[i]) {
				pos--;
			}
			if (pos > 0) {
				right[stack[pos]] = i;
			}
			if (pos < top) {
				left[i] = stack[pos + 1];
			}
			stack[++pos] = i;
			top = pos;
		}
	}

	public static void dfs(int u, int fa) {
		if (u == 0) {
			dp[u][0] = 1;
		} else {
			dfs(left[u], u);
			dfs(right[u], u);
			size[u] = size[left[u]] + size[right[u]] + 1;
			for (int i = 0; i <= size[left[u]]; i++) {
				for (int j = 0; j <= size[right[u]]; j++) {
					tmp[u][i + j] = (int) (tmp[u][i + j] + (long) dp[left[u]][i] * dp[right[u]][j] % MOD) % MOD;
				}
			}
			for (int i = 0; i <= size[u]; i++) {
				for (int j = 0; j <= i; j++) {
					dp[u][i] = (int) (dp[u][i] + (long) tmp[u][j] * fac[i - j] % MOD * c(size[u] - j, i - j) % MOD
							* c(arr[u] - arr[fa], i - j) % MOD) % MOD;
				}
			}
		}
	}

	public static int compute() {
		build();
		dfs(stack[1], 0);
		return dp[stack[1]][k];
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
