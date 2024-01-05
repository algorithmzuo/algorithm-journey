package class094;

// 砍树
// 一共有n棵树，每棵树都有两个信息：
// 第一天这棵树的初始重量、这棵树每天的增长重量
// 你每天最多能砍1棵树，砍下这棵树的收益为：
// 这棵树的初始重量 + 这棵树增长到这一天的总增重
// 从第1天开始，你一共有m天可以砍树，返回m天内你获得的最大收益
// 测试链接 : https://pintia.cn/problem-sets/91827364500/exam/problems/91827367873
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

public class Code05_CuttingTree {

	// 树的个数、天数最大值，不超过的量
	public static int MAXN = 251;

	// 树的编号为1 ~ n
	// tree[i][0] : 第i棵树第一天的初始重量
	// tree[i][1] : 第i棵树每天的增长重量
	public static int[][] tree = new int[MAXN][2];

	// dp[i][j] : 在j天内，从前i棵树中选若干棵树进行砍伐，最大收益是多少
	public static int[][] dp = new int[MAXN][MAXN];

	public static int t, n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		t = (int) in.nval;
		for (int i = 1; i <= t; i++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int j = 1; j <= n; j++) {
				in.nextToken();
				tree[j][0] = (int) in.nval;
			}
			for (int j = 1; j <= n; j++) {
				in.nextToken();
				tree[j][1] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 讲解073 - 01背包
	public static int compute() {
		// 树的初始重量不决定树的顺序，因为任何树砍了，就获得固定的初始量，和砍伐的顺序无关
		// 根据增长速度排序，增长量小的在前，增长量大的在后
		// 认为越靠后的树，越要尽量晚的砍伐，课上的重点内容
		Arrays.sort(tree, 1, n + 1, (o1, o2) -> o1[1] - o2[1]);
		// dp[0][...] = 0 : 表示如果没有树，不管过去多少天，收益都是0
		// dp[...][0] = 0 : 表示不管有几棵树，没有时间砍树，收益都是0
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + tree[i][0] + tree[i][1] * (j - 1));
			}
		}
		return dp[n][m];
	}

}