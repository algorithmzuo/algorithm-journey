package class111;

import java.util.ArrayList;
import java.util.List;

// 返回次数超过n/k的所有元素
// 给定一个大小为n的数组nums，给定一个正数k
// 返回次数超过n/k的所有元素，如果没有返回空列表
// 测试链接 : https://leetcode.cn/problems/majority-element-ii/
public class Code03_WaterKing2 {

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
				if (real > n / 3) {
					ans.add(cur);
				}
			}
		}
	}

}
