package class085;

// 萌数
// 如果一个数字，存在长度至少为2的回文子串，那么这种数字被称为萌数
// 比如101、110、111、1234321、45568
// 求[l,r]范围上，有多少个萌数
// 由于答案可能很大，所以输出答案对1000000007求余
// 测试链接 : https://www.luogu.com.cn/problem/P3413
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_MengNumber {

	public static int MOD = 1000000007;

	public static int MAXN = 1001;

	public static int[][][][] dp = new int[MAXN][11][11][2];

	public static void build(int n) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b <= 10; b++) {
				for (int c = 0; c <= 10; c++) {
					for (int d = 0; d <= 1; d++) {
						dp[a][b][c][d] = -1;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		String[] strs = br.readLine().split(" ");
		out.println(compute(strs[0].toCharArray(), strs[1].toCharArray()));
		out.flush();
		out.close();
		br.close();
	}

	public static int compute(char[] l, char[] r) {
		int ans = (cnt(r) - cnt(l) + MOD) % MOD;
		if (check(l)) {
			ans = (ans + 1) % MOD;
		}
		return ans;
	}

	// 返回0~num范围上萌数有多少个
	public static int cnt(char[] num) {
		if (num[0] == '0') {
			return 0;
		}
		int n = num.length;
		long all = 0;
		long base = 1;
		for (int i = n - 1; i >= 0; i--) {
			// 不理解的话看一下，讲解041-同余原理
			all = (all + base * (num[i] - '0')) % MOD;
			base = (base * 10) % MOD;
		}
		build(n);
		return (int) ((all - f(num, 0, 10, 10, 0) + MOD) % MOD);
	}

	// 从num的高位开始，当前来到第i位
	// 前一位数字是p、前前一位数字是pp，如果值是10，则表示那一位没有选择过数字
	// 如果之前的位已经确定比num小，那么free == 1，表示接下的数字可以自由选择
	// 如果之前的位和num一样，那么free == 0，表示接下的数字不能大于num当前位的数字
	// 返回<=num且不是萌数的数字有多少个
	public static int f(char[] num, int i, int pp, int p, int free) {
		if (i == num.length) {
			return 1;
		}
		if (dp[i][pp][p][free] != -1) {
			return dp[i][pp][p][free];
		}
		int ans = 0;
		if (free == 0) {
			if (p == 10) {
				// 当前来到的就是num的最高位
				ans = (ans + f(num, i + 1, 10, 10, 1)) % MOD; // 当前位不选数字
				for (int cur = 1; cur < num[i] - '0'; cur++) {
					ans = (ans + f(num, i + 1, p, cur, 1)) % MOD;
				}
				ans = (ans + f(num, i + 1, p, num[i] - '0', 0)) % MOD;
			} else {
				// free == 0，之前和num一样，此时不能自由选择数字
				// 前一位p，选择过数字，p -> 0 ~ 9
				for (int cur = 0; cur < num[i] - '0'; cur++) {
					if (pp != cur && p != cur) {
						ans = (ans + f(num, i + 1, p, cur, 1)) % MOD;
					}
				}
				if (pp != num[i] - '0' && p != num[i] - '0') {
					ans = (ans + f(num, i + 1, p, num[i] - '0', 0)) % MOD;
				}
			}
		} else {
			if (p == 10) {
				// free == 1，能自由选择数字
				// 从来没选过数字
				ans = (ans + f(num, i + 1, 10, 10, 1)) % MOD; // 依然不选数字
				for (int cur = 1; cur <= 9; cur++) {
					ans = (ans + f(num, i + 1, p, cur, 1)) % MOD;
				}
			} else {
				// free == 1，能自由选择数字
				// 之前选择过数字
				for (int cur = 0; cur <= 9; cur++) {
					if (pp != cur && p != cur) {
						ans = (ans + f(num, i + 1, p, cur, 1)) % MOD;
					}
				}
			}
		}
		dp[i][pp][p][free] = ans;
		return ans;
	}

	public static boolean check(char[] num) {
		for (int pp = -2, p = -1, i = 0; i < num.length; pp++, p++, i++) {
			if (pp >= 0 && num[pp] == num[i]) {
				return true;
			}
			if (p >= 0 && num[p] == num[i]) {
				return true;
			}
		}
		return false;
	}

}