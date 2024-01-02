package class095;

// 反尼姆游戏
// 桌子上有n堆石子，小约翰和他的哥哥轮流取石子
// 每个人取的时候，可以随意选择一堆石子，在这堆石子中取走任意多的石子，但不能一粒石子也不取
// 我们规定取到最后一粒石子的人算输
// 测试链接 : https://www.luogu.com.cn/problem/P4279
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_AntiNim {

	public static int MAXN = 51;

	public static int[] stones = new int[MAXN];

	public static int t, n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		t = (int) in.nval;
		for (int i = 0; i < t; i++) {
			in.nextToken();
			n = (int) in.nval;
			for (int j = 0; j < n; j++) {
				in.nextToken();
				stones[j] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static String compute() {
		int eor = 0, sum = 0;
		for (int i = 0; i < n; i++) {
			eor ^= stones[i];
			sum += stones[i] == 1 ? 1 : 0;
		}
		if (sum == n) {
			return (n & 1) == 1 ? "Brother" : "John";
		} else {
			return eor == 0 ? "Brother" : "John";
		}
	}

}
