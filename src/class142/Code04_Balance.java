package class142;

// 天平
// 测试链接 : https://www.luogu.com.cn/problem/P2474

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Balance {

	public static int MAXN = 51;

	public static int[][] dmax = new int[MAXN][MAXN];

	public static int[][] dmin = new int[MAXN][MAXN];

	public static char[][] s = new char[MAXN][MAXN];

	public static int n, a, b;

	public static int c1, c2, c3;

	public static void compute() {
		for (int i = 1; i <= n; i++) {
			dmax[i][i] = dmin[i][i] = 0;
		}
		// 讲解065，Floyd算法
		for (int jump = 1; jump <= n; jump++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					dmax[i][j] = Math.min(dmax[i][jump] + dmax[jump][j], dmax[i][j]);
					dmin[i][j] = Math.max(dmin[i][jump] + dmin[jump][j], dmin[i][j]);
				}
			}
		}
		c1 = c2 = c3 = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = i + 1; j <= n; j++) {
				if (i != a && i != b && j != a && j != b) {
					if (dmin[a][i] + dmin[b][j] > 0 || dmin[a][j] + dmin[b][i] > 0) {
						c1++;
					}
					if (dmax[a][i] + dmax[b][j] < 0 || dmax[a][j] + dmax[b][i] < 0) {
						c3++;
					}
					if (dmin[a][i] == dmax[a][i] && dmin[j][b] == dmax[j][b] && dmin[a][i] + dmin[b][j] == 0) {
						c2++;
					} else if (dmin[a][j] == dmax[a][j] && dmin[i][b] == dmax[i][b] && dmin[a][j] + dmin[b][i] == 0) {
						c2++;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		String[] numbers = br.readLine().split(" ");
		n = Integer.valueOf(numbers[0]);
		a = Integer.valueOf(numbers[1]);
		b = Integer.valueOf(numbers[2]);
		char[] line;
		for (int i = 1; i <= n; i++) {
			line = br.readLine().toCharArray();
			for (int j = 1; j <= n; j++) {
				s[i][j] = line[j - 1];
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (s[i][j] == '=') {
					dmax[i][j] = dmin[i][j] = 0;
				} else if (s[i][j] == '+') {
					dmax[i][j] = 2;
					dmin[i][j] = 1;
				} else if (s[i][j] == '-') {
					dmax[i][j] = -1;
					dmin[i][j] = -2;
				} else {
					dmax[i][j] = 2;
					dmin[i][j] = -2;
				}
			}
		}
		compute();
		out.println(c1 + " " + c2 + " " + c3);
		out.flush();
		out.close();
		br.close();
	}

}
