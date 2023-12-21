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
		int n = skills.length;
		int m = people.size();
		HashMap<String, Integer> map = new HashMap<>();
		int cnt = 0;
		for (String s : skills) {
			// 把所有必要技能依次编号
			map.put(s, cnt++);
		}
		// arr[i] : 第i号人掌握必要技能的状况，用位信息表示
		int[] arr = new int[m];
		for (int i = 0, status; i < m; i++) {
			status = 0;
			for (String skill : people.get(i)) {
				if (map.containsKey(skill)) {
					// 如果当前技能是必要的
					// 才设置status
					status |= 1 << map.get(skill);
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
			// s还没凑齐
			if (i == m - 1 || dp[i][s] != dp[i + 1][s]) {
				// 当初的决策是选择了i号人
				ans[j++] = i;
				s |= arr[i];
			}
		}
		return ans;
	}

	// arr : 每个人所掌握的必要技能的状态
	// m : 人的总数
	// n : 必要技能的数量
	// i : 当前来到第几号人
	// s : 必要技能覆盖的状态
	// 返回 : i....这些人，把必要技能都凑齐，至少需要几个人
	public static int f(int[] arr, int m, int n, int i, int s, int[][] dp) {
		if (s == (1 << n) - 1) {
			// 所有技能已经凑齐了
			return 0;
		}
		// 没凑齐
		if (i == m) {
			// 人已经没了，技能也没凑齐
			// 无效
			return Integer.MAX_VALUE;
		}
		if (dp[i][s] != -1) {
			return dp[i][s];
		}
		// 可能性1 : 不要i号人
		int p1 = f(arr, m, n, i + 1, s, dp);
		// 可能性2 : 要i号人
		int p2 = Integer.MAX_VALUE;
		int next2 = f(arr, m, n, i + 1, s | arr[i], dp);
		if (next2 != Integer.MAX_VALUE) {
			// 后续有效
			p2 = 1 + next2;
		}
		int ans = Math.min(p1, p2);
		dp[i][s] = ans;
		return ans;
	}

}
