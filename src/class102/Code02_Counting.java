package class102;

// 数数
// 我们称一个正整数x为幸运数字的条件为
// x的十进制中不包含数字串集合s中任意一个元素作为子串
// 例如s = { 22, 333, 0233 }
// 233是幸运数字，2333、20233、3223不是幸运数字
// 给定n和s，计算不大于n的幸运数字的个数
// 答案对1000000007取模
// 测试链接 : https://www.luogu.com.cn/problem/P3311
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_Counting {

	public static int MOD = 1000000007;

	public static int MAXN = 1301;

	public static int MAXS = 2001;

	public static char[] num;

	public static int n;

	public static int[][] tree = new int[MAXS][10];

	public static int[] fail = new int[MAXS];

	public static boolean[] wordEnd = new boolean[MAXS];

	public static int tot = 0;

	public static int[] queue = new int[MAXS];

	public static int[][][][] dp = new int[MAXN][MAXS][2][2];

	public static void clear() {
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= tot; j++) {
				dp[i][j][0][0] = -1;
				dp[i][j][0][1] = -1;
				dp[i][j][1][0] = -1;
				dp[i][j][1][1] = -1;
			}
		}
	}

	public static void insert(String word) {
		char[] w = word.toCharArray();
		int u = 0;
		for (int j = 0, c; j < w.length; j++) {
			c = w[j] - '0';
			if (tree[u][c] == 0) {
				tree[u][c] = ++tot;
			}
			u = tree[u][c];
		}
		wordEnd[u] = true;
	}

	public static void setFail() {
		int l = 0;
		int r = 0;
		for (int i = 0; i <= 9; i++) {
			if (tree[0][i] > 0) {
				queue[r++] = tree[0][i];
			}
		}
		while (l < r) {
			int u = queue[l++];
			wordEnd[u] |= wordEnd[fail[u]];
			for (int i = 0; i <= 9; i++) {
				if (tree[u][i] == 0) {
					tree[u][i] = tree[fail[u]][i];
				} else {
					fail[tree[u][i]] = tree[fail[u]][i];
					queue[r++] = tree[u][i];
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		num = in.readLine().toCharArray();
		n = num.length;
		// AC自动机建树
		int m = Integer.valueOf(in.readLine());
		for (int i = 1; i <= m; i++) {
			insert(in.readLine());
		}
		setFail();
		// 清空动态规划表
		clear();
		// 执行记忆化搜索
		out.println(f1(0, 0, 0, 0));
		// out.println(f2(0, 0, 0, 0));
		out.flush();
		out.close();
		in.close();
	}

	// 逻辑分支都详细列出来的版本
	public static int f1(int i, int j, int free, int has) {
		if (wordEnd[j]) {
			return 0;
		}
		if (i == n) {
			return has;
		}
		if (dp[i][j][free][has] != -1) {
			return dp[i][j][free][has];
		}
		int ans = 0;
		int cur = num[i] - '0';
		if (has == 0) {
			if (free == 0) {
				// 分支1 : 之前没有选择过数字 且 之前的决策等于num的前缀
				// 能来到这里说明i一定是0位置, 那么cur必然不是0
				// 当前选择不要数字
				ans = (ans + f1(i + 1, 0, 1, 0)) % MOD;
				// 当前选择的数字比cur小
				for (int pick = 1; pick < cur; pick++) {
					ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
				}
				// 当前选择的数字为cur
				ans = (ans + f1(i + 1, tree[j][cur], 0, 1)) % MOD;
			} else {
				// 分支2 : 之前没有选择过数字 且 之前的决策小于num的前缀
				// 当前选择不要数字
				ans = (ans + f1(i + 1, 0, 1, 0)) % MOD;
				// 当前可以选择1~9
				for (int pick = 1; pick <= 9; pick++) {
					ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
				}
			}
		} else {
			if (free == 0) {
				// 分支3 : 之前已经选择过数字 且 之前的决策等于num的前缀
				// 当前选择的数字比cur小
				for (int pick = 0; pick < cur; pick++) {
					ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
				}
				// 当前选择的数字为cur
				ans = (ans + f1(i + 1, tree[j][cur], 0, 1)) % MOD;
			} else {
				// 分支4 : 之前已经选择过数字 且 之前的决策小于num的前缀
				// 当前可以选择0~9
				for (int pick = 0; pick <= 9; pick++) {
					ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
				}
			}
		}
		dp[i][j][free][has] = ans;
		return ans;
	}

	// 逻辑合并版
	// 其实和f1方法完全一个意思
	public static int f2(int i, int u, int free, int has) {
		if (wordEnd[u]) {
			return 0;
		}
		if (i == n) {
			return has;
		}
		if (dp[i][u][free][has] != -1) {
			return dp[i][u][free][has];
		}
		int limit = free == 0 ? (num[i] - '0') : 9;
		int ans = 0;
		for (int pick = 0; pick <= limit; pick++) {
			ans = (ans + f2(i + 1, has == 0 && pick == 0 ? 0 : tree[u][pick], free == 0 && pick == limit ? 0 : 1,
					has == 0 && pick == 0 ? 0 : 1)) % MOD;
		}
		dp[i][u][free][has] = ans;
		return ans;
	}

}