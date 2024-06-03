package class127;

// 相邻与结果不为0的最大长度
// 1 <= n <= 10^5
// arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4310
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05 {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] dp = new int[32];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 0; i < n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		Arrays.fill(dp, 0);
		int ans = 0;
		for (int i = 0, num, cur; i < n; i++) {
			num = arr[i];
			cur = 1;
			for (int j = 0; j < 31; j++) {
				if (((num >> j) & 1) == 1) {
					cur = Math.max(cur, dp[j] + 1);
				}
			}
			for (int j = 0; j < 31; j++) {
				if (((num >> j) & 1) == 1) {
					dp[j] = Math.max(dp[j], cur);
				}
			}
			ans = Math.max(ans, cur);
		}
		return ans;
	}

}
