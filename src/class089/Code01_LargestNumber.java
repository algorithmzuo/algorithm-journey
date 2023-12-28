package class089;

import java.util.ArrayList;
import java.util.Arrays;

// 最大数
// 给定一组非负整数nums
// 重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数
// 测试链接 : https://leetcode.cn/problems/largest-number/
public class Code01_LargestNumber {

	// strs中全是非空字符串，要把所有字符串拼接起来，形成字典序最小的结果
	// 暴力方法
	// 为了验证
	// 生成所有可能的排列
	// 其中选出字典序最小的结果
	public static String way1(String[] strs) {
		ArrayList<String> ans = new ArrayList<>();
		f(strs, 0, ans);
		ans.sort((a, b) -> a.compareTo(b));
		return ans.get(0);
	}

	// 全排列代码，讲解038，常见经典递归过程解析
	public static void f(String[] strs, int i, ArrayList<String> ans) {
		if (i == strs.length) {
			StringBuilder path = new StringBuilder();
			for (String s : strs) {
				path.append(s);
			}
			ans.add(path.toString());
		} else {
			for (int j = i; j < strs.length; j++) {
				swap(strs, i, j);
				f(strs, i + 1, ans);
				swap(strs, i, j);
			}
		}
	}

	public static void swap(String[] strs, int i, int j) {
		String tmp = strs[i];
		strs[i] = strs[j];
		strs[j] = tmp;
	}

	// strs中全是非空字符串，要把所有字符串拼接起来，形成字典序最小的结果
	// 正式方法
	// 时间复杂度O(n*logn)
	public static String way2(String[] strs) {
		Arrays.sort(strs, (a, b) -> (a + b).compareTo(b + a));
		StringBuilder path = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			path.append(strs[i]);
		}
		return path.toString();
	}

	// 为了验证
	// 生成长度1~n的随机字符串数组
	public static String[] randomStringArray(int n, int m, int v) {
		String[] ans = new String[(int) (Math.random() * n) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = randomString(m, v);
		}
		return ans;
	}

	// 为了验证
	// 生成长度1~m，字符种类有v种，随机字符串
	public static String randomString(int m, int v) {
		int len = (int) (Math.random() * m) + 1;
		char[] ans = new char[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (char) ('a' + (int) (Math.random() * v));
		}
		return String.valueOf(ans);
	}

	// 为了验证
	// 对数器
	public static void main(String[] args) {
		int n = 8; // 数组中最多几个字符串
		int m = 5; // 字符串长度最大多长
		int v = 4; // 字符的种类有几种
		int testTimes = 2000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTimes; i++) {
			String[] strs = randomStringArray(n, m, v);
			String ans1 = way1(strs);
			String ans2 = way2(strs);
			if (!ans1.equals(ans2)) {
				// 如果出错了
				// 可以增加打印行为找到一组出错的例子
				// 然后去debug
				System.out.println("出错了！");
			}
			if (i % 100 == 0) {
				System.out.println("测试到第" + i + "组");
			}
		}
		System.out.println("测试结束");
	}

	// 测试链接 : https://leetcode.cn/problems/largest-number/
	public static String largestNumber(int[] nums) {
		int n = nums.length;
		String[] strs = new String[n];
		for (int i = 0; i < n; i++) {
			strs[i] = String.valueOf(nums[i]);
		}
		Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
		if (strs[0].equals("0")) {
			return "0";
		}
		StringBuilder path = new StringBuilder();
		for (String s : strs) {
			path.append(s);
		}
		return path.toString();
	}

}
