package class040;

// N皇后问题
// 测试链接 : https://leetcode.cn/problems/n-queens-ii/
public class WaysOfNQueens {

	public static int totalNQueens1(int n) {
		if (n < 1) {
			return 0;
		}
		return f1(0, new int[n], n);
	}

	public static int f1(int i, int[] record, int n) {
		if (i == n) {
			return 1;
		}
		int res = 0;
		for (int j = 0; j < n; j++) {
			if (isValid(record, i, j)) {
				record[i] = j;
				res += f1(i + 1, record, n);
			}
		}
		return res;
	}

	public static boolean isValid(int[] record, int i, int j) {
		for (int k = 0; k < i; k++) {
			if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
				return false;
			}
		}
		return true;
	}

	public static int totalNQueens2(int n) {
		if (n < 1) {
			return 0;
		}
		int limit = n == 32 ? -1 : (1 << n) - 1;
		return f2(limit, 0, 0, 0);
	}

	// 之前皇后的列影响：col
	// 之前皇后的左下对角线影响：left
	// 之前皇后的右下对角线影响：right
	public static int f2(int limit, int col, int left, int right) {
		if (col == limit) {
			return 1;
		}
		int pos = limit & (~(col | left | right));
		int rightOne = 0;
		int ans = 0;
		while (pos != 0) {
			rightOne = pos & (~pos + 1);
			pos = pos - rightOne;
			ans += f2(limit, col | rightOne, (left | rightOne) << 1, (right | rightOne) >>> 1);
		}
		return ans;
	}

	public static void main(String[] args) {
		int n = 14;
		long start, end;
		start = System.currentTimeMillis();
		System.out.println("方法1答案 : " + totalNQueens1(n));
		end = System.currentTimeMillis();
		System.out.println("方法1运行时间 : " + (end - start) + " 毫秒");

		start = System.currentTimeMillis();
		System.out.println("方法2答案 : " + totalNQueens2(n));
		end = System.currentTimeMillis();
		System.out.println("方法2运行时间 : " + (end - start) + " 毫秒");
	}

}
