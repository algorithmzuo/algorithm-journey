package class053;

import java.util.Arrays;

// 统计全1子矩形的数量
// 给你一个 m * n 的矩阵 mat，其中只有0和1两种值
// 请你返回有多少个 子矩形 的元素全部都是1
// 测试链接 : https://leetcode.cn/problems/count-submatrices-with-all-ones/
public class Code04_CountSubmatricesWithAllOnes {

	public static int MAXM = 151;

	public static int[] height = new int[MAXM];

	public static int[] stack = new int[MAXM];

	public static int r;

	public static int numSubmat(int[][] mat) {
		int n = mat.length;
		int m = mat[0].length;
		int ans = 0;
		Arrays.fill(height, 0, m, 0);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
			}
			ans += countFromBottom(m);
		}
		return ans;
	}

	// 比如
	//              1
	//              1
	//              1         1
	//    1         1         1
	//    1         1         1
	//    1         1         1
	//             
	//    2  ....   6   ....  9
	// 如上图，假设在6位置，1的高度为6
	// 在6位置的左边，离6位置最近、且小于高度6的位置是2，2位置的高度是3
	// 在6位置的右边，离6位置最近、且小于高度6的位置是9，9位置的高度是4
	// 此时我们求什么？
	// 1) 求在3~8范围上，必须以高度6作为高的矩形，有几个？
	// 2) 求在3~8范围上，必须以高度5作为高的矩形，有几个？
	// 也就是说，<=4的高度，一律不求
	// 那么，1) 求必须以位置6的高度6作为高的矩形，有几个？
	// 3..3  3..4  3..5  3..6  3..7  3..8
	// 4..4  4..5  4..6  4..7  4..8
	// 5..5  5..6  5..7  5..8
	// 6..6  6..7  6..8
	// 7..7  7..8
	// 8..8
	// 这么多！= 21 = (9 - 2 - 1) * (9 - 2) / 2
	// 这就是任何一个数字从栈里弹出的时候，计算矩形数量的方式
	public static int countFromBottom(int m) {
		int ans = 0;
		r = 0;
		for (int i = 0, left, len, down; i < m; i++) {
			while (r > 0 && height[stack[r - 1]] >= height[i]) {
				int cur = stack[--r];
				if (height[cur] > height[i]) {
					left = r == 0 ? -1 : stack[r - 1];
					len = i - left - 1;
					down = Math.max(left == -1 ? 0 : height[left], height[i]);
					ans += (height[cur] - down) * num(len);
				}
			}
			stack[r++] = i;
		}
		while (r > 0) {
			int cur = stack[--r];
			int left = r == 0 ? -1 : stack[r - 1];
			int len = m - left - 1;
			int down = left == -1 ? 0 : height[left];
			ans += (height[cur] - down) * num(len);
		}
		return ans;
	}

	public static int num(int len) {
		return ((len * (1 + len)) >> 1);
	}

}
