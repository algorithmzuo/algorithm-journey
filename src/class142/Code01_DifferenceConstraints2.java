package class142;

// 负环和差分约束模版题(转化成形式2进而转化成判断无限增加的环)
// 一共有n个变量，编号1~n，给定m个不等式，每个不等式的形式为
// Xi - Xj <= Ci，其中Xi和Xj为变量，Ci为常量
// 如果不等式存在矛盾导致无解，打印"NO"
// 如果有解，打印满足所有不等式的其中一组解(X1, X2...)
// 1 <= n、m <= 5 * 10^3
// -10^4 <= Ci <= +10^4 
// 测试链接 : https://www.luogu.com.cn/problem/P5960
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_DifferenceConstraints2 {

	public static int MAXN = 5001;

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

	public static int MAXQ = 5000001;

	public static int[] queue = new int[MAXQ];

	public static int h, t;

	public static boolean[] enter = new boolean[MAXN];

	public static int n, m;

	public static void prepare() {
		cnt = 1;
		h = t = 0;
		Arrays.fill(head, 0, n + 1, 0);
		// 所有距离先设置成最小值
		Arrays.fill(dist, 0, n + 1, Integer.MIN_VALUE);
		Arrays.fill(update, 0, n + 1, 0);
		Arrays.fill(enter, 0, n + 1, false);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	// 来自讲解065，spfa判断无限增加环，s是超级源点
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
				if (dist[v] < dist[u] + w) { // 变大才更新
					dist[v] = dist[u] + w;
					if (!enter[v]) {
						// 注意判断逻辑和讲解065的代码不一样
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
		in.nextToken(); n = (int) in.nval;
		in.nextToken(); m = (int) in.nval;
		prepare();
		// 0号点是连通超级源点，保证图的连通性
		for (int i = 1; i <= n; i++) {
			addEdge(0, i, 0);
		}
		for (int i = 1, u, v, w; i <= m; i++) {
			in.nextToken(); u = (int) in.nval;
			in.nextToken(); v = (int) in.nval;
			in.nextToken(); w = (int) in.nval;
			// 形式2的连边方式
			addEdge(u, v, -w);
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