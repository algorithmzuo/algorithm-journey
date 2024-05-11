package class121;

// 消防(迭代版)
// 一共n个节点，编号1~n，有n-1条边连接成一棵树，每条边上有非负权值
// 给定一个非负整数s，表示可以在树上选择一条长度不超过s的路径
// 然后在这条路径的点上建立消防站，居民可以去往任何消防站
// 目标是每个居民走到各自最近的消防站的总路程最短
// 返回最短总路程是多少
// 测试链接 : https://www.luogu.com.cn/problem/P2491
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_FireFighting2 {

	public static int MAXN = 300001;

	public static int n;

	public static int s;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(visited, 1, n, false);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static int start, end, diameter;

	public static int[] dist = new int[MAXN];

	public static int[] last = new int[MAXN];

	public static int[] pred = new int[MAXN];

	public static void road() {
		dfs1(1);
		start = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[start]) {
				start = i;
			}
		}
		dfs1(start);
		end = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[end]) {
				end = i;
			}
		}
		diameter = dist[end];
	}

	// dfs1方法改迭代版
	// 不会改看讲解118，讲了怎么从递归版改成迭代版
	public static int[][] ufwe = new int[MAXN][4];

	public static int stackSize;

	public static int u, f, w, e;

	public static void push(int u, int f, int w, int e) {
		ufwe[stackSize][0] = u;
		ufwe[stackSize][1] = f;
		ufwe[stackSize][2] = w;
		ufwe[stackSize][3] = e;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufwe[stackSize][0];
		f = ufwe[stackSize][1];
		w = ufwe[stackSize][2];
		e = ufwe[stackSize][3];
	}

	public static void dfs1(int root) {
		stackSize = 0;
		push(root, 0, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				last[u] = f;
				dist[u] = dist[f] + w;
				pred[u] = w;
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, w, e);
				if (to[e] != f) {
					push(to[e], u, weight[e], -1);
				}
			}
		}
	}

	public static boolean[] visited = new boolean[MAXN];

	public static void pathDistance() {
		for (int node = end; node != 0; node = last[node]) {
			visited[node] = true;
		}
		for (int node = end; node != 0; node = last[node]) {
			dist[node] = dfs2(node, 0);
		}
	}

	// dfs2方法不用改迭代居然能通过
	// 那就不改了
	public static int dfs2(int u, int c) {
		int max = c;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (!visited[v]) {
				visited[v] = true;
				max = Math.max(max, dfs2(v, c + weight[e]));
			}
		}
		return max;
	}

	public static int[] queue = new int[MAXN];

	public static int compute() {
		int suml = 0, sumr = 0;
		int h = 0, t = 0;
		int ans = Integer.MAX_VALUE;
		for (int l = end, r = end; l != 0; l = last[l]) {
			suml += pred[l];
			while (r != 0 && sumr + pred[r] - suml <= s) {
				while (h < t && dist[queue[t - 1]] <= dist[r]) {
					t--;
				}
				sumr += pred[r];
				queue[t++] = r;
				r = last[r];
			}
			ans = Math.min(ans, Math.max(Math.max(suml, dist[queue[h]]), diameter - sumr));
			if (queue[h] == l) {
				h++;
			}
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
		pathDistance();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
