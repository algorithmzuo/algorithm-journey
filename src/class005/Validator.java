package class005;

public class Validator {

	// 为了验证
	public static void main(String[] args) {
		// 随机数组最大长度
		int N = 200;
		// 随机数组每个值，在1~V之间等概率随机
		int V = 1000;
		// testTimes : 测试次数
		int testTimes = 50000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			// 随机得到一个长度，长度在[0~N-1]
			int n = (int) (Math.random() * N);
			// 得到随机数组
			int[] arr = randomArray(n, V);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			selectionSort(arr1);
			bubbleSort(arr2);
			insertionSort(arr3);
			if (!sameArray(arr1, arr2) || !sameArray(arr1, arr3)) {
				System.out.println("出错了!");
				// 当有错了
				// 打印是什么例子，出错的
				// 打印三个功能，各自排序成了什么样
				// 可能要把例子带入，每个方法，去debug！
			}
		}
		System.out.println("测试结束");
	}

	// 为了验证
	// 得到一个随机数组，长度是n
	// 数组中每个数，都在1~v之间，随机得到
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			// Math.random() -> double -> [0,1)范围山的一个小数，0.37463473126、0.001231231，等概率！
			// Math.random() * v -> double -> [0,v)一个小数，依然等概率
			// (int)(Math.random() * v) -> int -> 0 1 2 3 ... v-1，等概率的！
			// (int) (Math.random() * v) + 1 -> int -> 1 2 3 .... v，等概率的！
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了验证
	public static int[] copyArray(int[] arr) {
		int n = arr.length;
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了验证
	public static boolean sameArray(int[] arr1, int[] arr2) {
		int n = arr1.length;
		for (int i = 0; i < n; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// 数组中交换i和j位置的数
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 选择排序
	public static void selectionSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		for (int minIndex, i = 0; i < arr.length - 1; i++) {
			minIndex = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			swap(arr, i, minIndex);
		}
	}

	// 冒泡排序
	public static void bubbleSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		for (int end = arr.length - 1; end > 0; end--) {
			for (int i = 0; i < end; i++) {
				if (arr[i] > arr[i + 1]) {
					swap(arr, i, i + 1);
				}
			}
		}
	}

	// 插入排序
	public static void insertionSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
				swap(arr, j, j + 1);
			}
		}
	}

}