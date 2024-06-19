package class130;

// 不超过连续k个元素的最大累加和
// 给定一个长度为n的数组arr，你可以随意选择数字
// 要求选择的方案中，连续选择的个数不能超过k个
// 返回能得到的最大累加和
// 1 <= n、k <= 10^5
// 0 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P2627
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_ChooseLimitMaximumSum {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static long[] sum = new long[MAXN];

	public static long[] dp = new long[MAXN];

	public static int[] queue = new int[MAXN];

	public static int l, r;

	public static int n, k;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		for (int i = 1; i <= n; i++) {
			sum[i] = sum[i - 1] + arr[i];
		}
		l = r = 0;
		queue[r++] = 0;
		for (int i = 1; i <= n; i++) {
			while (l < r && value(queue[r - 1]) <= value(i)) {
				r--;
			}
			queue[r++] = i;
			if (l < r && queue[l] == i - k - 1) {
				l++;
			}
			dp[i] = value(queue[l]) + sum[i];
		}
		return dp[n];
	}

	// 不要i位置的数字产生的指标
	public static long value(int i) {
		return i == 0 ? 0 : (dp[i - 1] - sum[i]);
	}

}
