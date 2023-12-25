package class067;

// 节点数为n高度不大于m的二叉树个数
// 现在有n个节点，计算出有多少个不同结构的二叉树
// 满足节点个数为n且树的高度不超过m的方案
// 因为答案很大，所以答案需要模上1000000007后输出
// 测试链接 : https://www.nowcoder.com/practice/aaefe5896cce4204b276e213e725f3ea
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_NodenHeightNotLargerThanm {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			out.println(compute3(n, m));
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int MAXN = 51;

	public static int MOD = 1000000007;

	// 记忆化搜索
	public static long[][] dp1 = new long[MAXN][MAXN];

	static {
		for (int i = 0; i < MAXN; i++) {
			for (int j = 0; j < MAXN; j++) {
				dp1[i][j] = -1;
			}
		}
	}

	// 二叉树节点数为n
	// 高度不能超过m
	// 结构数返回
	// 记忆化搜索
	public static int compute1(int n, int m) {
		if (n == 0) {
			return 1;
		}
		// n > 0
		if (m == 0) {
			return 0;
		}
		if (dp1[n][m] != -1) {
			return (int) dp1[n][m];
		}
		long ans = 0;
		// n个点，头占掉1个
		for (int k = 0; k < n; k++) {
			// 一共n个节点，头节点已经占用了1个名额
			// 如果左树占用k个，那么右树就占用i-k-1个
			ans = (ans + ((long) compute1(k, m - 1) * compute1(n - k - 1, m - 1)) % MOD) % MOD;
		}
		dp1[n][m] = ans;
		return (int) ans;
	}

	// 严格位置依赖的动态规划
	public static long[][] dp2 = new long[MAXN][MAXN];

	public static int compute2(int n, int m) {
		for (int j = 0; j <= m; j++) {
			dp2[0][j] = 1;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				dp2[i][j] = 0;
				for (int k = 0; k < i; k++) {
					// 一共i个节点，头节点已经占用了1个名额
					// 如果左树占用k个，那么右树就占用i-k-1个
					dp2[i][j] = (dp2[i][j] + dp2[k][j - 1] * dp2[i - k - 1][j - 1] % MOD) % MOD;
				}
			}
		}
		return (int) dp2[n][m];
	}

	// 空间压缩
	public static long[] dp3 = new long[MAXN];

	public static int compute3(int n, int m) {
		dp3[0] = 1;
		for (int i = 1; i <= n; i++) {
			dp3[i] = 0;
		}
		for (int j = 1; j <= m; j++) {
			// 根据依赖，一定要先枚举列
			for (int i = n; i >= 1; i--) {
				// 再枚举行，而且i不需要到达0，i>=1即可
				dp3[i] = 0;
				for (int k = 0; k < i; k++) {
					// 枚举
					dp3[i] = (dp3[i] + dp3[k] * dp3[i - k - 1] % MOD) % MOD;
				}
			}
		}
		return (int) dp3[n];
	}

}
