package class146;

// 摇摆排序ii(满足全部进阶要求)
// 给定一个数组arr，重新排列数组，确保满足：arr[0] < arr[1] > arr[2] < arr[3] > ...
// 题目保证输入的数组一定有解，要求时间复杂度O(n)，额外空间复杂度O(1)
// 测试链接 : https://leetcode.cn/problems/wiggle-sort-ii/
public class Code06_WiggleSortII {

	// 最优解
	// 时间复杂度O(n)，额外空间复杂度O(1)
	public static void wiggleSort(int[] arr) {
		int n = arr.length;
		randomizedSelect(arr, n, n / 2);
		if ((n & 1) == 0) {
			shuffle(arr, 0, n - 1);
			reverse(arr, 0, n - 1);
		} else {
			shuffle(arr, 1, n - 1);
		}
	}

	// 随机选择算法，不会去看讲解024
	// 无序数组中找到，如果排序之后，在i位置的数x，顺便把数组调整为
	// 左边是小于x的部分    中间是等于x的部分    右边是大于x的部分
	// 时间复杂度O(n)，额外空间复杂度O(1)
	public static int randomizedSelect(int[] arr, int n, int i) {
		int ans = 0;
		for (int l = 0, r = n - 1; l <= r;) {
			partition(arr, l, r, arr[l + (int) (Math.random() * (r - l + 1))]);
			if (i < first) {
				r = first - 1;
			} else if (i > last) {
				l = last + 1;
			} else {
				ans = arr[i];
				break;
			}
		}
		return ans;
	}

	public static int first, last;

	public static void partition(int[] arr, int l, int r, int x) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			if (arr[i] == x) {
				i++;
			} else if (arr[i] < x) {
				swap(arr, first++, i++);
			} else {
				swap(arr, i, last--);
			}
		}
	}

	// 完美洗牌算法
	// 时间复杂度O(n)，额外空间复杂度O(1)
	public static int MAXN = 20;

	public static int[] start = new int[MAXN];

	public static int[] split = new int[MAXN];

	public static int size;

	public static void shuffle(int[] arr, int l, int r) {
		int n = r - l + 1;
		build(n);
		for (int i = size, m; n > 0;) {
			if (split[i] <= n) {
				m = (l + r) / 2;
				rotate(arr, l + split[i] / 2, m, m + split[i] / 2);
				circle(arr, l, l + split[i] - 1, i);
				l += split[i];
				n -= split[i];
			} else {
				i--;
			}
		}
	}

	public static void build(int n) {
		size = 0;
		for (int s = 1, p = 2; p <= n; s *= 3, p = s * 3 - 1) {
			start[++size] = s;
			split[size] = p;
		}
	}

	public static void rotate(int[] arr, int l, int m, int r) {
		reverse(arr, l, m);
		reverse(arr, m + 1, r);
		reverse(arr, l, r);
	}

	public static void reverse(int[] arr, int l, int r) {
		while (l < r) {
			swap(arr, l++, r--);
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void circle(int[] arr, int l, int r, int i) {
		for (int j = 1, init, cur, next, curv, nextv; j <= i; j++) {
			init = cur = l + start[j] - 1;
			next = to(cur, l, r);
			curv = arr[cur];
			while (next != init) {
				nextv = arr[next];
				arr[next] = curv;
				curv = nextv;
				cur = next;
				next = to(cur, l, r);
			}
			arr[init] = curv;
		}
	}

	public static int to(int i, int l, int r) {
		if (i <= (l + r) >> 1) {
			return i + (i - l + 1);
		} else {
			return i - (r - i + 1);
		}
	}

}
