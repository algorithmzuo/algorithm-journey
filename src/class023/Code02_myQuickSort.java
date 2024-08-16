package class023;

// 随机快速排序，填函数练习风格
// 测试链接 : https://leetcode.cn/problems/sort-an-array/

import java.util.Arrays;

public class Code02_myQuickSort {

	public static int[] sortArray(int[] nums) {
		quickSort(0, nums.length -1 , nums);
		return nums;
	}

	private static void quickSort(int l, int r, int[] nums) {
		if(l >= r){
			return;
		}
		int x = nums[l + (int) (Math.random() * (r - l + 1))];
		//int x  = l + ((r-l) >>1);
		System.out.println(l+ "---" + x +"---" + r);
		System.out.println("before: " + Arrays.toString(nums)) ;
		int[] midNumRange = partition(l, r, x, nums);
		System.out.println("midNumRange: " + Arrays.toString(midNumRange));
		System.out.println("after: "+ Arrays.toString(nums));
		quickSort(l, midNumRange[0] -1,  nums);
		quickSort(midNumRange[1] +1, r , nums );

	}


	private static int[] partition(int l, int r, int x, int[] nums){
		int smallerBoundary = l, greaterBounday = r;
		for (int i = l; i <=greaterBounday ;) {
			if(nums[i] < x){
				swap(nums, smallerBoundary++, i++ );
			}else if(nums[i] > x){
				swap(nums, greaterBounday--, i);
			}else{
				i++;
			}

		}
		return new int[]{smallerBoundary , greaterBounday };
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void main(String[] args) {
		int[] arr = sortArray(new int[]{5,1,1,2,0,0});
		System.out.println(Arrays.toString(arr));
	}

}