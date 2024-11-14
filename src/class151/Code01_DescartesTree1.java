package class151;

// 笛卡尔树模版(java版)
// 给定一个长度为n的数组arr，下标从1开始
// 构建一棵二叉树，下标按照搜索二叉树组织，值按照小根堆组织
// 建树的过程要求时间复杂度O(n)
// 建树之后，为了验证
// 打印，i * (left[i] + 1)，所有信息异或起来的值
// 打印，i * (right[i] + 1)，所有信息异或起来的值
// 1 <= n <= 10^7
// 测试链接 : https://www.luogu.com.cn/problem/P5854
// 如下实现是正确的，但java的版本无法通过所有测试用例
// 这是洛谷平台没有照顾各种语言的实现所导致的
// C++版本是Code01_DescartesTree2文件
// C++版本和java版本逻辑完全一样，可以通过所有测试用例
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_DescartesTree1 {

	public static int MAXN = 10000001;

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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		build();
		long ans1 = 0, ans2 = 0;
		for (int i = 1; i <= n; i++) {
			ans1 ^= (long) i * (left[i] + 1);
			ans2 ^= (long) i * (right[i] + 1);
		}
		out.println(ans1 + " " + ans2);
		out.flush();
		out.close();
		br.close();
	}

}
