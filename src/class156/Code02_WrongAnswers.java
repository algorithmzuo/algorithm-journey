package class156;

// 错误答案数量
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=3038
// 测试链接 : https://vjudge.net/problem/HDU-3038
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_WrongAnswers {

	public static int MAXN = 200001;

	public static int n, m, ans;

	public static int[] father = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static void prepare() {
		ans = 0;
		for (int i = 0; i <= n; i++) {
			father[i] = i;
			dist[i] = 0;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			int tmp = father[i];
			father[i] = find(tmp);
			dist[i] += dist[tmp];
		}
		return father[i];
	}

	public static void union(int l, int r, int v) {
		int lf = find(l), rf = find(r);
		if (lf == rf) {
			if ((dist[l] - dist[r]) != v) {
				ans++;
			}
		} else {
			father[lf] = rf;
			dist[lf] = v + dist[r] - dist[l];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			prepare();
			for (int i = 1, l, r, v; i <= m; i++) {
				in.nextToken();
				l = (int) in.nval - 1;
				in.nextToken();
				r = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				union(l, r, v);
			}
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
