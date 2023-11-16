package class075;

// 混合背包 + 多重背包普通窗口优化
// 能成功找零的钱数种类
// 每一种货币都给定面值val[i]，和拥有的数量cnt[i]
// 想知道目前拥有的货币，在钱数为1、2、3...m时
// 能找零成功的钱数有多少
// 也就是说当钱数的范围是1~m
// 返回这个范围上有多少可以找零成功的钱数
// 比如只有3元的货币，数量是5张
// m = 10
// 那么在1~10范围上，只有钱数是3、6、9时，可以成功找零
// 所以返回3表示有3种钱数可以找零成功
// 测试链接 : http://poj.org/problem?id=1742
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_MixedKnapsack {

	public static int MAXN = 101;

	public static int MAXM = 100001;

	public static int[] val = new int[MAXN];

	public static int[] cnt = new int[MAXN];

	public static boolean[] dp = new boolean[MAXM];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			if (n != 0 || m != 0) {
				for (int i = 1; i <= n; i++) {
					in.nextToken();
					val[i] = (int) in.nval;
				}
				for (int i = 1; i <= n; i++) {
					in.nextToken();
					cnt[i] = (int) in.nval;
				}
				out.println(compute());
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	// 直接提供空间压缩版
	public static int compute() {
		Arrays.fill(dp, 1, m + 1, false);
		dp[0] = true;
		for (int i = 1; i <= n; i++) {
			if (cnt[i] == 1) {
				// 01背包的空间压缩实现是从右往左更新的
				for (int j = m; j >= val[i]; j--) {
					if (dp[j - val[i]]) {
						dp[j] = true;
					}
				}
			} else if (val[i] * cnt[i] > m) {
				// 完全背包的空间压缩实现是从左往右更新的
				for (int j = val[i]; j <= m; j++) {
					if (dp[j - val[i]]) {
						dp[j] = true;
					}
				}
			} else {
				// 多重背包的空间压缩实现
				// 根据余数分组
				// 每一组都是从右往左更新的
				for (int mod = 0; mod < val[i]; mod++) {
					int trueCnt = 0;
					for (int j = m - mod, size = 0; j >= 0 && size <= cnt[i]; j -= val[i], size++) {
						trueCnt += dp[j] ? 1 : 0;
					}
					for (int j = m - mod, l = j - val[i] * (cnt[i] + 1); j >= 1; j -= val[i], l -= val[i]) {
						if (dp[j]) {
							trueCnt--;
						} else {
							if (trueCnt != 0) {
								dp[j] = true;
							}
						}
						if (l >= 0) {
							trueCnt += dp[l] ? 1 : 0;
						}
					}
				}
			}
		}
		int ans = 0;
		for (int i = 1; i <= m; i++) {
			if (dp[i]) {
				ans++;
			}
		}
		return ans;
	}

}
