package class038;

import java.util.HashSet;

// 字符串的全部子序列
// 子序列本身是可以有重复的，只是这个题目要求去重
// 测试链接 : https://www.nowcoder.com/practice/92e6247998294f2c933906fdedbc6e6a
public class Code01_Subsequences {

	public static String[] generatePermutation1(String str) {
		char[] s = str.toCharArray();
		HashSet<String> set = new HashSet<>();
		f1(s, 0, new StringBuilder(), set);
		int m = set.size();
		String[] ans = new String[m];
		int i = 0;
		for (String cur : set) {
			ans[i++] = cur;
		}
		return ans;
	}

	public static void f1(char[] s, int i, StringBuilder path, HashSet<String> set) {
		if (i == s.length) {
			set.add(path.toString());
		} else {
			path.append(s[i]);
			f1(s, i + 1, path, set);
			path.deleteCharAt(path.length() - 1);
			f1(s, i + 1, path, set);
		}
	}

	public static String[] generatePermutation2(String str) {
		char[] s = str.toCharArray();
		HashSet<String> set = new HashSet<>();
		f2(s, 0, new char[s.length], 0, set);
		int m = set.size();
		String[] ans = new String[m];
		int i = 0;
		for (String cur : set) {
			ans[i++] = cur;
		}
		return ans;
	}

	public static void f2(char[] s, int i, char[] path, int j, HashSet<String> set) {
		if (i == s.length) {
			set.add(String.valueOf(path, 0, j));
		} else {
			path[j] = s[i];
			f2(s, i + 1, path, j + 1, set);
			f2(s, i + 1, path, j, set);
		}
	}

}
