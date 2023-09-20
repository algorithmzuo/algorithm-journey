package class059;

import java.util.ArrayList;
import java.util.Arrays;

public class Code01_CreateGraph {

	// 点的最大数量
	public static int MAXN = 11;

	// 边的最大数量
	// 只有链式前向星方式建图需要这个数量
	// 注意如果无向图的最大数量是m条边，数量要准备m*2
	// 因为一条无向边要加两条有向边
	public static int MAXM = 21;

	// 邻接矩阵方式建图
	public static int[][] graph1 = new int[MAXN][MAXN];

	// 邻接表方式建图
	// public static ArrayList<ArrayList<Integer>> graph2 = new ArrayList<>();
	public static ArrayList<ArrayList<int[]>> graph2 = new ArrayList<>();

	// 链式前向星方式建图
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	// 如果边有权重，那么需要这个数组
	public static int[] weight = new int[MAXM];

	public static int cnt;

	public static void build(int n) {
		// 邻接矩阵清空
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				graph1[i][j] = 0;
			}
		}
		// 邻接表清空和准备
		graph2.clear();
		// 下标需要支持1~n，所以加入n+1个列表，0下标准备但不用
		for (int i = 0; i <= n; i++) {
			graph2.add(new ArrayList<>());
		}
		// 链式前向星清空
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	// 链式前向星加边
	public static void addEdge(int u, int v, int w) {
		// u -> v , 边权重是w
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	// 三种方式建立有向图带权图
	public static void directGraph(int[][] edges) {
		// 邻接矩阵建图
		for (int[] edge : edges) {
			graph1[edge[0]][edge[1]] = edge[2];
		}
		// 邻接表建图
		for (int[] edge : edges) {
			// graph2.get(edge[0]).add(edge[1]);
			graph2.get(edge[0]).add(new int[] { edge[1], edge[2] });
		}
		// 链式前向星建图
		for (int[] edge : edges) {
			addEdge(edge[0], edge[1], edge[2]);
		}
	}

	// 三种方式建立无向图带权图
	public static void undirectGraph(int[][] edges) {
		// 邻接矩阵建图
		for (int[] edge : edges) {
			graph1[edge[0]][edge[1]] = edge[2];
			graph1[edge[1]][edge[0]] = edge[2];
		}
		// 邻接表建图
		for (int[] edge : edges) {
			// graph2.get(edge[0]).add(edge[1]);
			// graph2.get(edge[1]).add(edge[0]);
			graph2.get(edge[0]).add(new int[] { edge[1], edge[2] });
			graph2.get(edge[1]).add(new int[] { edge[0], edge[2] });
		}
		// 链式前向星建图
		for (int[] edge : edges) {
			addEdge(edge[0], edge[1], edge[2]);
			addEdge(edge[1], edge[0], edge[2]);
		}
	}

	public static void traversal(int n) {
		System.out.println("邻接矩阵遍历 :");
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(graph1[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("邻接表遍历 :");
		for (int i = 1; i <= n; i++) {
			System.out.print(i + "(邻居、边权) : ");
			for (int[] edge : graph2.get(i)) {
				System.out.print("(" + edge[0] + "," + edge[1] + ") ");
			}
			System.out.println();
		}
		System.out.println("链式前向星 :");
		for (int i = 1; i <= n; i++) {
			System.out.print(i + "(邻居、边权) : ");
			// 注意这个for循环，链式前向星的方式遍历
			for (int ei = head[i]; ei > 0; ei = next[ei]) {
				System.out.print("(" + to[ei] + "," + weight[ei] + ") ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// 理解了带权图的建立过程，也就理解了不带权图
		// 点的编号为1...n
		// 例子1自己画一下图，有向带权图，然后打印结果
		int n1 = 4;
		int[][] edges1 = { { 1, 3, 6 }, { 4, 3, 4 }, { 2, 4, 2 }, { 1, 2, 7 }, { 2, 3, 5 }, { 3, 1, 1 } };
		build(n1);
		directGraph(edges1);
		traversal(n1);
		System.out.println("==============================");
		// 例子2自己画一下图，无向带权图，然后打印结果
		int n2 = 5;
		int[][] edges2 = { { 3, 5, 4 }, { 4, 1, 1 }, { 3, 4, 2 }, { 5, 2, 4 }, { 2, 3, 7 }, { 1, 5, 5 }, { 4, 2, 6 } };
		build(n2);
		undirectGraph(edges2);
		traversal(n2);
	}

}
