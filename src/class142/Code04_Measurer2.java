package class142;

// 题目4，倍杀测量者，另一种二分的写法
// 思路是不变的，二分的写法多种多样
// 代码中打注释的位置，就是更简单的二分逻辑，其他代码没有变化
// 测试链接 : https://www.luogu.com.cn/problem/P4926
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_Measurer2 {

	public static int MAXN = 1002;

	public static int MAXM = 3001;

	public static double INF = 1e10;

	public static int n, m1, m2;

	public static int[][] vow = new int[MAXN][4];

	public static int[][] score = new int[MAXN][2];

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static double[] weight = new double[MAXM];

	public static int cnt;

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

	// 另一种二分的写法
	public static double compute() {
		double l = 0, r = INF, m;
		// 二分进行60次，足够达到题目要求的精度
		// 二分完成后，l就是答案
		for (int i = 1; i <= 60; i++) {
			m = (l + r) / 2;
			if (check(m)) {
				l = m;
			} else {
				r = m;
			}
		}
		return l;
	}

	public static boolean check(double limit) {
		prepare();
		for (int i = 1; i <= n; i++) {
			addEdge(0, i, 0);
		}
		for (int i = 1; i <= m1; i++) {
			if (vow[i][0] == 1) {
				// 课上的代码没有这个判断，加上才是正确的，防止log里出现负数
				if (-limit + vow[i][3] >= 0) {
					addEdge(vow[i][1], vow[i][2], -Math.log(-limit + vow[i][3]));
				}
			} else {
				addEdge(vow[i][1], vow[i][2], Math.log(limit + vow[i][3]));
			}
		}
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
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m1 = (int) in.nval;
		in.nextToken();
		m2 = (int) in.nval;
		for (int i = 1; i <= m1; i++) {
			in.nextToken();
			vow[i][0] = (int) in.nval;
			in.nextToken();
			vow[i][1] = (int) in.nval;
			in.nextToken();
			vow[i][2] = (int) in.nval;
			in.nextToken();
			vow[i][3] = (int) in.nval;
		}
		for (int i = 1; i <= m2; i++) {
			in.nextToken();
			score[i][0] = (int) in.nval;
			in.nextToken();
			score[i][1] = (int) in.nval;
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
