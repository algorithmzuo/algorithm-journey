package class093;

// 跳跃游戏II
// 给定一个长度为n的整数数组nums
// 你初始在0下标，nums[i]表示你可以从i下标往右跳的最大距离
// 比如，nums[0] = 3
// 表示你可以从0下标去往：1下标、2下标、3下标
// 你达到i下标后，可以根据nums[i]的值继续往右跳
// 返回你到达n-1下标的最少跳跃次数
// 测试用例可以保证一定能到达
// 测试链接 : https://leetcode.cn/problems/jump-game-ii/
public class Code01_JumpGameII {

	public static int jump(int[] arr) {
		int n = arr.length;
		// 当前步以内，最右到哪
		int cur = 0;
		// 如果再一步，(当前步+1)以内，最右到哪
		int next = 0;
		// 一共需要跳几步
		int ans = 0;
		for (int i = 0; i < n; i++) {
			// 来到i下标
			// cur包括了i所在的位置，不用付出额外步数
			// cur没有包括i所在的位置，需要付出额外步数
			if (cur < i) {
				ans++;
				cur = next;
			}
			next = Math.max(next, i + arr[i]);
		}
		return ans;
	}

}
