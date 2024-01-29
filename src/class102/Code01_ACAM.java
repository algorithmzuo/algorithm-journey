package class102;

// ac自动机模版(威力加强版)
// 测试链接 : https://www.luogu.com.cn/problem/P5357
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_ACAM {

	public static int MAXN = 200001;

	public static int MAXS = 200001;

	public static int MAXC = 26;

	public static int[] end = new int[MAXN];

	public static int[][] tree = new int[MAXS][MAXC];

	public static int[] fail = new int[MAXS];

	public static int[] cnt = new int[MAXS];

	public static int tot = 0;

	public static int[] head = new int[MAXS];

	public static int[] next = new int[MAXS];

	public static int[] to = new int[MAXS];

	public static int edge = 0;

	public static int[] box = new int[MAXS];

	public static boolean[] visited = new boolean[MAXS];

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = Integer.valueOf(in.readLine());
		// ac自动机建树
		for (int i = 1; i <= n; i++) {
			insert(i, in.readLine());
		}
		setFail();
		// 读入大文章
		char[] s = in.readLine().toCharArray();
		for (int u = 0, i = 0; i < s.length; i++) {
			u = tree[u][s[i] - 'a'];
			// 增加匹配次数
			cnt[u]++;
		}
		for (int i = 1; i <= tot; i++) {
			// 根据fail指针建反图
			addEdge(fail[i], i);
		}
		// 在fail指针建的图上
		// 把词频汇总
		mergeCnt2(0);
		for (int i = 1; i <= n; i++) {
			out.println(cnt[end[i]]);
		}
		out.flush();
		out.close();
		in.close();
	}

	public static void insert(int i, String str) {
		char[] s = str.toCharArray();
		int u = 0;
		for (int j = 0, c; j < s.length; j++) {
			c = s[j] - 'a';
			if (tree[u][c] == 0) {
				tree[u][c] = ++tot;
			}
			u = tree[u][c];
		}
		end[i] = u; // 每个模式串在Trie树上的终止节点编号
	}

	public static void setFail() {
		int l = 0;
		int r = 0;
		for (int i = 0; i < MAXC; i++) {
			if (tree[0][i] > 0) {
				box[r++] = tree[0][i];
			}
		}
		while (l < r) {
			int u = box[l++];
			for (int i = 0; i < MAXC; i++) {
				if (tree[u][i] == 0) {
					tree[u][i] = tree[fail[u]][i];
				} else {
					fail[tree[u][i]] = tree[fail[u]][i];
					box[r++] = tree[u][i];
				}
			}
		}
	}

	public static void addEdge(int u, int v) {
		next[++edge] = head[u];
		head[u] = edge;
		to[edge] = v;
	}

	// 逻辑是对的
	// 但是递归开太多层了会爆栈
	// C++这道题不会爆栈
	// java会
	public static void mergeCnt1(int u) {
		for (int i = head[u]; i > 0; i = next[i]) {
			mergeCnt1(to[i]);
			cnt[u] += cnt[to[i]];
		}
	}

	// 改成非递归才能通过
	// 因为是用栈来模拟递归
	// 只消耗内存空间(box和visited)
	// 不消耗系统栈的空间
	// 所以很安全
	public static void mergeCnt2(int u) {
		// box当做栈来使用
		int r = 0;
		box[r++] = u;
		int cur;
		while (r > 0) {
			cur = box[r - 1];
			if (!visited[cur]) {
				visited[cur] = true;
				for (int i = head[cur]; i > 0; i = next[i]) {
					box[r++] = to[i];
				}
			} else {
				r--;
				for (int i = head[cur]; i > 0; i = next[i]) {
					cnt[cur] += cnt[to[i]];
				}
			}
		}
	}

}
