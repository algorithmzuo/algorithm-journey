package class040;

// N皇后问题
// 测试链接 : https://leetcode.cn/problems/n-queens-ii/
public class NQueens {

	// 用数组表示路径实现的N皇后问题，不推荐
	public static int totalNQueens1(int n) {
		if (n < 1) {
			return 0;
		}
		return f1(0, new int[n], n);
	}

	public static int f1(int i, int[] path, int n) {
		if (i == n) {
			return 1;
		}
		int res = 0;
		for (int j = 0; j < n; j++) {
			if (check(path, i, j)) {
				path[i] = j;
				res += f1(i + 1, path, n);
			}
		}
		return res;
	}

	public static boolean check(int[] path, int i, int j) {
		for (int k = 0; k < i; k++) {
			if (j == path[k] || Math.abs(i - k) == Math.abs(path[k] - j)) {
				return false;
			}
		}
		return true;
	}

	// 用位信息表示路径实现的N皇后问题，推荐
	public static int totalNQueens2(int n) {
		if (n < 1) {
			return 0;
		}
		int limit = (1 << n) - 1;
		return f2(limit, 0, 0, 0);
	}

	// 之前皇后的列影响：col
	// 之前皇后的左下对角线影响：left
	// 之前皇后的右下对角线影响：right
	public static int f2(int limit, int col, int left, int right) {
		if (col == limit) {
			return 1;
		}
		int ban = col | left | right;
		int candidate = limit & (~ban);
		int place = 0;
		int ans = 0;
		while (candidate != 0) {
			place = candidate & (-candidate);
			candidate ^= place;
			ans += f2(limit, col | place, (left | place) << 1, (right | place) >>> 1);
		}
		return ans;
	}

	public static void main(String[] args) {
		int n = 14;
		long start, end;
		System.out.println("测试开始");
		start = System.currentTimeMillis();
		System.out.println("方法1答案 : " + totalNQueens1(n));
		end = System.currentTimeMillis();
		System.out.println("方法1运行时间 : " + (end - start) + " 毫秒");

		start = System.currentTimeMillis();
		System.out.println("方法2答案 : " + totalNQueens2(n));
		end = System.currentTimeMillis();
		System.out.println("方法2运行时间 : " + (end - start) + " 毫秒");
		System.out.println("测试结束");
	}

}
