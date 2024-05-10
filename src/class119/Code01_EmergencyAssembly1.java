package class119;

// 紧急集合
// 一共有n个节点，编号1 ~ n，一定有n-1条边连接形成一颗树
// 从一个点到另一个点的路径上有几条边，就需要耗费几个金币
// 每条查询(a, b, c)表示有三个人分别站在a、b、c点上
// 他们想集合在树上的某个点，并且想花费的金币总数最少
// 一共有m条查询，打印m个答案
// 1 <= n <= 5 * 10^5
// 1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4281
// 如下实现是正确的，但是洛谷平台对空间卡的很严，只有使用C++能全部通过
// C++版本就是本节代码中的Code01_EmergencyAssembly2文件
// C++版本和java版本逻辑完全一样，但只有C++版本可以通过所有测试用例
// 这是洛谷平台没有照顾各种语言的实现所导致的
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的
// 提交以下的code，提交时请把类名改成"Main"

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_EmergencyAssembly1 {

	public static int MAXN = 500001;

	public static int LIMIT = 19;

	public static int power;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	// deep[i] : i节点在第几层，算距离用
	public static int[] deep = new int[MAXN];

	// 利用stjump求最低公共祖先
	public static int[][] stjump = new int[MAXN][LIMIT];

	public static int togather;

	public static long cost;

	public static void build(int n) {
		power = log2(n);
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs(int u, int f) {
		deep[u] = deep[f] + 1;
		stjump[u][0] = f;
		for (int p = 1; p <= power; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs(to[e], u);
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = power; p >= 0; p--) {
			if (deep[stjump[a][p]] >= deep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = power; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		build(n);
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(1, 0);
		for (int i = 1, a, b, c; i <= m; i++) {
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			in.nextToken();
			c = (int) in.nval;
			compute(a, b, c);
			out.println(togather + " " + cost);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void compute(int a, int b, int c) {
		// 来自对结构关系的深入分析，课上重点解释
		int h1 = lca(a, b), h2 = lca(a, c), h3 = lca(b, c);
		int high = h1 != h2 ? (deep[h1] < deep[h2] ? h1 : h2) : h1;
		int low = h1 != h2 ? (deep[h1] > deep[h2] ? h1 : h2) : h3;
		togather = low;
		cost = (long) deep[a] + deep[b] + deep[c] - deep[high] * 2 - deep[low];
	}

}
