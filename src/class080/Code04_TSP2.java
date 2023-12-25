package class080;

// 售货员的难题 - TSP问题
// 某乡有n个村庄(1<=n<=20)，有一个售货员，他要到各个村庄去售货
// 各村庄之间的路程s(1<=s<=1000)是已知的
// 且A村到B村的路程，与B到A的路大多不同(有向带权图)
// 为了提高效率，他从商店出发到每个村庄一次，然后返回商店所在的村，
// 假设商店所在的村庄为1
// 他不知道选择什么样的路线才能使所走的路程最短
// 请你帮他选择一条最短的路
// 测试链接 : https://www.luogu.com.cn/problem/P1171
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

// 卡空间是吧？绕一下！
public class Code04_TSP2 {

	public static int MAXN = 19;

	public static int[] start = new int[MAXN];

	public static int[] back = new int[MAXN];

	// 这个图中，其实是不算起始村的，其他村庄彼此到达的路径长度
	public static int[][] graph = new int[MAXN][MAXN];

	// 不算起始村庄的
	public static int[][] dp = new int[1 << MAXN][MAXN];

	public static int n;

	public static void build() {
		for (int s = 0; s < (1 << n); s++) {
			for (int i = 0; i < n; i++) {
				dp[s][i] = -1;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval - 1;
			build();
			in.nextToken();
			for (int i = 0; i < n; i++) {
				in.nextToken();
				start[i] = (int) in.nval;
			}
			for (int i = 0; i < n; i++) {
				in.nextToken();
				back[i] = (int) in.nval;
				for (int j = 0; j < n; j++) {
					in.nextToken();
					graph[i][j] = (int) in.nval;
				}
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		int ans = Integer.MAX_VALUE;
		// 起始村无编号
		for (int i = 0; i < n; i++) {
			// 起始村 -> i号村  +  i号村出发所有村子都走最终回到起始村
			ans = Math.min(ans, start[i] + f(1 << i, i));
		}
		return ans;
	}

	// s : 不包含起始村的
	public static int f(int s, int i) {
		if (s == (1 << n) - 1) {
			return back[i];
		}
		if (dp[s][i] != -1) {
			return dp[s][i];
		}
		int ans = Integer.MAX_VALUE;
		for (int j = 0; j < n; j++) {
			if ((s & (1 << j)) == 0) {
				ans = Math.min(ans, graph[i][j] + f(s | (1 << j), j));
			}
		}
		dp[s][i] = ans;
		return ans;
	}

}
