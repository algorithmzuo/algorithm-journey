package class024;

import java.util.Arrays;

// 无序数组中第K大的元素
// 测试链接 : https://leetcode.cn/problems/kth-largest-element-in-an-array/
public class myRandomizedSelect {

	// 随机选择算法，时间复杂度O(n)
	public static int findKthLargest(int[] nums, int k) {
		return randomizedSelect(nums, nums.length - k);
	}

	// 如果arr排序的话，第i位置的数字是什么
	public static int randomizedSelect(int[] arr, int targetIndex) {
		int ans = 0;

		int r = arr.length - 1;
		int[] mid = new int[2];
		for(int l = 0; l<=r;){
			int x = arr[(int) (l + (Math.random()*(r - l +1)))];
			// 划分 <x  =x >x
			// partition 返回的是x区间下标
			mid = partition(arr, l , r , x);
			if (targetIndex >= mid[0] && targetIndex <= mid[1]){
				ans = x;
				break;
			}else if (targetIndex < mid[0]){
				r = mid[0] - 1;
			}else {
				l = mid[1] + 1;
			}
		}
		return ans;
	}


	public static int[] partition(int[] arr, int l, int r, int x) {
		for(int i = l ; i<=r ;){
			if(arr[i] < x){
				swap(arr, l++,i++);
			}else if(arr[i] == x){
				i++;
			}else{
				swap(arr, i, r--);
			}
		}
		return new int[]{l, r };
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void main(String[] args) {
		int[] arr = new int[]{9,1,4,3,5,2,2,2,2};
		System.out.println(Arrays.toString(partition(arr,0, arr.length-1, 2)));
	}
}
