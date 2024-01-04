package class093;

// 过河问题
// 一共n人出游，他们走到一条河的西岸，想要过河到东岸
// 西岸边有一条船，一次只能乘坐两人，每个人都有一个渡河时间ti
// 船划到对岸的时间等于船上渡河时间较长的人所用时间
// 最少要花费多少时间，才能使所有人都过河
// 测试链接 : https://www.luogu.com.cn/problem/P1809
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

public class Code03_CrossRiver {

	public static int MAXN = 100001;

	public static int[] nums = new int[MAXN];

	public static int[] dp = new int[MAXN];

	public static int n;

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
			out.println(minCost());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int minCost() {
		Arrays.sort(nums, 0, n);
		if (n >= 1) {
			dp[0] = nums[0];
		}
		if (n >= 2) {
			dp[1] = nums[1];
		}
		if (n >= 3) {
			dp[2] = nums[0] + nums[1] + nums[2];
		}
		for (int i = 3; i < n; i++) {
			dp[i] = Math.min(dp[i - 2] + nums[1] + nums[0] + nums[i] + nums[1], dp[i - 1] + nums[i] + nums[0]);
		}
		return dp[n - 1];
	}

}