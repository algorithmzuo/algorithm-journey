package class117;

// 国旗计划
// 给定点的数量m，每个点编号1~m，所有点围成一个环
// 给定n条线段，每条线段(a, b)，表示线段从a顺时针到b
// 输入数据保证线段可以把整个环覆盖并且线段之间互不包含
// 返回一个结果数组ans，ans[i]表示一定选i号线段的情况下
// 至少需要几条线段才能覆盖整个环
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

public class Code04_FlagPlan {

	public static int MAXN = 200001;

	// 200001不超过2的18次方
	public static int STEP = 18;

	// 每条线段3个信息  : 线段编号、线段左边界、线段右边界
	public static int[][] line = new int[MAXN << 1][3];

	// starrive[i][s] : 从i号线段出发，跳的次数是2的s次方，能到达的最右线段的编号
	public static int[][] starrive = new int[MAXN << 1][STEP];

	public static int[] ans = new int[MAXN];

	public static int n, m, limit;

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
			starrive[i][0] = arrive;
		}
		for (int s = 1; s <= limit; s++) {
			for (int i = 1; i <= e; i++) {
				starrive[i][s] = starrive[starrive[i][s - 1]][s - 1];
			}
		}
	}

	public static int jump(int i) {
		int aim = line[i][1] + m, cur = i, next, ans = 1;
		for (int s = limit; s >= 0; s--) {
			next = starrive[cur][s];
			if (next != 0 && line[next][2] < aim) {
				ans += 1 << s;
				cur = next;
			}
		}
		return ans + 1;
	}

	public static void compute() {
		limit = log2(n);
		build();
		for (int i = 1; i <= n; i++) {
			ans[line[i][0]] = jump(i);
		}
	}

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

}
