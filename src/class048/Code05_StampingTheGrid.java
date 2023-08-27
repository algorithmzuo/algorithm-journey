package class048;

// 用邮票贴满网格图
// 给你一个 m x n 的二进制矩阵 grid
// 每个格子要么为 0 （空）要么为 1 （被占据）
// 给你邮票的尺寸为 stampHeight * stampWidth
// 我们想将邮票贴进二进制矩阵中，且满足以下 限制 和 要求 ：
// 覆盖所有空格子，不覆盖任何被占据的格子
// 可以放入任意数目的邮票，邮票可以相互有重叠部分
// 邮票不允许旋转，邮票必须完全在矩阵内
// 如果在满足上述要求的前提下，可以放入邮票，请返回 true ，否则返回 false
// 测试链接 : https://leetcode.cn/problems/stamping-the-grid/
public class Code05_StampingTheGrid {

	public static boolean possibleToStamp(int[][] grid, int h, int w) {
		int n = grid.length;
		int m = grid[0].length;
		// 左上角累加和数组
		// 查询原始矩阵中的某块儿累加和，快！
		int[][] sum = new int[n + 1][m + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				sum[i + 1][j + 1] = grid[i][j];
			}
		}
		build(sum);
		// 差分矩阵
		// 当贴邮票的时候，不再原始矩阵里贴
		// 在差分里贴
		int[][] diff = new int[n + 2][m + 2];
		for (int a = 1, c = a + h - 1; c <= n; a++, c++) {
			for (int b = 1, d = b + w - 1; d <= m; b++, d++) {
				// (a,b) (c,d)
				if (empty(sum, a, b, c, d)) {
					set(diff, a, b, c, d);
				}
			}
		}
		build(diff);
		// 检查所有的格子！
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 0 && diff[i + 1][j + 1] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static void build(int[][] m) {
		for (int i = 1; i < m.length; i++) {
			for (int j = 1; j < m[0].length; j++) {
				m[i][j] += m[i - 1][j] + m[i][j - 1] - m[i - 1][j - 1];
			}
		}
	}

	public static boolean empty(int[][] sum, int a, int b, int c, int d) {
		return sum[c][d] - sum[c][b - 1] - sum[a - 1][d] + sum[a - 1][b - 1] == 0;
	}

	public static void set(int[][] diff, int a, int b, int c, int d) {
		diff[a][b] += 1;
		diff[c + 1][d + 1] += 1;
		diff[c + 1][b] -= 1;
		diff[a][d + 1] -= 1;
	}

}