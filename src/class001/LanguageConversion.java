package class001;

// 测试链接 : https://leetcode.cn/problems/sort-an-array/
// 此时不要求掌握，因为这些排序后续的课都会讲到的
// 这里只是想说明代码语言的转换并不困难
// 整个系列虽然都是java讲的，但使用不同语言的同学听懂思路之后，想理解代码真的不是问题
// 语言问题并不是学习算法的障碍，有了人工智能工具之后，就更不是障碍了
public class LanguageConversion {

	class Solution {

		public static int[] sortArray(int[] nums) {
			if (nums.length > 1) {
				mergeSort(nums);
			}
			return nums;
		}

		public static int MAXN = 50001;

		// 以下是归并排序
		public static int[] help = new int[MAXN];

		public static void mergeSort(int[] arr) {
			int n = arr.length;
			for (int l, m, r, step = 1; step < n; step <<= 1) {
				l = 0;
				while (l < n) {
					m = l + step - 1;
					if (m + 1 >= n) {
						break;
					}
					r = Math.min(l + (step << 1) - 1, n - 1);
					merge(arr, l, m, r);
					l = r + 1;
				}
			}
		}

		public static void merge(int[] nums, int l, int m, int r) {
			int p1 = l;
			int p2 = m + 1;
			int i = l;
			while (p1 <= m && p2 <= r) {
				help[i++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
			}
			while (p1 <= m) {
				help[i++] = nums[p1++];
			}
			while (p2 <= r) {
				help[i++] = nums[p2++];
			}
			for (i = l; i <= r; i++) {
				nums[i] = help[i];
			}
		}

		// 以下是随机快速排序
		public static void quickSort(int[] arr) {
			sort(arr, 0, arr.length - 1);
		}

		public static void sort(int[] arr, int l, int r) {
			if (l >= r) {
				return;
			}
			// 随机这一下，常数时间比较大
			// 但只有这一下随机，才能在概率上把快速排序的时间复杂度收敛到O(n * logn)
			int x = arr[l + (int) (Math.random() * (r - l + 1))];
			partition(arr, l, r, x);
			int left = first;
			int right = last;
			sort(arr, l, left - 1);
			sort(arr, right + 1, r);
		}

		public static int first, last;

		public static void partition(int[] nums, int l, int r, int x) {
			first = l;
			last = r;
			int i = l;
			while (i <= last) {
				if (nums[i] == x) {
					i++;
				} else if (nums[i] < x) {
					swap(nums, first++, i++);
				} else {
					swap(nums, i, last--);
				}
			}
		}

		public static void swap(int[] arr, int i, int j) {
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}

		// 以下是堆排序
		public static void heapSort(int[] nums) {
			int n = nums.length;
			for (int i = n - 1; i >= 0; i--) {
				heapify(nums, i, n);
			}
			while (n > 1) {
				swap(nums, 0, --n);
				heapify(nums, 0, n);
			}
		}

		// 这个方法虽然堆排序用不上，但是堆结构里是重要方法，所以这里保留
		// 后面的课会讲
		public static void heapInsert(int[] nums, int i) {
			while (nums[i] > nums[(i - 1) / 2]) {
				swap(nums, i, (i - 1) / 2);
				i = (i - 1) / 2;
			}
		}

		public static void heapify(int[] nums, int i, int s) {
			int l = i * 2 + 1;
			while (l < s) {
				int best = l + 1 < s && nums[l + 1] > nums[l] ? l + 1 : l;
				best = nums[best] > nums[i] ? best : i;
				if (best == i) {
					break;
				}
				swap(nums, best, i);
				i = best;
				l = i * 2 + 1;
			}
		}

	}

}
