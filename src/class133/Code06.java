package class133;

// 测试链接 : https://www.luogu.com.cn/problem/P2962

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06 {

	public static int MAXN = 37;

	public static int[][] mat = new int[MAXN][MAXN];

	public static int[] status = new int[MAXN];

	public static int n, ans;

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				mat[i][j] = 0;
			}
			mat[i][i] = 1;
			mat[i][n + 1] = 1;
			status[i] = 0;
		}
	}

	public static void swap(int a, int b) {
		int[] tmp = mat[a];
		mat[a] = mat[b];
		mat[b] = tmp;
	}

	public static void gauss() {
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				if (mat[j][i] == 1) {
					swap(i, j);
					break;
				}
			}
			if (mat[i][i] == 1) {
				for (int j = 1; j <= n; j++) {
					if (i != j && mat[j][i] == 1) {
						for (int s = i + 1; s <= n + 1; s++) {
							mat[j][s] ^= mat[i][s];
						}
						mat[j][i] = 0;
					}
				}
			}
		}
	}

	public static void gauss2() {
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				if (mat[j][i] == 1) {
					swap(i, j);
					break;
				}
			}
			if (mat[i][i] == 1) {
				for (int j = i + 1; j <= n; j++) {
					if (mat[j][i] == 1) {
						for (int s = i; s <= n + 1; s++) {
							mat[j][s] ^= mat[i][s];
						}
					}
				}
			}
		}
		for (int i = n; i >= 1; i--) {
			for (int j = i + 1; j <= n; j++) {
				mat[i][n + 1] ^= mat[i][j] * mat[j][n + 1];
			}
		}
	}

	public static void dfs(int i, int num) {
		if (num >= ans) {
			return;
		}
		if (i == 0) {
			ans = num;
		} else {
			if (mat[i][i] == 1) {
				int cur = mat[i][n + 1];
				for (int j = i + 1; j <= n; j++) {
					cur ^= mat[i][j] * status[j];
				}
				dfs(i - 1, num + cur);
			} else {
				status[i] = 0;
				dfs(i - 1, num);
				status[i] = 1;
				dfs(i - 1, num + 1);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		prepare();
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1, u, v; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			mat[u][v] = 1;
			mat[v][u] = 1;
		}
		gauss2();
		int sign = 1;
		for (int i = 1; i <= n; i++) {
			if (mat[i][i] == 0) {
				sign = 0;
				break;
			}
		}
		if (sign == 1) {
			ans = 0;
			for (int i = 1; i <= n; i++) {
				ans += mat[i][n + 1];
			}
		} else {
			ans = n;
			dfs(n, 0);
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}