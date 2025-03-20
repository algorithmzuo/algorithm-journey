package class163;

// 表亲数量，java版
// 一共有n个节点，编号1~n，给定每个节点的父亲节点编号
// 如果父亲节点编号为0，说明当前节点是某棵树的头节点
// 注意，n个节点组成的是森林结构，可能有若干棵树
// 一共有m条查询，每条查询 x k，含义如下
// 如果x往上走k的距离，没有祖先节点，打印0
// 如果x往上走k的距离，能找到祖先节点a，那么从a往下走k的距离，除了x之外，可能还有其他节点
// 这些节点叫做x的k级表亲，打印这个表亲的数量
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF208E
// 测试链接 : https://codeforces.com/problemset/problem/208/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_BloodCousins1 {

	public static int MAXN = 100001;
	public static int MAXH = 20;
	public static int n, m;

	public static boolean[] root = new boolean[MAXN];

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN];
	public static int[] tog = new int[MAXN];
	public static int cntg;

	public static int[] headq = new int[MAXN];
	public static int[] nextq = new int[MAXN];
	public static int[] ansiq = new int[MAXN];
	public static int[] kq = new int[MAXN];
	public static int cntq;

	public static int[] siz = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXH];

	public static int[] nodeCnt = new int[MAXN];
	public static int[] ans = new int[MAXN];

	public static void addEdge(int u, int v) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addQuestion(int u, int i, int k) {
		nextq[++cntq] = headq[u];
		ansiq[cntq] = i;
		kq[cntq] = k;
		headq[u] = cntq;
	}

	public static int kAncestor(int u, int k) {
		for (int p = MAXH - 1; p >= 0; p--) {
			if (k >= 1 << p) {
				k -= 1 << p;
				u = stjump[u][p];
			}
		}
		return u;
	}

	public static void dfs1(int u, int fa) {
		siz[u] = 1;
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			dfs1(tog[e], u);
		}
		for (int e = headg[u], v; e > 0; e = nextg[e]) {
			v = tog[e];
			siz[u] += siz[v];
			if (son[u] == 0 || siz[son[u]] < siz[v]) {
				son[u] = v;
			}
		}
	}

	public static void effect(int u) {
		nodeCnt[dep[u]]++;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			effect(tog[e]);
		}
	}

	public static void cancle(int u) {
		nodeCnt[dep[u]]--;
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			cancle(tog[e]);
		}
	}

	public static void dfs2(int u, int keep) {
		for (int e = headg[u], v; e > 0; e = nextg[e]) {
			v = tog[e];
			if (v != son[u]) {
				dfs2(v, 0);
			}
		}
		if (son[u] != 0) {
			dfs2(son[u], 1);
		}
		nodeCnt[dep[u]]++;
		for (int e = headg[u], v; e > 0; e = nextg[e]) {
			v = tog[e];
			if (v != son[u]) {
				effect(v);
			}
		}
		for (int i = headq[u]; i > 0; i = nextq[i]) {
			ans[ansiq[i]] = nodeCnt[dep[u] + kq[i]];
		}
		if (keep == 0) {
			cancle(u);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1, father; i <= n; i++) {
			in.nextToken();
			father = (int) in.nval;
			if (father == 0) {
				root[i] = true;
			} else {
				addEdge(father, i);
			}
		}
		for (int i = 1; i <= n; i++) {
			// 因为是森林结构，可能有多棵子树彼此是不联通的
			// 所以对每一棵子树进行重链剖分、生成倍增表
			if (root[i]) {
				dfs1(i, 0);
			}
		}
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, u, k, kfather; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
			kfather = kAncestor(u, k);
			if (kfather != 0) {
				addQuestion(kfather, i, k);
			}
		}
		for (int i = 1; i <= n; i++) {
			// 因为是森林结构，可能有多棵子树彼此是不联通的
			// 所以认为每一棵树的头都是轻儿子
			// 这样每棵子树运行完后都会消除影响
			// 不会影响下一棵子树的答案
			if (root[i]) {
				dfs2(i, 0);
			}
		}
		for (int i = 1; i <= m; i++) {
			if (ans[i] == 0) {
				out.print("0 ");
			} else {
				out.print((ans[i] - 1) + " ");
			}
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}
