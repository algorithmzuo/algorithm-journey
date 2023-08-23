package class047;

// 每个点的水位高度问题
// 问题比较复杂，描述见测试链接
// 测试链接 : https://www.luogu.com.cn/problem/P5026
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_WaterHeight {

	public static int MAXN = 1200001;

	public static int[] diff = new int[MAXN];

	public static int[] ans = new int[MAXN];

	// 负数保护
	public static int protection = 30001;

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 0, v, x; i < n; i++) {
				in.nextToken();
				v = (int) in.nval;
				in.nextToken();
				x = (int) in.nval;
				change(v, x);
			}
			compute();
			out.print(ans[1]);
			for (int i = 2; i <= m; i++) {
				out.print(" " + ans[i]);
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void change(int v, int x) {
		diff[x - 3 * v + 1 + protection] += 1;
		diff[x - 2 * v + 1 + protection] -= 2;
		diff[x + 1 + protection] += 2;
		diff[x + 2 * v + 1 + protection] -= 2;
		diff[x + 3 * v + 1 + protection] += 1;
	}

	public static void compute() {
		for (int i = 1; i <= m + protection; i++) {
			diff[i] += diff[i - 1];
		}
		int cur = 0;
		for (int i = 1; i <= protection; i++) {
			cur += diff[i];
		}
		for (int i = protection + 1, j = 1; i <= m + protection; i++, j++) {
			cur += diff[i];
			ans[j] = cur;
		}
	}

}
