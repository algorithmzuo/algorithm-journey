package class128;

// 测试链接 : https://leetcode.cn/problems/super-egg-drop/
public class Code05_EggDrop {

	public static int superEggDrop(int k, int n) {
		if (k < 1 || n < 1) {
			return 0;
		}
		int time = log(n) + 1;
		if (k >= time) {
			return time;
		}
		int[] dp = new int[k];
		int ans = 0, pre, tmp;
		while (true) {
			ans++;
			pre = 0;
			for (int i = 0; i < k; i++) {
				tmp = dp[i];
				dp[i] = dp[i] + pre + 1;
				pre = tmp;
				if (dp[i] >= n) {
					return ans;
				}
			}
		}
	}

	public static int log(int n) {
		int ans = -1;
		while (n != 0) {
			ans++;
			n >>= 1;
		}
		return ans;
	}

}
