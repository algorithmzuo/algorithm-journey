package class121;

// dfs1改迭代
// 测试链接 : https://www.luogu.com.cn/problem/P2491

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
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static int start, end, diameter;

	public static int[] dist = new int[MAXN];

	public static int[] path = new int[MAXN];

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
				path[u] = f;
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
		Arrays.fill(visited, 1, n, false);
		for (int node = end; node != 0; node = path[node]) {
			visited[node] = true;
		}
		for (int node = end; node != 0; node = path[node]) {
			dist[node] = dfs2(node, 0);
		}
	}

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

	public static int[] sum = new int[MAXN];

	public static int[] queue = new int[MAXN];

	public static int compute() {
		sum[end] = 0;
		for (int i = end; path[i] != 0; i = path[i]) {
			sum[path[i]] = sum[i] + pred[i];
		}
		int h = 0, t = 0;
		int ans = Integer.MAX_VALUE;
		for (int l = end, r = end, lastr = end; l != 0; l = path[l]) {
			while (path[r] != 0 && sum[path[r]] - sum[l] <= s) {
				while (h < t && dist[queue[t - 1]] <= dist[path[r]]) {
					t--;
				}
				queue[t++] = path[r];
				lastr = r;
				r = path[r];
			}
			ans = Math.min(ans, Math.max(dist[queue[h]], Math.max(sum[l], sum[start] - sum[lastr])));
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
