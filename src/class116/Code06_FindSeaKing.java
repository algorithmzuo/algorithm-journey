package class116;

import java.util.Arrays;

// 子数组里的海王数
// 子数组的海王数首先必须是子数组上出现次数最多的数(水王数)，并且要求出现次数>=t，t是参数
// 设计一个数据结构并实现如下两个方法
// 1) MajorityChecker(int[] arr) : 用数组arr对MajorityChecker初始化
// 2) int query(int l, int r, int t) : 返回arr[l...r]上的海王数，不存在返回-1
// 测试链接 : https://leetcode.cn/problems/online-majority-element-in-subarray/
public class Code06_FindSeaKing {

	// 本实现只使用固定数组结构
	// 其他语言的同学很容易改写
	// 时间复杂度也保证了是最优
	class MajorityChecker {

		public static int MAXN = 20001;

		public static int[][] nums = new int[MAXN][2];

		// 维护线段树一段范围，候选是谁
		public static int[] cand = new int[MAXN << 2];

		// 维护线段树一段范围，候选血量
		public static int[] hp = new int[MAXN << 2];

		public static int n;

		public MajorityChecker(int[] arr) {
			n = arr.length;
			buildCnt(arr);
			buildTree(arr, 1, n, 1);
		}

		public int query(int l, int r, int t) {
			int[] ch = findCandidate(l + 1, r + 1, 1, n, 1);
			int candidate = ch[0];
			return cnt(l, r, candidate) >= t ? candidate : -1;
		}

		public void buildCnt(int[] arr) {
			for (int i = 0; i < n; i++) {
				nums[i][0] = arr[i];
				nums[i][1] = i;
			}
			Arrays.sort(nums, 0, n, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		}

		public int cnt(int l, int r, int v) {
			return bs(v, r) - bs(v, l - 1);
		}

		// arr[0 ~ i]范围上
		// (<v的数) + (==v但下标<=i的数)，有几个
		public int bs(int v, int i) {
			int left = 0, right = n - 1, mid;
			int find = -1;
			while (left <= right) {
				mid = (left + right) >> 1;
				if (nums[mid][0] < v || (nums[mid][0] == v && nums[mid][1] <= i)) {
					find = mid;
					left = mid + 1;
				} else {
					right = mid - 1;
				}
			}
			return find + 1;
		}

		public void up(int i) {
			int lc = cand[i << 1], lh = hp[i << 1];
			int rc = cand[i << 1 | 1], rh = hp[i << 1 | 1];
			cand[i] = lc == rc || lh >= rh ? lc : rc;
			hp[i] = lc == rc ? (lh + rh) : Math.abs(lh - rh);
		}

		public void buildTree(int[] arr, int l, int r, int i) {
			if (l == r) {
				cand[i] = arr[l - 1];
				hp[i] = 1;
			} else {
				int mid = (l + r) >> 1;
				buildTree(arr, l, mid, i << 1);
				buildTree(arr, mid + 1, r, i << 1 | 1);
				up(i);
			}
		}

		public int[] findCandidate(int jobl, int jobr, int l, int r, int i) {
			if (jobl <= l && r <= jobr) {
				return new int[] { cand[i], hp[i] };
			} else {
				int mid = (l + r) >> 1;
				if (jobr <= mid) {
					return findCandidate(jobl, jobr, l, mid, i << 1);
				}
				if (jobl > mid) {
					return findCandidate(jobl, jobr, mid + 1, r, i << 1 | 1);
				}
				int[] lch = findCandidate(jobl, jobr, l, mid, i << 1);
				int[] rch = findCandidate(jobl, jobr, mid + 1, r, i << 1 | 1);
				int lc = lch[0], lh = lch[1];
				int rc = rch[0], rh = rch[1];
				int c = lc == rc || lh >= rh ? lc : rc;
				int h = lc == rc ? (lh + rh) : Math.abs(lh - rh);
				return new int[] { c, h };
			}
		}
	}

}
