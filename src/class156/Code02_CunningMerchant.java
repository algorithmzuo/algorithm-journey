package class156;

// 狡猾的商人，带权并查集模版题2
// 有n个月的收入，下标1 ~ n，但是并不知道每个月收入是多少
// 操作 l r v，代表从第l个月到第r个月，总收入为v
// 一共给你m个操作，请判断给定的数据是自洽还是自相矛盾
// 自洽打印true，自相矛盾打印false
// 1 <= n <= 100    1 <= m <= 1000
// 总收入不会超过int类型范围
// 测试链接 : https://www.luogu.com.cn/problem/P2294
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_CunningMerchant {

	public static int MAXN = 102;

	public static int t, n, m;

	public static boolean ans;

	public static int[] father = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static void prepare() {
		ans = true;
		for (int i = 1; i <= n; i++) {
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
		in.nextToken();
		t = (int) in.nval;
		for (int c = 1; c <= t; c++) {
			in.nextToken();
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
					ans = false;
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
