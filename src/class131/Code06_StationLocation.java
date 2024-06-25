package class131;

// 基站选址
// 一共有n个村庄排成一排，从左往右依次出现1号、2号、3号..n号村庄
// dist[i]表示i号村庄到1号村庄的距离，该数组一定有序且无重复值
// cost[i]表示i号村庄建立基站的安装费用
// range[i]表示如果i号村庄不建立基站，在range[i]距离内的基站也可以给i号村庄提供服务
// warranty[i]表示如果i号村庄最终得不到任何基站的服务，需要给多少补偿费用
// 最多可以选择k个村庄安装基站，返回总花费最少是多少，总花费包括安装费用和补偿费用
// 1 <= n <= 20000
// 1 <= k <= 100
// 1 <= dist[i]、 range[i] <= 10^9
// 1 <= cost[i]、 warranty[i] <= 10^4
// k <= n
// 测试链接 : https://www.luogu.com.cn/problem/P2605
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_StationLocation {

	public static int MAXN = 20002;

	// 和1号村庄之间的距离，输入一定保证有序
	public static int[] dist = new int[MAXN];

	// 修建基站花费
	public static int[] cost = new int[MAXN];

	// 覆盖范围
	public static int[] range = new int[MAXN];

	// 赔偿费用
	public static int[] warranty = new int[MAXN];

	// 最左在哪建基站可以覆盖到
	public static int[] left = new int[MAXN];

	// 最右在哪建基站可以覆盖到
	public static int[] right = new int[MAXN];

	// 线段树维护最小值信息
	public static int[] min = new int[MAXN << 2];

	// 线段树维护加的懒更新
	public static int[] add = new int[MAXN << 2];

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int cnt;

	// 动态规划表
	public static int[] dp = new int[MAXN];

	public static int n, k;

	public static void prepare() {
		cnt = 1;
		for (int i = 1; i <= n; i++) {
			left[i] = search(dist[i] - range[i]);
			right[i] = search(dist[i] + range[i]);
			if (dist[right[i]] > dist[i] + range[i]) {
				right[i]--;
			}
			addEdge(right[i], i);
		}
	}

	// 在dist数组中二分查找>=d的最左位置
	public static int search(int d) {
		int l = 1, r = n, m;
		int ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (dist[m] >= d) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	// 链式前向星加边
	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void up(int i) {
		min[i] = Math.min(min[i << 1], min[i << 1 | 1]);
	}

	public static void down(int i) {
		if (add[i] != 0) {
			lazy(i << 1, add[i]);
			lazy(i << 1 | 1, add[i]);
			add[i] = 0;
		}
	}

	public static void lazy(int i, int v) {
		min[i] += v;
		add[i] += v;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			min[i] = dp[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		add[i] = 0;
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return min[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		int ans = Integer.MAX_VALUE;
		if (jobl <= mid) {
			ans = Math.min(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.min(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
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
		k = Math.min(n, (int) in.nval);
		for (int i = 2; i <= n; i++) {
			in.nextToken();
			dist[i] = (int) in.nval;
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			cost[i] = (int) in.nval;
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			range[i] = (int) in.nval;
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			warranty[i] = (int) in.nval;
		}
		// 补充一个在无穷远的村庄
		dist[++n] = Integer.MAX_VALUE;
		prepare();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		for (int i = 1, tmp = 0; i <= n; i++) {
			dp[i] = tmp + cost[i];
			for (int ei = head[i]; ei != 0; ei = next[ei]) {
				tmp += warranty[to[ei]];
			}
		}
		int ans = dp[n];
		// 可以建多个基站的情况
		// 认为最多有k+1个基站，并且在补充村庄(无穷远)一定要建一个基站
		// 也就是用一个单独的基站，去负责补充村庄，那么这个花费是0
		// 剩下的基站，去负责补充村庄左边真实出现的村庄，返回最少总费用
		for (int j = 2; j <= k + 1; j++) {
			build(1, n, 1);
			for (int i = 1; i <= n; i++) {
				if (j <= i) {
					dp[i] = query(j - 1, i - 1, 1, n, 1) + cost[i];
				}
				for (int ei = head[i], pre; ei != 0; ei = next[ei]) {
					pre = to[ei];
					if (left[pre] > 1) {
						add(j - 1, left[pre] - 1, warranty[pre], 1, n, 1);
					}
				}
			}
			ans = Math.min(ans, dp[n]);
		}
		return ans;
	}

}
