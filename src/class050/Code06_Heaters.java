package class050;

import java.util.Arrays;

// 供暖器
// 冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
// 在加热器的加热半径范围内的每个房屋都可以获得供暖。
// 现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置
// 请你找出并返回可以覆盖所有房屋的最小加热半径。
// 说明：所有供暖器都遵循你的半径标准，加热的半径也一样。
// 测试链接 : https://leetcode.cn/problems/heaters/
public class Code06_Heaters {

	// 时间复杂度O(n * logn)，因为有排序，额外空间复杂度O(1)
	public static int findRadius(int[] houses, int[] heaters) {
		Arrays.sort(houses);
		Arrays.sort(heaters);
		int ans = 0;
		for (int i = 0, j = 0; i < houses.length; i++) {
			// i号房屋
			// j号供暖器
			while (!best(houses, heaters, i, j)) {
				j++;
			}
			ans = Math.max(ans, Math.abs(heaters[j] - houses[i]));
		}
		return ans;
	}

	// 这个函数含义：
	// 当前的地点houses[i]由heaters[j]来供暖是最优的吗？
	// 当前的地点houses[i]由heaters[j]来供暖，产生的半径是a
	// 当前的地点houses[i]由heaters[j + 1]来供暖，产生的半径是b
	// 如果a < b, 说明是最优，供暖不应该跳下一个位置
	// 如果a >= b, 说明不是最优，应该跳下一个位置
	public static boolean best(int[] houses, int[] heaters, int i, int j) {
		return j == heaters.length - 1 
				||
			   Math.abs(heaters[j] - houses[i]) < Math.abs(heaters[j + 1] - houses[i]);
	}

}
