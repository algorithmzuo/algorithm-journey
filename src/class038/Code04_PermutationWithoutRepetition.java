package class038;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// 有重复项数组的去重全排列
// 测试链接 : https://leetcode.cn/problems/permutations-ii/
// 1:03:25 ~ 1:06:38
public class Code04_PermutationWithoutRepetition {

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        func(nums, 0, ans);
        return ans;
    }

    private void func(int[] nums, int i, List<List<Integer>> ans) {
        if (i == nums.length) {
            List<Integer> cur = new ArrayList<>();
            for (int num : nums)
                cur.add(num);
            ans.add(cur);
        } else {
            for (int j = i; j < nums.length; j++) {
                if (check(nums, i, j)) continue;
                swap(nums, i, j);
                func(nums, i + 1, ans);
                swap(nums, i, j);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        if (i == j) return;
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    private boolean check(int[] nums, int i, int j) {
        while (i < j && nums[i] != nums[j])
            i++;
        return i < j;
    }

}
