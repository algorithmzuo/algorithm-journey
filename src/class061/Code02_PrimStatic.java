package class061;

// Prim算法优化（洛谷）
// 静态空间实现
// 时间复杂度O(n + m) + O((m+n) * log n)
// 测试链接 : https://www.luogu.com.cn/problem/P3366
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

// 建图用链式前向星
// 堆也是用数组结构手写的、且只和节点个数有关
// 这个实现留给有需要的同学
// 但是一般情况下并不需要做到这个程度

public class Code02_PrimStatic {

	public static int MAXN = 5001;

	public static int MAXM = 400001;

	public static int n, m;

	// 链式前向星建图
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// 改写的堆结构
	public static int[][] heap = new int[MAXN][2];

	// where[v] = -1，表示v这个节点，从来没有进入过堆
	// where[v] = -2，表示v这个节点，已经弹出过了
	// where[v] = i(>=0)，表示v这个节点，在堆上的i位置
	public static int[] where = new int[MAXN];

	// 堆的大小
	public static int heapSize;

	// 找到的节点个数
	public static int nodeCnt;

	public static void build() {
		cnt = 1;
		heapSize = 0;
		nodeCnt = 0;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(where, 1, n + 1, -1);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	// 当前处理的是编号为ei的边！
	public static void addOrUpdateOrIgnore(int ei) {
		int v = to[ei];
		int w = weight[ei];
		// 去往v点，权重w
		if (where[v] == -1) {
			// v这个点，从来没有进入过堆！
			heap[heapSize][0] = v;
			heap[heapSize][1] = w;
			where[v] = heapSize++;
			heapInsert(where[v]);
		} else if (where[v] >= 0) {
			// v这个点的记录，在堆上的位置是where[v]
			heap[where[v]][1] = Math.min(heap[where[v]][1], w);
			heapInsert(where[v]);
		}
	}

	public static void heapInsert(int i) {
		while (heap[i][1] < heap[(i - 1) / 2][1]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	public static int u;

	public static int w;

	// 堆顶的记录：节点 -> u、到节点的花费 -> w
	public static void pop() {
		u = heap[0][0];
		w = heap[0][1];
		swap(0, --heapSize);
		heapify(0);
		where[u] = -2;
		nodeCnt++;
	}

	public static void heapify(int i) {
		int l = 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && heap[l + 1][1] < heap[l][1] ? l + 1 : l;
			best = heap[best][1] < heap[i][1] ? best : i;
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

	// 堆上，i位置的信息 和 j位置的信息 交换！
	public static void swap(int i, int j) {
		int a = heap[i][0];
		int b = heap[j][0];
		where[a] = j;
		where[b] = i;
		int[] tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			build();
			for (int i = 0, u, v, w; i < m; i++) {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				in.nextToken();
				w = (int) in.nval;
				addEdge(u, v, w);
				addEdge(v, u, w);
			}
			int ans = prim();
			out.println(nodeCnt == n ? ans : "orz");
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int prim() {
		// 1节点出发
		nodeCnt = 1;
		where[1] = -2;
		for (int ei = head[1]; ei > 0; ei = next[ei]) {
			addOrUpdateOrIgnore(ei);
		}
		int ans = 0;
		while (!isEmpty()) {
			pop();
			ans += w;
			for (int ei = head[u]; ei > 0; ei = next[ei]) {
				addOrUpdateOrIgnore(ei);
			}
		}
		return ans;
	}

}