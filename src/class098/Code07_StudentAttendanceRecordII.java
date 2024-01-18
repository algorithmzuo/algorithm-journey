package class098;

// 学生出勤记录II
// 可以用字符串表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）
// 记录中只含下面三种字符：
// 'A'：Absent，缺勤
// 'L'：Late，迟到
// 'P'：Present，到场
// 如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：
// 按 总出勤 计，学生缺勤（'A'）严格 少于两天
// 学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。
// 给你一个整数n，表示出勤记录的长度（次数）
// 请你返回记录长度为n时，可能获得出勤奖励的记录情况数量
// 答案可能很大，结果对1000000007取模
// 测试链接 : https://leetcode.cn/problems/student-attendance-record-ii/
public class Code07_StudentAttendanceRecordII {

	// 正式方法
	// 矩阵快速幂
	// 时间复杂度O(logn)
	public static int MOD = 1000000007;

	public static int checkRecord(int n) {
		// 1天的情况下，各种状态的合法数量
		int[][] start = { { 1, 1, 0, 1, 0, 0 } };
		int[][] base = {
				{ 1, 1, 0, 1, 0, 0 },
				{ 1, 0, 1, 1, 0, 0 },
				{ 1, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 1, 1, 0 },
				{ 0, 0, 0, 1, 0, 1 },
				{ 0, 0, 0, 1, 0, 0 }
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
