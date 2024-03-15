package class111;

import java.util.Arrays;

// 查询一段范围上的水王数
// 设计一个数据结构，找到给定子数组的水王数
// 子数组的水王数首先必须是该子数组上出现次数最多的数
// 其次出现次数要大于等于t，t是给定的要求
// 实现MajorityChecker类及其如下方法
// 1) MajorityChecker(int[] arr)
//   用数组arr对MajorityChecker初始化
// 2) int query(int l, int r, int t) 
//   返回子数组arr[l...r]上的水王数，t是给定的要求
//   如果没有满足条件的水王数，返回-1
// 测试链接 : https://leetcode.cn/problems/online-majority-element-in-subarray/
public class Code03_WaterKing3 {

	// 用java语言自带的动态数组可以让常数时间更快
	// 但是其他语言的同学改写难度就大了
	// 所以本实现不用任何动态结构
	// 就用所有语言都有的简单数组实现
	// 这样所有语言的同学都能看懂
	// 时间复杂度也保证了是最优的
	class MajorityChecker {

		public int n;

		public int[][] nums;

		public int[] cand;

		public int[] hp;

		public MajorityChecker(int[] arr) {
			n = arr.length;
			nums = new int[n][2];
			buildCnt(arr, n);
			cand = new int[n << 2];
			hp = new int[n << 2];
			buildTree(arr, 1, n, 1);
		}

		public int query(int l, int r, int t) {
			query(l + 1, r + 1, 1, n, 1);
			int candidate = c;
			return cnt(l, r, candidate) >= t ? candidate : -1;
		}

		private void buildCnt(int[] arr, int n) {
			for (int i = 0; i < n; i++) {
				nums[i][0] = arr[i];
				nums[i][1] = i;
			}
			Arrays.sort(nums, 0, n, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		}

		private int cnt(int l, int r, int v) {
			return bs(v, r) - bs(v, l - 1);
		}

		private int bs(int v, int i) {
			int left = 0, right = n - 1, mid;
			int find = -1;
			while (left <= right) {
				mid = (left + right) / 2;
				if (nums[mid][0] < v || (nums[mid][0] == v && nums[mid][1] <= i)) {
					find = mid;
					left = mid + 1;
				} else {
					right = mid - 1;
				}
			}
			return find + 1;
		}

		private void buildTree(int[] arr, int l, int r, int rt) {
			if (l == r) {
				cand[rt] = arr[l - 1];
				hp[rt] = 1;
			} else {
				int mid = (l + r) / 2;
				buildTree(arr, l, mid, rt << 1);
				buildTree(arr, mid + 1, r, rt << 1 | 1);
				int lc = cand[rt << 1], rc = cand[rt << 1 | 1];
				int lh = hp[rt << 1], rh = hp[rt << 1 | 1];
				if (lc == rc) {
					cand[rt] = lc;
					hp[rt] = lh + rh;
				} else {
					cand[rt] = lh >= rh ? lc : rc;
					hp[rt] = Math.abs(lh - rh);
				}
			}
		}

		private static int c, h;

		private void query(int jobl, int jor, int l, int r, int rt) {
			if (jobl <= l && r <= jor) {
				c = cand[rt];
				h = hp[rt];
			} else {
				int mid = (l + r) / 2;
				if (jor <= mid) {
					query(jobl, jor, l, mid, rt << 1);
				} else if (jobl > mid) {
					query(jobl, jor, mid + 1, r, rt << 1 | 1);
				} else {
					query(jobl, jor, l, mid, rt << 1);
					int lc = c, lh = h;
					query(jobl, jor, mid + 1, r, rt << 1 | 1);
					int rc = c, rh = h;
					if (lc == rc) {
						c = lc;
						h = lh + rh;
					} else {
						if (lh >= rh) {
							c = lc;
							h = lh - rh;
						} else {
							c = rc;
							h = rh - lh;
						}
					}
				}
			}
		}
	}

}
