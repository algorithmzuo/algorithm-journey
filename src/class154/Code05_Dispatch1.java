package class154;

// 派遣，dfs用递归实现，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1552
// 提交以下的code，提交时请把类名改成"Main"，一些测试用例通过不了
// 这是因为java语言用递归方式实现dfs，递归会爆栈
// 需要把dfs实现成迭代版，才能全部通过，就是Code05_Dispatch2文件
// C++语言用递归方式实现，可以直接通过，就是Code05_Dispatch3文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_Dispatch1 {

	public static int MAXN = 100001;

	public static int n, m;

	public static long ans;

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int cnt;

	// 薪水
	public static long[] cost = new long[MAXN];

	// 领导力
	public static long[] lead = new long[MAXN];

	// 左孩子
	public static int[] left = new int[MAXN];

	// 右孩子
	public static int[] right = new int[MAXN];

	// 距离
	public static int[] dist = new int[MAXN];

	// 寻找堆顶需要
	public static int[] father = new int[MAXN];

	// 堆的大小
	public static int[] size = new int[MAXN];

	// 堆的费用和
	public static long[] sum = new long[MAXN];

	public static void prepare() {
		ans = 0;
		cnt = 1;
		dist[0] = -1;
		for (int i = 1; i <= n; i++) {
			head[i] = left[i] = right[i] = dist[i] = size[i] = 0;
			sum[i] = 0;
			father[i] = i;
		}
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static int find(int i) {
		father[i] = father[i] == i ? i : find(father[i]);
		return father[i];
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		// 本题是维护大根堆
		if (cost[i] < cost[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		right[i] = merge(right[i], j);
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		father[left[i]] = father[right[i]] = i;
		return i;
	}

	public static int pop(int i) {
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		father[i] = merge(left[i], right[i]);
		left[i] = right[i] = dist[i] = 0;
		return father[i];
	}

	// dfs用递归实现
	public static void dfs(int u) {
		for (int ei = head[u]; ei > 0; ei = next[ei]) {
			dfs(to[ei]);
		}
		int usize = 1;
		long usum = cost[u];
		for (int ei = head[u], v, l, r; ei > 0; ei = next[ei]) {
			v = to[ei];
			l = find(u);
			r = find(v);
			usize += size[r];
			usum += sum[r];
			merge(l, r);
		}
		int i;
		while (usum > m) {
			i = find(u);
			usize--;
			usum -= cost[i];
			pop(i);
		}
		i = find(u);
		size[i] = usize;
		sum[i] = usum;
		ans = Math.max(ans, (long) usize * lead[u]);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		prepare();
		int root = 0;
		for (int i = 1, f; i <= n; i++) {
			in.nextToken();
			f = (int) in.nval;
			in.nextToken();
			cost[i] = (int) in.nval;
			in.nextToken();
			lead[i] = (int) in.nval;
			if (f == 0) {
				root = i;
			} else {
				addEdge(f, i);
			}
		}
		dfs(root);
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
