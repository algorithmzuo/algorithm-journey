package class098;

// 统计元音字母序列的数目
// 给你一个整数n，请你帮忙统计一下我们可以按下述规则形成多少个长度为n的字符串：
// 字符串中的每个字符都应当是小写元音字母（'a', 'e', 'i', 'o', 'u'）
// 每个元音 'a' 后面都只能跟着 'e'
// 每个元音 'e' 后面只能跟着 'a' 或者是 'i'
// 每个元音 'i' 后面 不能 再跟着另一个 'i'
// 每个元音 'o' 后面只能跟着 'i' 或者是 'u'
// 每个元音 'u' 后面只能跟着 'a'
// 由于答案可能会很大，结果对1000000007取模
// 测试链接 : https://leetcode.cn/problems/count-vowels-permutation/
public class Code06_CountVowelsPermutation {

	// 正式方法
	// 矩阵快速幂
	// 时间复杂度O(logn)
	public static int MOD = 1000000007;

	public static int countVowelPermutation(int n) {
		// 长度为1的时候，以a、e、i、o、u结尾的合法数量
		int[][] start = { { 1, 1, 1, 1, 1 } };
		int[][] base = {
				{ 0, 1, 0, 0, 0 },
				{ 1, 0, 1, 0, 0 },
				{ 1, 1, 0, 1, 1 },
				{ 0, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 0 }
				};
		int[][] ans = multiply(start, power(base, n - 1));
		int ret = 0;
		for (int a : ans[0]) {
			ret = (ret + a) % MOD;
		}
		return ret;
	}

	// 矩阵相乘 + 乘法取模
	// a的列数一定要等于b的行数
	public static int[][] multiply(int[][] a, int[][] b) {
		int n = a.length;
		int m = b[0].length;
		int k = a[0].length;
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int c = 0; c < k; c++) {
					ans[i][j] = (int) (((long) a[i][c] * b[c][j] + ans[i][j]) % MOD);
				}
			}
		}
		return ans;
	}

	// 矩阵快速幂
	public static int[][] power(int[][] m, int p) {
		int n = m.length;
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++) {
			ans[i][i] = 1;
		}
		for (; p != 0; p >>= 1) {
			if ((p & 1) != 0) {
				ans = multiply(ans, m);
			}
			m = multiply(m, m);
		}
		return ans;
	}

}
