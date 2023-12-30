package class092;

// 将数组分成几个递增序列
// 给你一个有序的正数数组 nums 和整数 K
// 判断该数组是否可以被分成一个或几个 长度至少 为 K 的 不相交的递增子序列
// 数组中的所有数字，都要被，若干不相交的递增子序列包含
// 测试链接 : https://leetcode.cn/problems/divide-array-into-increasing-sequences/
public class Code05_DivideArrayIncreasingSequences {

	public static boolean canDivideIntoSubsequences(int[] nums, int k) {
		int cnt = 1;
		// maxCnt : 最大词频
		int maxCnt = 1;
		// 在有序数组中，求某个数的最大词频
		for (int i = 1; i < nums.length; i++) {
			if (nums[i - 1] != nums[i]) {
				maxCnt = Math.max(maxCnt, cnt);
				cnt = 1;
			} else {
				cnt++;
			}
		}
		maxCnt = Math.max(maxCnt, cnt);
		// 向下取整如果满足 >= k
		// 那么所有的递增子序列长度一定 >= k
		return nums.length / maxCnt >= k;
	}

}
