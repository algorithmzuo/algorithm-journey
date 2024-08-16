package class038;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 没有重复项数字的全排列
// 测试链接 : https://leetcode.cn/problems/permutations/
public class Code03_myPermutations {

	public static List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> ans =  new ArrayList<>();
		if(nums != null){
			f(nums, 0, ans);
		}
		return ans;
	}

	public static void f(int[] nums, int index, List<List<Integer>> ans){
		if(index ==  nums.length){
			List<Integer> subAns = new ArrayList<>();
			for (int i = 0; i < nums.length; i++) {
				subAns.add(nums[i]);
			}
			ans.add(subAns);
			return;
		}

		for (int i = index; i < nums.length; i++) {
			swap(nums, index, i);
			f(nums,index+1,ans);
			swap(nums, index,i);
		}
	}

	public static void swap (int[] nums, int i, int j){
		int tmp = nums[i];
		nums[i] =nums[j];
		nums[j] = tmp;
	}



	public static void main(String[] args) {
		int[] nums = { 1,2,3,4};
		List<List<Integer>> ans = permute(nums);
		for (List<Integer> list : ans) {
			for (int num : list) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}

}
