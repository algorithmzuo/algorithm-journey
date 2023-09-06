package class051;

// 爱吃香蕉的珂珂
// 珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉
// 警卫已经离开了，将在 h 小时后回来。
// 珂珂可以决定她吃香蕉的速度 k （单位：根/小时)
// 每个小时，她将会选择一堆香蕉，从中吃掉 k 根
// 如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉
// 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
// 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）
// 测试链接 : https://leetcode.cn/problems/koko-eating-bananas/
public class Code01_KokoEatingBananas {

	// 时间复杂度O(n * log(max))，额外空间复杂度O(1)
	public static int minEatingSpeed(int[] piles, int h) {
		int l = 1;
		int r = 0;
		for (int pile : piles) {
			r = Math.max(r, pile);
		}
		int ans = 0;
		int m = 0;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (f(piles, m) <= h) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static long f(int[] piles, int speed) {
		long ans = 0;
		for (int pile : piles) {
			ans += (pile + speed - 1) / speed;
		}
		return ans;
	}

}
