package class095;

// 反尼姆博弈(反常游戏)
// 一共有n堆石头，两人轮流进行游戏
// 在每个玩家的回合中，玩家需要选择任何一个非空的石头堆，并从这堆石头中移除任意正数的石头数量
// 谁先拿走最后的石头就失败，返回最终谁会获胜
// 先手获胜，打印John
// 后手获胜，打印Brother
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

public class Code04_AntiNimGame {

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
			return eor != 0 ? "John" : "Brother";
		}
	}

}
