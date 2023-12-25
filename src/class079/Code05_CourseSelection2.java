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

// 最优解，链式前向星建图 + dfn序的利用 + 巧妙定义下的尝试
// 时间复杂度O(n*m)，觉得难可以跳过，这个最优解是非常巧妙和精彩的！
public class Code05_CourseSelection2 {

	public static int MAXN = 301;

	public static int[] nums = new int[MAXN];

	// 链式前向星建图
	public static int edgeCnt;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	// dfn的计数
	public static int dfnCnt;

	// 下标为dfn序号
	public static int[] val = new int[MAXN + 1];

	// 下标为dfn序号
	public static int[] size = new int[MAXN + 1];

	// 动态规划表
	public static int[][] dp = new int[MAXN + 2][MAXN];

	public static int n, m;

	public static void build(int n, int m) {
		edgeCnt = 1;
		dfnCnt = 0;
		Arrays.fill(head, 0, n + 1, 0);
		Arrays.fill(dp[n + 2], 0, m + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[edgeCnt] = head[u];
		to[edgeCnt] = v;
		head[u] = edgeCnt++;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			build(n, m);
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				addEdge((int) in.nval, i);
				in.nextToken();
				nums[i] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		f(0);
		// 节点编号0 ~ n，dfn序号范围1 ~ n+1
		// 接下来的逻辑其实就是01背包！不过经历了很多转化
		// 整体的顺序是根据dfn序来进行的，从大的dfn序，遍历到小的dfn序
		// dp[i][j] : i ~ n+1 范围的节点，选择j个节点一定要形成有效结构的情况下，最大的累加和
		// 怎么定义有效结构？重点！重点！重点！
		// 假设i ~ n+1范围上，目前所有头节点的上方，有一个总的头节点
		// i ~ n+1范围所有节点，选出来j个节点的结构，
		// 挂在这个假想的总头节点之下，是一个连续的结构，没有断开的情况
		// 那么就说，i ~ n+1范围所有节点，选出来j个节点的结构是一个有效结构
		for (int i = n + 1; i >= 2; i--) {
			for (int j = 1; j <= m; j++) {
				dp[i][j] = Math.max(dp[i + size[i]][j], val[i] + dp[i + 1][j - 1]);
			}
		}
		// dp[2][m] : 2 ~ n范围上，选择m个节点一定要形成有效结构的情况下，最大的累加和
		// 最后来到dfn序为1的节点，一定是原始的0号节点
		// 原始0号节点下方一定挂着有效结构
		// 并且和补充的0号节点一定能整体连在一起，没有任何跳跃连接
		// 于是整个问题解决
		return nums[0] + dp[2][m];
	}

	// u这棵子树的节点数返回
	public static int f(int u) {
		int i = ++dfnCnt;
		val[i] = nums[u];
		size[i] = 1;
		for (int ei = head[u], v; ei > 0; ei = next[ei]) {
			v = to[ei];
			size[i] += f(v);
		}
		return size[i];
	}

}