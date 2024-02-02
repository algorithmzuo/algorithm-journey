package class102;

// AC自动机模版(优化版)
// 给你若干目标字符串，还有一篇文章
// 返回每个目标字符串在文章中出现了几次
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

	// 目标字符串的数量
	public static int MAXN = 200001;

	// 所有目标字符串的总字符数量
	public static int MAXS = 200001;

	// 记录每个目标串的结尾节点编号
	public static int[] end = new int[MAXN];

	// AC自动机
	public static int[][] tree = new int[MAXS][26];

	public static int[] fail = new int[MAXS];

	public static int cnt = 0;

	// 具体题目相关，本题为收集词频
	// 所以每个节点记录词频
	public static int[] times = new int[MAXS];

	// 可以用作队列或者栈，一个容器而已
	public static int[] box = new int[MAXS];

	// 链式前向星，为了建立fail指针的反图
	public static int[] head = new int[MAXS];

	public static int[] next = new int[MAXS];

	public static int[] to = new int[MAXS];

	public static int edge = 0;

	// 遍历fail反图，递归方法会爆栈，所以用非递归替代
	public static boolean[] visited = new boolean[MAXS];

	// AC自动机加入目标字符串
	public static void insert(int i, String str) {
		char[] s = str.toCharArray();
		int u = 0;
		for (int j = 0, c; j < s.length; j++) {
			c = s[j] - 'a';
			if (tree[u][c] == 0) {
				tree[u][c] = ++cnt;
			}
			u = tree[u][c];
		}
		// 每个目标字符串的结尾节点编号
		end[i] = u;
	}

	// 加入所有目标字符串之后
	// 设置fail指针 以及 设置直接直通表
	// 做了AC自动机固定的优化
	public static void setFail() {
		// box当做队列来使用
		int l = 0;
		int r = 0;
		for (int i = 0; i <= 25; i++) {
			if (tree[0][i] > 0) {
				box[r++] = tree[0][i];
			}
		}
		while (l < r) {
			int u = box[l++];
			for (int i = 0; i <= 25; i++) {
				if (tree[u][i] == 0) {
					tree[u][i] = tree[fail[u]][i];
				} else {
					fail[tree[u][i]] = tree[fail[u]][i];
					box[r++] = tree[u][i];
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = Integer.valueOf(in.readLine());
		// AC自动机建树
		for (int i = 1; i <= n; i++) {
			insert(i, in.readLine());
		}
		setFail();
		// 读入大文章
		char[] s = in.readLine().toCharArray();
		for (int u = 0, i = 0; i < s.length; i++) {
			u = tree[u][s[i] - 'a'];
			// 增加匹配次数
			times[u]++;
		}
		for (int i = 1; i <= cnt; i++) {
			// 根据fail指针建反图
			// 其实是一颗树
			addEdge(fail[i], i);
		}
		// 遍历fail指针建的树
		// 汇总每个节点的词频
		f2(0);
		for (int i = 1; i <= n; i++) {
			out.println(times[end[i]]);
		}
		out.flush();
		out.close();
		in.close();
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
	public static void f1(int u) {
		for (int i = head[u]; i > 0; i = next[i]) {
			f1(to[i]);
			times[u] += times[to[i]];
		}
	}

	// 改成非递归才能通过
	// 因为是用栈来模拟递归
	// 只消耗内存空间(box和visited)
	// 不消耗系统栈的空间
	// 所以很安全
	public static void f2(int u) {
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
					times[cur] += times[to[i]];
				}
			}
		}
	}

}
