package class048;

import java.util.Arrays;

// 最强祝福力场
// 小扣在探索丛林的过程中，无意间发现了传说中"落寞的黄金之都"
// 而在这片建筑废墟的地带中，小扣使用探测仪监测到了存在某种带有「祝福」效果的力场
// 经过不断的勘测记录，小扣将所有力场的分布都记录了下来
// forceField[i] = [x,y,side] 
// 表示第 i 片力场将覆盖以坐标 (x,y) 为中心，边长为 side 的正方形区域。
// 若任意一点的 力场强度 等于覆盖该点的力场数量
// 请求出在这片地带中 力场强度 最强处的 力场强度
// 注意：力场范围的边缘同样被力场覆盖。
// 测试链接 : https://leetcode.cn/problems/xepqZ5/
public class Code05_StrongestForceField {

	// 时间复杂度O(n^2)，额外空间复杂度O(n^2)，n是力场的个数
	public static int fieldOfGreatestBlessing(int[][] fields) {
		int n = fields.length;
		// n : 矩形的个数，x 2*n个坐标
		long[] xs = new long[n << 1];
		long[] ys = new long[n << 1];
		for (int i = 0, k = 0, p = 0; i < n; i++) {
			long x = fields[i][0];
			long y = fields[i][1];
			long r = fields[i][2];
			xs[k++] = (x << 1) - r;
			xs[k++] = (x << 1) + r;
			ys[p++] = (y << 1) - r;
			ys[p++] = (y << 1) + r;
		}
		// xs数组中，排序了且相同值只留一份，返回有效长度
		int sizex = sort(xs);
		// ys数组中，排序了且相同值只留一份，返回有效长度
		int sizey = sort(ys);
		// n个力场，sizex : 2 * n, sizey : 2 * n
		int[][] diff = new int[sizex + 2][sizey + 2];
		for (int i = 0, a, b, c, d; i < n; i++) {
			long x = fields[i][0];
			long y = fields[i][1];
			long r = fields[i][2];
			a = rank(xs, (x << 1) - r, sizex);
			b = rank(ys, (y << 1) - r, sizey);
			c = rank(xs, (x << 1) + r, sizex);
			d = rank(ys, (y << 1) + r, sizey);
			add(diff, a, b, c, d);
		}
		int ans = 0;
		// O(n^2)
		for (int i = 1; i < diff.length; i++) {
			for (int j = 1; j < diff[0].length; j++) {
				diff[i][j] += diff[i - 1][j] + diff[i][j - 1] - diff[i - 1][j - 1];
				ans = Math.max(ans, diff[i][j]);
			}
		}
		return ans;
	}

	// [50,70,30,70,30,60] 长度6
	// [30,30,50,60,70,70]
	// [30,50,60,70] 60 -> 3
	//  1  2  3  4
	// 长度4，
 	public static int sort(long[] nums) {
		Arrays.sort(nums);
		int size = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != nums[size - 1]) {
				nums[size++] = nums[i];
			}
		}
		return size;
	}

 	// nums 有序数组，有效长度是size，0~size-1范围上无重复值
 	// 已知v一定在nums[0~size-1]，返回v所对应的编号
	public static int rank(long[] nums, long v, int size) {
		int l = 0;
		int r = size - 1;
		int m, ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (nums[m] >= v) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans + 1;
	}

	// 二维差分
	public static void add(int[][] diff, int a, int b, int c, int d) {
		diff[a][b] += 1;
		diff[c + 1][d + 1] += 1;
		diff[c + 1][b] -= 1;
		diff[a][d + 1] -= 1;
	}

}
