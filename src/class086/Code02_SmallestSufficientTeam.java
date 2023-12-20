package class086;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// 最小的必要团队
// 作为项目经理，你规划了一份需求的技能清单req_skills
// 并打算从备选人员名单people中选出些人组成必要团队
// 编号为i的备选人员people[i]含有一份该备选人员掌握的技能列表
// 所谓必要团队，就是在这个团队中
// 对于所需求的技能列表req_skills中列出的每项技能，团队中至少有一名成员已经掌握
// 请你返回规模最小的必要团队，团队成员用人员编号表示
// 你可以按 任意顺序 返回答案，题目数据保证答案存在
// 测试链接 : https://leetcode.cn/problems/smallest-sufficient-team/
public class Code02_SmallestSufficientTeam {

	public static int[] smallestSufficientTeam(String[] skills, List<List<String>> people) {
		Arrays.sort(skills);
		int n = skills.length;
		int m = people.size();
		HashMap<String, Integer> map = new HashMap<>();
		int cnt = 0;
		for (String s : skills) {
			if (!map.containsKey(s)) {
				map.put(s, cnt++);
			}
		}
		int[] arr = new int[m];
		for (int i = 0, status; i < m; i++) {
			status = 0;
			for (String s : people.get(i)) {
				if (map.containsKey(s)) {
					status |= 1 << map.get(s);
				}
			}
			arr[i] = status;
		}
		int[][] dp = new int[m][1 << n];
		for (int i = 0; i < m; i++) {
			Arrays.fill(dp[i], -1);
		}
		int size = f(arr, m, n, 0, 0, dp);
		int[] ans = new int[size];
		for (int j = 0, i = 0, s = 0; s != (1 << n) - 1; i++) {
			if (i + 1 == m || dp[i][s] != dp[i + 1][s]) {
				ans[j++] = i;
				s |= arr[i];
			}
		}
		return ans;
	}

	public static int f(int[] arr, int m, int n, int i, int s, int[][] dp) {
		if (s == (1 << n) - 1) {
			return 0;
		}
		if (i == m) {
			return Integer.MAX_VALUE;
		}
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		int p1 = f(arr, m, n, i + 1, s, dp);
		int p2 = Integer.MAX_VALUE;
		int next2 = f(arr, m, n, i + 1, s | arr[i], dp);
		if (next2 != Integer.MAX_VALUE) {
			p2 = 1 + next2;
		}
		int ans = Math.min(p1, p2);
		dp[i][s] = ans;
		return ans;
	}

}
