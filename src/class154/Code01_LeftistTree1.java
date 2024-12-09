package class154;

// 左偏树模版题1，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3377
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_LeftistTree1 {

	public static int MAXN = 100001;

	// 左偏树需要
	public static int[] num = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	// 并查集需要
	public static int[] father = new int[MAXN];

	public static int find(int i) {
		if (father[i] != i) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (num[i] > num[j]) {
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
		father[i] = father[left[i]] = father[right[i]] = i;
		return i;
	}

	public static int pop(int i) {
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		// 下面这一句的功能
		// 因为有路径压缩，所以i下方的某个节点x，可能有father[x] = i
		// 但是现在堆要去掉i了，所以x一直往上找到i是无效的
		// 为了i能再往上找到正确的头，所以有下面这句
		father[i] = merge(left[i], right[i]);
		num[i] = -1;
		left[i] = right[i] = 0;
		return father[i];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			in.nextToken();
			num[i] = (int) in.nval;
		}
		for (int i = 1, op, x, y; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				if (num[x] != -1 && num[y] != -1) {
					int l = find(x);
					int r = find(y);
					if (l != r) {
						merge(l, r);
					}
				}
			} else {
				in.nextToken();
				x = (int) in.nval;
				if (num[x] == -1) {
					out.println(-1);
				} else {
					int ans = find(x);
					out.println(num[ans]);
					pop(ans);
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
