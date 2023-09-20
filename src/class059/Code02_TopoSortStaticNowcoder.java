package class059;

// 拓扑排序模版（牛客）
// 链式前向星建图（静态方式）
// 测试链接 : https://www.nowcoder.com/practice/88f7e156ca7d43a1a535f619cd3f495c
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

public class Code02_TopoSortStaticNowcoder {

	public static int MAXN = 200001;

	public static int MAXM = 200001;

	// 建图相关，链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int cnt;

	// 拓扑排序，用到队列
	public static int[] queue = new int[MAXN];

	public static int l, r;

	// 拓扑排序，入度表
	public static int[] indegree = new int[MAXN];

	// 收集拓扑排序的结果
	public static int[] ans = new int[MAXN];

	public static int n, m;

	public static void build(int n) {
		cnt = 1;
		Arrays.fill(head, 0, n + 1, 0);
		Arrays.fill(indegree, 0, n + 1, 0);
	}

	// 用链式前向星建图
	public static void addEdge(int f, int t) {
		next[cnt] = head[f];
		to[cnt] = t;
		head[f] = cnt++;
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
			if (!topoSort()) {
				out.println(-1);
			} else {
				for (int i = 0; i < n - 1; i++) {
					out.print(ans[i] + " ");
				}
				out.println(ans[n - 1]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	public static boolean topoSort() {
		l = r = 0;
		for (int i = 1; i <= n; i++) {
			if (indegree[i] == 0) {
				queue[r++] = i;
			}
		}
		int fill = 0;
		while (l < r) {
			int cur = queue[l++];
			ans[fill++] = cur;
			// 用链式前向星的方式，遍历cur的相邻节点
			for (int ei = head[cur]; ei != 0; ei = next[ei]) {
				if (--indegree[to[ei]] == 0) {
					queue[r++] = to[ei];
				}
			}
		}
		return fill == n;
	}

}