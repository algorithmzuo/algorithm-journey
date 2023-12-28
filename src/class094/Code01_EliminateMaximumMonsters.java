package class094;

import java.util.Arrays;

// 消灭怪物的最大数量
// 你正在玩一款电子游戏，在游戏中你需要保护城市免受怪物侵袭
// 给定一个下标从0开始且大小为n的整数数组dist
// 其中dist[i]是第i个怪物与城市的初始距离
// 怪物以恒定的速度走向城市，每个怪物的速度都以一个长度为n的整数数组speed表示
// 其中speed[i]是第i个怪物的速度
// 你有一种武器，一旦充满电，就可以消灭一个怪物
// 但是，武器需要1的时间才能充电完成
// 武器在游戏开始时是充满电的状态，怪物从0时刻开始移动
// 一旦任一怪物到达城市，你就输掉了这场游戏
// 如果某个怪物恰好在某一分钟开始时到达城市，这也会被视为输掉游戏
// 在你可以使用武器之前，游戏就会结束
// 返回在你输掉游戏前可以消灭的怪物的最大数量
// 如果你可以在所有怪物到达城市前将它们全部消灭返回n
// 测试链接 : https://leetcode.cn/problems/eliminate-maximum-number-of-monsters/
public class Code01_EliminateMaximumMonsters {

	public static int eliminateMaximum(int[] dist, int[] speed) {
		int n = dist.length;
		int[] come = new int[n];
		for (int i = 0; i < n; i++) {
			come[i] = (dist[i] + speed[i] - 1) / speed[i];
		}
		Arrays.sort(come);
		for (int i = 0; i < n; i++) {
			if (come[i] <= i) {
				return i;
			}
		}
		return n;
	}

}
