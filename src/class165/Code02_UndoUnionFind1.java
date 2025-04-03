package class165;

// 可撤销并查集模版题，java版
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc302_h
// 测试链接 : https://atcoder.jp/contests/abc302/tasks/abc302_h
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_UndoUnionFind1 {

	public static int MAXN = 200001;
	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt;

	public static int[] father = new int[MAXN];
	public static int[] ncnt = new int[MAXN];
	public static int[] ecnt = new int[MAXN];

	public static int[][] opstack = new int[MAXN][2];
	public static int stacksiz = 0;

	public static int[] ans = new int[MAXN];
	public static int ball = 0;

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	public static int find(int i) {
		while (i != father[i]) {
			i = father[i];
		}
		return i;
	}

	public static void merge(int h1, int h2) {
		int big, small;
		if (ncnt[h1] >= ncnt[h2]) {
			big = h1;
			small = h2;
		} else {
			big = h2;
			small = h1;
		}
		father[small] = big;
		ncnt[big] += ncnt[small];
		ecnt[big] += ecnt[small] + 1;
		stacksiz++;
		opstack[stacksiz][0] = big;
		opstack[stacksiz][1] = small;
	}

	public static void undo() {
		int big = opstack[stacksiz][0];
		int small = opstack[stacksiz][1];
		stacksiz--;
		father[small] = small;
		ncnt[big] -= ncnt[small];
		ecnt[big] -= ecnt[small] + 1;
	}

	public static void dfs(int u, int fa) {
		int h1 = find(a[u]), h2 = find(b[u]);
		boolean merged = false;
		int add = 0;
		if (h1 == h2) {
			ecnt[h1]++;
			if (ncnt[h1] == ecnt[h1]) {
				ball++;
				add = 1;
			}
		} else {
			if (ecnt[h1] < ncnt[h1] || ecnt[h2] < ncnt[h2]) {
				ball++;
				add = 1;
			}
			merge(h1, h2);
			merged = true;
		}
		if (u != 1) {
			ans[u] = ball;
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			if (to[e] != fa) {
				dfs(to[e], u);
			}
		}
		if (merged) {
			undo();
		} else {
			ecnt[h1]--;
		}
		ball -= add;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			a[i] = (int) in.nval;
			in.nextToken();
			b[i] = (int) in.nval;
		}
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			ncnt[i] = 1;
			ecnt[i] = 0;
		}
		dfs(1, 0);
		for (int i = 2; i < n; i++) {
			out.print(ans[i] + " ");
		}
		out.println(ans[n]);
		out.flush();
		out.close();
		br.close();
	}

}
