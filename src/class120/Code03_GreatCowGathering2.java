package class120;

// 迭代版
// 测试链接 : https://www.luogu.com.cn/problem/P2986
// 所有递归函数一律改成等义的迭代版
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_GreatCowGathering2 {

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

	// nfe是为了实现迭代版而准备的栈
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

	// 迭代版
	public static void findCenter(int root) {
		stackSize = 0;
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				size[u] = cow[u];
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
				int max = 0;
				for (int i = head[u], v; i != 0; i = next[i]) {
					v = to[i];
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
		}
	}

	// 迭代版
	public static void collectWeights(int root) {
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
				int v = to[e];
				if (v != f) {
					pathcost[v] = pathcost[u] + weight[e];
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
		findCenter(1);
		pathcost[center] = 0;
		collectWeights(center);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += (long) cow[i] * pathcost[i];
		}
		return ans;
	}

}
