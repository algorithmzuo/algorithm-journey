package class078;

// 没有上司的舞会
// 某大学有n个职员，编号为1...n
// 他们之间有从属关系，也就是说他们的关系就像一棵以校长为根的树
// 父结点就是子结点的直接上司
// 现在有个周年庆宴会，宴会每邀请来一个职员都会增加一定的快乐指数 
// 但是如果某个职员的直接上司来参加舞会了
// 那么这个职员就无论如何也不肯来参加舞会了
// 所以请你编程计算邀请哪些职员可以使快乐指数最大
// 返回最大的快乐指数。
// 测试链接 : https://www.luogu.com.cn/problem/P1352
// 本题和讲解037的题目7类似
// 链式链接 : https://leetcode.cn/problems/house-robber-iii/
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

public class Code05_Dancing {

	public static int MAXN = 6001;

	public static int[] nums = new int[MAXN];

	public static boolean[] boss = new boolean[MAXN];

	// 链式前向星建图
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int cnt;

	// 动态规划表
	// no[i] : i为头的整棵树，在i不来的情况下，整棵树能得到的最大快乐值
	public static int[] no = new int[MAXN];

	// no[i] : i为头的整棵树，在i来的情况下，整棵树能得到的最大快乐值
	public static int[] yes = new int[MAXN];

	public static int n, h;

	public static void build(int n) {
		Arrays.fill(boss, 1, n + 1, true);
		Arrays.fill(head, 1, n + 1, 0);
		cnt = 1;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			build(n);
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				nums[i] = (int) in.nval;
			}
			for (int i = 1, low, high; i < n; i++) {
				in.nextToken();
				low = (int) in.nval;
				in.nextToken();
				high = (int) in.nval;
				addEdge(high, low);
				boss[low] = false;
			}
			for (int i = 1; i <= n; i++) {
				if (boss[i]) {
					h = i;
					break;
				}
			}
			f(h);
			out.println(Math.max(no[h], yes[h]));
		}
		out.flush();
		out.close();
		br.close();
	}

	
	public static void f(int u) {
		no[u] = 0;
		yes[u] = nums[u];
		for (int ei = head[u], v; ei > 0; ei = next[ei]) {
			v = to[ei];
			f(v);
			no[u] += Math.max(no[v], yes[v]);
			yes[u] += no[v];
		}
	}

}
