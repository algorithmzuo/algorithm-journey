package class128;

// 大楼扔鸡蛋问题
// 一共有k枚相同的鸡蛋，一共有n层楼，已知存在楼层f(0<=f<=n)
// 从>f的楼层扔鸡蛋一定会碎，从<=f的楼层扔鸡蛋，同一个鸡蛋扔几次都不会碎
// 鸡蛋一旦碎了，就不能再使用了，只能选择另外的鸡蛋
// 现在想确定f的值，返回最少扔几次鸡蛋一定可以测出该值
// 测试链接 : https://leetcode.cn/problems/super-egg-drop/
public class Code06_EggDrop {

	public static int superEggDrop(int k, int n) {
		if (k < 1 || n < 1) {
			return 0;
		}
		int time = bs(n) + 1;
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

	public static int bs(int n) {
		int ans = -1;
		while (n != 0) {
			ans++;
			n >>= 1;
		}
		return ans;
	}

}
