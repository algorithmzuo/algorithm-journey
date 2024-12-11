package class154;

// 派遣，dfs用迭代实现，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1552
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_Dispatch2 {

	public static int MAXN = 100001;

	public static int n, m;

	public static long ans;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int cnt;

	public static long[] cost = new long[MAXN];

	public static long[] lead = new long[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static long[] sum = new long[MAXN];

	public static void prepare() {
		ans = 0;
		cnt = 1;
		for (int i = 1; i <= n; i++) {
			head[i] = left[i] = right[i] = dist[i] = size[i] = 0;
			sum[i] = 0;
			father[i] = i;
		}
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static int find(int i) {
		if (father[i] != i) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (cost[i] < cost[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		right[i] = merge(right[i], j);
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		father[i] = father[left[i]] = father[right[i]] = i;
		return i;
	}

	public static int pop(int i) {
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		father[i] = merge(left[i], right[i]);
		left[i] = right[i] = 0;
		return father[i];
	}

	// dfs从递归版改成迭代版，不会的话，看讲解118
	// 重点讲了树上问题从递归版改成迭代版的方法
	public static int[][] ue = new int[MAXN][2];

	public static int u, ei;

	public static int ssize;

	public static void push(int u, int ei) {
		ue[ssize][0] = u;
		ue[ssize][1] = ei;
		ssize++;
	}

	public static void pop() {
		--ssize;
		u = ue[ssize][0];
		ei = ue[ssize][1];
	}

	// dfs用迭代实现
	public static void dfs(int root) {
		ssize = 0;
		push(root, -1);
		while (ssize > 0) {
			pop();
			if (ei == -1) {
				ei = head[u];
			} else {
				ei = next[ei];
			}
			if (ei > 0) {
				push(u, ei);
				push(to[ei], -1);
			} else {
				int usize = 1;
				long usum = cost[u];
				for (int ei = head[u], v, l, r; ei > 0; ei = next[ei]) {
					v = to[ei];
					l = find(u);
					r = find(v);
					usize += size[r];
					usum += sum[r];
					merge(l, r);
				}
				int i;
				while (usum > m) {
					i = find(u);
					usize--;
					usum -= cost[i];
					pop(i);
				}
				i = find(u);
				size[i] = usize;
				sum[i] = usum;
				ans = Math.max(ans, (long) usize * lead[u]);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		prepare();
		int root = 0;
		for (int i = 1, f; i <= n; i++) {
			in.nextToken();
			f = (int) in.nval;
			in.nextToken();
			cost[i] = (int) in.nval;
			in.nextToken();
			lead[i] = (int) in.nval;
			if (f == 0) {
				root = i;
			} else {
				addEdge(f, i);
			}
		}
		dfs(root);
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
