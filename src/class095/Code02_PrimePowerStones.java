package class095;

// 质数次方版取石子(巴什博弈扩展)
// 一共有n颗石子，两个人轮流拿
// 每一轮当前选手可以拿 p的k次方 颗石子
// 当前选手可以随意决定p和k，但要保证p是质数、k是自然数
// 拿到最后一颗石子的人获胜
// 根据石子数返回谁赢
// 如果先手赢，返回"October wins!"
// 如果后手赢，输出"Roy wins!"
// 测试链接 : https://www.luogu.com.cn/problem/P4018
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_PrimePowerStones {

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
			out.println(compute(n));
		}
		out.flush();
		out.close();
		br.close();
	}

	public static String compute(int n) {
		return n % 6 != 0 ? "October wins!" : "Roy wins!";
	}

}
