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

	// 题目说了m <= 10^6，代表湖泊宽度
	// 这就是MAXN的含义，湖泊最大宽度的极限
	public static int MAXN = 1000001;

	// 数值保护，因为题目中v最大是10000
	// 所以左侧影响最远的位置到达了x - 3 * v + 1
	// 所以右侧影响最远的位置到达了x + 3 * v + 1
	// x如果是正式的位置(1~m)，那么左、右侧可能超过正式位置差不多30000的规模
	// 这就是OFFSET的含义
	public static int OFFSET = 30001;

	// 湖泊宽度是MAXN，是正式位置的范围
	// 左、右侧可能超过正式位置差不多OFFSET的规模
	// 所以准备一个长度为OFFSET + MAXN + OFFSET的diff数组
	// 这样一来，左侧影响最远的位置......右侧影响最远的位置，都可以被diff表示得下
	// 详细解释看代码中的注释
	public static int[] diff = new int[OFFSET + MAXN + OFFSET];

	// 收集正式位置的水位，所以ans准备MAXN大小即可
	public static int[] ans = new int[MAXN];

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
		// 为了防止x - 3 * v + 1是个负数，从而进行更多边界讨论
		// 所以加一个较大的数字，这样一来如下的下标就都在0以上了
		diff[x - 3 * v + 1 + OFFSET] += 1;
		diff[x - 2 * v + 1 + OFFSET] -= 2;
		diff[x + 1 + OFFSET] += 2;
		diff[x + 2 * v + 1 + OFFSET] -= 2;
		diff[x + 3 * v + 1 + OFFSET] += 1;
	}

	public static void compute() {
		// 先把diff加工成 "增幅数组"
		for (int i = 1; i <= m + OFFSET; i++) {
			diff[i] += diff[i - 1];
		}
		// cur = 0表示: 一开始i=0位置的水位是0
		int cur = 0;
		// 正式的水位信息收集开始之前，先得到i=OFFSET位置的水位
		for (int i = 1; i <= OFFSET; i++) {
			cur += diff[i];
		}
		// i = OFFSET + 1位置，就是正式位置(1位置)的开始
		// 每一步得到水位数值，记录在ans中
		// 收集完成后，在主函数打印ans即可
		for (int i = OFFSET + 1, j = 1; i <= m + OFFSET; i++, j++) {
			cur += diff[i];
			ans[j] = cur;
		}
	}

}
