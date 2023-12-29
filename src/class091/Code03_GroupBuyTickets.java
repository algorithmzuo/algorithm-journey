package class091;

import java.util.PriorityQueue;

// 组团买票
// 景区里一共有m个项目，景区的第i个项目有如下两个参数：
// game[i] = { Ki, Bi }，Ki、Bi一定是正数
// Ki代表折扣系数，Bi代表票价
// 举个例子 : Ki = 2, Bi = 10
// 如果只有1个人买票，单张门票的价格为 : Bi - Ki * 1 = 8
// 所以这1个人游玩该项目要花8元
// 如果有2个人买票，单张门票的价格为 : Bi - Ki * 2 = 6
// 所以这2个人游玩该项目要花6 * 2 = 12元
// 如果有5个人买票，单张门票的价格为 : Bi - Ki * 5 = 0
// 所以这5个人游玩该项目要花5 * 0 = 0元
// 如果有更多人买票，都认为花0元(因为让项目倒贴钱实在是太操蛋了)
// 于是可以认为，如果有x个人买票，单张门票的价格为 : Bi - Ki * x
// x个人游玩这个项目的总花费是 : max { x * (Bi - Ki * x), 0 }
// 单位一共有n个人，每个人最多可以选1个项目来游玩，也可以不选任何项目
// 所有员工将在明晚提交选择，然后由你去按照上面的规则，统一花钱购票
// 你想知道自己需要准备多少钱，就可以应付所有可能的情况，返回这个最保险的钱数
// 数据量描述 : 
// 1 <= M、N、Ki、Bi <= 10^5
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code03_GroupBuyTickets {

	// 暴力方法
	// 为了验证
	// 每个人做出所有可能的选择
	// 时间复杂度O((m+1)的n次方)
	public static int enough1(int n, int[][] games) {
		int m = games.length;
		int[] cnts = new int[m];
		return f(0, n, m, games, cnts);
	}

	public static int f(int i, int n, int m, int[][] games, int[] cnts) {
		if (i == n) {
			int ans = 0;
			for (int j = 0, k, b, x; j < m; j++) {
				k = games[j][0];
				b = games[j][1];
				x = cnts[j];
				ans += Math.max((b - k * x) * x, 0);
			}
			return ans;
		} else {
			int ans = f(i + 1, n, m, games, cnts);
			for (int j = 0; j < m; j++) {
				cnts[j]++;
				ans = Math.max(ans, f(i + 1, n, m, games, cnts));
				cnts[j]--;
			}
			return ans;
		}
	}

	// 正式方法
	// 时间复杂度O(n * logm)
	public static int enough2(int n, int[][] games) {
		// 哪个项目，再来一人挣得最多
		// 大根堆
		PriorityQueue<Game> heap = new PriorityQueue<>((a, b) -> b.earn() - a.earn());
		for (int[] g : games) {
			heap.add(new Game(g[0], g[1]));
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			// 一个个的人，依次送到当前最挣钱的项目里去
			if (heap.peek().earn() <= 0) {
				break;
			}
			Game cur = heap.poll();
			ans += cur.earn();
			cur.people++;
			heap.add(cur);
		}
		return ans;
	}

	public static class Game {
		public int ki; // 折扣系数
		public int bi; // 门票原价
		public int people; // 之前的人数

		public Game(int k, int b) {
			ki = k;
			bi = b;
			people = 0;
		}

		// 如果再来一人，这个项目得到多少钱
		public int earn() {
			// bi - (people + 1) * ki : 当前的人，门票原价减少了，当前的门票价格
			// people * ki : 当前人的到来，之前的所有人，门票价格都再减去ki
			return bi - (people + 1) * ki - people * ki;
		}

	}

	// 为了验证
	public static int[][] randomGames(int m, int v) {
		int[][] ans = new int[m][2];
		for (int i = 0; i < m; i++) {
			ans[i][0] = (int) (Math.random() * v) + 1;
			ans[i][1] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	// 为了验证
	public static void main(String[] args) {
		int N = 8;
		int M = 8;
		int V = 20;
		int testTimes = 2000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int m = (int) (Math.random() * M) + 1;
			int[][] games = randomGames(m, V);
			int ans1 = enough1(n, games);
			int ans2 = enough2(n, games);
			if (ans1 != ans2) {
				System.out.println("出错了！");
			}
			if (i % 100 == 0) {
				System.out.println("测试到第" + i + "组");
			}
		}
		System.out.println("测试结束");
	}

}
