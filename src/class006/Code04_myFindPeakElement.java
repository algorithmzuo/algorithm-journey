package class006;

// 峰值元素是指其值严格大于左右相邻值的元素
// 给你一个整数数组 nums，已知任何两个相邻的值都不相等
// 找到峰值元素并返回其索引
// 数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
// 你可以假设 nums[-1] = nums[n] = 无穷小
// 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
public class Code04_myFindPeakElement {

	// 测试链接 : https://leetcode.cn/problems/find-peak-element/
	class Solution {

		public static int findPeakElement(int[] nums) {
			if(nums == null || nums.length <2){
				return 0;
			}else if(nums[0] > nums[1]){
				return 0;
			}else if(nums[nums.length -1] >nums[nums.length -2]){
				return nums.length -1;
			}else{
				int ans = -1;
				int L = 0, R = nums.length -1, M = 0;
				while(L <= R){
					M = L + ((R - L) >> 1);
					if (nums[M - 1] < nums[M]) {
						if(nums[M] >nums[M+1]){
							return M;
						}else{
							L = M;
						}
					} else if(nums[M -1 ] > nums[M]){
						R = M;
					}
				}
				return ans;
			}
		}

		//尽量往大的方向靠
		public static int findPeakElement1(int[] nums) {
			int left = 0, right = nums.length - 1;
			while(left<right){
				int mid = left + (right - left) / 2;
				// mid 大=》R=mid
				if (nums[mid] > nums[mid + 1]) {
					right = mid;
				} else {
					//mid + 1 大 => left往大的方向靠
					left = mid + 1;
				}
			}
			return left;
		}



	}
	public static void main(String[] args) {
		int[] arr  = null;
//		System.out.println(Solution.findPeakElement(arr));
		System.out.println(arr.length);
	}

}
