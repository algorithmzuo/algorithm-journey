package class129;

// 跑路
// 一共有n个节点，编号1~n，一共有m条有向边，每条边1公里
// 有一个空间跑路器，每秒你都可以直接移动2^k公里，每秒钟可以随意决定k的值
// 题目保证1到n之间一定可以到达，返回1到n最少用几秒
// 2 <= n <= 50
// m <= 10^4
// k <= 64
// 测试链接 : https://www.luogu.com.cn/problem/P1613
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_RanAway {

	public static int MAXN = 61;

	public static int MAXP = 64;

	public static int NA = Integer.MAX_VALUE;

	// st[i][j][p] : i到j的距离是不是2^p
	public static boolean[][][] st = new boolean[MAXN][MAXN][MAXP + 1];

	// time[i][j] : i到j的最短时间
	public static int[][] time = new int[MAXN][MAXN];

	public static int n, m;

	public static void build() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				st[i][j][0] = false;
				time[i][j] = NA;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, u, v; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			st[u][v][0] = true;
			time[u][v] = 1;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	// 需要先掌握，讲解065 - Floyd算法
	public static int compute() {
		// 先枚举次方
		// 再枚举跳板
		// 最后枚举每一组(i,j)
		for (int p = 1; p <= MAXP; p++) {
			for (int jump = 1; jump <= n; jump++) {
				for (int i = 1; i <= n; i++) {
					for (int j = 1; j <= n; j++) {
						if (st[i][jump][p - 1] && st[jump][j][p - 1]) {
							st[i][j][p] = true;
							time[i][j] = 1;
						}
					}
				}
			}
		}
		if (time[1][n] != 1) {
			// 先枚举跳板
			// 最后枚举每一组(i,j)
			for (int jump = 1; jump <= n; jump++) {
				for (int i = 1; i <= n; i++) {
					for (int j = 1; j <= n; j++) {
						if (time[i][jump] != NA && time[jump][j] != NA) {
							time[i][j] = Math.min(time[i][j], time[i][jump] + time[jump][j]);
						}
					}
				}
			}
		}
		return time[1][n];
	}

}
