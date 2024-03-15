package class111;

// 水王数
// 给定一个大小为n的数组nums，返回其中的水王数
// 水王数是指在数组中出现次数大于n/2的元素
// 如果数组不存在水王数返回-1
// 测试链接 : https://leetcode.cn/problems/majority-element/
public class Code03_WaterKing1 {

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
