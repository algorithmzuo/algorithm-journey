package class146;

// 完美洗牌算法
// 给定数组arr，给定某个范围arr[l..r]，该范围长度为n，n是偶数
// 因为n是偶数，所以给定的范围可以分成左右两个部分，arr[l1, l2, ...lk, r1, r2, ...rk]
// 请把arr[l..r]范围上的数字调整成arr[r1, l1, r2, l2, ... rk, lk]，其他位置的数字不变
// 要求时间复杂度O(n)，额外空间复杂度O(1)，对数器验证
public class Code05_PerfectShuffle {

	// 申请额外空间的做法
	// 保证调整的范围是偶数长度
	// 为了测试
	public static void shuffle1(int[] arr, int l, int r) {
		int n = r - l + 1;
		int[] help = new int[n];
		for (int l1 = l, r1 = (l + r) / 2 + 1, j = 0; j < n; l1++, r1++) {
			help[j++] = arr[r1];
			help[j++] = arr[l1];
		}
		for (int i = l, j = 0; j < n; i++, j++) {
			arr[i] = help[j];
		}
	}

	// 正式方法
	// 完美洗牌算法
	public static int MAXN = 20;

	public static int[] start = new int[MAXN];

	public static int[] split = new int[MAXN];

	public static int size;

	// 保证调整的范围是偶数长度
	public static void shuffle2(int[] arr, int l, int r) {
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

	// 生成随机数组
	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 拷贝数组
	// 为了测试
	public static int[] copyArray(int[] arr) {
		int n = arr.length;
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	public static void main(String[] args) {
		int n = 10000;
		int v = 100000;
		int[] arr1 = randomArray(n, v);
		int[] arr2 = copyArray(arr1);
		int test = 50000;
		System.out.println("测试开始");
		for (int i = 1, a, b, l, r; i <= test; i++) {
			a = (int) (Math.random() * n);
			b = (int) (Math.random() * n);
			l = Math.min(a, b);
			r = Math.max(a, b);
			if (((r - l + 1) & 1) == 0) {
				shuffle1(arr1, l, r);
				shuffle2(arr2, l, r);
			}
		}
		for (int i = 0; i < n; i++) {
			if (arr1[i] != arr2[i]) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
