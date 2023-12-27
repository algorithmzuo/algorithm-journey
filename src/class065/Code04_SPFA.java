package class065;

// bellman-ford + SPFA优化（洛谷）
// 给定一个 n个点的有向图，请求出图中是否存在从顶点 1 出发能到达的负环
// 负环的定义是：一条边权之和为负数的回路。
// 测试链接 : https://www.luogu.com.cn/problem/P3385

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

	public static int[] distance = new int[MAXN];

	public static int[] updateCnt = new int[MAXN];

	public static int[] queue = new int[MAXQ];

	public static int l, r;

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

	// bellman-ford + SPFA
	public static boolean spfa(int n) {
		distance[1] = 0;
		updateCnt[1]++;
		queue[r++] = 1;
		enter[1] = true;
		while (l < r) {
			int u = queue[l++];
			enter[u] = false;
			for (int edge = head[u], v, w; edge > 0; edge = next[edge]) {
				v = to[edge];
				w = weight[edge];
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
