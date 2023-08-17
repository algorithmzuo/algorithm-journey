package class038;

import java.util.HashSet;

// 字符串的全部子序列
// 测试链接 : https://www.nowcoder.com/practice/92e6247998294f2c933906fdedbc6e6a
public class Code01_Subsequences {

	public static String[] generatePermutation(String str) {
		char[] s = str.toCharArray();
		HashSet<String> set = new HashSet<>();
		f(s, 0, new StringBuilder(), set);
		int m = set.size();
		String[] ans = new String[m];
		int i = 0;
		for (String cur : set) {
			ans[i++] = cur;
		}
		return ans;
	}

	public static void f(char[] s, int i, StringBuilder path, HashSet<String> set) {
		if (i == s.length) {
			set.add(path.toString());
		} else {
			f(s, i + 1, path, set);
			path.append(s[i]);
			f(s, i + 1, path, set);
			path.deleteCharAt(path.length() - 1);
		}
	}

}
