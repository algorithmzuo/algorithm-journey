package class156;

// 推导部分和
// 测试链接 : https://www.luogu.com.cn/problem/P8779
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_DerivePartialSums {

	public static int MAXN = 200001;

	public static long INF = Long.MAX_VALUE;

	public static int n, m, q;

	public static int[] father = new int[MAXN];

	public static long[] dist = new long[MAXN];

	public static void prepare() {
		for (int i = 0; i <= n; i++) {
			father[i] = i;
			dist[i] = 0;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			int tmp = father[i];
			father[i] = find(father[i]);
			dist[i] += dist[tmp];
		}
		return father[i];
	}

	public static void union(int l, int r, long v) {
		int lf = find(l), rf = find(r);
		if (lf != rf) {
			father[lf] = rf;
			dist[lf] = v + dist[r] - dist[l];
		}
	}

	public static long query(int l, int r) {
		if (find(l) != find(r)) {
			return INF;
		}
		return dist[l] - dist[r];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		in.nextToken();
		q = (int) in.nval;
		prepare();
		int l, r;
		long v;
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			l = (int) in.nval - 1;
			in.nextToken();
			r = (int) in.nval;
			in.nextToken();
			v = (long) in.nval;
			union(l, r, v);
		}
		for (int i = 1; i <= q; i++) {
			in.nextToken();
			l = (int) in.nval - 1;
			in.nextToken();
			r = (int) in.nval;
			v = query(l, r);
			if (v == INF) {
				out.println("UNKNOWN");
			} else {
				out.println(v);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
