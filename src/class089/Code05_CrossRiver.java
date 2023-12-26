package class089;

// 过河问题
// 有一个大晴天，Oliver与同学们一共N人出游
// 他们走到一条河的东岸边，想要过河到西岸
// 而东岸边有一条小船，船太小了，一次只能乘坐两人
// 每个人都有一个渡河时间T，船划到对岸的时间等于船上渡河时间较长的人所用时间
// 现在已知N个人的渡河时间T，Oliver 想要你告诉他，他们最少要花费多少时间，才能使所有人都过河
// 注意，只有船在东岸（西岸）的人才能坐上船划到对岸
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

public class Code05_CrossRiver {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

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
				arr[i] = (int) in.nval;
			}
			int ans = minCost();
			out.println(ans);
			out.flush();
		}

	}

	public static int minCost() {
		Arrays.sort(arr, 0, n);
		if (n >= 1) {
			dp[0] = arr[0];
		}
		if (n >= 2) {
			dp[1] = arr[1];
		}
		if (n >= 3) {
			dp[2] = arr[0] + arr[1] + arr[2];
		}
		for (int i = 3; i < n; i++) {
			dp[i] = Math.min(dp[i - 2] + arr[1] + arr[0] + arr[i] + arr[1], dp[i - 1] + arr[i] + arr[0]);
		}
		return dp[n - 1];
	}

}