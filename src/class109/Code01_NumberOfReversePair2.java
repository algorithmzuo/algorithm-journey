package class109;

// 逆序对数量(值域树状数组)
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

	public static void add(int i, int v) {
		while (i <= m) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	// 1~i范围的累加和
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
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		Arrays.sort(sort, 1, n + 1);
		m = 1;
		for (int i = 2; i <= n; i++) {
			if (sort[m] != sort[i]) {
				sort[++m] = sort[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = rank(arr[i]);
		}
		long ans = 0;
		for (int i = n; i >= 1; i--) {
			// 右边有多少数字是 <= 当前数值 - 1
			ans += sum(arr[i] - 1);
			// 增加当前数字的词频
			add(arr[i], 1);
		}
		return ans;
	}

	// 给定原始值v
	// 返回排名值(排序部分1~m中的下标)
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
