package class064;

// Dijkstra算法模版（洛谷），静态空间实现
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

// 以下的实现，即便是比赛也能通过
// 建图用链式前向星、堆也是用数组结构手写的，不用任何动态结构
// 这个实现留给有需要的同学，这个测试必须这么写才能通过
// 但是一般情况下并不需要做到这个程度
public class Code02_DijkstraLuogu {

	public static int MAXN = 100001;

	public static int MAXM = 200001;

	// head、next、to、weight、cnt是链式前向星建图需要的
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// 自己实现堆
	// heap[i][0] : 来到的当前点
	// heap[i][1] : 从s来到当前点的距离
	// heap、hsize是建堆需要的
	public static int[][] heap = new int[MAXM][2];

	public static int hsize;

	// ans[i] : s到i的最短距离
	public static int[] ans = new int[MAXN];

	public static int n, m, s;

	// 该做的初始化
	public static void build(int n) {
		cnt = 1;
		hsize = 0;
		Arrays.fill(head, 0, n + 1, 0);
		Arrays.fill(ans, 0, n + 1, -1);
	}

	// 链式前向星建图
	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	// 记录加入堆
	public static void heapAdd(int v, int w) {
		heap[hsize][0] = v;
		heap[hsize][1] = w;
		heapInsert(hsize++);
	}

	public static int cur, wei;

	// 从堆顶拿出记录，更新到cur和wei，供上游使用
	// 弹出数据后，继续维持小根堆
	public static void heapPop() {
		cur = heap[0][0];
		wei = heap[0][1];
		swap(0, --hsize);
		heapify(0);
	}

	// 堆的常见方法
	public static void heapInsert(int i) {
		while (heap[i][1] < heap[(i - 1) / 2][1]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	// 堆的常见方法
	public static void heapify(int i) {
		int l = i * 2 + 1;
		while (l < hsize) {
			int best = l + 1 < hsize && heap[l + 1][1] < heap[l][1] ? l + 1 : l;
			best = heap[best][1] < heap[i][1] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static void swap(int i, int j) {
		int tmp = heap[i][0];
		heap[i][0] = heap[j][0];
		heap[j][0] = tmp;
		tmp = heap[i][1];
		heap[i][1] = heap[j][1];
		heap[j][1] = tmp;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			in.nextToken();
			s = (int) in.nval;
			build(n);
			for (int i = 0, u, v, w; i < m; i++) {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				in.nextToken();
				w = (int) in.nval;
				addEdge(u, v, w);
			}
			dijkstra();
			out.print(ans[1]);
			for (int i = 2; i <= n; i++) {
				out.print(" " + ans[i]);
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void dijkstra() {
		heapAdd(s, 0);
		while (hsize > 0) {
			heapPop();
			if (ans[cur] != -1) {
				continue;
			}
			ans[cur] = wei;
			// 链式前向星的方式遍历
			for (int edge = head[cur]; edge != 0; edge = next[edge]) {
				if (ans[to[edge]] == -1) {
					heapAdd(to[edge], weight[edge] + wei);
				}
			}
		}
	}

}
