package class154;

// 猴王，java版
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
		// 本题是维护大根堆
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
		num[i] /= 2; // 根据题目要求，这里是除以2
		left[i] = right[i] = dist[i] = 0;
		return father[i];
	}

	public static int fight(int x, int y) {
		int l = find(x);
		int r = find(y);
		if (l == r) {
			return -1;
		}
		father[l] = father[r] = merge(merge(pop(l), l), merge(pop(r), r));
		return num[father[l]];
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
				System.out.println(fight(x, y));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
