package class092;

// 跳跃游戏II
// 给定一个长度为n的0索引整数数组nums，初始位置为nums[0]
// 每个元素nums[i]表示从索引i向前跳转的最大长度
// 换句话说，如果你在nums[i]处，你可以跳转到任意nums[i+j]处
// 0 <= j <= nums[i] 
// i + j < n
// 返回到达nums[n - 1]的最小跳跃次数
// 生成的测试用例可以到达nums[n-1]
// 测试链接 : https://leetcode.cn/problems/jump-game-ii/
public class Code03_JumpGameII {

	public static int jump(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int step = 0;
		int cur = 0;
		int next = 0;
		for (int i = 0; i < arr.length; i++) {
			if (cur < i) {
				step++;
				cur = next;
			}
			next = Math.max(next, i + arr[i]);
		}
		return step;
	}

}
