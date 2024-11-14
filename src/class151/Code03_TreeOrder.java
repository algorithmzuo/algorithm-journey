package class151;

// 树的序
// 给定一个长度为n的数组arr，表示依次插入数字，会形成一棵搜索二叉树
// 也许同样的一个序列，依次插入数字后，也能形成同样形态的搜索二叉树
// 请返回字典序尽量小的结果
// 1 <= n <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1377
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_TreeOrder {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static int n;

	public static void build() {
		for (int i = 1, top = 0, pos = 0; i <= n; i++) {
			pos = top;
			while (pos > 0 && arr[stack[pos]] > arr[i]) {
				pos--;
			}
			if (pos > 0) {
				right[stack[pos]] = i;
			}
			if (pos < top) {
				left[i] = stack[pos + 1];
			}
			stack[++pos] = i;
			top = pos;
		}
	}

	// 防止递归爆栈用迭代的方式实现先序遍历
	public static void pre() {
		int size = 1, i = 0, cur;
		while (size > 0) {
			cur = stack[size--];
			arr[++i] = cur;
			if (right[cur] != 0) {
				stack[++size] = right[cur];
			}
			if (left[cur] != 0) {
				stack[++size] = left[cur];
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[(int) in.nval] = i;
		}
		build();
		pre();
		for (int i = 1; i <= n; i++) {
			out.print(arr[i] + " ");
		}
		out.flush();
		out.close();
		br.close();
	}

}
