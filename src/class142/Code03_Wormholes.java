package class142;

// 虫洞
// 测试链接 : https://www.luogu.com.cn/problem/P2850

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_Wormholes {

	public static int MAXN = 501;

	public static int MAXM = 10001;

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// spfa
	public static int[] dist = new int[MAXN];

	public static int[] update = new int[MAXN];

	public static int MAXQ = 300001;

	public static int[] queue = new int[MAXQ];

	public static int h, t;

	public static boolean[] enter = new boolean[MAXN];

	public static int n, m1, m2;

	public static void prepare() {
		cnt = 1;
		h = t = 0;
		Arrays.fill(head, 0, n + 1, 0);
		Arrays.fill(dist, 0, n + 1, Integer.MAX_VALUE);
		Arrays.fill(update, 0, n + 1, 0);
		Arrays.fill(enter, 0, n + 1, false);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static boolean spfa(int s) {
		dist[s] = 0;
		update[s] = 1;
		queue[t++] = s;
		enter[s] = true;
		while (h < t) {
			int u = queue[h++];
			enter[u] = false;
			for (int ei = head[u], v, w; ei > 0; ei = next[ei]) {
				v = to[ei];
				w = weight[ei];
				if (dist[v] > dist[u] + w) {
					dist[v] = dist[u] + w;
					if (!enter[v]) {
						if (++update[v] > n) {
							return true;
						}
						queue[t++] = v;
						enter[v] = true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			m1 = (int) in.nval;
			in.nextToken();
			m2 = (int) in.nval;
			prepare();
			for (int i = 1, u, v, w; i <= m1; i++) {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				in.nextToken();
				w = (int) in.nval;
				addEdge(u, v, w);
				addEdge(v, u, w);
			}
			for (int i = 1, u, v, w; i <= m2; i++) {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				in.nextToken();
				w = (int) in.nval;
				addEdge(u, v, -w);
			}
			for (int i = 1; i <= n; i++) {
				addEdge(0, i, 0);
			}
			if (spfa(0)) {
				out.println("YES");
			} else {
				out.println("NO");
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
