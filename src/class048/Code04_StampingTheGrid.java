package class048;

// 用邮票贴满网格图
// 给你一个 m * n 的二进制矩阵 grid
// 每个格子要么为 0 （空）要么为 1 （被占据）
// 给你邮票的尺寸为 stampHeight * stampWidth
// 我们想将邮票贴进二进制矩阵中，且满足以下 限制 和 要求 ：
// 覆盖所有空格子，不覆盖任何被占据的格子
// 可以放入任意数目的邮票，邮票可以相互有重叠部分
// 邮票不允许旋转，邮票必须完全在矩阵内
// 如果在满足上述要求的前提下，可以放入邮票，请返回 true ，否则返回 false
// 测试链接 : https://leetcode.cn/problems/stamping-the-grid/
public class Code04_StampingTheGrid {

	// 时间复杂度O(n*m)，额外空间复杂度O(n*m)
	public static boolean possibleToStamp(int[][] grid, int h, int w) {
		int n = grid.length;
		int m = grid[0].length;
		// sum是前缀和数组
		// 查询原始矩阵中的某个范围的累加和很快速
		int[][] sum = new int[n + 1][m + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				sum[i + 1][j + 1] = grid[i][j];
			}
		}
		build(sum);
		// 差分矩阵
		// 当贴邮票的时候，不再原始矩阵里贴，在差分矩阵里贴
		// 原始矩阵就用来判断能不能贴邮票，不进行修改
		// 每贴一张邮票都在差分矩阵里修改
		int[][] diff = new int[n + 2][m + 2];
		for (int a = 1, c = a + h - 1; c <= n; a++, c++) {
			for (int b = 1, d = b + w - 1; d <= m; b++, d++) {
				// 原始矩阵中 (a,b)左上角点
				// 根据邮票规格，h、w，算出右下角点(c,d)
				// 这个区域彻底都是0，那么: 
				// sumRegion(sum, a, b, c, d) == 0
				// 那么此时这个区域可以贴邮票
				if (sumRegion(sum, a, b, c, d) == 0) {
					add(diff, a, b, c, d);
				}
			}
		}
		build(diff);
		// 检查所有的格子！
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// 原始矩阵里：grid[i][j] == 0，说明是个洞
				// 差分矩阵里：diff[i + 1][j + 1] == 0，说明洞上并没有邮票
				// 此时返回false
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

	public static int sumRegion(int[][] sum, int a, int b, int c, int d) {
		return sum[c][d] - sum[c][b - 1] - sum[a - 1][d] + sum[a - 1][b - 1];
	}

	public static void add(int[][] diff, int a, int b, int c, int d) {
		diff[a][b] += 1;
		diff[c + 1][d + 1] += 1;
		diff[c + 1][b] -= 1;
		diff[a][d + 1] -= 1;
	}

}