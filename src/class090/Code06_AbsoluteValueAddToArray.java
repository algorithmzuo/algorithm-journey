package class090;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// 加入差值绝对值直到长度固定
// 给定一个非负数组arr，计算任何两个数差值的绝对值
// 如果arr中没有，都要加入到arr里，但是只加一份
// 然后新的arr继续计算任何两个数差值的绝对值，
// 如果arr中没有，都要加入到arr里，但是只加一份
// 一直到arr大小固定，返回arr最终的长度
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code06_AbsoluteValueAddToArray {

	// 暴力方法
	// 为了验证
	public static int len1(int[] arr) {
		ArrayList<Integer> list = new ArrayList<>();
		HashSet<Integer> set = new HashSet<>();
		for (int num : arr) {
			list.add(num);
			set.add(num);
		}
		while (!finish(list, set))
			;
		return list.size();
	}

	public static boolean finish(ArrayList<Integer> list, HashSet<Integer> set) {
		int len = list.size();
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				int abs = Math.abs(list.get(i) - list.get(j));
				if (!set.contains(abs)) {
					list.add(abs);
					set.add(abs);
				}
			}
		}
		return len == list.size();
	}

	// 正式方法
	// 时间复杂度O(n)
	public static int len2(int[] arr) {
		int max = 0;
		// 找到任意一个非0的值
		int gcd = 0;
		for (int num : arr) {
			max = Math.max(max, num);
			if (num != 0) {
				gcd = num;
			}
		}
		if (gcd == 0) { // 数组中都是0
			return arr.length;
		}
		// 不都是0
		// 0 7次
		// 5 5次
		HashMap<Integer, Integer> cnts = new HashMap<>();
		for (int num : arr) {
			if (num != 0) {
				gcd = gcd(gcd, num);
			}
			cnts.put(num, cnts.getOrDefault(num, 0) + 1);
		}
		int ans = max / gcd;
		int maxCnt = 0;
		for (int key : cnts.keySet()) {
			if (key != 0) {
				ans += cnts.get(key) - 1;
			}
			maxCnt = Math.max(maxCnt, cnts.get(key));
		}
		ans += cnts.getOrDefault(0, maxCnt > 1 ? 1 : 0);
		return ans;
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 50;
		int V = 100;
		int testTimes = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] nums = randomArray(n, V);
			int ans1 = len1(nums);
			int ans2 = len2(nums);
			if (ans1 != ans2) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

}
