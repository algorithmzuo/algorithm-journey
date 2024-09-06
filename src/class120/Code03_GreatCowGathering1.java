package class120;

// 牛群聚集(递归版)
// 一共有n个节点，编号1~n，每个点有牛的数量
// 一共有n-1条边把所有点连通起来形成一棵树，每条边有权值
// 想把所有的牛汇聚在一点，希望走过的总距离最小
// 返回总距离最小是多少
// 利用重心的性质：
// 树上的边权如果都>=0，不管边权怎么分布，所有节点都走向重心的总距离和最小
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

	// cow[i] : i号农场牛的数量
	public static int[] cow = new int[MAXN];

	// 牛的总数
	public static int cowSum;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static int best, center;

	// size[i] : 从1号节点开始dfs的过程中，以i为头的子树，牛的总量
	public static int[] size = new int[MAXN];

	// path[i] : 从重心节点开始dfs的过程中，从重心到达i节点，距离是多少
	public static int[] path = new int[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		cowSum = 0;
		best = Integer.MAX_VALUE;
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
		// 如下代码是遍历完成后再做统计工作
		// 这个写法和之前的逻辑是一样的，为什么要拆开写？
		// 为了后续改迭代版方便
		int maxsub = 0;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				size[u] += size[v];
				maxsub = Math.max(maxsub, size[v]);
			}
		}
		maxsub = Math.max(maxsub, cowSum - size[u]);
		if (maxsub < best) {
			best = maxsub;
			center = u;
		}
	}

	public static void setPath(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				path[v] = path[u] + weight[e];
				setPath(v, u);
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
		path[center] = 0;
		setPath(center, 0);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += (long) cow[i] * path[i];
		}
		return ans;
	}

}
