package class120;

// 牛群聚集(迭代版)
// 一共有n个节点，编号1~n，每个点有牛的数量
// 一共有n-1条边把所有点连通起来形成一棵树，每条边有权值
// 想把所有的牛汇聚在一点，希望走过的总距离最小
// 返回总距离最小是多少
// 利用重心的性质：
// 树上的边权如果都>=0，不管边权怎么分布，所有节点都走向重心的总距离和最小
// 测试链接 : https://www.luogu.com.cn/problem/P2986
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

	public static int[] cow = new int[MAXN];

	public static int cowSum;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static int best, center;

	public static int[] size = new int[MAXN];

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

	// 迭代版
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
	public static void findCenter(int root) {
		stackSize = 0;
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) { // 如果第一次来到当前节点u
				size[u] = cow[u];
				e = head[u];
			} else { // 如果不是第一次来到当前节点u
				e = next[e];
			}
			if (e != 0) { // 如果还有后续边、还有后续子节点
				push(u, f, e);
				if (to[e] != f) {
					push(to[e], u, -1);
				}
			} else { // 如果没有后续边了，那么就做最后的统计工作
				int maxsub = 0;
				for (int i = head[u], v; i != 0; i = next[i]) {
					v = to[i];
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
		}
	}

	// 迭代版
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
	public static void setPath(int root) {
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
					path[v] = path[u] + weight[e];
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
		path[center] = 0;
		setPath(center);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += (long) cow[i] * path[i];
		}
		return ans;
	}

}
