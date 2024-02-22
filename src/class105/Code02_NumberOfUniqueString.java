package class105;

import java.util.Arrays;
import java.util.HashSet;

// 独特子串的数量
// 给你一个由数字组成的字符串s
// 返回s中独特子字符串数量
// 其中的每一个数字出现的频率都相同
// 测试链接 : https://leetcode.cn/problems/unique-substrings-with-equal-digit-frequency/
public class Code02_NumberOfUniqueString {

	public static int equalDigitFrequency(String str) {
		long base = 499;
		char[] s = str.toCharArray();
		int n = s.length;
		HashSet<Long> set = new HashSet<>();
		int[] cnt = new int[10];
		for (int i = 0; i < n; i++) {
			Arrays.fill(cnt, 0);
			long hashCode = 0;
			int curVal = 0, maxCnt = 0, maxCntKinds = 0, allKinds = 0;
			for (int j = i; j < n; j++) {
				curVal = s[j] - '0';
				hashCode = hashCode * base + curVal + 1;
				cnt[curVal]++;
				if (cnt[curVal] == 1) {
					allKinds++;
				}
				if (cnt[curVal] > maxCnt) {
					maxCnt = cnt[curVal];
					maxCntKinds = 1;
				} else if (cnt[curVal] == maxCnt) {
					maxCntKinds++;
				}
				if (maxCntKinds == allKinds) {
					set.add(hashCode);
				}
			}
		}
		return set.size();
	}

}
