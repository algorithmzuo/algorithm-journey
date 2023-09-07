package class061;

// Kruskal算法
// 测试链接 : https://www.nowcoder.com/practice/c23eab7bb39748b6b224a8a3afbe396b
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

public class Code01_Kruskal {

	public static int MAXM = 100001;

	public static int[][] edges = new int[MAXM][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			for (int i = 0; i < m; i++) {
				in.nextToken();
				edges[i][0] = (int) in.nval;
				in.nextToken();
				edges[i][1] = (int) in.nval;
				in.nextToken();
				edges[i][2] = (int) in.nval;
			}
			Arrays.sort(edges, 0, m, (a, b) -> a[2] - b[2]);
			build(n);
			int ans = 0;
			for (int[] edge : edges) {
				if (union(edge[0], edge[1])) {
					ans += edge[2];
				}
			}
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int MAXN = 10001;

	public static int[] father = new int[MAXN];

	public static void build(int n) {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	// 如果x和y，原本是一个集合，返回false
	// 如果x和y，不是一个集合，合并之后后返回true
	public static boolean union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			father[fx] = fy;
			return true;
		} else {
			return false;
		}
	}

}
