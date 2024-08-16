package class025;

import java.util.Arrays;

// 堆结构和堆排序，填函数练习风格
// 测试链接 : https://leetcode.cn/problems/sort-an-array/
public class Code02_myHeapSort {

	public static int[] sortArray(int[] arr) {
		if(arr.length > 1){
			heapSort1(arr);
		}
		return arr;
	}

	// 从顶到底建立大根堆，O(n * logn)
	// 依次弹出堆内最大值并排好序，O(n * logn)
	// 整体时间复杂度O(n * logn)
	public static void heapSort1(int[] arr) {
		System.out.println("before: heapInsert " + Arrays.toString(arr));
		for(int i = 0 ; i< arr.length ;i++){
			heapInsert(arr, i);
		}
		System.out.println("after: heapInsert " + Arrays.toString(arr));

		for(int size = arr.length  ; size > 0 ;  ){
			swap(arr,--size,0);
			heapify(arr,0, size);
		}

	}

	// 从底到顶建立大根堆，O(n)
	// 依次弹出堆内最大值并排好序，O(n * logn)
	// 整体时间复杂度O(n * logn)
	public static void heapSort2(int[] arr) {
		for(int i = arr.length -1 ; i>=0 ; i--){
			heapify(arr, i, arr.length);
		}

		for(int size = arr.length ; size > 1;){
			swap(arr, 0, --size);
			heapify(arr, 0, size);
		}
	}

	// i位置的数，向上调整大根堆
	// arr[i] = x，x是新来的！往上看，直到不比父亲大，或者来到0位置(顶)
	public static void heapInsert(int[] arr, int i) {
		while(arr[i] > arr[(i-1) /2]){
			swap(arr, i, (i-1)/2);
			i = (i-1)/2;
		}
	}

	// i位置的数，变小了，又想维持大根堆结构
	// 向下调整大根堆
	// 当前堆的大小为size
	public static void heapify(int[] arr, int i, int size) {
		int  l = i * 2 + 1;
		while(l < size){
			int best = (l + 1 < size) && (arr[l] < arr[l+1]) ? l+1 : l;
			best = arr[i] > arr[best] ? i : best;
			if( i ==  best){
				break;
			}
			swap(arr, i, best);
			i = best;
			l = best * 2 +1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void main(String[] args) {
		int[] arr = new int[]{-4,0,7,4,9,-5,-1,0,-7,-1};
//		int[] arr = new int[] {0, 0, 1, 1, 2, 5};
		System.out.println(Arrays.toString(sortArray(arr)));
//		heapify(arr, 0, 4 );
//		System.out.println(Arrays.toString(arr));
	}



}