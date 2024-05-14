package class123;

// 树上聚会后送k个人的总时间最少
// 测试链接 : https://www.luogu.com.cn/problem/P6419
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_Kamp {

	public static int MAXN = 500001;

	public static int n;

	public static int k;

	public static int[] people = new int[MAXN];

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static long[] incost = new long[MAXN];

	public static long[] outcost = new long[MAXN];

	public static long[] infirst = new long[MAXN];

	public static long[] insecond = new long[MAXN];

	public static long[] outfirst = new long[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(people, 1, n + 1, 0);
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(incost, 1, n + 1, 0);
		Arrays.fill(outcost, 1, n + 1, 0);
		Arrays.fill(infirst, 1, n + 1, 0);
		Arrays.fill(insecond, 1, n + 1, 0);
		Arrays.fill(outfirst, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void dfs(int u, int f) {
		size[u] = people[u];
		for (int e = head[u], v, w; e != 0; e = next[e]) {
			v = to[e];
			w = weight[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				if (size[v] > 0) {
					incost[u] += incost[v] + (long) w * 2;
					if (infirst[u] < infirst[v] + w) {
						insecond[u] = infirst[u];
						infirst[u] = infirst[v] + w;
					} else if (insecond[u] < infirst[v] + w) {
						insecond[u] = infirst[v] + w;
					}
				}
			}
		}
	}

	public static void dp(int u, int f) {
		for (int e = head[u], v, w; e != 0; e = next[e]) {
			v = to[e];
			w = weight[e];
			if (v != f) {
				if (k - size[v] > 0) {
					outcost[v] = outcost[u] + (incost[u] - incost[v]);
					if (size[v] == 0) {
						outcost[v] += (long) w * 2;
					}
					if (infirst[v] + w == infirst[u]) {
						outfirst[v] = Math.max(outfirst[u], insecond[u]) + w;
					} else {
						outfirst[v] = Math.max(outfirst[u], infirst[u]) + w;
					}
				}
				dp(v, u);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		in.nextToken();
		k = (int) in.nval;
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
		for (int i = 1, u; i <= k; i++) {
			in.nextToken();
			u = (int) in.nval;
			people[u]++;
		}
		dfs(1, 0);
		dp(1, 0);
		for (int i = 1; i <= n; i++) {
			out.println(incost[i] + outcost[i] - Math.max(outfirst[i], infirst[i]));
		}
		out.flush();
		out.close();
		br.close();
	}

}
