package class123;

// 聚会后送k个人回家总时间最少
// 给定一棵n个点的树，边权代表走过边需要花费的时间
// 给定k个人分别在树上的哪些节点
// 这k个人选择了一个聚会点，所有的人都去往聚会点进行聚会
// 聚会结束后需要一辆车从聚会点出发，把这k个人分别送回去，一次送一个
// 当司机送完最后一个乘客，就不需要回到聚会点了，所以司机需要做出最好的选择
// 如果聚会点在i，请问司机最少需要多少时间可以把k个人都送回家
// i = 1 ~ n，打印所有的答案
// 测试链接 : https://www.luogu.com.cn/problem/P6419
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code07_Kamp {

	public static int MAXN = 500001;

	public static int n;

	public static int k;

	public static int[] people = new int[MAXN];

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static long[] innerBack = new long[MAXN];

	public static long[] inmax1 = new long[MAXN];

	public static long[] inmax2 = new long[MAXN];

	public static long[] outerBack = new long[MAXN];

	public static long[] outmax = new long[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(people, 1, n + 1, 0);
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(innerBack, 1, n + 1, 0);
		Arrays.fill(outerBack, 1, n + 1, 0);
		Arrays.fill(inmax1, 1, n + 1, 0);
		Arrays.fill(inmax2, 1, n + 1, 0);
		Arrays.fill(outmax, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void dfs1(int u, int f) {
		for (int e = head[u], v, w; e != 0; e = next[e]) {
			v = to[e];
			w = weight[e];
			if (v != f) {
				dfs1(v, u);
				people[u] += people[v];
				if (people[v] > 0) {
					innerBack[u] += innerBack[v] + (long) w * 2;
					if (inmax1[u] < inmax1[v] + w) {
						inmax2[u] = inmax1[u];
						inmax1[u] = inmax1[v] + w;
					} else if (inmax2[u] < inmax1[v] + w) {
						inmax2[u] = inmax1[v] + w;
					}
				}
			}
		}
	}

	public static void dfs2(int u, int f) {
		for (int e = head[u], v, w; e != 0; e = next[e]) {
			v = to[e];
			w = weight[e];
			if (v != f) {
				if (k - people[v] > 0) {
					outerBack[v] = outerBack[u] + (innerBack[u] - innerBack[v]);
					if (people[v] == 0) {
						outerBack[v] += (long) w * 2;
					}
					if (inmax1[v] + w != inmax1[u]) {
						outmax[v] = Math.max(outmax[u], inmax1[u]) + w;
					} else {
						outmax[v] = Math.max(outmax[u], inmax2[u]) + w;
					}
				}
				dfs2(v, u);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1, u, v, w; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			in.nextToken();
			w = (int) in.nval;
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		for (int i = 1, u; i <= k; i++) {
			in.nextToken();
			u = (int) in.nval;
			people[u]++;
		}
		dfs1(1, 0);
		dfs2(1, 0);
		for (int i = 1; i <= n; i++) {
			out.println(innerBack[i] + outerBack[i] - Math.max(inmax1[i], outmax[i]));
		}
		out.flush();
		out.close();
		br.close();
	}

}
