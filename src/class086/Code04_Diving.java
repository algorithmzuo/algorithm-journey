package class086;

// 潜水的最大时间与方案
// 一共有n个工具，每个工具都有自己的重量a、阻力b、提升的停留时间c
// 因为背包有限，所以只能背重量不超过m的工具
// 因为力气有限，所以只能背阻力不超过v的工具
// 希望能在水下停留的时间最久
// 返回最久的停留时间和下标字典序最小的选择工具的方案
// 字典序设定 : 
// 下标方案整体构成的字符串保证字典序最小
// 比如下标方案"1 120"比下标方案"1 2"字典序小
// 测试链接 : https://www.luogu.com.cn/problem/P1759
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

// 讲解069 - 多维费用背包 + 空间压缩
public class Code04_Diving {

	public static int MAXN = 101;

	public static int MAXM = 201;

	public static int[] a = new int[MAXN];

	public static int[] b = new int[MAXN];

	public static int[] c = new int[MAXN];

	public static int[][] dp = new int[MAXM][MAXM];

	public static String[][] path = new String[MAXM][MAXM];

	public static int m, v, n;

	public static void build() {
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= v; j++) {
				dp[i][j] = 0;
				path[i][j] = null;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			m = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			in.nextToken();
			n = (int) in.nval;
			build();
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				a[i] = (int) in.nval;
				in.nextToken();
				b[i] = (int) in.nval;
				in.nextToken();
				c[i] = (int) in.nval;
			}
			compute();
			out.println(dp[m][v]);
			out.println(path[m][v]);
		}
		out.flush();
		out.close();
		br.close();
	}

	// 这个方法就是讲了很多遍的空间压缩技巧
	// 请务必掌握动态规划前几节课对这一技巧的讲解
	public static void compute() {
		String cur;
		for (int i = 1; i <= n; i++) {
			for (int j = m; j >= a[i]; j--) {
				for (int k = v; k >= b[i]; k--) {
					if (path[j - a[i]][k - b[i]] == null) {
						cur = String.valueOf(i);
					} else {
						cur = path[j - a[i]][k - b[i]] + " " + String.valueOf(i);
					}
					if (dp[j][k] < dp[j - a[i]][k - b[i]] + c[i]) {
						dp[j][k] = dp[j - a[i]][k - b[i]] + c[i];
						path[j][k] = cur;
					} else if (dp[j][k] == dp[j - a[i]][k - b[i]] + c[i]) {
						if (cur.compareTo(path[j][k]) < 0) {
							path[j][k] = cur;
						}
					}
				}
			}
		}
	}

}
