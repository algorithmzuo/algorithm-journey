package class117;

// ST表查询最大公约数
// 给定一个长度为n的数组arr，一共有m次查询
// 每次查询arr[l~r]上所有数的最大公约数
// 测试链接 : https://www.luogu.com.cn/problem/P1890
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_SparseTableGCD {

	public static int MAXN = 1001;

	public static int LIMIT = 10;

	public static int[] arr = new int[MAXN];

	public static int[] log2 = new int[MAXN];

	public static int[][] stgcd = new int[MAXN][LIMIT];

	public static void build(int n) {
		log2[0] = -1;
		for (int i = 1; i <= n; i++) {
			log2[i] = log2[i >> 1] + 1;
			stgcd[i][0] = arr[i];
		}
		for (int p = 1; p <= log2[n]; p++) {
			for (int i = 1; i + (1 << p) - 1 <= n; i++) {
				stgcd[i][p] = gcd(stgcd[i][p - 1], stgcd[i + (1 << (p - 1))][p - 1]);
			}
		}
	}

	// 算法讲解041 - 辗转相除法求最大公约数
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static int query(int l, int r) {
		int p = log2[r - l + 1];
		return gcd(stgcd[l][p], stgcd[r - (1 << p) + 1][p]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		build(n);
		for (int i = 1, l, r; i <= m; i++) {
			in.nextToken();
			l = (int) in.nval;
			in.nextToken();
			r = (int) in.nval;
			out.println(query(l, r));
		}
		out.flush();
		out.close();
		br.close();
	}

}
