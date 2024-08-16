package class030;

import java.util.Arrays;

// 数组中有2种数出现了奇数次，其他的数都出现了偶数次
// 返回这2种出现了奇数次的数
// 测试链接 : https://leetcode.cn/problems/single-number-iii/
public class Code05_myDoubleNumber {

	public static int[] singleNumber(int[] nums) {
		//1- 得到总和 xor , cuz xor = ans1 + ans2
		int xor = 0;
		for (int num: nums) {
			xor ^= num;
		}
		//2- xor & -xor => 得到xor最右侧不等于1的bit
		int bit = xor & (-xor);
		//3- 遍历数组 将此位等于1不等于1分开,只计算不含1的
		int  xor2 = 0;
		for(int i = 0; i< nums.length ;i++){
			if((nums[i] & bit) == 0 ){
				xor2 ^= nums[i];
			}
		}
		//4- 以等于0 区异或得到ans1 => ans2 = xor ^ ans1
		return new int[]{xor2, xor ^ xor2};
	}

	public static void main(String[] args) {
		int[] num1 = new int[]{1,3,1,3,2,4};
		System.out.println(Arrays.toString(singleNumber(num1)));
	}
}
