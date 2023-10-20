package class068;

import java.util.ArrayList;
import java.util.List;

// 最少删除多少字符可以变成子串
// 给定两个字符串s1和s2
// 返回s1至少删除多少字符可以成为s2的子串
// 对数器验证
public class Code05_MinimumDeleteBecomeSubstring {

	// 暴力方法
	// 为了验证
	public static int minDelete1(String s1, String s2) {
		List<String> list = new ArrayList<>();
		f(s1.toCharArray(), 0, "", list);
		list.sort((a, b) -> b.length() - a.length());
		for (String str : list) {
			if (s2.indexOf(str) != -1) {
				return s1.length() - str.length();
			}
		}
		return s1.length();
	}

	// 生成s1字符串的所有子序列串
	public static void f(char[] s1, int i, String path, List<String> list) {
		if (i == s1.length) {
			list.add(path);
		} else {
			f(s1, i + 1, path, list);
			f(s1, i + 1, path + s1[i], list);
		}
	}

	// 正式方法，动态规划
	// 已经展示太多次从递归到动态规划了
	// 直接写动态规划吧
	// 也不做空间压缩了，因为千篇一律
	// 有兴趣的同学自己试试
	public static int minDelete2(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		// dp[len1][len2] :
		// s1[取len1长度]至少删除多少字符可以变成s2[取len2长度]的任意后缀串
		int[][] dp = new int[n + 1][m + 1];
		for (int len1 = 1; len1 <= n; len1++) {
			dp[len1][0] = len1;
			for (int len2 = 1; len2 <= m; len2++) {
				if (s1[len1 - 1] == s2[len2 - 1]) {
					dp[len1][len2] = dp[len1 - 1][len2 - 1];
				} else {
					dp[len1][len2] = dp[len1 - 1][len2] + 1;
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int j = 0; j <= m; j++) {
			ans = Math.min(ans, dp[n][j]);
		}
		return ans;
	}

	// 为了验证
	// 生成长度为n，有v种字符的随机字符串
	public static String randomString(int n, int v) {
		char[] ans = new char[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (char) ('a' + (int) (Math.random() * v));
		}
		return String.valueOf(ans);
	}

	// 为了验证
	// 对数器
	public static void main(String[] args) {
		int n = 12;
		int v = 3;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len1 = (int) (Math.random() * n) + 1;
			int len2 = (int) (Math.random() * n) + 1;
			String s1 = randomString(len1, v);
			String s2 = randomString(len2, v);
			int ans1 = minDelete1(s1, s2);
			int ans2 = minDelete2(s1, s2);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
