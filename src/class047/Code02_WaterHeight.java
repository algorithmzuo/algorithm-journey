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
	// 所以准备一个长度为OFFSET + MAXN + OFFSET的数组
	// 这样一来，左侧影响最远的位置...右侧影响最远的位置，
	// 都可以被arr中的下标表示出来，就省去了很多越界讨论
	// 详细解释看change方法的注释
	public static int[] arr = new int[OFFSET + MAXN + OFFSET];

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
				// v体积的朋友，在x处落水，修改差分数组
				change(v, x);
			}
			// 生成水位数组
			compute();
			// 开始收集答案
			// 0...OFFSET这些位置是之前的辅助位置，为了防止越界设计的
			// 从OFFSET+1开始往下数m个，才是正式的位置
			// 打印这些位置，就是返回所有正式位置的答案
			int start = OFFSET + 1;
			out.print(arr[start++]);
			for (int i = 2; i <= m; i++) {
				out.print(" " + arr[start++]);
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void change(int v, int x) {
		// 为了防止x - 3 * v + 1是个负数，进而有很多、很烦的边界讨论
		// 所以任何位置，都加上一个较大的数字(OFFSET)
		// 这样一来，所有下标就都在0以上了，省去了太多的边界问题
		// 这就是为什么arr在初始化的时候要准备OFFSET + MAXN + OFFSET这么多的空间
		arr[x - 3 * v + 1 + OFFSET] += 1;
		arr[x - 2 * v + 1 + OFFSET] -= 2;
		arr[x + 1 + OFFSET] += 2;
		arr[x + 2 * v + 1 + OFFSET] -= 2;
		arr[x + 3 * v + 1 + OFFSET] += 1;
	}

	public static void compute() {
		// 先把arr加工成 "增幅数组"
		for (int i = 1; i <= m + OFFSET; i++) {
			arr[i] += arr[i - 1];
		}
		// 最后把arr加工成 "水位数组"
		for (int i = 1; i <= m + OFFSET; i++) {
			arr[i] += arr[i - 1];
		}
	}

}
