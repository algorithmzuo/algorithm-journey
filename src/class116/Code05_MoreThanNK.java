package class116;

import java.util.ArrayList;
import java.util.List;

// 出现次数大于n/k的数
// 给定一个大小为n的数组nums，给定一个较小的正数k
// 水王数是指在数组中出现次数大于n/k的数
// 返回所有的水王数，如果没有水王数返回空列表
// 测试链接 : https://leetcode.cn/problems/majority-element-ii/
public class Code05_MoreThanNK {

	public static List<Integer> majorityElement(int[] nums) {
		return majority(nums, 3);
	}

	public static List<Integer> majority(int[] nums, int k) {
		int[][] cands = new int[--k][2];
		for (int num : nums) {
			update(cands, k, num);
		}
		List<Integer> ans = new ArrayList<>();
		collect(cands, k, nums, nums.length, ans);
		return ans;
	}

	public static void update(int[][] cands, int k, int num) {
		for (int i = 0; i < k; i++) {
			if (cands[i][0] == num && cands[i][1] > 0) {
				cands[i][1]++;
				return;
			}
		}
		for (int i = 0; i < k; i++) {
			if (cands[i][1] == 0) {
				cands[i][0] = num;
				cands[i][1] = 1;
				return;
			}
		}
		for (int i = 0; i < k; i++) {
			if (cands[i][1] > 0) {
				cands[i][1]--;
			}
		}
	}

	public static void collect(int[][] cands, int k, int[] nums, int n, List<Integer> ans) {
		for (int i = 0, cur, real; i < k; i++) {
			if (cands[i][1] > 0) {
				cur = cands[i][0];
				real = 0;
				for (int num : nums) {
					if (cur == num) {
						real++;
					}
				}
				if (real > n / (k + 1)) {
					ans.add(cur);
				}
			}
		}
	}

}
