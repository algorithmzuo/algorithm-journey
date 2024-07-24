package class118;

// 树上倍增解法迭代版
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 所有递归函数一律改成等义的迭代版
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_Multiply2 {

	public static int MAXN = 500001;

	public static int LIMIT = 20;

	public static int power;

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static int cnt;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[][] stjump = new int[MAXN][LIMIT];

	public static int[] deep = new int[MAXN];

	public static void build(int n) {
		power = log2(n);
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	// dfs迭代版
	// ufe是为了实现迭代版而准备的栈
	public static int[][] ufe = new int[MAXN][3];

	public static int stackSize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stackSize][0] = u;
		ufe[stackSize][1] = f;
		ufe[stackSize][2] = e;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufe[stackSize][0];
		f = ufe[stackSize][1];
		e = ufe[stackSize][2];
	}

	public static void dfs(int root) {
		stackSize = 0;
		// 栈里存放三个信息
		// u : 当前处理的点
		// f : 当前点u的父节点
		// e : 处理到几号边了
		// 如果e==-1，表示之前没有处理过u的任何边
		// 如果e==0，表示u的边都已经处理完了
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				deep[u] = deep[f] + 1;
				stjump[u][0] = f;
				for (int p = 1; p <= power; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
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
		in.nextToken();
		int root = (int) in.nval;
		build(n);
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(root);
		for (int i = 1, a, b; i <= m; i++) {
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			out.println(lca(a, b));
		}
		out.flush();
		out.close();
		br.close();
	}

}
