package class095;

// 尼姆博弈
// 一共有 n 堆石头，两人轮流进行游戏
// 在每个玩家的回合中，玩家需要 选择任一 非空 石头堆，从中移除任意 非零 数量的石头
// 如果不能移除任意的石头，就输掉游戏
// 返回先手是否有必胜策略
// 测试链接 : https://leetcode.cn/problems/game-of-nim/
public class Code03_NimGame {

	public boolean nimGame(int[] piles) {
		int eor = 0;
		for (int num : piles) {
			eor ^= num;
		}
		return eor != 0;
	}

}
