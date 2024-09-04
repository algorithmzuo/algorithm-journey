package class142;

// 虫洞
// 农田有n块，编号1~n，有m1条路，m2个虫洞，路是无向的，虫洞是有向的
// 每条边的信息为: u v w，表示从u走到v，用时w秒
// 每个虫洞信息为: u v w，表示从u走到v，用时-w秒
// 你在某个时刻，从某点出发最终回到该点，时间上还想回到出发时刻之前
// 返回能不能做到这一点，能做到打印"Yes"，不能做到打印"No"
// 1 <= n  <= 500
// 1 <= m1 <= 2500
// 1 <= m2 <= 500
// 1 <= p  <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P2850
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

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

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// spfa需要
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
