package class129;

// 开车旅行
// 给定一个长度为n的数组arr，下标1 ~ n范围，数组无重复值
// 近的定义、距离的定义，和题目4一致
// a和b同坐一辆车开始往右旅行，a先开车，b后开车，此后每到达一点都换人驾驶
// 如果a在某点驾驶，那么车去往该点右侧第二近的点，如果b在某点驾驶，那么车去往该点右侧第一近的点
// a和b从s位置出发，如果开车总距离超过x，或轮到某人时右侧无点可选，那么旅行停止
// 问题1 : 给定距离x0，返回1 ~ n-1中从哪个点出发，a行驶距离 / b行驶距离，比值最小
//         如果从多个点出发时，比值都为最小，那么返回arr中的值最大的点
// 问题2 : 给定s、x，返回旅行停止时，a开了多少距离、b开了多少距离
// 问题1只调用1次
// 问题2调用m次，每组有不同的s、x
// 1 <= n、m、s <= 10^5
// -10^9 <= arr[i] <= 10^9
// 1 <= x0、x <= 10^9
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

	public static int MAXN = 100002;

	public static int MAXP = 20;

	public static int[] arr = new int[MAXN];

	public static int[] to1 = new int[MAXN];

	public static int[] dist1 = new int[MAXN];

	public static int[] to2 = new int[MAXN];

	public static int[] dist2 = new int[MAXN];

	public static int[][] rank = new int[MAXN][2];

	public static int[] last = new int[MAXN];

	public static int[] next = new int[MAXN];

	// stto[i][p] : 从i位置出发，a和b轮流开2^p轮之后，车到达了几号点
	public static int[][] stto = new int[MAXN][MAXP + 1];

	// stdist[i][p] : 从i位置出发，a和b轮流开2^p轮之后，总距离是多少
	public static int[][] stdist = new int[MAXN][MAXP + 1];

	// sta[i][p] : 从i位置出发，a和b轮流开2^p轮之后，a行驶了多少距离
	public static int[][] sta = new int[MAXN][MAXP + 1];

	// stb[i][p] : 从i位置出发，a和b轮流开2^p轮之后，b行驶了多少距离
	public static int[][] stb = new int[MAXN][MAXP + 1];

	public static int n, m;

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
		int x0 = (int) in.nval;
		out.println(best(x0));
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			int s = (int) in.nval;
			in.nextToken();
			int x = (int) in.nval;
			travel(s, x);
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
		rank[0][0] = 0;
		rank[n + 1][0] = 0;
		for (int i = 1; i <= n; i++) {
			last[rank[i][0]] = rank[i - 1][0];
			next[rank[i][0]] = rank[i + 1][0];
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

	public static void update(int i, int j) {
		if (j == 0) {
			return;
		}
		int dist = Math.abs(arr[i] - arr[j]);
		if (to1[i] == 0 || dist < dist1[i] || (dist == dist1[i] && arr[j] < arr[to1[i]])) {
			to2[i] = to1[i];
			dist2[i] = dist1[i];
			to1[i] = j;
			dist1[i] = dist;
		} else if (to2[i] == 0 || dist < dist2[i] || (dist == dist2[i] && arr[j] < arr[to2[i]])) {
			to2[i] = j;
			dist2[i] = dist;
		}
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
		// 倍增初始化
		for (int i = 1; i <= n; i++) {
			stto[i][0] = to1[to2[i]];
			stdist[i][0] = dist2[i] + dist1[to2[i]];
			sta[i][0] = dist2[i];
			stb[i][0] = dist1[to2[i]];
		}
		// 生成倍增表
		for (int p = 1; p <= MAXP; p++) {
			for (int i = 1; i <= n; i++) {
				stto[i][p] = stto[stto[i][p - 1]][p - 1];
				if (stto[i][p] != 0) {
					stdist[i][p] = stdist[i][p - 1] + stdist[stto[i][p - 1]][p - 1];
					sta[i][p] = sta[i][p - 1] + sta[stto[i][p - 1]][p - 1];
					stb[i][p] = stb[i][p - 1] + stb[stto[i][p - 1]][p - 1];
				}
			}
		}
	}

	public static int best(int x0) {
		int ans = 0;
		double min = Double.MAX_VALUE;
		double cur;
		for (int i = 1; i < n; i++) {
			travel(i, x0);
			// cur这么设置更安全一些
			cur = b == 0 ? Double.MAX_VALUE : ((double) a / (double) b);
			if (ans == 0 || cur < min || (cur == min && arr[i] > arr[ans])) {
				ans = i;
				min = cur;
			}
		}
		return ans;
	}

	public static int a, b;

	public static void travel(int s, int x) {
		a = 0;
		b = 0;
		for (int p = MAXP; p >= 0; p--) {
			if (stto[s][p] != 0 && x >= stdist[s][p]) {
				x -= stdist[s][p];
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
