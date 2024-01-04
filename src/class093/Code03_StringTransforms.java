package class093;

import java.util.Arrays;

// 字符串转化
// 给出两个长度相同的字符串str1和str2
// 请你帮忙判断字符串str1能不能在 零次 或 多次 转化后变成字符串str2
// 每一次转化时，你可以将str1中出现的所有相同字母变成其他任何小写英文字母
// 只有在字符串str1能够通过上述方式顺利转化为字符串str2时才能返回true
// 测试链接 : https://leetcode.cn/problems/string-transforms-into-another-string/
public class Code03_StringTransforms {

	public static boolean canConvert(String str1, String str2) {
		if (str1.equals(str2)) {
			return true;
		}
		// map[x] : str2中字符x的词频
		int[] map = new int[26];
		// kinds : str2中字符的种类数
		int kinds = 0;
		for (int i = 0; i < str2.length(); i++) {
			if (map[str2.charAt(i) - 'a']++ == 0) {
				kinds++;
			}
		}
		if (kinds == 26) {
			return false;
		}
		Arrays.fill(map, -1);
		// map[x] = y : str1中的字符x上次出现在str1中的y位置
		for (int i = 0, cur; i < str1.length(); i++) {
			cur = str1.charAt(i) - 'a';
			if (map[cur] != -1 && str2.charAt(map[cur]) != str2.charAt(i)) {
				return false;
			}
			map[cur] = i;
		}
		return true;
	}

}
