package class128;

// 苹果和盘子
// 有m个苹果，认为苹果之间无差别，有n个盘子，认为盘子之间无差别
// 比如5个苹果如果放进3个盘子，那么(1, 3, 1) (1, 1, 3) (3, 1, 1)认为是同一种方法
// 允许有些盘子是空的，返回有多少种放置方法
// 1 <= m, n <= 10^3
// 测试链接 : https://www.nowcoder.com/practice/bfd8234bb5e84be0b493656e390bdebf
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_ApplesPlates {

	public static int MAXM = 11;

	public static int MAXN = 11;

	public static int[][] dp = new int[MAXM][MAXN];

	public static int m, n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		m = (int) in.nval;
		in.nextToken();
		n = (int) in.nval;
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				dp[i][j] = -1;
			}
		}
		return f(m, n);
	}

	// 如果苹果数为0，有1种摆法：什么也不摆
	// 如果苹果数不为0，但是盘子数为0，有0种摆法（做不到）
	// 如果苹果数不为0，盘子数也不为0，进行如下的情况讨论：
	// 假设苹果数为apples，盘子数为plates
	// 可能性 1) apples < plates
	// 这种情况下，一定有多余的盘子，这些盘子完全没用，所以砍掉
	// 后续是f(apples, apples)
	// 可能性 2) apples >= plates
	// 在可能性2)下，讨论摆法，有如下两种选择
	// 选择a) 不是所有的盘子都使用
	// 选择b) 就是所有的盘子都使用
	// 对于选择a)，既然不是所有盘子都使用，那么后续就是f(apples, plates - 1)
	// 意思是：既然不是所有盘子都使用，那盘子减少一个，然后继续讨论吧！
	// 对于选择b)，既然就是所有的盘子都使用，那么先把所有盘子都摆上1个苹果。
	// 剩余苹果数 = apples - plates
	// 然后继续讨论，剩下的这些苹果，怎么摆进plates个盘子里，
	// 所以后续是f(apples - plates, plates)
	public static int f(int apples, int plates) {
		if (dp[apples][plates] != -1) {
			return dp[apples][plates];
		}
		int ans = 0;
		if (apples == 0) {
			ans = 1;
		} else if (plates == 0) {
			ans = 0;
		} else if (plates > apples) {
			ans = f(apples, apples);
		} else {
			ans = f(apples, plates - 1) + f(apples - plates, plates);
		}
		dp[apples][plates] = ans;
		return ans;
	}

}