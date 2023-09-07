package class055;

// 满足不等式的最大值
// 给你一个数组 points 和一个整数 k
// 数组中每个元素都表示二维平面上的点的坐标，并按照横坐标 x 的值从小到大排序
// 也就是说 points[i] = [xi, yi]
// 并且在 1 <= i < j <= points.length 的前提下，xi < xj 总成立
// 请你找出 yi + yj + |xi - xj| 的 最大值，
// 其中 |xi - xj| <= k 且 1 <= i < j <= points.length
// 题目测试数据保证至少存在一对能够满足 |xi - xj| <= k 的点。
// 测试链接 : https://leetcode.cn/problems/max-value-of-equation/
public class Code02_MaxValueOfEquation {

	public static int MAXN = 100001;

	public static int[][] deque = new int[MAXN][2];

	public static int l, r;

	public static int findMaxValueOfEquation(int[][] points, int k) {
		int n = points.length;
		l = r = 0;
		int ans = Integer.MIN_VALUE;
		for (int j = 0, x, y; j < n; j++) {
			x = points[j][0];
			y = points[j][1];
			while (l < r && deque[l][0] + k < x) {
				l++;
			}
			if (l < r) {
				ans = Math.max(ans, y + deque[l][1] + x - deque[l][0]);
			}
			while (l < r && deque[r - 1][1] - deque[r - 1][0] <= y - x) {
				r--;
			}
			deque[r][0] = x;
			deque[r++][1] = y;
		}
		return ans;
	}

}
