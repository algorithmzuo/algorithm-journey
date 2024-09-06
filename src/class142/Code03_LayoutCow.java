package class142;

// 布局奶牛
// 编号1到编号n的奶牛从左往右站成一排，你可以决定任意相邻奶牛之间的距离
// 有m1条好友信息，有m2条情敌信息，好友间希望距离更近，情敌间希望距离更远
// 每条好友信息为 : u v w，表示希望u和v之间的距离 <= w，输入保证u < v
// 每条情敌信息为 : u v w，表示希望u和v之间的距离 >= w，输入保证u < v
// 你需要安排奶牛的布局，满足所有的好友信息和情敌信息
// 如果不存在合法方案，返回-1
// 如果存在合法方案，返回1号奶牛和n号奶牛之间的最大距离
// 如果存在合法方案，并且1号奶牛和n号奶牛之间的距离可以无穷远，返回-2
// 测试链接 : https://www.luogu.com.cn/problem/P4878
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_LayoutCow {

	public static int MAXN = 1001;

	public static int MAXM = 20001;

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// spfa需要
	public static int[] dist = new int[MAXN];

	public static int[] update = new int[MAXN];

	public static int MAXQ = 1000001;

	public static int[] queue = new int[MAXQ];

	public static int h, t;

	public static boolean[] enter = new boolean[MAXN];

	public static int n, m1, m2;

	public static void prepare() {
		cnt = 1;
		Arrays.fill(head, 0, n + 1, 0);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static int spfa(int s) {
		h = t = 0;
		Arrays.fill(dist, 0, n + 1, Integer.MAX_VALUE);
		Arrays.fill(update, 0, n + 1, 0);
		Arrays.fill(enter, 0, n + 1, false);
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
							return -1;
						}
						queue[t++] = v;
						enter[v] = true;
					}
				}
			}
		}
		if (dist[n] == Integer.MAX_VALUE) {
			return -2;
		}
		return dist[n];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken(); n = (int) in.nval;
		in.nextToken(); m1 = (int) in.nval;
		in.nextToken(); m2 = (int) in.nval;
		prepare();
		// 0号点是连通超级源点，保证图的连通性
		for (int i = 1; i <= n; i++) {
			addEdge(0, i, 0);
		}
		// 好友关系连边
		for (int i = 1, u, v, w; i <= m1; i++) {
			in.nextToken(); u = (int) in.nval;
			in.nextToken(); v = (int) in.nval;
			in.nextToken(); w = (int) in.nval;
			addEdge(u, v, w);
		}
		// 情敌关系连边
		for (int i = 1, u, v, w; i <= m2; i++) {
			in.nextToken(); u = (int) in.nval;
			in.nextToken(); v = (int) in.nval;
			in.nextToken(); w = (int) in.nval;
			addEdge(v, u, -w);
		}
		// 根据本题的模型，一定要增加如下的边，不然会出错
		for (int i = 1; i < n; i++) {
			addEdge(i + 1, i, 0);
		}
		int ans = spfa(0);
		if (ans == -1) {
			out.println(ans);
		} else {
			ans = spfa(1);
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
