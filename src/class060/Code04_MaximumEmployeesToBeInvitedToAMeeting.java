package class060;

// 参加会议的最多员工数
// 一个公司准备组织一场会议，邀请名单上有 n 位员工
// 公司准备了一张 圆形 的桌子，可以坐下 任意数目 的员工
// 员工编号为 0 到 n - 1 。每位员工都有一位 喜欢 的员工
// 每位员工 当且仅当 他被安排在喜欢员工的旁边，他才会参加会议
// 每位员工喜欢的员工 不会 是他自己。
// 给你一个下标从 0 开始的整数数组 favorite
// 其中 favorite[i] 表示第 i 位员工喜欢的员工。请你返回参加会议的 最多员工数目
// 测试链接 : https://leetcode.cn/problems/maximum-employees-to-be-invited-to-a-meeting/
public class Code04_MaximumEmployeesToBeInvitedToAMeeting {

	public static int maximumInvitations(int[] favorite) {
		int n = favorite.length;
		int[] indegree = new int[n];
		for (int i = 0; i < n; i++) {
			indegree[favorite[i]]++;
		}
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				queue[r++] = i;
			}
		}
		int[] deep = new int[n];
		while (l < r) {
			int cur = queue[l++];
			deep[cur]++;
			int next = favorite[cur];
			deep[next] = Math.max(deep[next], deep[cur]);
			if (--indegree[next] == 0) {
				queue[r++] = next;
			}
		}
		int sumOfSmallRings = 0;
		int bigRings = 0;
		for (int i = 0; i < n; i++) {
			if (indegree[i] > 0) {
				int maxSize = 1;
				indegree[i] = 0;
				for (int j = favorite[i]; j != i; j = favorite[j]) {
					maxSize++;
					indegree[j] = 0;
				}
				if (maxSize == 2) {
					sumOfSmallRings += 2 + deep[i] + deep[favorite[i]];
				} else {
					bigRings = Math.max(bigRings, maxSize);
				}
			}
		}
		return Math.max(sumOfSmallRings, bigRings);
	}

}