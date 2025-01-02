package class156;

// 关押罪犯
// 测试链接 : https://www.luogu.com.cn/problem/P1525
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code07_DetainCriminals {

	public static int MAXN = 20002;

	public static int MAXM = 100001;

	public static int n, m;

	public static int[] father = new int[MAXN];

	public static int[] enemy = new int[MAXN];

	public static int[][] arr = new int[MAXM][3];

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			enemy[i] = 0;
		}
	}

	public static int find(int i) {
		father[i] = father[i] == i ? i : find(father[i]);
		return father[i];
	}

	public static void union(int l, int r) {
		father[find(l)] = find(r);
	}

	public static boolean same(int l, int r) {
		return find(l) == find(r);
	}

	public static int compute() {
		Arrays.sort(arr, 1, m + 1, (a, b) -> b[2] - a[2]);
		int ans = 0;
		for (int i = 1, l, r, v; i <= m; i++) {
			l = arr[i][0];
			r = arr[i][1];
			v = arr[i][2];
			if (same(l, r)) {
				ans = v;
				break;
			} else {
				if (enemy[l] == 0) {
					enemy[l] = r;
				} else {
					union(enemy[l], r);
				}
				if (enemy[r] == 0) {
					enemy[r] = l;
				} else {
					union(l, enemy[r]);
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		prepare();
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			arr[i][0] = (int) in.nval;
			in.nextToken();
			arr[i][1] = (int) in.nval;
			in.nextToken();
			arr[i][2] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
