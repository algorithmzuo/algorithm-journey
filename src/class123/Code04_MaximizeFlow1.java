package class123;

// 选择节点做根使流量和最大(递归版)
// 给定一棵n个点的树，边的边权代表流量限制
// 从边上流过的流量，不能超过流量限制
// 现在想知道以某个节点做根时，流到所有叶节点的流量，最大是多少
// 测试链接 : http://poj.org/problem?id=3585
// 提交以下的code，提交时请把类名改成"Main"
// C++这么写能通过，java会因为递归层数太多而爆栈
// java能通过的写法参考本节课Code04_MaximizeFlow2文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_MaximizeFlow1 {

	public static int MAXN = 200001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	// degree[u] : 有几条边和u节点相连
	public static int[] degree = new int[MAXN];

	// flow[u] : 从u出发流向u节点为头的子树上，所有的叶节点，流量是多少
	public static int[] flow = new int[MAXN];

	// dp[u] : 从u出发流向u节点为根的整棵树上，所有的叶节点，流量是多少
	public static int[] dp = new int[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(degree, 1, n + 1, 0);
		Arrays.fill(flow, 1, n + 1, 0);
		Arrays.fill(dp, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void dfs1(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (degree[v] == 1) {
					flow[u] += weight[e];
				} else {
					flow[u] += Math.min(flow[v], weight[e]);
				}
			}
		}
	}

	public static void dfs2(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (degree[u] == 1) {
					dp[v] = flow[v] + weight[e];
				} else {
					// uOut : u流向外的流量
					int uOut = dp[u] - Math.min(flow[v], weight[e]);
					dp[v] = flow[v] + Math.min(uOut, weight[e]);
				}
				dfs2(v, u);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int testCase = (int) in.nval;
		for (int t = 1; t <= testCase; t++) {
			in.nextToken();
			n = (int) in.nval;
			build();
			for (int i = 1, u, v, w; i < n; i++) {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				in.nextToken();
				w = (int) in.nval;
				addEdge(u, v, w);
				addEdge(v, u, w);
				degree[u]++;
				degree[v]++;
			}
			dfs1(1, 0);
			dp[1] = flow[1];
			dfs2(1, 0);
			int ans = 0;
			for (int i = 1; i <= n; i++) {
				ans = Math.max(ans, dp[i]);
			}
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
