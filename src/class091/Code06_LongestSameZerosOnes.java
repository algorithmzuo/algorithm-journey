package class091;

import java.util.HashMap;

// 两个0和1数量相等区间的最大长度
// 给出一个长度为n的01串，现在请你找到两个区间
// 使得这两个区间中，1的个数相等，0的个数也相等
// 这两个区间可以相交，但是不可以完全重叠，即两个区间的左右端点不可以完全一样
// 现在请你找到两个最长的区间，满足以上要求
// 返回区间最大长度
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code06_LongestSameZerosOnes {

	// 暴力方法
	// 为了验证
	public static int len1(int[] arr) {
		HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			int zero = 0;
			int one = 0;
			for (int j = i; j < arr.length; j++) {
				zero += arr[j] == 0 ? 1 : 0;
				one += arr[j] == 1 ? 1 : 0;
				map.putIfAbsent(zero, new HashMap<>());
				map.get(zero).put(one, map.get(zero).getOrDefault(one, 0) + 1);
			}
		}
		int ans = 0;
		for (int zeros : map.keySet()) {
			for (int ones : map.get(zeros).keySet()) {
				int num = map.get(zeros).get(ones);
				if (num > 1) {
					ans = Math.max(ans, zeros + ones);
				}
			}
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(n)
	public static int len2(int[] arr) {
		int leftZero = -1;
		int rightZero = -1;
		int leftOne = -1;
		int rightOne = -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0) {
				leftZero = i;
				break;
			}
		}
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 1) {
				leftOne = i;
				break;
			}
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i] == 0) {
				rightZero = i;
				break;
			}
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i] == 1) {
				rightOne = i;
				break;
			}
		}
		int p1 = rightZero - leftZero;
		int p2 = rightOne - leftOne;
		return Math.max(p1, p2);
	}

	// 为了验证
	public static int[] randomArray(int len) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * 2);
		}
		return ans;
	}

	// 为了验证
	public static void main(String[] args) {
		int N = 500;
		int testTimes = 2000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTimes; i++) {
			int n = (int) (Math.random() * N) + 2;
			int[] arr = randomArray(n);
			int ans1 = len1(arr);
			int ans2 = len2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
			if (i % 100 == 0) {
				System.out.println("测试到第" + i + "组");
			}
		}
		System.out.println("测试结束");
	}

}