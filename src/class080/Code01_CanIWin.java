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

	public static boolean canIWin(int n, int m) {
		if (m == 0) {
			return true;
		}
		if ((n * (n + 1) >> 1) < m) {
			return false;
		}
		// dp[status] == 1 算过，答案是true
		// dp[status] == -1 算过，答案是false
		// dp[status] == 0 没算过
		int[] dp = new int[1 << (n + 1)];
		return f(n, (1 << (n + 1)) - 1, m, dp);
	}

	public static boolean f(int n, int status, int rest, int[] dp) {
		if (rest <= 0) {
			return false;
		}
		if (dp[status] != 0) {
			return dp[status] == 1;
		}
		boolean ans = false;
		for (int i = 1; i <= n; i++) {
			if ((status & (1 << i)) != 0 && !f(n, (status ^ (1 << i)), rest - i, dp)) {
				ans = true;
				break;
			}
		}
		dp[status] = ans ? 1 : -1;
		return ans;
	}

}
