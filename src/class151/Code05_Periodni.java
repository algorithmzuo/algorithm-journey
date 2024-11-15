package class151;

// 表格填数
// 给定一个长度为n的数组arr，arr[i]表示i位置上方的正方形格子数量
// 那么从1位置到n位置，每个位置就是一个直方图，所有的直方图紧靠在一起
// 在这片区域中，你要放入k个相同数字，不能有任意两个数字在同一行或者同一列
// 注意在这片区域中，如果某一行中间断开了，使得两个数字无法在这一行连通，则不算违规
// 返回填入数字的方法数，答案对 1000000007 取模
// 1 <= n、k <= 500    0 <= arr[i] <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P6453
// 因为本题给定的可用空间很少，所以数组为int类型，不用long类型
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_Periodni {

	public static int MOD = 1000000007;

	public static int MAXN = 501;

	public static int MAXH = 1000000;

	// 所有数字
	public static int[] arr = new int[MAXN];

	// 阶乘余数表
	public static int[] fac = new int[MAXH + 1];

	// 阶乘逆元表
	public static int[] inv = new int[MAXH + 1];

	// 笛卡尔树需要
	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] stack = new int[MAXN];

	// dfs需要
	public static int[] size = new int[MAXN];

	// dp是动态规划表
	public static int[][] dp = new int[MAXN][MAXN];

	// tmp是动态规划的临时结果
	public static int[] tmp = new int[MAXN];

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
		return n < k ? 0 : (int) ((long) fac[n] * inv[k] % MOD * inv[n - k] % MOD);
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
			return;
		}
		dfs(left[u], u);
		dfs(right[u], u);
		size[u] = size[left[u]] + size[right[u]] + 1;
		Arrays.fill(tmp, 0, k + 1, 0);
		// 所有dfs过程都算上，这一部分的总复杂度O(n^2)
		for (int l = 0; l <= Math.min(size[left[u]], k); l++) {
			for (int r = 0; r <= Math.min(size[right[u]], k - l); r++) {
				tmp[l + r] = (int) (tmp[l + r] + (long) dp[left[u]][l] * dp[right[u]][r] % MOD) % MOD;
			}
		}
		// 所有dfs过程都算上，这一部分的总复杂度O(min(n的3次方, n * k平方))
		for (int i = 0; i <= Math.min(size[u], k); i++) {
			for (int p = 0; p <= i; p++) {
				dp[u][i] = (int) (dp[u][i] + (long) c(size[u] - p, i - p) * c(arr[u] - arr[fa], i - p) % MOD
						* fac[i - p] % MOD * tmp[p] % MOD) % MOD;
			}
		}
	}

	public static int compute() {
		build();
		dp[0][0] = 1;
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