package class123;

// 聚会后送每个人回家最短用时
// 给定一棵n个点的树，边权代表走过边需要花费的时间
// 给定k个人分别在树上的哪些节点
// 这k个人选择了一个聚会点，所有的人都去往聚会点进行聚会
// 聚会结束后，所有人都会上一辆车，车会把每个人送回家
// 送完最后一个乘客，车不需要回到聚会点
// 如果聚会点在i，请问从聚会地点出发直到送最后一个人回家，最短用时多少
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

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	// people[i]: i内部，有多少乘客要送
	public static int[] people = new int[MAXN];

	// incost[i]: i内部，从i出发送完所有乘客回到i的最少代价
	public static long[] incost = new long[MAXN];

	// inner1[i]: i内部，从i出发送乘客的最长链
	public static long[] inner1 = new long[MAXN];

	// inner2[i]: i内部，从i出发送乘客的次长链
	public static long[] inner2 = new long[MAXN];

	// 注意 : inner1[i]和inner2[i]所代表的链，一定要来自i的不同儿子

	// choose[i]: 送乘客的最长链来自i的哪个儿子
	public static int[] choose = new int[MAXN];

	// outcost[i]: i外部，从i出发送完所有乘客回到i的最少代价
	public static long[] outcost = new long[MAXN];

	// outer[i]: i外部，从i出发送乘客的最长链
	public static long[] outer = new long[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(people, 1, n + 1, 0);
		Arrays.fill(incost, 1, n + 1, 0);
		Arrays.fill(inner1, 1, n + 1, 0);
		Arrays.fill(inner2, 1, n + 1, 0);
		Arrays.fill(choose, 1, n + 1, 0);
		Arrays.fill(outcost, 1, n + 1, 0);
		Arrays.fill(outer, 1, n + 1, 0);
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
					incost[u] += incost[v] + (long) w * 2;
					if (inner1[u] < inner1[v] + w) {
						choose[u] = v;
						inner2[u] = inner1[u];
						inner1[u] = inner1[v] + w;
					} else if (inner2[u] < inner1[v] + w) {
						inner2[u] = inner1[v] + w;
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
					if (people[v] == 0) {
						outcost[v] = outcost[u] + incost[u] + (long) w * 2;
					} else {
						outcost[v] = outcost[u] + incost[u] - incost[v];
					}
					if (v != choose[u]) {
						outer[v] = Math.max(outer[u], inner1[u]) + w;
					} else {
						outer[v] = Math.max(outer[u], inner2[u]) + w;
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
			out.println(incost[i] + outcost[i] - Math.max(inner1[i], outer[i]));
		}
		out.flush();
		out.close();
		br.close();
	}

}
