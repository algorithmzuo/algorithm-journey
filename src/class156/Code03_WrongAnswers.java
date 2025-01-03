package class156;

// 错误答案数量，带权并查集模版题3
// 有n个数字，下标1 ~ n，但是并不知道每个数字是多少
// 操作 l r v，代表l~r范围上累加和为v
// 一共m个操作，如果某个操作和之前的操作信息自相矛盾，认为当前操作是错误的，不进行这个操作
// 最后打印错误操作的数量
// 1 <= n <= 200000    1 <= m <= 40000
// 累加和不会超过int类型范围
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=3038
// 测试链接 : https://vjudge.net/problem/HDU-3038
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_WrongAnswers {

	public static int MAXN = 200002;

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
		if (lf != rf) {
			father[lf] = rf;
			dist[lf] = v + dist[r] - dist[l];
		}
	}

	public static boolean check(int l, int r, int v) {
		if (find(l) == find(r)) {
			if ((dist[l] - dist[r]) != v) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval + 1;
			in.nextToken();
			m = (int) in.nval;
			prepare();
			for (int i = 1, l, r, v; i <= m; i++) {
				in.nextToken();
				l = (int) in.nval;
				in.nextToken();
				r = (int) in.nval + 1;
				in.nextToken();
				v = (int) in.nval;
				if (!check(l, r, v)) {
					ans++;
				} else {
					union(l, r, v);
				}
			}
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
