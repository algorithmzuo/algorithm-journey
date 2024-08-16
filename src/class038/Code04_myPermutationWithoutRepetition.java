package class038;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

// 有重复项数组的去重全排列
// 测试链接 : https://leetcode.cn/problems/permutations-ii/
public class Code04_myPermutationWithoutRepetition {

	public static List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> ans = new ArrayList<>();
		if(nums != null){
			Arrays.sort(nums);
			f(nums, 0, ans);
		}
		return ans;
	}

	public static void f(int nums[], int curIndex, List<List<Integer>> ans){
		if(curIndex == nums.length){
			List<Integer> subAns = Arrays.stream(nums).boxed().collect(Collectors.toList());
			ans.add(subAns);
			return;
		}
		// 找到下标为不同元素

		for (int i = curIndex; i < nums.length ; ) {
			swap(nums, curIndex, i);
			f(nums, curIndex +1 , ans);
			swap(nums,curIndex, i );
			i = findNextSwapIndex(nums, nums[curIndex], i);
		}
	}

	private static int findNextSwapIndex(int[] nums, int target, int preIndex) {
		for (int i = preIndex + 1; i < nums.length; i++) {
			if(nums[i] != target && nums[i] != nums[preIndex]){
				return i;
			}
		}
		return nums.length;
	}

	private static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] =nums[j];
		nums[j] = tmp;
	}


	public static void main(String[] args) {
		int[] nums = { 1, 1, 1,2};
		List<List<Integer>> ans = permuteUnique(nums);
		for (List<Integer> list : ans) {
			for (int num : list) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}
}
