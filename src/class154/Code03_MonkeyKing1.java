package class154;

// 猴王，java版
// 给定n只猴子的战斗力，一开始每个猴子都是独立的阵营
// 一共有m次冲突，每次冲突给定两只猴子的编号x、y
// 如果x和y在同一阵营，这次冲突停止，打印-1
// 如果x和y在不同阵营，x所在阵营的最强猴子会和y所在阵营的最强猴子进行打斗
// 打斗的结果是，两个各自阵营的最强猴子，战斗力都减半，向下取整，其他猴子战力不变
// 然后两个阵营合并，打印合并后的阵营最大战斗力
// 题目可能有多组数据，需要监控输入流直到结束
// 1 <= n, m <= 10^5
// 0 <= 猴子战斗力 <= 32768
// 测试链接 : https://www.luogu.com.cn/problem/P1456
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_MonkeyKing1 {

	public static int MAXN = 100001;

	public static int n, m;

	public static int[] num = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static void prepare() {
		dist[0] = -1;
		for (int i = 1; i <= n; i++) {
			left[i] = right[i] = dist[i] = 0;
			father[i] = i;
		}
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
		// 维护大根堆
		if (num[i] < num[j]) {
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

	public static int fight(int x, int y) {
		int a = find(x);
		int b = find(y);
		if (a == b) {
			return -1;
		}
		int l = pop(a);
		int r = pop(b);
		num[a] /= 2;
		num[b] /= 2;
		father[a] = father[b] = father[l] = father[r] = merge(merge(l, a), merge(r, b));
		return num[father[a]];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			prepare();
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				num[i] = (int) in.nval;
			}
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1, x, y; i <= m; i++) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				out.println(fight(x, y));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
