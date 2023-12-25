package class068;

import java.util.ArrayList;
import java.util.List;

// 删除至少几个字符可以变成另一个字符串的子串
// 给定两个字符串s1和s2
// 返回s1至少删除多少字符可以成为s2的子串
// 对数器验证
public class Code05_MinimumDeleteBecomeSubstring {

	// 暴力方法
	// 为了验证
	public static int minDelete1(String s1, String s2) {
		List<String> list = new ArrayList<>();
		f(s1.toCharArray(), 0, "", list);
		// 排序 : 长度大的子序列先考虑
		// 因为如果长度大的子序列是s2的子串
		// 那么需要删掉的字符数量 = s1的长度 - s1子序列长度
		// 子序列长度越大，需要删掉的字符数量就越少
		// 所以长度大的子序列先考虑
		list.sort((a, b) -> b.length() - a.length());
		for (String str : list) {
			if (s2.indexOf(str) != -1) {
				// 检查s2中，是否包含当前的s1子序列str
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
		// s1[前缀长度为i]至少删除多少字符，可以变成s2[前缀长度为j]的任意后缀串
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 1; i <= n; i++) {
			dp[i][0] = i;
			for (int j = 1; j <= m; j++) {
				if (s1[i - 1] == s2[j - 1]) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = dp[i - 1][j] + 1;
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
		// 测试的数据量比较小
		// 那是因为数据量大了，暴力方法过不了
		// 但是这个数据量足够说明正式方法是正确的
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
