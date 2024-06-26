package class131;

// 方伯伯的玉米田
// 给定一个长度为n的数组arr
// 每次可以选择一个区间[l,r]，区间内的数字都+1，最多执行k次
// 返回执行完成后，最长的不下降子序列长度
// 1 <= n <= 10^4
// 1 <= arr[i] <= 5000
// 2 <= k <= 500
// 测试链接 : https://www.luogu.com.cn/problem/P3287
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_CornField {

	public static int MAXN = 10001;

	public static int MAXK = 501;

	public static int MAXH = 5500;

	public static int[] arr = new int[MAXN];

	public static int[][] tree = new int[MAXH + 1][MAXK + 1];

	public static int n, k;

	// 树状数组下标必须从1开始
	// 但是操作次数是0~k，那么树状数组下标对应是1~k
	// 所以操作次数是y，树状数组下标是y+1
	public static void update(int x, int y, int v) {
		for (int i = x; i <= MAXH; i += i & -i) {
			for (int j = y + 1; j <= k + 1; j += j & -j) {
				tree[i][j] = Math.max(tree[i][j], v);
			}
		}
	}

	// 树状数组下标必须从1开始
	// 但是操作次数是0~k，那么树状数组下标对应是1~k
	// 所以操作次数是y，树状数组下标是y+1
	public static int max(int x, int y) {
		int ans = 0;
		for (int i = x; i > 0; i -= i & -i) {
			for (int j = y + 1; j > 0; j -= j & -j) {
				ans = Math.max(ans, tree[i][j]);
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		// 注意这里第二层for循环，j一定是从k~0的枚举
		// 课上进行了重点图解，防止同一个i产生的记录之间相互影响
		int v, dp;
		for (int i = 1; i <= n; i++) {
			for (int j = k; j >= 0; j--) {
				v = arr[i] + j;
				dp = max(v, j) + 1;
				update(v, j, dp);
			}
		}
		return max(MAXH, k);
	}

}
