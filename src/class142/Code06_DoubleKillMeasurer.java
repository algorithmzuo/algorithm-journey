package class142;

// 倍杀测量者
// 如果选手A的分数 不小于 选手B的分数的k倍(k是非负实数)
// 我们就称选手A k倍杀 选手B，也可以称选手B被选手A k倍杀
// 一共有n个选手，编号1~n，有m1条誓言，有m2条真实得分
// 每条誓言的形式为两种，具体为
// 誓言 1 u v k : 选手u如果没有 k倍杀 选手v，选手u就穿女装
// 誓言 2 u v k : 选手u如果被选手v k倍杀，选手u就穿女装
// 真实得分的形式为 u w : 选手u得了w分
// 这种誓言太过残酷，你不想看到很多人比赛后穿女装，于是想规定一个非负实数ans，效果如下
// 对于 誓言 1 u v k : 选手u如果没有 (k-ans)倍杀 选手v，选手u才用穿女装
// 对于 誓言 2 u v k : 选手u如果被选手v (k+ans)倍杀，选手u才用穿女装
// 相当于ans提高了穿女装的条件，但是你也不想看到完全没女装出现的情况
// 请问ans最大到多少，能保证比赛后一定有人穿女装，精度到小数点后5位即可
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

public class Code06_DoubleKillMeasurer {

	public static int MAXN = 1001;

	public static int MAXM = 5001;

	public static int MAXK = 10;

	public static double sml = 1e-6;

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] op = new int[MAXM];

	public static double[] weight = new double[MAXM];

	public static double[] ktimes = new double[MAXM];

	public static int cnt;

	// spfa需要
	public static double[] dist = new double[MAXN];

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

	public static void addEdge(int u, int v, int o, double w, double k) {
		next[cnt] = head[u];
		to[cnt] = v;
		op[cnt] = o;
		weight[cnt] = w;
		ktimes[cnt] = k;
		head[u] = cnt++;
	}

	public static boolean spfa(int s, double limit) {
		h = t = 0;
		Arrays.fill(dist, 0, n + 1, -1e9);
		Arrays.fill(update, 0, n + 1, 0);
		Arrays.fill(enter, 0, n + 1, false);
		dist[s] = 0;
		update[s] = 1;
		queue[t++] = s;
		enter[s] = true;
		while (h < t) {
			int u = queue[h++];
			enter[u] = false;
			int v, o;
			double w, k;
			for (int ei = head[u]; ei > 0; ei = next[ei]) {
				v = to[ei];
				o = op[ei];
				k = ktimes[ei];
				if (o == 1) {
					w = Math.log(k - limit);
				} else if (o == 2) {
					w = -Math.log(k + limit);
				} else {
					w = weight[ei];
				}
				// 注意这里是变大才更新，是否能发现无限增加的环，与是否发现负环，本质是一样的
				if (dist[v] < dist[u] + w) {
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

	public static double compute() {
		double ans = 0, l = 0, r = MAXK, mid;
		while (r - l >= sml) {
			mid = (l + r) / 2;
			if (spfa(0, mid)) {
				ans = mid;
				l = mid + sml;
			} else {
				r = mid - sml;
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
		m1 = (int) in.nval;
		in.nextToken();
		m2 = (int) in.nval;
		prepare();
		for (int i = 1, op, u, v, k; i <= m1; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
			addEdge(v, u, op, 0, k);
		}
		for (int i = 1, u, w; i <= m2; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			w = (int) in.nval;
			addEdge(0, u, 0, Math.log(w), 0);
			addEdge(u, 0, 0, -Math.log(w), 0);
		}
		for (int i = 1; i <= n; i++) {
			addEdge(0, i, 0, 0, 0);
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
