package class004;

import java.util.Arrays;

public class mySelectBubbleInsert {

	// 数组中交换i和j位置的数
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 选择排序 - 依次選擇最小的
	public static void selectionSort(int[] arr) {
		// 1- boundary condition
		if(arr == null || arr.length < 2){
			return;
		}

		for (int i = 0; i < arr.length -1 ; i++) {
			int minIndex = i;
			for (int j = i +1; j < arr.length ; j++) {
				if(arr[minIndex] > arr[j]){
					minIndex = j;
				}
			}
			swap(arr,i,minIndex);
		}
	}

	// 冒泡排序
	public static void bubbleSort(int[] arr) {
		if(arr == null || arr.length <2){
			return;
		}
		for (int i = 0; i < arr.length -1 ; i++) {
			for (int j = i; j <arr.length -1  ; j++) {
				if(arr[j] > arr[j+1]){
					swap(arr, j ,j + 1);
				}
			}
		}
	}

	// 插入排序
	public static void insertionSort(int[] arr) {
		if(arr == null || arr.length <2){
			return;
		}

		for (int i = 1; i < arr.length; i++) {

			for (int j = i ; j >= 1; j--) {
				if(arr[j-1] > arr[j] ){
					swap(arr, j-1, j);
				}
			}
		}
	}

	public static void main(String[] args) {
		int[] arr = {9,8,3,1,2,3,5,3,4,6};
		int[] arr1 = {9,8,3,1,2,3,5,3,4,6};
		selectionSort(arr);
		insertionSort(arr1);
		System.out.println(Arrays.toString(arr1));
	}
}