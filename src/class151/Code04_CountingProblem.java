package class151;

// 序列计数
// 有一个概念叫，最左端最大值位置，表示一段范围上
// 如果最大值有一个，那么最大值所在的位置，就是最左端最大值位置
// 如果最大值有多个，最左的那个所在的位置，就是最左端最大值位置
// 给定一个长度为n的数组A，那么必然存在等长的数组B，当选择同样的子范围时
// 两者在这段范围上，最左端最大值位置是相同的，不仅存在这样的数组B，而且数量无限多
// 现在要求，数组B中的每个值都在[1,m]范围，返回有多少个这样的数组，答案对 1000000007 取模
// 2 <= n、m <= 2 * 10^5    1 <= A[i] <= m    n * m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/CF1748E
// 测试链接 : https://codeforces.com/problemset/problem/1748/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_CountingProblem {

	public static int MOD = 1000000007;

	public static int MAXN = 1000001;

	// 所有数字
	public static int[] arr = new int[MAXN];

	// 笛卡尔树需要
	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] stack = new int[MAXN];

	// tmp是动态规划的临时结果
	public static long[] tmp = new long[MAXN];

	public static int n, m;

	public static void build() {
		for (int i = 1, top = 0, pos; i <= n; i++) {
			pos = top;
			while (pos > 0 && arr[stack[pos]] < arr[i]) {
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

	public static void dfs(int u, int[][] dp) {
		if (u == 0) {
			return;
		}
		dfs(left[u], dp);
		dfs(right[u], dp);
		for (int j = 1; j <= m; j++) {
			tmp[j] = (long) dp[left[u]][j - 1] * dp[right[u]][j] % MOD;
		}
		for (int j = 1; j <= m; j++) {
			dp[u][j] = (int) ((dp[u][j - 1] + tmp[j]) % MOD);
		}
	}

	public static void clear() {
		Arrays.fill(left, 1, n + 1, 0);
		Arrays.fill(right, 1, n + 1, 0);
	}

	public static long compute() {
		build();
		// 虽然n * m <= 10^6，但是n和m，单独都是2 * 10^5规模
		// 所以提前准备固定大小的表是不可能的，空间根本接受不了
		// 所以根据此时具体的n和m的大小，临时申请动态规划表，总大小不超过10^6
		int[][] dp = new int[n + 1][m + 1];
		for (int j = 0; j <= m; j++) {
			// 没有节点时，不管要求节点值是什么，都默认有1种形态
			// 因为没有节点时，根本不影响任何决策
			dp[0][j] = 1;
		}
		dfs(stack[1], dp);
		clear();
		return dp[stack[1]][m];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

}
