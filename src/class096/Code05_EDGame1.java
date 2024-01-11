package class096;

// 计算两堆石子的SG值
// 桌上有两堆石子，石头数量分别为a、b
// 任取一堆石子，将其移走，然后分割同一组的另一堆石子
// 从中取出若干个石子放在被移走的位置，组成新的一堆
// 操作完成后，组内每堆的石子数必须保证大于0
// 显然，被分割的一堆的石子数至少要为2
// 两个人轮流进行分割操作，如果轮到某人进行操作时，两堆石子数均为1，判此人输掉比赛
// 计算sg[a][b]的值并找到简洁规律
// 本文件仅为题目5打表找规律的代码
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

	public static void f1() {
		System.out.println("石子数9以内所有组合的sg值");
		System.out.println();
		System.out.print("    ");
		for (int i = 1; i <= 9; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		for (int a = 1; a <= 9; a++) {
			System.out.print(a + "   ");
			for (int b = 1; b < a; b++) {
				System.out.print("X ");
			}
			for (int b = a; b <= 9; b++) {
				int sg = sg(a, b);
				System.out.print(sg + " ");
			}
			System.out.println();
		}
	}

	public static void f2() {
		System.out.println("石子数9以内所有组合的sg值，但是行列都-1");
		System.out.println();
		System.out.print("    ");
		for (int i = 0; i <= 8; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		for (int a = 1; a <= 9; a++) {
			System.out.print((a - 1) + "   ");
			for (int b = 1; b < a; b++) {
				System.out.print("X ");
			}
			for (int b = a; b <= 9; b++) {
				int sg = sg(a, b);
				System.out.print(sg + " ");
			}
			System.out.println();
		}
	}

	public static void f3() {
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

	// 返回status最低位的0在第几位
	public static int lowZero(int status) {
		int cnt = 0;
		while (status > 0) {
			if ((status & 1) == 0) {
				break;
			}
			status >>= 1;
			cnt++;
		}
		return cnt;
	}

	public static void main(String[] args) {
		build();
		f1();
		System.out.println();
		System.out.println();
		f2();
		System.out.println();
		System.out.println();
		f3();
	}

}
