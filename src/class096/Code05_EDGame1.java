package class096;

// E&D游戏
// 桌子上有2n堆石子，编号为1、2、3...2n
// 其中1、2为一组；3、4为一组；5、6为一组...2n-1、2n为一组
// 每组可以进行分割操作：
// 任取一堆石子，将其移走，然后分割同一组的另一堆石子
// 从中取出若干个石子放在被移走的位置，组成新的一堆
// 操作完成后，所有堆的石子数必须保证大于0
// 显然，被分割的一堆的石子数至少要为2
// 两个人轮流进行分割操作，如果轮到某人进行操作时，所有堆的石子数均为1，判此人输掉比赛
// 返回先手能不能获胜
// 测试链接 : https://www.luogu.com.cn/problem/P2148
// 本文件仅为打表找规律的实现
public class Code05_EDGame1 {

	public static int MAXN = 1001;

	public static int[][] dp = new int[MAXN][MAXN];

	public static void build() {
		for (int i = 0; i < MAXN; i++) {
			for (int j = 0; j < MAXN; j++) {
				dp[i][j] = -1;
			}
		}
	}

	public static int sg(int a, int b) {
		if (a == 1 && b == 1) {
			return 0;
		}
		if (dp[a][b] != -1) {
			return dp[a][b];
		}
		boolean[] appear = new boolean[Math.max(a, b) + 1];
		if (a > 1) {
			for (int l = 1, r = a - 1; l < a; l++, r--) {
				appear[sg(l, r)] = true;
			}
		}
		if (b > 1) {
			for (int l = 1, r = b - 1; l < b; l++, r--) {
				appear[sg(l, r)] = true;
			}
		}
		int ans = 0;
		for (int s = 0; s <= Math.max(a, b); s++) {
			if (!appear[s]) {
				ans = s;
				break;
			}
		}
		dp[a][b] = ans;
		return ans;
	}

	public static void check1() {
		System.out.println("打印石头组合的sg值");
		System.out.println();
		System.out.print("    ");
		for (int i = 1; i <= 9; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		for (int a = 1; a <= 9; a++) {
			System.out.print(a + "   ");
			for (int b = 1; b <= 9; b++) {
				int sg = sg(a, b);
				System.out.print(sg + " ");
			}
			System.out.println();
		}
	}

	public static void check2() {
		System.out.println("打印石头组合的sg值，但是行列都-1");
		System.out.println();
		System.out.print("    ");
		for (int i = 0; i <= 8; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		for (int a = 1; a <= 9; a++) {
			System.out.print((a - 1) + "   ");
			for (int b = 1; b <= 9; b++) {
				int sg = sg(a, b);
				System.out.print(sg + " ");
			}
			System.out.println();
		}
	}

	public static void check3() {
		System.out.println("测试开始");
		for (int a = 1; a < MAXN; a++) {
			for (int b = 1; b < MAXN; b++) {
				int sg1 = sg(a, b);
				int sg2 = lowZero((a - 1) | (b - 1));
				if (sg1 != sg2) {
					System.out.println("出错了!");
				}
			}
		}
		System.out.println("测试结束");
	}

	public static int lowZero(int num) {
		int cnt = 0;
		while (num > 0) {
			if ((num & 1) == 0) {
				break;
			}
			num >>= 1;
			cnt++;
		}
		return cnt;
	}

	public static void main(String[] args) {
		build();
		check1();
		System.out.println();
		System.out.println();
		check2();
		System.out.println();
		System.out.println();
		check3();
	}

}
