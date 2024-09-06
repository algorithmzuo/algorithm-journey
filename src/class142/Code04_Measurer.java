package class142;

// 倍杀测量者
// 如果 A的分数 >= B的分数 * k，k是正实数，就称 A k倍杀 B，或称 B被A k倍杀了
// 一场比赛中，一共有n个选手，有m1条誓言记录，有m2条选手得分记录，得分只可能是正实数
// 类型1的誓言 u v k : 选手u 没有k倍杀 选手v，那么选手u就穿女装
// 类型2的誓言 u v k : 选手u 被选手v k倍杀了，那么选手u就穿女装
// 选手的得分    u w : 选手u得了w分，如果某选手没有得分记录，按照尽量不穿女装的情况推测
// 你希望看到比赛后有人穿女装，但不想看到很多人穿女装，于是想制定正实数ans，效果如下
// 类型1的誓言，比例调整成(k-ans)，类型2的誓言，比例调整成(k+ans)，即提高了穿女装的条件
// 计算ans最大多少，依然有人穿女装，保留小数点后4位，如果不干预也没人穿女装，返回-1
// 1 <= n, m1, m2 <= 1000
// 1 <= k <= 10
// 1 <= w <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4926
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_Measurer {

	public static int MAXN = 1002;

	public static int MAXM = 3001;

	public static double INF = 1e10;

	public static double sml = 1e-6;

	public static int n, m1, m2;

	// 誓言记录(誓言类型, u, v, k)
	public static int[][] vow = new int[MAXN][4];

	// 得分记录(u, w)
	public static int[][] score = new int[MAXN][2];

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static double[] weight = new double[MAXM];

	public static int cnt;

	// spfa需要
	public static double[] dist = new double[MAXN];

	public static int[] update = new int[MAXN];

	public static int MAXQ = 1000001;

	public static int[] queue = new int[MAXQ];

	public static int h, t;

	public static boolean[] enter = new boolean[MAXN];

	public static void prepare() {
		cnt = 1;
		h = t = 0;
		Arrays.fill(head, 0, n + 2, 0);
		Arrays.fill(dist, 0, n + 2, INF);
		Arrays.fill(update, 0, n + 2, 0);
		Arrays.fill(enter, 0, n + 2, false);
	}

	public static void addEdge(int u, int v, double w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static double compute() {
		double l = 0, r = INF, m, ans = 0;
		while (r - l >= sml) {
			m = (l + r) / 2;
			if (check(m)) {
				ans = m;
				l = m + sml;
			} else {
				r = m - sml;
			}
		}
		return ans;
	}

	// 是否有人穿女装
	public static boolean check(double limit) {
		prepare();
		// 0号点是连通超级源点，保证图的连通
		for (int i = 1; i <= n; i++) {
			addEdge(0, i, 0);
		}
		// 倍杀关系的建边
		for (int i = 1; i <= m1; i++) {
			if (vow[i][0] == 1) {
				addEdge(vow[i][1], vow[i][2], -Math.log(-limit + vow[i][3]));
			} else {
				// 因为类型2的誓言是<关系，所以减去最小精度后，就可以认为是<=关系
				addEdge(vow[i][1], vow[i][2], Math.log(limit + vow[i][3] - sml));
			}
		}
		// n+1号点是限制超级源点，保证确定得分的选手之间的关系
		// 本题测试数据有限，两个超级源点合并居然也能通过
		// 原理上两个超级源点一定要分开，课上进行了重点讲解
		for (int i = 1; i <= m2; i++) {
			addEdge(n + 1, score[i][0], Math.log(score[i][1]));
			addEdge(score[i][0], n + 1, -Math.log(score[i][1]));
		}
		return spfa(0);
	}

	public static boolean spfa(int s) {
		dist[s] = 0;
		update[s] = 1;
		queue[t++] = s;
		enter[s] = true;
		while (h < t) {
			int u = queue[h++];
			enter[u] = false;
			for (int ei = head[u]; ei > 0; ei = next[ei]) {
				int v = to[ei];
				double w = weight[ei];
				if (dist[v] > dist[u] + w) {
					dist[v] = dist[u] + w;
					if (!enter[v]) {
						// 0...n+1号点，一共n+2个点，所以这里判断 > n + 1
						if (++update[v] > n + 1) {
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
		in.nextToken(); m1 = (int) in.nval;
		in.nextToken(); m2 = (int) in.nval;
		for (int i = 1; i <= m1; i++) {
			in.nextToken(); vow[i][0] = (int) in.nval;
			in.nextToken(); vow[i][1] = (int) in.nval;
			in.nextToken(); vow[i][2] = (int) in.nval;
			in.nextToken(); vow[i][3] = (int) in.nval;
		}
		for (int i = 1; i <= m2; i++) {
			in.nextToken(); score[i][0] = (int) in.nval;
			in.nextToken(); score[i][1] = (int) in.nval;
		}
		double ans = compute();
		if (ans == 0) {
			out.println("-1");
		} else {
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
