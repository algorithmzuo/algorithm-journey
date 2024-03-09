package class108;

// 二维数组上范围增加、范围查询，使用树状数组的模版(java)
// 测试链接 : https://www.luogu.com.cn/problem/P4514
// 如下实现是正确的，但是洛谷平台对空间卡的很严，只有使用C++能全部通过
// java的版本就是无法完全通过的，空间会超过限制，主要是IO空间占用大
// 这是洛谷平台没有照顾各种语言的实现所导致的
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的
// C++版本就是Code04_IndexTreeTwoDimensionalArray2文件
// C++版本和java版本逻辑完全一样，但只有C++版本可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_IndexTreeTwoDimensionalArray1 {

	public static int MAXN = 2050;

	public static int MAXM = 2050;

	public static int[][] tree1 = new int[MAXN][MAXM];

	public static int[][] tree2 = new int[MAXN][MAXM];

	public static int[][] tree3 = new int[MAXN][MAXM];

	public static int[][] tree4 = new int[MAXN][MAXM];

	public static int n, m;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int x, int y, int v) {
		for (int i = x; i <= n; i += lowbit(i)) {
			for (int j = y; j <= m; j += lowbit(j)) {
				tree1[i][j] += v;
				tree2[i][j] += x * v;
				tree3[i][j] += y * v;
				tree4[i][j] += x * y * v;
			}
		}
	}

	public static int sum(int x, int y) {
		int ans = 0;
		for (int i = x; i > 0; i -= lowbit(i)) {
			for (int j = y; j > 0; j -= lowbit(j)) {
				ans += (x + 1) * (y + 1) * tree1[i][j] - (y + 1) * tree2[i][j] - (x + 1) * tree3[i][j] + tree4[i][j];
			}
		}
		return ans;
	}

	public static void add(int a, int b, int c, int d, int v) {
		add(a, b, v);
		add(a, d + 1, -v);
		add(c + 1, b, -v);
		add(c + 1, d + 1, v);
	}

	public static int range(int a, int b, int c, int d) {
		return sum(c, d) - sum(a - 1, d) - sum(c, b - 1) + sum(a - 1, b - 1);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		String op;
		int a, b, c, d, v;
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			op = in.sval;
			if (op.equals("X")) {
				in.nextToken(); n = (int) in.nval;
				in.nextToken(); m = (int) in.nval;
			} else if (op.equals("L")) {
				in.nextToken(); a = (int) in.nval;
				in.nextToken(); b = (int) in.nval;
				in.nextToken(); c = (int) in.nval;
				in.nextToken(); d = (int) in.nval;
				in.nextToken(); v = (int) in.nval;
				add(a, b, c, d, v);
			} else {
				in.nextToken(); a = (int) in.nval;
				in.nextToken(); b = (int) in.nval;
				in.nextToken(); c = (int) in.nval;
				in.nextToken(); d = (int) in.nval;
				out.println(range(a, b, c, d));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
