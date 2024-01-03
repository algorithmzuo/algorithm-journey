package class096;

// 分裂游戏
// 一共有n个瓶子，编号为0~n-1，第i个瓶子里装有pi个糖豆
// 有两个玩家，轮流取糖豆，每一轮的当前玩家必须选i、j、k三个瓶子
// 并且满足i < j <= k，从i号瓶子中拿出1颗，并且往j、k瓶子中各自放入1颗
// 要求i号瓶子中要有糖豆，如果j == k，那么相当于从一个瓶子中拿出1颗，向另一个瓶子放入了2颗
// 如果轮到某个玩家无法按规则行动，那么他将输掉比赛
// 先手希望知道在自己一定获胜的情况下，第一步取糖的行动，字典序最小的结果
// 先手还希望知道自己有多少种第一步取糖的行动，可以确保自己获胜
// 测试链接 : https://www.luogu.com.cn/problem/P3185
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_SplitGame {

	public static int MAXN = 21;

	public static int[] nums = new int[MAXN];

	public static int[] sg = new int[MAXN];

	public static int MAXV = 101;

	public static boolean[] appear = new boolean[MAXV];

	public static int t, n;

	public static void getSG() {
		sg[0] = 0;
		for (int i = 1; i < MAXN; i++) {
			Arrays.fill(appear, false);
			for (int j = i - 1; j >= 0; j--) {
				for (int k = j; k >= 0; k--) {
					appear[sg[j] ^ sg[k]] = true;
				}
			}
			for (int s = 0; s < MAXV; s++) {
				if (!appear[s]) {
					sg[i] = s;
					break;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		getSG();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		t = (int) in.nval;
		while (t-- > 0) {
			in.nextToken();
			n = (int) in.nval;
			for (int i = n - 1; i >= 0; i--) {
				in.nextToken();
				nums[i] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static String compute() {
		int eor = 0;
		for (int i = n - 1; i >= 0; i--) {
			if (nums[i] % 2 == 1) {
				eor ^= sg[i];
			}
		}
		if (eor == 0) {
			return "-1 -1 -1\n" + "0";
		}
		int cnt = 0, a = -1, b = -1, c = -1, rest;
		for (int i = n - 1; i >= 1; i--) {
			if (nums[i] > 0) {
				for (int j = i - 1; j >= 0; j--) {
					for (int k = j; k >= 0; k--) {
						rest = eor ^ sg[i] ^ sg[j] ^ sg[k];
						if (rest == 0) {
							if (a == -1) {
								a = n - 1 - i;
								b = n - 1 - j;
								c = n - 1 - k;
							}
							cnt++;
						}
					}
				}
			}
		}
		return String.valueOf(a + " " + b + " " + c + "\n" + cnt);
	}

}
