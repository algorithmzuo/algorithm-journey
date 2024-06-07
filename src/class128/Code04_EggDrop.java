package class128;

// 大楼扔鸡蛋问题
// 一共有k枚相同的鸡蛋，一共有n层楼
// 已知一定存在楼层f(0<=f<=n)，从>f的楼层扔鸡蛋一定会碎，从<=f的楼层扔鸡蛋，扔几次都不会碎
// 鸡蛋一旦碎了就不能再使用，只能选择另外的鸡蛋
// 现在想确定f的值，返回最少扔几次鸡蛋，可以确保测出该值
// 1 <= k <= 100
// 1 <= n <= 10^4
// 测试链接 : https://leetcode.cn/problems/super-egg-drop/
public class Code04_EggDrop {

	public static int superEggDrop(int k, int n) {
		if (k == 1) {
			return n;
		}
		int log = log(n);
		if (k >= log) {
			return log;
		}
		int[] dp = new int[k];
		int pre, tmp, ans = 0;
		while (dp[k - 1] < n) {
			ans++;
			pre = 0;
			for (int i = 0; i < k; i++) {
				tmp = dp[i];
				dp[i] = dp[i] + pre + 1;
				pre = tmp;
			}
		}
		return ans;
	}

	public static int log(int n) {
		int ans = 0;
		while (n != 0) {
			ans++;
			n >>= 1;
		}
		return ans;
	}

}
