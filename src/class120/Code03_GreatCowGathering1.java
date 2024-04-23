package class120;

// 递归版
// 测试链接 : https://www.luogu.com.cn/problem/P2986
// 提交以下的code，提交时请把类名改成"Main"
// C++这么写能通过，java会因为递归层数太多而爆栈
// java能通过的写法参考本节课Code03_GreatCowGathering2文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_GreatCowGathering1 {

	public static int MAXN = 100001;

	public static int n;

	public static int cowSum;

	public static int cnt;

	public static int maxSize, center;

	public static int[] cow = new int[MAXN];

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int[] size = new int[MAXN];

	public static int[] pathcost = new int[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		cowSum = 0;
		maxSize = Integer.MAX_VALUE;
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void findCenter(int u, int f) {
		size[u] = cow[u];
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				findCenter(v, u);
			}
		}
		// 为什么要拆开写？为了后续改迭代版方便分析
		int max = 0;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				size[u] += size[v];
				max = Math.max(max, size[v]);
			}
		}
		max = Math.max(max, cowSum - size[u]);
		if (max < maxSize) {
			maxSize = max;
			center = u;
		}
	}

	public static void setWeights(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				pathcost[v] = pathcost[u] + weight[e];
				setWeights(v, u);
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
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			cow[i] = (int) in.nval;
		}
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
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		for (int i = 1; i <= n; i++) {
			cowSum += cow[i];
		}
		findCenter(1, 0);
		pathcost[center] = 0;
		setWeights(center, 0);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += (long) cow[i] * pathcost[i];
		}
		return ans;
	}

}
