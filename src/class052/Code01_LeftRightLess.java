package class052;

// 单调栈求每个位置左右两侧，离当前位置最近、且值严格小于的位置
// 给定一个可能含有重复值的数组 arr
// 找到每一个 i 位置左边和右边离 i 位置最近且值比 arr[i] 小的位置
// 返回所有位置相应的信息。
// 输入描述：
// 第一行输入一个数字 n，表示数组 arr 的长度。
// 以下一行输入 n 个数字，表示数组的值
// 输出描述：
// 输出n行，每行两个数字 L 和 R，如果不存在，则值为 -1，下标从 0 开始。
// 测试链接 : https://www.nowcoder.com/practice/2a2c00e7a88a498693568cef63a4b7bb
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_LeftRightLess {

	public static int MAXN = 1000001;

	public static int[] arr = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static int[][] ans = new int[MAXN][2];

	public static int n, r;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			compute();
			for (int i = 0; i < n; i++) {
				out.println(ans[i][0] + " " + ans[i][1]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	// arr[0...n-1]
	public static void compute() {
		r = 0;
		int cur;
		// 遍历阶段
		for (int i = 0; i < n; i++) {
			// i -> arr[i]
			while (r > 0 && arr[stack[r - 1]] >= arr[i]) {
				cur = stack[--r];
				// cur当前弹出的位置，左边最近且小
				ans[cur][0] = r > 0 ? stack[r - 1] : -1;
				ans[cur][1] = i;
			}
			stack[r++] = i;
		}
		// 清算阶段
		while (r > 0) {
			cur = stack[--r];
			ans[cur][0] = r > 0 ? stack[r - 1] : -1;
			ans[cur][1] = -1;
		}
		// 修正阶段
		// 左侧的答案不需要修正一定是正确的，只有右侧答案需要修正
		// 从右往左修正，n-1位置的右侧答案一定是-1，不需要修正
		for (int i = n - 2; i >= 0; i--) {
			if (ans[i][1] != -1 && arr[ans[i][1]] == arr[i]) {
				ans[i][1] = ans[ans[i][1]][1];
			}
		}
	}

}
