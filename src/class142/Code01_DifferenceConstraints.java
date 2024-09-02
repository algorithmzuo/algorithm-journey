package class142;

// 负环和差分约束模版题
// 测试链接 : https://www.luogu.com.cn/problem/P5960

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_DifferenceConstraints {

	public static int MAXN = 5001;

	public static int MAXM = 10001;

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// SPFA需要
	// 源点出发到每个节点的距离表
	public static int[] dist = new int[MAXN];

	// 节点被松弛的次数
	public static int[] update = new int[MAXN];

	// 队列的大小
	public static int MAXQ = 5000001;

	// 哪些节点被松弛了放入队列
	public static int[] queue = new int[MAXQ];

	// 队列的头和尾
	public static int h, t;

	// 节点是否在队列中
	public static boolean[] enter = new boolean[MAXN];

	public static int n, m;

	public static void prepare() {
		cnt = 1;
		Arrays.fill(head, 0, n + 1, 0);
		h = t = 0;
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

	// 来自讲解065，spfa判断负环，s是超级源点
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
						// 注意判断负环的逻辑和讲解065的代码不一样
						// 因为节点0是额外增加的超级源点
						// 所以节点数量增加了1个，所以这么判断
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
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		prepare();
		for (int i = 1, u, v, w; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			in.nextToken();
			w = (int) in.nval;
			addEdge(v, u, w);
		}
		for (int i = 1; i <= n; i++) {
			addEdge(0, i, 0);
		}
		if (spfa(0)) {
			out.println("NO");
		} else {
			for (int i = 1; i <= n; i++) {
				out.print(dist[i] + " ");
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

}