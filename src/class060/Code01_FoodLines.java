package class060;

// 最大食物链计数
// a -> b，代表a在食物链中被b捕食
// 给定一个有向无环图，返回
// 这个图中从最初级动物到最顶级捕食者的食物链有几条
// 测试链接 : https://www.luogu.com.cn/problem/P4017
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

public class Code01_FoodLines {

	public static int MAXN = 5001;

	public static int MAXM = 500001;

	public static int MOD = 80112002;

	// 链式前向星建图
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int cnt;

	// 拓扑排序需要的队列
	public static int[] queue = new int[MAXN];

	// 拓扑排序需要的入度表
	public static int[] indegree = new int[MAXN];

	// 拓扑排序需要的推送信息
	public static int[] lines = new int[MAXN];

	public static int n, m;

	public static void build(int n) {
		cnt = 1;
		Arrays.fill(indegree, 0, n + 1, 0);
		Arrays.fill(lines, 0, n + 1, 0);
		Arrays.fill(head, 0, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
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
			for (int i = 0, u, v; i < m; i++) {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				addEdge(u, v);
				indegree[v]++;
			}
			out.println(ways());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int ways() {
		int l = 0;
		int r = 0;
		for (int i = 1; i <= n; i++) {
			if (indegree[i] == 0) {
				queue[r++] = i;
				lines[i] = 1;
			}
		}
		int ans = 0;
		while (l < r) {
			int u = queue[l++];
			if (head[u] == 0) {
				// 当前的u节点不再有后续邻居了
				ans = (ans + lines[u]) % MOD;
			} else {
				for (int ei = head[u], v; ei > 0; ei = next[ei]) {
					// u -> v
					v = to[ei];
					lines[v] = (lines[v] + lines[u]) % MOD;
					if (--indegree[v] == 0) {
						queue[r++] = v;
					}
				}
			}
		}
		return ans;
	}

}
