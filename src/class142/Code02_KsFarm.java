package class142;

// 小k的农场
// 一共有n个农场，编号1~n，给定m条关系，每条关系是如下三种形式中的一种
// 关系1 a b c : 表示农场a比农场b至少多种植了c个作物
// 关系2 a b c : 表示农场a比农场b至多多种植了c个作物
// 关系3 a b   : 表示农场a和农场b种植了一样多的作物
// 如果关系之间能推出矛盾，打印"No"，不存在矛盾，打印"Yes"
// 1 <= n、m <= 5 * 10^3
// 1 <= c <= 5 * 10^3
// 测试链接 : https://www.luogu.com.cn/problem/P1993
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_KsFarm {

	public static int MAXN = 5001;

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

	public static int MAXQ = 20000001;

	public static int[] queue = new int[MAXQ];

	public static int h, t;

	public static boolean[] enter = new boolean[MAXN];

	public static int n, m;

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
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		prepare();
		for (int i = 1; i <= n; i++) {
			addEdge(0, i, 0);
		}
		for (int i = 1, type, u, v, w; i <= m; i++) {
			in.nextToken(); type = (int) in.nval;
			in.nextToken(); u = (int) in.nval;
			in.nextToken(); v = (int) in.nval;
			if (type == 1) {
				in.nextToken();
				w = (int) in.nval;
				addEdge(u, v, -w);
			} else if (type == 2) {
				in.nextToken();
				w = (int) in.nval;
				addEdge(v, u, w);
			} else {
				addEdge(u, v, 0);
				addEdge(v, u, 0);
			}
		}
		if (spfa(0)) {
			out.println("No");
		} else {
			out.println("Yes");
		}
		out.flush();
		out.close();
		br.close();
	}

}
