package class028;

import java.util.Arrays;

// 基数排序
// 测试链接 : https://leetcode.cn/problems/sort-an-array/
public class Code02_RadixSort {

	// 可以设置进制，不一定10进制，随你设置
	public static int BASE = 10;

	public static int MAXN = 50001;

	public static int[] help = new int[MAXN];

	public static int[] cnts = new int[BASE];

	public static int[] sortArray(int[] arr) {
		if (arr.length > 1) {
			// 如果会溢出，那么要改用long类型数组来排序
			int n = arr.length;
			// 找到数组中的最小值
			int min = arr[0];
			for (int i = 1; i < n; i++) {
				min = Math.min(min, arr[i]);
			}
			int max = 0;
			for (int i = 0; i < n; i++) {
				// 数组中的每个数字，减去数组中的最小值，就把arr转成了非负数组
				arr[i] -= min;
				// 记录数组中的最大值
				max = Math.max(max, arr[i]);
			}
			// 根据最大值在BASE进制下的位数，决定基数排序做多少轮
			radixSort(arr, n, bits(max));
			// 数组中所有数都减去了最小值，所以最后不要忘了还原
			for (int i = 0; i < n; i++) {
				arr[i] += min;
			}
		}
		return arr;
	}

	// 返回number在BASE进制下有几位
	public static int bits(int number) {
		int bits = 0;
		while(number > 0){
			number/=BASE;
			bits++;
		}
		return bits;
	}

	// 基数排序核心代码
	// arr内要保证没有负数
	// n是arr的长度
	// bits是arr中最大值在BASE进制下有几位
	public static void radixSort(int[] arr, int n, int bits) {
		for (int bit = 0, offset = 1 ; bit < bits; bit++, offset*=BASE) {
			// 1- 初始化位数的数组
			Arrays.fill(cnts,0);
			// 2- 遍历数组中 每个数 统计位数频率
			for (int i = 0; i < n; i++) {
				int curBit = (arr[i] / offset) % BASE;
				cnts[curBit]++;
			}
			// 3- 位数累加和
			for(int i = 1; i < BASE;i++){
				cnts[i]+=cnts[i-1];
			}

			// 4- 根据位数累加和 填进去help数组
			for (int i = n -1 ; i >=0 ; i--) {
				int supposePos = (arr[i] / offset) % BASE;
				help[--cnts[supposePos]] = arr[i];
			}
			// 5- 从Help数组复制回去arr数组
			for (int i = 0; i < n; i++) {
				arr[i] = help[i];
			}
			System.out.println(Arrays.toString(arr));
		}
	}

	public static void main(String[] args) {
		int[] arr = new int[]{614,412,211,129,218,123};
		radixSort(arr, arr.length ,bits(614));
		System.out.println(Arrays.toString(arr));
//		System.out.println(bits(12456));
	}

}
