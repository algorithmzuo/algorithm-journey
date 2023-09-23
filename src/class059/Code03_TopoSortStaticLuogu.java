package class059;

// 字典序最小的拓扑排序
// 要求返回所有正确的拓扑排序中 字典序最小 的结果
// 建图请使用链式前向星方式，因为比赛平台用其他建图方式会卡空间
// 测试链接 : https://www.luogu.com.cn/problem/U107394
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

public class Code03_TopoSortStaticLuogu {

	public static int MAXN = 100001;

	public static int MAXM = 100001;

	// 建图相关，链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int cnt;

	// 拓扑排序，不用队列，用小根堆，为了得到字典序最小的拓扑排序
	public static int[] heap = new int[MAXN];

	public static int heapSize;

	// 拓扑排序，入度表
	public static int[] indegree = new int[MAXN];

	// 收集拓扑排序的结果
	public static int[] ans = new int[MAXN];

	public static int n, m;

	// 清理之前的脏数据
	public static void build(int n) {
		cnt = 1;
		heapSize = 0;
		Arrays.fill(head, 0, n + 1, 0);
		Arrays.fill(indegree, 0, n + 1, 0);
	}

	// 用链式前向星建图
	public static void addEdge(int f, int t) {
		next[cnt] = head[f];
		to[cnt] = t;
		head[f] = cnt++;
	}

	// 小根堆里加入数字
	public static void push(int num) {
		int i = heapSize++;
		heap[i] = num;
		// heapInsert的过程
		while (heap[i] < heap[(i - 1) / 2]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	// 小根堆里弹出最小值
	public static int pop() {
		int ans = heap[0];
		heap[0] = heap[--heapSize];
		// heapify的过程
		int i = 0;
		int l = 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && heap[l + 1] < heap[l] ? l + 1 : l;
			best = heap[best] < heap[i] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
		return ans;
	}

	// 判断小根堆是否为空
	public static boolean isEmpty() {
		return heapSize == 0;
	}

	// 交换堆上两个位置的数字
	public static void swap(int i, int j) {
		int tmp = heap[i];
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
			build(n);
			for (int i = 0, from, to; i < m; i++) {
				in.nextToken();
				from = (int) in.nval;
				in.nextToken();
				to = (int) in.nval;
				addEdge(from, to);
				indegree[to]++;
			}
			topoSort();
			for (int i = 0; i < n - 1; i++) {
				out.print(ans[i] + " ");
			}
			out.println(ans[n - 1]);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void topoSort() {
		for (int i = 1; i <= n; i++) {
			if (indegree[i] == 0) {
				push(i);
			}
		}
		int fill = 0;
		while (!isEmpty()) {
			int cur = pop();
			ans[fill++] = cur;
			// 用链式前向星的方式，遍历cur的相邻节点
			for (int ei = head[cur]; ei != 0; ei = next[ei]) {
				if (--indegree[to[ei]] == 0) {
					push(to[ei]);
				}
			}
		}
	}

}