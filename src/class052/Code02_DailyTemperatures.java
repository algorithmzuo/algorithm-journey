package class052;

// 每日温度
// 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer
// 其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后
// 如果气温在这之后都不会升高，请在该位置用 0 来代替。
// 测试链接 : https://leetcode.cn/problems/daily-temperatures/
public class Code02_DailyTemperatures {

	public static int MAXN = 100001;

	public static int[] stack = new int[MAXN];

	public static int r;

	public static int[] dailyTemperatures(int[] nums) {
		int n = nums.length;
		int[] ans = new int[n];
		r = 0;
		for (int i = 0, cur; i < n; i++) {
			// 相等时候的处理，相等也加入单调栈
			while (r > 0 && nums[stack[r - 1]] < nums[i]) {
				cur = stack[--r];
				ans[cur] = i - cur;
			}
			stack[r++] = i;
		}
		return ans;
	}

}
