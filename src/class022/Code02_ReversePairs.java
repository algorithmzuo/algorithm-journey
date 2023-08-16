package class022;

// 翻转对数量
// 测试链接 : https://leetcode.cn/problems/reverse-pairs/
public class Code02_ReversePairs {

	public static int MAXN = 50001;

	public static int[] help = new int[MAXN];

	public static int reversePairs(int[] arr) {
		return counts(arr, 0, arr.length - 1);
	}

	// 统计l...r范围上，翻转对的数量，同时l...r范围统计完后变有序
	// 时间复杂度O(n * logn)
	public static int counts(int[] arr, int l, int r) {
		if (l == r) {
			return 0;
		}
		int m = (l + r) / 2;
		return counts(arr, l, m) + counts(arr, m + 1, r) + merge(arr, l, m, r);
	}

	public static int merge(int[] arr, int l, int m, int r) {
		// 统计部分
		int ans = 0;
		for (int i = l, j = m + 1; i <= m; i++) {
			while (j <= r && (long) arr[i] > (long) arr[j] * 2) {
				j++;
			}
			ans += j - m - 1;
		}
		// 正常merge
		int i = l;
		int a = l;
		int b = m + 1;
		while (a <= m && b <= r) {
			help[i++] = arr[a] <= arr[b] ? arr[a++] : arr[b++];
		}
		while (a <= m) {
			help[i++] = arr[a++];
		}
		while (b <= r) {
			help[i++] = arr[b++];
		}
		for (i = l; i <= r; i++) {
			arr[i] = help[i];
		}
		return ans;
	}

}