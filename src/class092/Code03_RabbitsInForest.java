package class092;

import java.util.Arrays;

// 森林中的兔子
// 森林中有未知数量的兔子
// 提问其中若干只兔子 "还有多少只兔子与你（指被提问的兔子）颜色相同?"
// 将答案收集到一个整数数组answers中，其中answers[i]是第i只兔子的回答
// 给你数组 answers ，返回森林中兔子的最少数量
// 测试链接 : https://leetcode.cn/problems/rabbits-in-forest/
public class Code03_RabbitsInForest {

	public static int numRabbits(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Arrays.sort(arr);
		int x = arr[0];
		int c = 1;
		int ans = 0;
		for (int i = 1; i < arr.length; i++) {
			if (x != arr[i]) {
				ans += ((c + x) / (x + 1)) * (x + 1);
				x = arr[i];
				c = 1;
			} else {
				c++;
			}
		}
		return ans + ((c + x) / (x + 1)) * (x + 1);
	}

}
