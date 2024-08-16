package class038;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的组合
// 答案 不能 包含重复的组合。返回的答案中，组合可以按 任意顺序 排列
// 注意其实要求返回的不是子集，因为子集一定是不包含相同元素的，要返回的其实是不重复的组合
// =》 本质上是不同数字的不同数量的 排列组合的过程
// 比如输入：nums = [1,2,2]
// 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
// 测试链接 : https://leetcode.cn/problems/subsets-ii/
public class Code02_myCombinations {

	public static List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> ans =  new ArrayList<>();
		if(nums != null){
			Arrays.sort(nums);
			f(nums,0, new int[nums.length], 0, ans);
		}
		return ans;
	}

	/**
	 *
	 * @param nums 一个整数数组
	 * @param i 当前下标
	 * @param path 截至上个节点的路径信息
	 * @param size path的size大小
	 * @param ans 子答案
	 */
	public static void f(int[] nums, int i, int[] path, int size, List<List<Integer>> ans) {
		if(i == nums.length){
			// 把此路径的值丢进去ans中
			List<Integer> subAns = new ArrayList<>();
			for (int j = 0; j < size; j++) {
				subAns.add(path[j]);
			}
			ans.add(subAns);
			return;
		}
		// 找到下一个不同元素开始的下标
		int k = i;
		while(k < nums.length && nums[k] == nums[i] ){
			k++;
		}
		// 当此组合不包含这个元素
		f(nums, k, path, size, ans);
		// 递增这个组合包含的元素个数
		int count = k - i ;
		for (int j = 0; j < count; j++) {
			path[size++] = nums[i];
			f(nums, k, path, size, ans);
		}
	}


}
