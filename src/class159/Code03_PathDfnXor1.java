package class159;

// 路径和子树的异或，java版
// 一共有n个节点，n-1条边，组成一棵树，1号节点为树头，每个节点给定点权
// 一共有m条查询，每条查询是如下两种类型中的一种
// 1 x y   : 以x为头的子树中任选一个值，希望异或y之后的值最大，打印最大值
// 2 x y z : 节点x到节点y的路径中任选一个值，希望异或z之后的值最大，打印最大值
// 2 <= n、m <= 10^5
// 1 <= 点权、z < 2^30
// 测试链接 : https://www.luogu.com.cn/problem/P4592
// java实现的逻辑一定是正确的，但是通过不了
// 因为这道题根据C++的运行空间，制定通过标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code03_PathDfnXor2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_PathDfnXor1 {

	public static int MAXN = 100001;

	public static int MAXT = MAXN * 62;

	public static int MAXH = 16;

	public static int BIT = 29;

	public static int n, m;

	// 每个节点的点权
	public static int[] arr = new int[MAXN];

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	// 链式前向星的边的计数
	public static int cntg = 0;

	// 树上dfs求节点深度
	public static int[] deep = new int[MAXN];

	// 树上dfs求子树大小
	public static int[] size = new int[MAXN];

	// 树上dfs求st表
	public static int[][] stjump = new int[MAXN][MAXH];

	// 树上dfs求每个节点的dfn序号
	public static int[] dfn = new int[MAXN];

	// dfn序号计数
	public static int cntd = 0;

	// 1类型的可持久化01Trie，根据dfn序号的次序建树
	public static int[] root1 = new int[MAXN];

	// 2类型的可持久化01Trie，根据父节点的版本建新树
	public static int[] root2 = new int[MAXN];

	// 1类型和2类型都可以用这个tree结构
	public static int[][] tree = new int[MAXT][2];

	// 1类型和2类型都可以用这个pass数组
	public static int[] pass = new int[MAXT];

	// 1类型和2类型一起的节点计数
	public static int cntt = 0;

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int insert(int num, int i) {
		int rt = ++cntt;
		tree[rt][0] = tree[i][0];
		tree[rt][1] = tree[i][1];
		pass[rt] = pass[i] + 1;
		for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
			path = (num >> b) & 1;
			i = tree[i][path];
			cur = ++cntt;
			tree[cur][0] = tree[i][0];
			tree[cur][1] = tree[i][1];
			pass[cur] = pass[i] + 1;
			tree[pre][path] = cur;
		}
		return rt;
	}

	public static int query(int num, int u, int v) {
		int ans = 0;
		for (int b = BIT, path, best; b >= 0; b--) {
			path = (num >> b) & 1;
			best = path ^ 1;
			if (pass[tree[v][best]] > pass[tree[u][best]]) {
				ans += 1 << b;
				u = tree[u][best];
				v = tree[v][best];
			} else {
				u = tree[u][path];
				v = tree[v][path];
			}
		}
		return ans;
	}

	// 按道理说dfs1应该改成迭代版，防止递归爆栈
	// 不过本题给定的空间很小，java版怎么也无法通过，索性不改了
	// 有兴趣的同学可以看一下，讲解118，详解了树上dfs从递归版改迭代版
	public static void dfs1(int u, int fa) {
		deep[u] = deep[fa] + 1;
		size[u] = 1;
		stjump[u][0] = fa;
		dfn[u] = ++cntd;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int ei = head[u], v; ei > 0; ei = next[ei]) {
			v = to[ei];
			if (v != fa) {
				dfs1(v, u);
				size[u] += size[v];
			}
		}
	}

	// 按道理说dfs2应该改成迭代版，防止递归爆栈
	// 不过本题给定的空间很小，java版怎么也无法通过，索性不改了
	// 有兴趣的同学可以看一下，讲解118，详解了树上dfs从递归版改迭代版
	public static void dfs2(int u, int fa) {
		root1[dfn[u]] = insert(arr[u], root1[dfn[u] - 1]);
		root2[u] = insert(arr[u], root2[fa]);
		for (int ei = head[u]; ei > 0; ei = next[ei]) {
			if (to[ei] != fa) {
				dfs2(to[ei], u);
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (deep[stjump[a][p]] >= deep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
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
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs1(1, 0);
		dfs2(1, 0);
		for (int i = 1, op, x, y, z; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			in.nextToken();
			y = (int) in.nval;
			if (op == 1) {
				out.println(query(y, root1[dfn[x] - 1], root1[dfn[x] + size[x] - 1]));
			} else {
				in.nextToken();
				z = (int) in.nval;
				int lcafa = stjump[lca(x, y)][0];
				int ans = Math.max(query(z, root2[lcafa], root2[x]), query(z, root2[lcafa], root2[y]));
				out.println(ans);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
