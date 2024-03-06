package class108;

// 树状数组范围增加、单点查询模版
// 用于可差分的信息，本题以累加和举例
// 测试链接 : https://www.luogu.com.cn/problem/P3368
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_IndexTreeIntervalAddSingleQuery {

	public static int MAXN = 500002;

	// 树状数组的范围一定从1下标开始，不从0下标开始！
	// tree维持差分数组前缀和信息
	public static int[] tree = new int[MAXN];

	public static int n, m;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	// 返回1~i范围累加和
	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1, v; i <= n; i++) {
				in.nextToken();
				v = (int) in.nval;
				add(i, v);
				add(i + 1, -v);
			}
			for (int i = 1, a, b, c, d; i <= m; i++) {
				in.nextToken();
				a = (int) in.nval;
				if (a == 1) {
					in.nextToken();
					b = (int) in.nval;
					in.nextToken();
					c = (int) in.nval;
					in.nextToken();
					d = (int) in.nval;
					add(b, d);
					add(c + 1, -d);
				} else {
					in.nextToken();
					b = (int) in.nval;
					out.println(sum(b));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
