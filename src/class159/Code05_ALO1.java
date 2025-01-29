package class159;

// 生成能量密度最大的宝石，java版
// 给定一个长度为n的数组arr，数组中没有重复数字
// 你可以随意选择一个子数组，长度要求大于等于2，因为这样一来，子数组必存在次大值
// 子数组的次大值 ^ 子数组中除了次大值之外随意选一个数字
// 所能得到的最大结果，叫做子数组的能量密度
// 那么必有某个子数组，拥有最大的能量密度，打印这个最大的能量密度
// 2 <= n <= 5 * 10^4
// 0 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4098
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_ALO1 {

	public static int MAXN = 50002;

	public static int MAXT = MAXN * 32;

	public static int BIT = 30;

	public static int n;

	public static int[][] arr = new int[MAXN][2];

	public static int[] root = new int[MAXN];

	public static int[][] tree = new int[MAXT][2];

	public static int[] pass = new int[MAXT];

	public static int cnt;

	public static int[] last = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int insert(int num, int i) {
		int rt = ++cnt;
		tree[rt][0] = tree[i][0];
		tree[rt][1] = tree[i][1];
		pass[rt] = pass[i] + 1;
		for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
			path = (num >> b) & 1;
			i = tree[i][path];
			cur = ++cnt;
			tree[cur][0] = tree[i][0];
			tree[cur][1] = tree[i][1];
			pass[cur] = pass[i] + 1;
			tree[pre][path] = cur;
		}
		return rt;
	}

	public static int query(int num, int u, int v) {
		int ans = 0;
		for (int b = BIT, path, best; b >= 0; b--) {
			path = (num >> b) & 1;
			best = path ^ 1;
			if (pass[tree[v][best]] > pass[tree[u][best]]) {
				ans += 1 << b;
				u = tree[u][best];
				v = tree[v][best];
			} else {
				u = tree[u][path];
				v = tree[v][path];
			}
		}
		return ans;
	}

	public static void prepare() {
		last[0] = 0;
		next[0] = 1;
		last[n + 1] = n;
		next[n + 1] = n + 1;
		for (int i = 1; i <= n; i++) {
			root[i] = insert(arr[i][1], root[i - 1]);
			last[i] = i - 1;
			next[i] = i + 1;
		}
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[1] - b[1]);
	}

	public static int compute() {
		int ans = 0;
		for (int i = 1, index, value, l1, l2, r1, r2; i <= n; i++) {
			index = arr[i][0];
			value = arr[i][1];
			l1 = last[index];
			l2 = last[l1];
			r1 = next[index];
			r2 = next[r1];
			if (l1 != 0) {
				ans = Math.max(ans, query(value, root[l2], root[r1 - 1]));
			}
			if (r1 != n + 1) {
				ans = Math.max(ans, query(value, root[l1], root[r2 - 1]));
			}
			next[l1] = r1;
			last[r1] = l1;
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
			arr[i][0] = i;
			in.nextToken();
			arr[i][1] = (int) in.nval;
		}
		prepare();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
