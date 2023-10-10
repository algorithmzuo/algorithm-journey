package class064;

// Dijkstra算法模版（洛谷）
// 静态空间实现 : 链式前向星 + 反向索引堆
// 测试链接 : https://www.luogu.com.cn/problem/P4779
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_DijkstraLuogu {

	public static int MAXN = 100001;

	public static int MAXM = 200001;

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// 反向索引堆
	public static int[] heap = new int[MAXN];

	// where[v] = -1，表示v这个节点，从来没有进入过堆
	// where[v] = -2，表示v这个节点，已经弹出过了
	// where[v] = i(>=0)，表示v这个节点，在堆上的i位置
	public static int[] where = new int[MAXN];

	public static int heapSize;

	public static int[] distance = new int[MAXN];

	public static int n, m, s;

	public static void build() {
		cnt = 1;
		heapSize = 0;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(where, 1, n + 1, -1);
		Arrays.fill(distance, 1, n + 1, Integer.MAX_VALUE);
	}

	// 链式前向星建图
	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void addOrUpdateOrIgnore(int v, int w) {
		if (where[v] == -1) {
			heap[heapSize] = v;
			where[v] = heapSize++;
			distance[v] = w;
			heapInsert(where[v]);
		} else if (where[v] >= 0) {
			distance[v] = Math.min(distance[v], w);
			heapInsert(where[v]);
		}
	}

	public static void heapInsert(int i) {
		while (distance[heap[i]] < distance[heap[(i - 1) / 2]]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	public static int pop() {
		int ans = heap[0];
		swap(0, --heapSize);
		heapify(0);
		where[ans] = -2;
		return ans;
	}

	public static void heapify(int i) {
		int l = 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && distance[heap[l + 1]] < distance[heap[l]] ? l + 1 : l;
			best = distance[heap[best]] < distance[heap[i]] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static boolean isEmpty() {
		return heapSize == 0;
	}

	public static void swap(int i, int j) {
		int tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
		where[heap[i]] = i;
		where[heap[j]] = j;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken(); m = (int) in.nval;
			in.nextToken(); s = (int) in.nval;
			build();
			for (int i = 0, u, v, w; i < m; i++) {
				in.nextToken(); u = (int) in.nval;
				in.nextToken(); v = (int) in.nval;
				in.nextToken(); w = (int) in.nval;
				addEdge(u, v, w);
			}
			dijkstra();
			out.print(distance[1]);
			for (int i = 2; i <= n; i++) {
				out.print(" " + distance[i]);
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void dijkstra() {
		addOrUpdateOrIgnore(s, 0);
		while (!isEmpty()) {
			int v = pop();
			for (int ei = head[v]; ei > 0; ei = next[ei]) {
				addOrUpdateOrIgnore(to[ei], distance[v] + weight[ei]);
			}
		}
	}

}
