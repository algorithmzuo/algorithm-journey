package class109;

// 逆序对数量(归并分治)
// 给定一个长度为n的数组arr
// 如果 i < j 且 arr[i] > arr[j]
// 那么(i,j)就是一个逆序对
// 求arr中逆序对的数量
// 1 <= n <= 5 * 10^5
// 1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1908
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_NumberOfReversePair1 {

	public static int MAXN = 500001;

	public static int[] arr = new int[MAXN];

	public static int[] help = new int[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		return f(1, n);
	}

	// 归并分治
	// 1) 统计i、j来自l~r范围的情况下，逆序对数量
	// 2) 统计完成后，让arr[l...r]变成有序的
	public static long f(int l, int r) {
		if (l == r) {
			return 0;
		}
		int m = (l + r) / 2;
		return f(l, m) + f(m + 1, r) + merge(l, m, r);
	}

	public static long merge(int l, int m, int r) {
		// i来自l.....m
		// j来自m+1...r
		// 统计有多少逆序对
		long ans = 0;
		for (int i = m, j = r; i >= l; i--) {
			while (j >= m + 1 && arr[i] <= arr[j]) {
				j--;
			}
			ans += j - m;
		}
		// 左右部分合并，整体变有序，归并排序的过程
		int i = l;
		int a = l;
		int b = m + 1;
		while (a <= m && b <= r) {
			help[i++] = arr[a] <= arr[b] ? arr[a++] : arr[b++];
		}
		while (a <= m) {
			help[i++] = arr[a++];
		}
		while (b <= r) {
			help[i++] = arr[b++];
		}
		for (i = l; i <= r; i++) {
			arr[i] = help[i];
		}
		return ans;
	}

}
