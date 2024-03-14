package class109;

// 逆序对数量(树状数组实现)
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
import java.util.Arrays;

public class Code01_NumberOfReversePair2 {

	public static int MAXN = 500001;

	public static int[] arr = new int[MAXN];

	public static int[] sort = new int[MAXN];

	public static int[] tree = new int[MAXN];

	public static int n, m;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i) {
		while (i <= m) {
			tree[i]++;
			i += lowbit(i);
		}
	}

	public static long sum(int i) {
		long ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
			sort[i] = arr[i];
		}
		out.println(number());
		out.flush();
		out.close();
		br.close();
	}

	public static long number() {
		Arrays.sort(sort, 1, n + 1);
		m = 1;
		for (int i = 2; i <= n; i++) {
			if (sort[m] != sort[i]) {
				sort[++m] = sort[i];
			}
		}
		long ans = 0;
		for (int i = n, num; i >= 1; i--) {
			num = rank(arr[i]);
			add(num);
			ans += sum(num - 1);
		}
		return ans;
	}

	public static int rank(int v) {
		int l = 1, r = m, mid;
		int ans = 0;
		while (l <= r) {
			mid = (l + r) / 2;
			if (sort[mid] >= v) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

}
