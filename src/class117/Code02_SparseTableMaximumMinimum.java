package class117;

// ST表查询最大值和最小值
// 给定一个长度为n的数组arr，一共有m次查询
// 每次查询arr[l~r]上的最大值和最小值
// 每次查询只需要打印最大值-最小值的结果
// 测试链接 : https://www.luogu.com.cn/problem/P2880
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_SparseTableMaximumMinimum {

	public static int MAXN = 50001;

	// 2的15次方是<=50001且最接近的
	// 所以次方可能是0~15
	// 于是准备16长度够用了
	public static int LIMIT = 16;

	public static int[] arr = new int[MAXN];

	// log2[i] : 查询<=i情况下，最大的2的幂，是2的几次方
	public static int[] log2 = new int[MAXN];

	public static int[][] stmax = new int[MAXN][LIMIT];

	public static int[][] stmin = new int[MAXN][LIMIT];

	public static void build(int n) {
		log2[0] = -1;
		for (int i = 1; i <= n; i++) {
			log2[i] = log2[i >> 1] + 1;
			stmax[i][0] = arr[i];
			stmin[i][0] = arr[i];
		}
		for (int p = 1; p <= log2[n]; p++) {
			for (int i = 1; i + (1 << p) - 1 <= n; i++) {
				stmax[i][p] = Math.max(stmax[i][p - 1], stmax[i + (1 << (p - 1))][p - 1]);
				stmin[i][p] = Math.min(stmin[i][p - 1], stmin[i + (1 << (p - 1))][p - 1]);
			}
		}
	}

	public static int query(int l, int r) {
		int p = log2[r - l + 1];
		int a = Math.max(stmax[l][p], stmax[r - (1 << p) + 1][p]);
		int b = Math.min(stmin[l][p], stmin[r - (1 << p) + 1][p]);
		return a - b;
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
