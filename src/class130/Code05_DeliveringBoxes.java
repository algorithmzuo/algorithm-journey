package class130;

// 送箱子到码头的最少行程数
// 一共有m个码头，编号1 ~ m， 给定长度为n的二维数组boxes
// boxes[i][0]表示i号箱子要送往的码头，boxes[i][1]表示i号箱子重量
// 有一辆马车，一次最多能装a个箱子并且箱子总重量不能超过b
// 马车一开始在仓库，可以在0位置，马车每开动一次，认为行程+1
// 箱子必须按照boxes规定的顺序被放上马车，也必须按照顺序被送往各自的码头
// 马车上相邻的箱子如果去往同一个码头，那么认为共享同一趟行程
// 马车可能经过多次送货，每次装货需要回到仓库，认为行程+1，送完所有的货，最终要回到仓库，行程+1
// 返回至少需要几个行程能把所有的货都送完
// 所有数据的范围 <= 10^5
// 测试链接 : https://leetcode.cn/problems/delivering-boxes-from-storage-to-ports/
public class Code05_DeliveringBoxes {

	public static int boxDelivering(int[][] boxes, int m, int a, int b) {
		int n = boxes.length;
		// dp[i] : 马车拉完前i个货物并回仓库，需要的最少行程
		int[] dp = new int[n + 1];
		dp[1] = 2;
		// 马车最后一趟的窗口[l...r]
		// 窗口内重量weight，窗口内需要的行程trip
		for (int l = 0, r = 1, weight = boxes[0][1], trip = 2; r < n; r++) {
			weight += boxes[r][1];
			if (boxes[r][0] != boxes[r - 1][0]) {
				trip++;
			}
			// 1) 最后一趟货物的个数超了、总重量超了，最后一趟需要减少货物
			// 2) 增加之前的货物范围，发现dp值没变化，那就把更多的货，分给之前去拉，最后一趟减少货物
			while (r - l + 1 > a || weight > b || dp[l] == dp[l + 1]) {
				weight -= boxes[l++][1];
				if (boxes[l][0] != boxes[l - 1][0]) {
					trip--;
				}
			}
			dp[r + 1] = dp[l] + trip;
		}
		return dp[n];
	}

}
