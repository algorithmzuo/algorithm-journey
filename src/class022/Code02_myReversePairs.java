package class022;

// 翻转对数量
// 测试链接 : https://leetcode.cn/problems/reverse-pairs/
public class Code02_myReversePairs {

	public static int MAXN = 50001;

	public static int[] help = new int[MAXN];

	public static int reversePairs(int[] arr) {
		return counts(arr, 0, arr.length - 1);
	}

	// 统计l...r范围上，翻转对的数量，同时l...r范围统计完后变有序
	// 时间复杂度O(n * logn)
	public static int counts(int[] arr, int l, int r) {
		int m = l + ((r - l) >> 1);
		//System.out.println(l + "---" +m + "---" +r);
		if(l == r){
			return 0;
		}
		return counts(arr, l , m ) + counts(arr, m +1, r) + merge(arr, l, m, r);
	}

	public static int merge(int[] arr, int l, int m, int r) {
		// count
		int count = 0, curCount = 0;
		for(int i = l , j = m +1; i <= m ; i++){
			while(j<=r && arr[i] > 2*arr[j]){
				curCount++;
				j++;
			}
			count += curCount;

		}
		System.out.println(l + "---" +m + "---" +r + "==>" + count);
		// merge
		int i = l , j = m + 1, k=0;
		while(i<=m && j<=r ){
			help[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
		}

		while(i <= m){
			help[k++] = arr[i++];
		}

		while(j<=r){
			help[k++] = arr[j++];
		}

		for (i = l ,k=0; i<=r; ) {
			arr[i++] = help[k++];
		}
		return  count;
	}


	public static void main(String[] args) {
		int[] arr = new int[]{2147483647,2147483647,2147483647,2147483647,2147483647,2147483647};
		System.out.println(Integer.MAX_VALUE);
		System.out.println(reversePairs(arr));
	}
}
