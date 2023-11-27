package class080;

// 我能赢吗
// 在 "100 game" 这个游戏中
// 两名玩家轮流选择从1到10的任意整数
// 累计整数和，先使得累计整数和达到或超过100的玩家获胜
// 如果我们将游戏规则改为 : 玩家不能重复使用整数
// 例如，两个玩家可以轮流从公共整数池中抽取从1到15的整数（不放回）
// 直到累计整数和 >= 100
// 给定两个整数choose和total
// 表示挑选1~choose范围的数字，到达或者超过total获胜
// 若先出手的玩家能稳赢则返回 true ，否则返回 false
// 假设两位玩家游戏时都绝顶聪明
// 测试链接 : https://leetcode.cn/problems/can-i-win/
public class Code01_CanIWin {

	public static boolean canIWin(int choose, int total) {
		if (total == 0) {
			return true;
		}
		if ((choose * (choose + 1) >> 1) < total) {
			return false;
		}
		// dp[status] == 1 算过，答案是true
		// dp[status] == -1 算过，答案是false
		// dp[status] == 0 没算过
		int[] dp = new int[1 << (choose + 1)];
		return f(choose, 0, total, dp);
	}

	public static boolean f(int choose, int status, int rest, int[] dp) {
		if (dp[status] != 0) {
			return dp[status] == 1 ? true : false;
		}
		boolean ans = false;
		if (rest > 0) {
			for (int i = 1; i <= choose; i++) {
				if (((1 << i) & status) == 0) {
					if (!f(choose, (status | (1 << i)), rest - i, dp)) {
						ans = true;
						break;
					}
				}
			}
		}
		dp[status] = ans ? 1 : -1;
		return ans;
	}

}
