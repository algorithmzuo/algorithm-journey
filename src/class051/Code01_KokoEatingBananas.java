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

	public static int minEatingSpeed(int[] piles, int h) {
		int L = 1;
		int R = 0;
		for (int pile : piles) {
			R = Math.max(R, pile);
		}
		int ans = 0;
		int M = 0;
		while (L <= R) {
			M = L + ((R - L) >> 1);
			if (hours(piles, M) <= h) {
				ans = M;
				R = M - 1;
			} else {
				L = M + 1;
			}
		}
		return ans;
	}

	public static long hours(int[] piles, int speed) {
		long ans = 0;
		int offset = speed - 1;
		for (int pile : piles) {
			ans += (pile + offset) / speed;
		}
		return ans;
	}

}
