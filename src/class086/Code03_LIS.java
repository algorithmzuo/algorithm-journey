package class086;

// 最长递增子序列字典序最小的结果
// 给定数组arr，设长度为n
// 输出arr的最长递增子序列
// 如果有多个答案，请输出其中字典序最小的
// 注意这道题的字典序设定（根据提交的结果推论的）：
// 每个数字看作是单独的字符，比如120认为比36的字典序大
// 保证从左到右每个数字尽量小
// 测试链接 : https://www.nowcoder.com/practice/30fb9b3cab9742ecae9acda1c75bf927
// 测试链接 : https://www.luogu.com.cn/problem/T386911
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

// 讲解072 - 最长递增子序列及其扩展
public class Code03_LIS {

	public static int MAXN = 100001;

	public static int[] nums = new int[MAXN];

	public static int[] dp = new int[MAXN];

	public static int[] ends = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static int n, k;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				nums[i] = (int) in.nval;
			}
			lis();
			for (int i = 0; i < k - 1; i++) {
				out.print(ans[i] + " ");
			}
			out.println(ans[k - 1]);
		}
		out.flush();
		out.close();
		br.close();
	}

	// nums[...]
	public static void lis() {
		k = dp();
		Arrays.fill(ans, 0, k, Integer.MAX_VALUE);
		for (int i = 0; i < n; i++) {
			if (dp[i] == k) {
				// 注意这里
				// 为什么不用判断直接设置
				// 有讲究，课上重点讲了
				ans[0] = nums[i];
			} else {
				if (ans[k - dp[i] - 1] < nums[i]) {
					// 注意这里
					// 为什么只需要判断比前一位(ans[k-dp[i]-1])大即可
					// 有讲究，课上重点讲了
					ans[k - dp[i]] = nums[i];
				}
			}
		}
	}

	// dp[i] : 必须以i位置的数字开头的情况下，最长递增子序列长度
	// 填好dp表 + 返回最长递增子序列长度
	public static int dp() {
		int len = 0;
		for (int i = n - 1, find; i >= 0; i--) {
			find = bs(len, nums[i]);
			if (find == -1) {
				ends[len++] = nums[i];
				dp[i] = len;
			} else {
				ends[find] = nums[i];
				dp[i] = find + 1;
			}
		}
		return len;
	}

	// ends[有效区]从大到小的
	// 二分的方式找<=num的最左位置
	public static int bs(int len, int num) {
		int l = 0, r = len - 1, m, ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (ends[m] <= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
