package class095;

// 巴什博弈
// 一共有n颗石子，两个人轮流拿，每次可以拿1~m颗石子
// 拿到最后一颗石子的人获胜，根据n、m返回谁赢
public class Code01_BashGame {

	public static String bashGame(int n, int m) {
		return n % (m + 1) != 0 ? "先手" : "后手";
	}

}
