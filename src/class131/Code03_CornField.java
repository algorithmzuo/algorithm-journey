package class131;

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

	public static int[][] dp = new int[MAXN][MAXK];

	public static int[][] tree = new int[MAXH + 1][MAXK + 1];

	public static int n, k;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void update(int x, int y, int v) {
		for (int i = x; i <= MAXH; i += lowbit(i)) {
			for (int j = y; j <= k + 1; j += lowbit(j)) {
				tree[i][j] = Math.max(tree[i][j], v);
			}
		}
	}

	public static int max(int x, int y) {
		int ans = 0;
		for (int i = x; i > 0; i -= lowbit(i)) {
			for (int j = y; j > 0; j -= lowbit(j)) {
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
		for (int i = 1; i <= n; i++) {
			for (int j = k; j >= 0; j--) {
				dp[i][j] = max(arr[i] + j, j + 1) + 1;
				update(arr[i] + j, j + 1, dp[i][j]);
			}
		}
		return max(MAXH, k + 1);
	}

}
