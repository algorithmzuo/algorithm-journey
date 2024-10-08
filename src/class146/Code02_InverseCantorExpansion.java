package class146;

// 逆康托展开
// 测试链接 : https://www.luogu.com.cn/problem/UVA11525
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_InverseCantorExpansion {

	public static int MAXN = 50001;

	public static int[] arr = new int[MAXN];

	public static int[] sum = new int[MAXN << 2];

	public static int n;

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = 1;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			sum[i] = sum[i << 1] + sum[i << 1 | 1];
		}
	}

	public static void add(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			sum[i] += jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				add(jobi, jobv, l, mid, i << 1);
			} else {
				add(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			sum[i] = sum[i << 1] + sum[i << 1 | 1];
		}
	}

	public static int get(int k, int l, int r, int i) {
		int ans;
		if (l == r) {
			ans = l;
		} else {
			int mid = (l + r) >> 1;
			if (sum[i << 1] >= k) {
				ans = get(k, l, mid, i << 1);
			} else {
				ans = get(k - sum[i << 1], mid + 1, r, i << 1 | 1);
			}
		}
		return ans;
	}

	public static void compute() {
		build(1, n, 1);
		for (int i = 1; i <= n; i++) {
			arr[i] = get(arr[i] + 1, 1, n, 1);
			add(arr[i], -1, 1, n, 1);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			compute();
			out.print(arr[1]);
			for (int i = 2; i <= n; i++) {
				out.print(" " + arr[i]);
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

}
