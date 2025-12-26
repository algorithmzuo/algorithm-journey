package class162;

// 树上k级祖先，java版
// 一共有n个节点，编号1~n，给定一个长度为n的数组arr，表示依赖关系
// 如果arr[i] = j，表示i号节点的父节点是j，如果arr[i] == 0，表示i号节点是树头
// 一共有m条查询，每条查询 x k : 打印x往上走k步的祖先节点编号
// 题目要求预处理的时间复杂度O(n * log n)，处理每条查询的时间复杂度O(1)
// 题目要求强制在线，必须按顺序处理每条查询，如何得到每条查询的入参，请打开测试链接查看
// 1 <= n <= 5 * 10^5
// 1 <= m <= 5 * 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P5903
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_TreeKthAncestor1 {

	public static int MAXN = 500001;
	public static int MAXP = 20;
	public static int n, m;
	public static long s;
	public static int root;

	// 链式前向星，注意是有向图，所以边的数量不需要增倍
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg = 0;

	// 倍增表 + 长链剖分
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int[] dep = new int[MAXN];
	public static int[] len = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int cntd = 0;

	// 查询答案需要
	public static int[] high = new int[MAXN];
	public static int[] up = new int[MAXN];
	public static int[] down = new int[MAXN];

	// 题目规定如何得到输入数据的函数
	// C++中有无符号整数，java中没有，选择用long类型代替
	public static long get(long x) {
		x ^= x << 13;
		x &= 0xffffffffL;
		x ^= x >>> 17;
		x ^= x << 5;
		x &= 0xffffffffL;
		return s = x;
	}

	public static void setUp(int u, int i, int v) {
		up[dfn[u] + i] = v;
	}

	public static int getUp(int u, int i) {
		return up[dfn[u] + i];
	}

	public static void setDown(int u, int i, int v) {
		down[dfn[u] + i] = v;
	}

	public static int getDown(int u, int i) {
		return down[dfn[u] + i];
	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版
	public static void dfs1(int u, int f) {
		stjump[u][0] = f;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		dep[u] = dep[f] + 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (son[u] == 0 || len[son[u]] < len[v]) {
					son[u] = v;
				}
			}
		}
		len[u] = len[son[u]] + 1;
	}

	// 递归版
	public static void dfs2(int u, int t) {
		top[u] = t;
		dfn[u] = ++cntd;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != stjump[u][0] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	public static int[][] fse = new int[MAXN][3];

	public static int stacksize, first, second, edge;

	public static void push(int fir, int sec, int edg) {
		fse[stacksize][0] = fir;
		fse[stacksize][1] = sec;
		fse[stacksize][2] = edg;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		first = fse[stacksize][0];
		second = fse[stacksize][1];
		edge = fse[stacksize][2];
	}

	// dfs1的迭代版
	public static void dfs3() {
		stacksize = 0;
		push(root, 0, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				stjump[first][0] = second;
				for (int p = 1; p < MAXP; p++) {
					stjump[first][p] = stjump[stjump[first][p - 1]][p - 1];
				}
				dep[first] = dep[second] + 1;
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != second) {
					push(to[edge], first, -1);
				}
			} else {
				for (int e = head[first], v; e > 0; e = next[e]) {
					v = to[e];
					if (v != second) {
						if (son[first] == 0 || len[son[first]] < len[v]) {
							son[first] = v;
						}
					}
				}
				len[first] = len[son[first]] + 1;
			}
		}
	}

	// dfs2的迭代版
	public static void dfs4() {
		stacksize = 0;
		push(root, root, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) { // edge == -1，表示第一次来到当前节点，并且先处理重儿子
				top[first] = second;
				dfn[first] = ++cntd;
				if (son[first] == 0) {
					continue;
				}
				push(first, second, -2);
				push(son[first], second, -1);
				continue;
			} else if (edge == -2) { // edge == -2，表示处理完当前节点的重儿子，回到了当前节点
				edge = head[first];
			} else { // edge >= 0, 继续处理其他的边
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != stjump[first][0] && to[edge] != son[first]) {
					push(to[edge], to[edge], -1);
				}
			}
		}
	}

	public static void prepare() {
		dfs3();
		dfs4();
		high[0] = -1;
		for (int i = 1; i <= n; i++) {
			high[i] = high[i / 2] + 1;
		}
		for (int u = 1; u <= n; u++) {
			if (top[u] == u) {
				for (int i = 0, a = u, b = u; i < len[u]; i++, a = stjump[a][0], b = son[b]) {
					setUp(u, i, a);
					setDown(u, i, b);
				}
			}
		}
	}

	public static int query(int x, int k) {
		if (k == 0) {
			return x;
		}
		if (k == 1 << high[k]) {
			return stjump[x][high[k]];
		}
		x = stjump[x][high[k]];
		k -= 1 << high[k];
		k -= dep[x] - dep[top[x]];
		x = top[x];
		return (k >= 0) ? getUp(x, k) : getDown(x, -k);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		in.nextToken();
		s = (long) in.nval;
		for (int i = 1, father; i <= n; i++) {
			in.nextToken();
			father = (int) in.nval;
			if (father == 0) {
				root = i;
			} else {
				addEdge(father, i);
			}
		}
		prepare();
		long ans = 0;
		for (int i = 1, x, k, lastAns = 0; i <= m; i++) {
			x = (int) ((get(s) ^ lastAns) % n + 1);
			k = (int) ((get(s) ^ lastAns) % dep[x]);
			lastAns = query(x, k);
			ans ^= (long) i * lastAns;
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
