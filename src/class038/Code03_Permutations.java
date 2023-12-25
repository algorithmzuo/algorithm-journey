package class038;

import java.util.ArrayList;
import java.util.List;

// 没有重复项数字的全排列
// 测试链接 : https://leetcode.cn/problems/permutations/
// 39:20 ~ 1:03:27
public class Code03_Permutations {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        func(nums, 0, ans);
        return ans;
    }

    public static void func(int[] nums, int i, List<List<Integer>> ans) {
        if (i == nums.length) {
            List<Integer> cur = new ArrayList<>();
            for (int num : nums)
                cur.add(num);
            ans.add(cur);
        } else {
            for (int j = i; j < nums.length; j++) {
                swap(nums, i, j);
                func(nums, i + 1, ans);
                swap(nums, i, j); // 特别重要，课上进行了详细的图解
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        if (i == j) return;
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> ans = permute(nums);
        for (List<Integer> list : ans) {
            for (int num : list) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

}
