package class111;

// 水王数
// 给定一个大小为n的数组nums，返回其中的多数元素
// 多数元素是指在数组中出现次数大于n/2的元素。
// 你可以假设数组是非空的，并且给定的数组总是存在多数元素
// 测试链接 : https://leetcode.cn/problems/majority-element/
public class Code01_WaterKing1 {

	public static int majorityElement(int[] nums) {
		int cand = 0;
		int hp = 0;
		for (int num : nums) {
			if (hp == 0) {
				cand = num;
				hp = 1;
			} else if (num == cand) {
				hp++;
			} else {
				hp--;
			}
		}
		if (hp == 0) {
			return -1;
		}
		hp = 0;
		for (int num : nums) {
			if (num == cand) {
				hp++;
			}
		}
		return hp > nums.length / 2 ? cand : -1;
	}

}
