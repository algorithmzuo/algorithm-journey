package class129;

// 开车旅行
// 测试链接 : https://www.luogu.com.cn/problem/P1081
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_RoadTrip {

	public static int MAXN = 100001;

	public static int MAXP = 20;

	public static int[] arr = new int[MAXN];

	public static int[] to1 = new int[MAXN];

	public static int[] dist1 = new int[MAXN];

	public static int[] to2 = new int[MAXN];

	public static int[] dist2 = new int[MAXN];

	public static int[][] rank = new int[MAXN][2];

	public static int[] last = new int[MAXN];

	public static int[] next = new int[MAXN];

	// 如下四个结构是倍增表
	public static int[][] stto = new int[MAXN][MAXP + 1];

	public static int[][] stab = new int[MAXN][MAXP + 1];

	public static int[][] sta = new int[MAXN][MAXP + 1];

	public static int[][] stb = new int[MAXN][MAXP + 1];

	public static int n, m, x0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		near();
		st();
		in.nextToken();
		x0 = (int) in.nval;
		out.println(compute1());
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, s, x; i <= m; i++) {
			in.nextToken();
			s = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			compute2(s, x);
			out.println(a + " " + b);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void near() {
		for (int i = 1; i <= n; i++) {
			rank[i][0] = i;
			rank[i][1] = arr[i];
		}
		Arrays.sort(rank, 1, n + 1, (a, b) -> a[1] - b[1]);
		for (int i = 1; i <= n; i++) {
			last[rank[i][0]] = i == 1 ? 0 : rank[i - 1][0];
			next[rank[i][0]] = i == n ? 0 : rank[i + 1][0];
		}
		for (int i = 1; i <= n; i++) {
			to1[i] = 0;
			dist1[i] = 0;
			to2[i] = 0;
			dist2[i] = 0;
			update(i, last[i]);
			update(i, last[last[i]]);
			update(i, next[i]);
			update(i, next[next[i]]);
			delete(i);
		}
	}

	public static void update(int i, int r) {
		if (r == 0) {
			return;
		}
		int d = Math.abs(arr[i] - arr[r]);
		if (better(to1[i], dist1[i], r, d)) {
			to2[i] = to1[i];
			dist2[i] = dist1[i];
			to1[i] = r;
			dist1[i] = d;
		} else if (better(to2[i], dist2[i], r, d)) {
			to2[i] = r;
			dist2[i] = d;
		}
	}

	public static boolean better(int r1, int d1, int r2, int d2) {
		if (r1 == 0) {
			return true;
		}
		return d2 < d1 || (d2 == d1 && arr[r2] < arr[r1]);
	}

	public static void delete(int i) {
		int l = last[i];
		int r = next[i];
		if (l != 0) {
			next[l] = r;
		}
		if (r != 0) {
			last[r] = l;
		}
	}

	public static void st() {
		// 倍增初始值
		for (int i = 1; i <= n; i++) {
			stto[i][0] = to1[to2[i]];
			stab[i][0] = dist2[i] + dist1[to2[i]];
			sta[i][0] = dist2[i];
			stb[i][0] = dist1[to2[i]];
		}
		// 生成倍增表
		for (int p = 1; p <= MAXP; p++) {
			for (int i = 1; i <= n; i++) {
				stto[i][p] = stto[stto[i][p - 1]][p - 1];
				if (stto[i][p] != 0) {
					stab[i][p] = stab[i][p - 1] + stab[stto[i][p - 1]][p - 1];
					sta[i][p] = sta[i][p - 1] + sta[stto[i][p - 1]][p - 1];
					stb[i][p] = stb[i][p - 1] + stb[stto[i][p - 1]][p - 1];
				}
			}
		}
	}

	public static int compute1() {
		int ans = 0;
		double min = Double.MAX_VALUE, cur;
		for (int i = 1; i <= n; i++) {
			compute2(i, x0);
			if (a > 0) {
				cur = (double) a / (double) b;
				if (ans == 0 || cur < min || (cur == min && arr[i] > arr[ans])) {
					min = cur;
					ans = i;
				}
			}
		}
		return ans;
	}

	public static int a, b;

	public static void compute2(int s, int x) {
		a = 0;
		b = 0;
		for (int p = MAXP; p >= 0; p--) {
			if (stab[s][p] != 0 && x >= stab[s][p]) {
				x -= stab[s][p];
				a += sta[s][p];
				b += stb[s][p];
				s = stto[s][p];
			}
		}
		if (dist2[s] <= x) {
			a += dist2[s];
		}
	}

}
