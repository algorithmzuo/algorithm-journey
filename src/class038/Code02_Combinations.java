package class038;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

// 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的组合
// 答案 不能 包含重复的组合。返回的答案中，组合可以按 任意顺序 排列
// 注意其实要求返回的不是子集，因为子集一定是不包含相同元素的，要返回的其实是不重复的组合
// 比如输入：nums = [1,2,2]
// 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
// 测试链接 : https://leetcode.cn/problems/subsets-ii/
// 20:10 ~ 39:14
public class Code02_Combinations {

    public static List<List<Integer>> subsetsWithDup1(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        func(nums, 0, new int[nums.length], 0, ans);
        return ans;
    }

    public static void func(int[] nums, int i, int[] path, int size, List<List<Integer>> ans) {
        if (i == nums.length) {
            ArrayList<Integer> cur = new ArrayList<>();
            for (int j = 0; j < size; j++)
                cur.add(path[j]);
            ans.add(cur);
        } else {
            // 下一组的第一个数的位置
            int j = i + 1;
            while (j < nums.length && nums[i] == nums[j]) {
                j++;
            }
            // 当前数x，要0个
            func(nums, j, path, size, ans);
            // 当前数x，要1个、要2个、要3个...都尝试
            for (; i < j; i++) {
                path[size++] = nums[i];
                func(nums, j, path, size, ans);
            }
        }
    }

    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(ans, new ArrayList<>(), nums, 0);
        return ans;
    }

    private void backtrack(List<List<Integer>> ans, List<Integer> temp, int[] nums, int start) {
        ans.add(new ArrayList<>(temp));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue; // Skip duplicates
            temp.add(nums[i]);
            backtrack(ans, temp, nums, i + 1);
            temp.remove(temp.size() - 1);
        }
    }

}
