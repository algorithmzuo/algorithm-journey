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

	public static int MAXC = 10;

	public static char[] num;

	public static int n;

	public static int[][] tree = new int[MAXS][MAXC];

	public static int[] fail = new int[MAXS];

	public static boolean[] wordEnd = new boolean[MAXS];

	public static int tot = 0;

	public static int[] queue = new int[MAXS];

	public static int[][][][] dp = new int[MAXN][MAXS][2][2];

	public static void build() {
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
		for (int i = 0; i < MAXC; i++) {
			if (tree[0][i] > 0) {
				queue[r++] = tree[0][i];
			}
		}
		while (l < r) {
			int u = queue[l++];
			wordEnd[u] |= wordEnd[fail[u]];
			for (int i = 0; i < MAXC; i++) {
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
		// ac自动机建树
		int m = Integer.valueOf(in.readLine());
		for (int i = 1; i <= m; i++) {
			insert(in.readLine());
		}
		setFail();
		// 清空动态规划表
		build();
		// 执行记忆化搜索
		out.println(f(0, 0, 0, 0));
		out.flush();
		out.close();
		in.close();
	}

	public static int f(int i, int u, int free, int has) {
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
			ans = (ans + f(i + 1,
					has == 0 && pick == 0 ? 0 : tree[u][pick], // 这一句很重要
					free == 0 && pick == limit ? 0 : 1,
					has == 0 && pick == 0 ? 0 : 1)) % MOD;
		}
		dp[i][u][free][has] = ans;
		return ans;
	}

}