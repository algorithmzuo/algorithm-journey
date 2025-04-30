package class061;

// Prim算法模版（洛谷）
// 动态空间实现
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
import java.util.ArrayList;
import java.util.PriorityQueue;

// 时间复杂度O(n + m) + O(m * log m)
public class Code02_PrimDynamic {
//todo 先构建图，正向逆向都建，然后构建小根堆，根据权重排序，找一个节点，将他的临近节点都添加到堆中，弹出判断是否读过，没读过就依次处理。
//   这个找一个点，查看他的最小权重的点是否处理过
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			ArrayList<ArrayList<int[]>> graph = new ArrayList<>();
			int n = (int) in.nval;
			for (int i = 0; i <= n; i++) {
				graph.add(new ArrayList<>());
			}
			in.nextToken();
			int m = (int) in.nval;
			for (int i = 0, u, v, w; i < m; i++) {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				in.nextToken();
				w = (int) in.nval;
				graph.get(u).add(new int[] { v, w });
				graph.get(v).add(new int[] { u, w });
			}
			// int[] record
			// record[0] : 到达的节点
			// record[1] : 到达的花费
			PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
			for (int[] edge : graph.get(1)) {
				heap.add(edge);
			}
			// 哪些节点已经发现过了
			boolean[] set = new boolean[n + 1];
			int nodeCnt = 1;
			set[1] = true;
			int ans = 0;
			while (!heap.isEmpty()) {
				int[] edge = heap.poll();
				int next = edge[0];
				int cost = edge[1];
				if (!set[next]) {
					nodeCnt++;
					set[next] = true;
					ans += cost;
					for (int[] e : graph.get(next)) {
						heap.add(e);
					}
				}
			}
			out.println(nodeCnt == n ? ans : "orz");
		}
		out.flush();
		out.close();
		br.close();
	}

}