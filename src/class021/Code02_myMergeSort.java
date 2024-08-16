package class021;

// 归并排序，填函数练习风格
// 测试链接 : https://leetcode.cn/problems/sort-an-array/

import java.util.Arrays;

public class Code02_myMergeSort {

	public static int MAXN = 50001;
	public static int[] help = new int[MAXN];
	public static int[] sortArray(int[] nums) {
		if(nums.length < 2){
			return nums;
		}
		//mergeSort1(0, nums.length-1 , nums);
		mergeSort2( nums);
		return nums;
	}

	public static void mergeSort1(int L, int R, int[] nums){
		if(L == R){
			return;
		}

		int M = L + ((R-L) >>1);
//		System.out.println(L+"----"+M+"-----"+R);
		mergeSort1(L, M, nums);
		mergeSort1(M + 1,R, nums);
//		System.out.println("---merging-----");
		merge(L,M,R,nums);
//		System.out.println(Arrays.toString(nums));
	}

	public static void mergeSort2(int[] nums){
		int N = nums.length;
		int L , R=0;
		for(int step =1; step < N ;step<<=1){
			L = 0 ;
			while (L < N){
				int M = Math.min(L + step -1,N-1);
				if(M == N-1)
					break;
				R = Math.min(N-1, L + 2*step -1);
				merge(L,M,R,nums);
				L = R + 1;
			}
		}
	}


	public static void merge(int L, int M, int R, int[] nums){
		int k,i,j=0;
		for(k=0, i=L,j=M+1; i<=M && j<=R; ){
			help[k++] = nums[i] <= nums[j] ? nums[i++] : nums[j++];
		}
		while(i<=M){
			help[k++] = nums[i++];
		}

		while(j<=R){
			help[k++] = nums[j++];
		}

		for (int l= 0, h=L; l < k; ) {
			nums[h++] = help[l++];
		}
	}

	public static void main(String[] args) {
		int[] nums = {1,3,2,5,2,1,5};
		sortArray(nums);
		System.out.println(Arrays.toString(nums));
	}

}