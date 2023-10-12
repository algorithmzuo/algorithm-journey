package class065;

// Floyd算法模版（洛谷）
// 本题测试链接 : https://www.luogu.com.cn/problem/P2910
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_Floyd {

	public static int MAXN = 101;

	public static int MAXM = 10001;

	public static int[] path = new int[MAXM];

	public static int[][] distance = new int[MAXN][MAXN];

	public static int n, m, ans;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 0; i < m; i++) {
				in.nextToken();
				path[i] = (int) in.nval - 1;
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					in.nextToken();
					distance[i][j] = (int) in.nval;
				}
			}
			floyd();
			ans = 0;
			for (int i = 1; i < m; i++) {
				ans += distance[path[i - 1]][path[i]];
			}
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void floyd() {
		// O(N^3)的过程
		// 枚举每个跳板
		// 注意，跳板要最先枚举！跳板要最先枚举！跳板要最先枚举！
		for (int bridge = 0; bridge < n; bridge++) { // 跳板
			for (int from = 0; from < n; from++) { // from
				for (int to = 0; to < n; to++) { // to
					if (distance[from][bridge] != Integer.MAX_VALUE 
							&& distance[bridge][to] != Integer.MAX_VALUE
							&& distance[from][to] > distance[from][bridge] + distance[bridge][to]) {
						distance[from][to] = distance[from][bridge] + distance[bridge][to];
					}
				}
			}
		}
	}

}
