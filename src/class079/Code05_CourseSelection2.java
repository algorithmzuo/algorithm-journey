package class079;

// 选课
// 在大学里每个学生，为了达到一定的学分，必须从很多课程里选择一些课程来学习
// 在课程里有些课程必须在某些课程之前学习，如高等数学总是在其它课程之前学习
// 现在有 N 门功课，每门课有个学分，每门课有一门或没有直接先修课
// 若课程 a 是课程 b 的先修课即只有学完了课程 a，才能学习课程 b
// 一个学生要从这些课程里选择 M 门课程学习
// 问他能获得的最大学分是多少
// 测试链接 : https://www.luogu.com.cn/problem/P2014
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

// 链式前向星建图 + 空间压缩优化的动态规划
public class Code05_CourseSelection2 {

	public static int MAXN = 301;

	public static int[] nums = new int[MAXN];

	// 链式前向星建图
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int cnt;

	// 动态规划表
	public static int[][] dp = new int[MAXN][MAXN];

	public static int n, m;

	public static void build(int n, int m) {
		cnt = 1;
		Arrays.fill(head, 0, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval + 1;
			build(n, m);
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				addEdge((int) in.nval, i);
				in.nextToken();
				nums[i] = (int) in.nval;
			}
			compute();
			out.println(dp[0][m]);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void compute() {
		for (int i = 1; i <= n; i++) {
			dp[i][1] = nums[i];
		}
		dp(0);
	}

	// (i, j, k) 依赖 :
	// (i, j-1, k)
	// (i, j-1, k-s) + (v, v固定的所有节点数量, s)
	public static void dp(int i) {
		for (int ei = head[i], v; ei > 0; ei = next[ei]) {
			// dp[i][j][k]，这个ei的变动等同于j++
			// 根据依赖关系，j层的表出来，去堆j+1层的表
			v = to[ei];
			dp(v);
			for (int k = m + 1; k >= 2; k--) {
				// 根据依赖(i, j, k)依赖(i, j-1, k-s)的值
				// 所以k要从大到小遍历，因为这样遍历的时候
				// dp[i][k-s]是j-1层的dp[i][k-s]的值
				for (int s = 1; s < k; s++) {
					// dp[i][j][k]其中一个分支 :
					// dp[i][j-1][k-s] + dp[v][v固定的所有节点数量][s]
					// 只不过做了空间压缩
					dp[i][k] = Math.max(dp[i][k], dp[i][k - s] + dp[v][s]);
				}
			}
		}
	}

}
