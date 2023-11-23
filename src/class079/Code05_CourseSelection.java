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

// dfn序 + 背包 + 树型dp
// 觉得难可以跳过
// 目前几乎所有题解，都不是最优解的方法
// 不是最优解的做法不再介绍，只讲最优解
// 最优解时间复杂度O(n*m)
public class Code05_CourseSelection {

	public static int MAXN = 301;

	public static int[] nums = new int[MAXN];

	// 链式前向星建图
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int cnt;

	// dfn序对应的真实节点的编号
	// 原始节点编号最大范围1~300
	// 因为补充了0号节点，所以节点编号最大范围0~300
	// 因为dfn序从1开始，所以节点编号最大范围0~300，对应dfn序最大范围1~301
	// 所以node数组长度为MAXN+1
	public static int[] node = new int[MAXN + 1];

	// dfn的计数
	public static int dfnCnt;

	// 每个节点子树的大小
	public static int[] size = new int[MAXN];

	// 上面已经说了，dfn序最大范围1~301
	// dfn序301对应的节点，是树上最后的叶节点，后续就不再有节点了
	// 但是dp[i][j]依赖dp[i + size[i]][j]、dp[i + 1][j - 1]
	// 所以再补一个dp[302][...]，表示没有任何节点的情况
	// 于是dp表最大是MAXN+2行
	public static int[][] dp = new int[MAXN + 2][MAXN];

	public static int n, m;

	public static void build(int n) {
		cnt = 1;
		dfnCnt = 0;
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
			m = (int) in.nval;
			build(n);
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
		n++;
		f(0);
		// 接下来的逻辑其实就是01背包！不过经历了很多转化
		// 整体的顺序是根据dfn序来进行的
		// dp[i][j] : i ~ n范围上，一定要形成有效结构的情况下，选择j个节点最大收益是多少
		// 什么是有效结构？
		// 就是依次出现更上方节点的时候，不会出现跳跃连接的结构，课上讲的内容
		Arrays.fill(dp[n + 1], 0); // dp[n+1][....]所有值都是0，表示没有任何节点的情况
		for (int i = n, cur; i >= 2; i--) {
			for (int j = 1; j <= m; j++) {
				cur = node[i];
				dp[i][j] = Math.max(dp[i + size[cur]][j], nums[cur] + dp[i + 1][j - 1]);
			}
		}
		// dp[2][m] : 2 ~ n范围上，形成有效结构的情况下，选择m个节点最大收益是多少
		// 最后来到dfn序为1的节点，一定是原始的0号节点
		// 所以选择0号节点，0号节点下方挂着有效结构，于是整个问题解决
		return nums[0] + dp[2][m];
	}

	public static void f(int u) {
		node[++dfnCnt] = u;
		size[u] = 1;
		for (int ei = head[u], v; ei > 0; ei = next[ei]) {
			v = to[ei];
			f(v);
			size[u] += size[v];
		}
	}

}