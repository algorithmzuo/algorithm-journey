package class092;

import java.util.Arrays;

// 森林中的兔子
// 森林中有未知数量的兔子
// 提问其中若干只兔子 "还有多少只兔子与你颜色相同?"
// 将答案收集到一个整数数组answers中
// 其中answers[i]是第i只兔子的回答
// 每只兔子都不会说谎，返回森林中兔子的最少数量
// 测试链接 : https://leetcode.cn/problems/rabbits-in-forest/
public class Code02_RabbitsInForest {

	public static int numRabbits(int[] arr) {
		Arrays.sort(arr);
		int x = arr[0];
		int cnt = 1;
		int ans = 0;
		for (int i = 1; i < arr.length; i++) {
			if (x != arr[i]) {
				ans += ((cnt + x) / (x + 1)) * (x + 1);
				x = arr[i];
				cnt = 1;
			} else {
				cnt++;
			}
		}
		return ans + ((cnt + x) / (x + 1)) * (x + 1);
	}

}
