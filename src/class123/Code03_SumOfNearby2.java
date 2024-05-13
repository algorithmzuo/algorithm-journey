package class123;

// 每个节点距离k以内的权值和(迭代版)
// 注意k并不大
// 测试链接 : https://www.luogu.com.cn/problem/P3047
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_SumOfNearby2 {

	public static int MAXN = 100001;

	public static int MAXK = 21;

	public static int n;

	public static int k;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[][] sum = new int[MAXN][MAXK];

	public static int[] ans = new int[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	// dfs1方法改迭代版
	// 不会改看讲解118，讲了怎么从递归版改成迭代版
	public static int[][] ufe = new int[MAXN][3];

	public static int stackSize;

	public static int u, f, e;

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

	public static void dfs1(int root) {
		stackSize = 0;
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			} else {
				for (int e = head[u], v; e != 0; e = next[e]) {
					v = to[e];
					if (v != f) {
						for (int j = 1; j <= k; j++) {
							sum[u][j] += sum[v][j - 1];
						}
					}
				}
			}
		}
	}

	// dfs2方法改迭代版
	// 不会改看讲解118，讲了怎么从递归版改成迭代版
	public static void dfs2(int root) {
		stackSize = 0;
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				for (int i = 0; i <= k; i++) {
					ans[u] += sum[u][i];
				}
				e = head[u];
			} else {
				int v = to[e];
				if (v != f) {
					// 这里的v是之前的子节点
					// 调整回来
					for (int i = 1; i <= k; i++) {
						sum[v][i] -= sum[u][i - 1];
					}
					for (int i = 1; i <= k; i++) {
						sum[u][i] += sum[v][i - 1];
					}
				}
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				int v = to[e];
				if (v != f) {
					for (int i = 1; i <= k; i++) {
						sum[u][i] -= sum[v][i - 1];
					}
					for (int i = 1; i <= k; i++) {
						sum[v][i] += sum[u][i - 1];
					}
					push(v, u, -1);
				}
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
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			sum[i][0] = (int) in.nval;
		}
		dfs1(1);
		dfs2(1);
		for (int i = 1; i <= n; i++) {
			out.println(ans[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
