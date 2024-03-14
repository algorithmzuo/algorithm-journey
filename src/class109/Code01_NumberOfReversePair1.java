package class109;

// 逆序对数量(归并分治实现)
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(number(1, n));
		out.flush();
		out.close();
		br.close();
	}

	public static long number(int l, int r) {
		if (l >= r) {
			return 0;
		}
		int m = (l + r) / 2;
		return number(l, m) + number(m + 1, r) + merge(l, m, r);
	}

	public static long merge(int l, int m, int r) {
		int i = r, p1 = m, p2 = r;
		long ans = 0;
		while (p1 >= l && p2 > m) {
			ans += arr[p1] > arr[p2] ? (p2 - m) : 0;
			help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
		}
		while (p1 >= l) {
			help[i--] = arr[p1--];
		}
		while (p2 > m) {
			help[i--] = arr[p2--];
		}
		for (i = l; i <= r; i++) {
			arr[i] = help[i];
		}
		return ans;
	}

}
