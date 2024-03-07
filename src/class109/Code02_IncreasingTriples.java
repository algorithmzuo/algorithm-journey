package class109;

// 升序三元组数量
// 给定一个数组arr，如果i < j < k且arr[i] < arr[j] < arr[k]
// 那么称(i, j, k)为一个升序三元组
// 返回arr中升序三元组的数量
// 测试链接 : https://www.luogu.com.cn/problem/P1637
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

public class Code02_IncreasingTriples {

	public static int MAXN = 30001;

	public static int MAXV = 100001;

	public static int[] arr = new int[MAXN];

	public static long[] tree2 = new long[MAXV];

	public static long[] tree3 = new long[MAXV];

	public static int n, m;

	public static void build() {
		Arrays.fill(tree2, 1, m + 1, 0);
		Arrays.fill(tree3, 1, m + 1, 0);
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(long[] tree, int i, long v) {
		while (i <= m) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static long sum(long[] tree, int i) {
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
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			m = 0;
			for (int i = 0, v; i < n; i++) {
				in.nextToken();
				v = (int) in.nval;
				arr[i] = v;
				m = Math.max(m, v);
			}
			build();
			long ans = 0;
			for (int i = 0; i < n; i++) {
				ans += sum(tree3, arr[i] - 1);
				add(tree2, arr[i], 1);
				add(tree3, arr[i], sum(tree2, arr[i] - 1));
			}
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
