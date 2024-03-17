package class109;

import java.util.Arrays;

// 最长递增子序列的个数
// 给定一个未排序的整数数组nums，返回最长递增子序列的个数
// 测试链接 : https://leetcode.cn/problems/number-of-longest-increasing-subsequence/
// 本题在讲解072，最长递增子序列问题与扩展，就做出过预告
// 具体可以看讲解072视频最后的部分
// 用树状数组实现时间复杂度O(n * logn)
public class Code03_NumberOfLIS {

	public static int MAXN = 2001;

	public static int[] sort = new int[MAXN];

	// 维护信息 : 以数值i结尾的最长递增子序列，长度是多少
	// 维护的信息以树状数组组织
	public static int[] treeMaxLen = new int[MAXN];

	// 维护信息 : 以数值i结尾的最长递增子序列，个数是多少
	// 维护的信息以树状数组组织
	public static int[] treeMaxLenCnt = new int[MAXN];

	public static int m;

	public static int lowbit(int i) {
		return i & -i;
	}

	// 查询结尾数值<=i的最长递增子序列的长度，赋值给maxLen
	// 查询结尾数值<=i的最长递增子序列的个数，赋值给maxLenCnt
	public static int maxLen, maxLenCnt;

	public static void query(int i) {
		maxLen = maxLenCnt = 0;
		while (i > 0) {
			if (maxLen == treeMaxLen[i]) {
				maxLenCnt += treeMaxLenCnt[i];
			} else if (maxLen < treeMaxLen[i]) {
				maxLen = treeMaxLen[i];
				maxLenCnt = treeMaxLenCnt[i];
			}
			i -= lowbit(i);
		}
	}

	// 以数值i结尾的最长递增子序列，长度达到了len，个数增加了cnt
	// 更新树状数组
	public static void add(int i, int len, int cnt) {
		while (i <= m) {
			if (treeMaxLen[i] == len) {
				treeMaxLenCnt[i] += cnt;
			} else if (treeMaxLen[i] < len) {
				treeMaxLen[i] = len;
				treeMaxLenCnt[i] = cnt;
			}
			i += lowbit(i);
		}
	}

	public static int findNumberOfLIS(int[] nums) {
		int n = nums.length;
		for (int i = 1; i <= n; i++) {
			sort[i] = nums[i - 1];
		}
		Arrays.sort(sort, 1, n + 1);
		m = 1;
		for (int i = 2; i <= n; i++) {
			if (sort[m] != sort[i]) {
				sort[++m] = sort[i];
			}
		}
		Arrays.fill(treeMaxLen, 1, m + 1, 0);
		Arrays.fill(treeMaxLenCnt, 1, m + 1, 0);
		int i;
		for (int num : nums) {
			i = rank(num);
			query(i - 1);
			if (maxLen == 0) {
				// 如果查出数值<=i-1结尾的最长递增子序列长度为0
				// 那么说明，以值i结尾的最长递增子序列长度就是1，计数增加1
				add(i, 1, 1);
			} else {
				// 如果查出数值<=i-1结尾的最长递增子序列长度为maxLen != 0
				// 那么说明，以值i结尾的最长递增子序列长度就是maxLen + 1，计数增加maxLenCnt
				add(i, maxLen + 1, maxLenCnt);
			}
		}
		query(m);
		return maxLenCnt;
	}

	public static int rank(int v) {
		int ans = 0;
		int l = 1, r = m, mid;
		while (l <= r) {
			mid = (l + r) / 2;
			if (sort[mid] >= v) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

}
