package class121;

// 消防(递归版)
// 一共n个节点，编号1~n，有n-1条边连接成一棵树，每条边上有非负权值
// 给定一个非负整数s，表示可以在树上选择一条长度不超过s的路径
// 然后在这条路径的点上建立消防站，每个居民可以去往这条路径上的任何消防站
// 目标：哪怕最远的居民走到消防站的距离也要尽量少
// 返回最远居民走到消防站的最短距离
// 测试链接 : https://www.luogu.com.cn/problem/P2491
// 提交以下的code，提交时请把类名改成"Main"
// C++这么写能通过，java会因为递归层数太多而爆栈
// java能通过的写法参考本节课Code05_FireFighting2文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_FireFighting1 {

	public static int MAXN = 300001;

	public static int n;

	public static int s;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static int start;

	public static int end;

	public static int diameter;

	public static int[] dist = new int[MAXN];

	public static int[] last = new int[MAXN];

	// pred[i] : i节点在直径上，和前一个点之间的距离，以start做根
	public static int[] pred = new int[MAXN];

	public static boolean[] diameterPath = new boolean[MAXN];

	public static int[] maxDist = new int[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(diameterPath, 1, n, false);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void road() {
		dfs(1, 0, 0);
		start = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[start]) {
				start = i;
			}
		}
		dfs(start, 0, 0);
		end = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[end]) {
				end = i;
			}
		}
		diameter = dist[end];
	}

	public static void dfs(int u, int f, int w) {
		last[u] = f;
		dist[u] = dist[f] + w;
		pred[u] = w;
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs(to[e], u, weight[e]);
			}
		}
	}

	public static void distance() {
		for (int i = end; i != 0; i = last[i]) {
			diameterPath[i] = true;
		}
		for (int i = end; i != 0; i = last[i]) {
			maxDist[i] = maxDistanceExceptDiameter(i, 0, 0);
		}
	}

	// 不能走向直径路径上的节点
	// 能走出的最大距离
	public static int maxDistanceExceptDiameter(int u, int f, int c) {
		int ans = c;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (!diameterPath[v] && v != f) {
				ans = Math.max(ans, maxDistanceExceptDiameter(v, u, c + weight[e]));
			}
		}
		return ans;
	}

	// 单调队列维护窗口内最大值
	// 不会的看讲解054
	public static int[] queue = new int[MAXN];

	public static int compute() {
		int suml = 0, sumr = 0;
		// 用h和t表示单调队列的头和尾
		int h = 0, t = 0;
		int ans = Integer.MAX_VALUE;
		// 窗口范围[l,r)，左闭右开，直径上的窗口[l...r-1]
		// l是窗口左端点，r是窗口右端点的再下一个点
		// 课上图解是从start到end，实际是从end到start，思路没有区别
		for (int l = end, r = end; l != 0; l = last[l]) {
			while (r != 0 && sumr - suml + pred[r] <= s) {
				while (h < t && maxDist[queue[t - 1]] <= maxDist[r]) {
					t--;
				}
				sumr += pred[r];
				queue[t++] = r;
				r = last[r];
			}
			ans = Math.min(ans, Math.max(Math.max(suml, maxDist[queue[h]]), diameter - sumr));
			if (queue[h] == l) {
				h++;
			}
			suml += pred[l];
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		s = (int) in.nval;
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
		}
		road();
		distance();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
