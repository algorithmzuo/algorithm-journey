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

	public static int[] arr = new int[MAXN];

	public static int[] sort = new int[MAXN];

	// 维护信息 : 课上讲的up1数组
	// tree1不是up1数组，是up1数组的树状数组
	public static long[] tree1 = new long[MAXN];

	// 维护信息 : 课上讲的up2数组
	// tree2不是up2数组，是up2数组的树状数组
	public static long[] tree2 = new long[MAXN];

	public static int n, m;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(long[] tree, int i, long c) {
		while (i <= m) {
			tree[i] += c;
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

	// 时间复杂度O(n * logn)
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
		for (int i = 1; i <= n; i++) {
			// 查询以当前值做结尾的升序三元组数量
			ans += sum(tree2, arr[i] - 1);
			// 更新以当前值做结尾的升序一元组数量
			add(tree1, arr[i], 1);
			// 更新以当前值做结尾的升序二元组数量
			add(tree2, arr[i], sum(tree1, arr[i] - 1));
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
