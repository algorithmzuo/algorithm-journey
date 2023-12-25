package class065;

// Bellman-Ford + SPFA优化模版（洛谷）
// 给定一个 n个点的有向图，请求出图中是否存在从顶点 1 出发能到达的负环
// 负环的定义是：一条边权之和为负数的回路。
// 测试链接 : https://www.luogu.com.cn/problem/P3385
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_SPFA {

	public static int MAXN = 2001;

	public static int MAXM = 6001;

	// 链式前向星建图需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// SPFA需要
	public static int MAXQ = 4000001;

	// 源点出发到每个节点的距离表
	public static int[] distance = new int[MAXN];

	// 节点被松弛的次数
	public static int[] updateCnt = new int[MAXN];

	// 哪些节点被松弛了放入队列
	public static int[] queue = new int[MAXQ];

	public static int l, r;

	// 节点是否已经在队列中
	public static boolean[] enter = new boolean[MAXN];

	public static void build(int n) {
		cnt = 1;
		l = r = 0;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(enter, 1, n + 1, false);
		Arrays.fill(distance, 1, n + 1, Integer.MAX_VALUE);
		Arrays.fill(updateCnt, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int i = 0, n, m; i < cases; i++) {
			in.nextToken(); n = (int) in.nval;
			in.nextToken(); m = (int) in.nval;
			build(n);
			for (int j = 0, u, v, w; j < m; j++) {
				in.nextToken(); u = (int) in.nval;
				in.nextToken(); v = (int) in.nval;
				in.nextToken(); w = (int) in.nval;
				if (w >= 0) {
					addEdge(u, v, w);
					addEdge(v, u, w);
				} else {
					addEdge(u, v, w);
				}
			}
			out.println(spfa(n) ? "YES" : "NO");
		}
		out.flush();
		out.close();
		br.close();
	}

	// Bellman-Ford + SPFA优化的模版
	public static boolean spfa(int n) {
		distance[1] = 0;
		updateCnt[1]++;
		queue[r++] = 1;
		enter[1] = true;
		while (l < r) {
			int u = queue[l++];
			enter[u] = false;
			for (int ei = head[u], v, w; ei > 0; ei = next[ei]) {
				v = to[ei];
				w = weight[ei];
				if (distance[u] + w < distance[v]) {
					distance[v] = distance[u] + w;
					if (!enter[v]) {
						if (updateCnt[v]++ == n) {
							return true;
						}
						queue[r++] = v;
						enter[v] = true;
					}
				}
			}
		}
		return false;
	}

}
