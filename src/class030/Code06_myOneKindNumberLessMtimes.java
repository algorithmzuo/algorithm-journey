package class030;

import java.util.Arrays;

// 数组中只有1种数出现次数少于m次，其他数都出现了m次
// 返回出现次数小于m次的那种数
// 测试链接 : https://leetcode.cn/problems/single-number-ii/
// 注意 : 测试题目只是通用方法的一个特例，课上讲了更通用的情况
public class Code06_myOneKindNumberLessMtimes {

	public static int singleNumber(int[] nums) {
		return find(nums,3);
	}

	// 更通用的方法
	// 已知数组中只有1种数出现次数少于m次，其他数都出现了m次
	// 返回出现次数小于m次的那种数
	public static int find(int[] nums, int m) {
		int[] cnts = new int[32];
		// 1- 遍历每个数字的每个位数
		for (int num: nums) {
			for(int i = 0 ; i<32 ; i++){
				cnts[i] += num >>i &1;
			}
		}
		System.out.println(Arrays.toString(cnts));
		// 2- %m ，有余数的就是那个仅存的一个数字
		int ans = 0;
		for (int i = 0; i < 32; i++) {
			if(cnts[i] % m !=0){
				ans|=(1<<i);
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] arr = new int[]{-1,-1,-4,-1};
		System.out.println(singleNumber(arr));
//		System.out.println(1<<0);
//		System.out.println(-1  & (1<<0));
	}

}
