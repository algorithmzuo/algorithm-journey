package class117;

// 国旗计划
// 给定点的数量m，点的编号1~m，所有点围成一个环
// i号点一定顺时针到达i+1号点，最终m号点顺指针回到1号点
// 给定n条线段，每条线段(a, b)，表示线段从点a顺时针到点b
// 输入数据保证所有线段可以把整个环覆盖
// 输入数据保证每条线段不会完全在另一条线段的内部
// 也就是线段之间可能有重合但一定互不包含
// 返回一个长度为n的结果数组ans，ans[x]表示一定选x号线段的情况下
// 至少选几条线段能覆盖整个环
// 测试链接 : https://www.luogu.com.cn/problem/P4155
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

public class Code01_FlagPlan {

	public static int MAXN = 200001;

	public static int LIMIT = 18;

	public static int power;

	// 每条线段3个信息 : 线段编号、线段左边界、线段右边界
	public static int[][] line = new int[MAXN << 1][3];

	// stjump[i][p] : 从i号线段出发，跳的次数是2的p次方，能到达的最右线段的编号
	public static int[][] stjump = new int[MAXN << 1][LIMIT];

	public static int[] ans = new int[MAXN];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			line[i][0] = i;
			in.nextToken();
			line[i][1] = (int) in.nval;
			in.nextToken();
			line[i][2] = (int) in.nval;
		}
		compute();
		out.print(ans[1]);
		for (int i = 2; i <= n; i++) {
			out.print(" " + ans[i]);
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

	public static void compute() {
		power = log2(n);
		build();
		for (int i = 1; i <= n; i++) {
			ans[line[i][0]] = jump(i);
		}
	}

	// <=n最接近的2的幂，是2的几次方
	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static void build() {
		for (int i = 1; i <= n; i++) {
			if (line[i][1] > line[i][2]) {
				line[i][2] += m;
			}
		}
		Arrays.sort(line, 1, n + 1, (a, b) -> a[1] - b[1]);
		for (int i = 1; i <= n; i++) {
			line[i + n][0] = line[i][0];
			line[i + n][1] = line[i][1] + m;
			line[i + n][2] = line[i][2] + m;
		}
		int e = n << 1;
		for (int i = 1, arrive = 1; i <= e; i++) {
			while (arrive + 1 <= e && line[arrive + 1][1] <= line[i][2]) {
				arrive++;
			}
			stjump[i][0] = arrive;
		}
		for (int p = 1; p <= power; p++) {
			for (int i = 1; i <= e; i++) {
				stjump[i][p] = stjump[stjump[i][p - 1]][p - 1];
			}
		}
	}

	public static int jump(int i) {
		int aim = line[i][1] + m, cur = i, next, ans = 0;
		for (int p = power; p >= 0; p--) {
			next = stjump[cur][p];
			if (next != 0 && line[next][2] < aim) {
				ans += 1 << p;
				cur = next;
			}
		}
		return ans + 1 + 1;
	}

}
