package class142;

// 天平
// 一共有n个砝码，编号1~n，每个砝码的重量均为1克，或者2克，或者3克 
// 砝码与砝码之间的关系是一个n * n的二维数组s
// s[i][j] == '+'，砝码i比砝码j重        s[i][j] == '-'，砝码i比砝码j轻
// s[i][j] == '='，砝码i和砝码j重量一样   s[i][j] == '?'，砝码i和砝码j关系未知
// 数据保证至少存在一种情况符合该矩阵
// 给定编号为a和b的砝码，这两个砝码一定会放在天平的左边，你要另选两个砝码放在天平右边
// 返回有多少种方法，一定让天平左边重(ans1)，一定让天平一样重(ans2)，一定让天平右边重(ans3)
// 1 <= n <= 50
// 测试链接 : https://www.luogu.com.cn/problem/P2474
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Balance {

	public static int MAXN = 51;

	public static int[][] dmin = new int[MAXN][MAXN];

	public static int[][] dmax = new int[MAXN][MAXN];

	public static char[][] s = new char[MAXN][MAXN];

	public static int n, a, b;

	public static int ans1, ans2, ans3;

	public static void compute() {
		// 设置初始关系
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (s[i][j] == '=') {
					dmin[i][j] = 0;
					dmax[i][j] = 0;
				} else if (s[i][j] == '+') {
					dmin[i][j] = 1;
					dmax[i][j] = 2;
				} else if (s[i][j] == '-') {
					dmin[i][j] = -2;
					dmax[i][j] = -1;
				} else {
					dmin[i][j] = -2;
					dmax[i][j] = 2;
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			dmin[i][i] = 0;
			dmax[i][i] = 0;
		}
		// 来自讲解065，Floyd算法
		for (int bridge = 1; bridge <= n; bridge++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					dmin[i][j] = Math.max(dmin[i][j], dmin[i][bridge] + dmin[bridge][j]);
					dmax[i][j] = Math.min(dmax[i][j], dmax[i][bridge] + dmax[bridge][j]);
				}
			}
		}
		// 统计答案
		ans1 = ans2 = ans3 = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j < i; j++) {
				if (i != a && i != b && j != a && j != b) {
					if (dmin[a][i] > dmax[j][b] || dmin[a][j] > dmax[i][b]) {
						ans1++;
					}
					if (dmax[a][i] < dmin[j][b] || dmax[a][j] < dmin[i][b]) {
						ans3++;
					}
					if (dmin[a][i] == dmax[a][i] && dmin[j][b] == dmax[j][b] && dmin[a][i] == dmin[j][b]) {
						ans2++;
					} else if (dmin[b][i] == dmax[b][i] && dmin[j][a] == dmax[j][a] && dmin[b][i] == dmin[j][a]) {
						ans2++;
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
		compute();
		out.println(ans1 + " " + ans2 + " " + ans3);
		out.flush();
		out.close();
		br.close();
	}

}
