package class127;

// 青蛙过河最少踩的石子数
// 在河上有一座独木桥，一只青蛙想沿着独木桥从河的一侧跳到另一侧
// 在桥上有一些石子，青蛙很讨厌踩在这些石子上
// 我们可以把独木桥上青蛙可能到达的点看成数轴上的一串整点0...l
// 其中L是桥的长度，坐标为0的点表示桥的起点，坐标为l的点表示桥的终点
// 青蛙从桥的起点开始，不停的向终点方向跳跃，一次跳跃的距离是[s,t]之间的任意正整数
// 当青蛙跳到或跳过坐标为l的点时，就算青蛙已经跳出了独木桥
// 题目给出独木桥的长度l，青蛙跳跃的距离范围s、t，题目还给定m个桥上石子的位置
// 你的任务是确定青蛙要想过河，最少需要踩到的石子数
// 测试链接 : https://www.luogu.com.cn/problem/P1052
// 1 <= n <= 10^7
// 1 <= s <= t <= 10
// 1 <= m <= 100
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04 {

	public static int MAXN = 101;

	public static int MAXL = 100001;

	public static int MAXK = 201;

	public static int[] arr = new int[MAXN];

	public static int[] distance = new int[MAXN];

	public static int[] dp = new int[MAXL];

	public static boolean[] stone = new boolean[MAXL];

	public static boolean[] reach = new boolean[201];

	public static int l, s, t, m, cut;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		l = (int) in.nval;
		in.nextToken();
		s = (int) in.nval;
		in.nextToken();
		t = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= m; ++i) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		if (s == t) {
			int ans = 0;
			for (int i = 1; i <= Math.min(l, m); ++i) {
				if (arr[i] % s == 0) {
					ans++;
				}
			}
			return ans;
		} else { // s < t
			Arrays.sort(arr, 1, m + 1);
			// 可以直接给一个保守的距离，不需要算
			// 因为s和t，不大，<= 10
			cut = reduce(s, t);
			for (int i = 1; i <= m; i++) {
				distance[i] = distance[i - 1] + Math.min(arr[i] - arr[i - 1], cut);
				stone[distance[i]] = true;
			}
			l = Math.min(l, distance[m] + cut);
			Arrays.fill(dp, 1, l + 1, MAXN);
			for (int i = 1; i <= l; i++) {
				for (int j = Math.max(i - t, 0); j <= i - s; j++) {
					dp[i] = Math.min(dp[i], dp[j] + (stone[i] ? 1 : 0));
				}
			}
			int ans = MAXN;
			for (int i = distance[m] + 1; i <= l; i++) {
				ans = Math.min(ans, dp[i]);
			}
			return ans;
		}
	}

	// 一旦s和t定了，那么距离多远就可以缩减
	public static int reduce(int s, int t) {
		Arrays.fill(reach, false);
		int cnt = 0;
		int ans = 0;
		for (int i = 0; i < MAXK; i++) {
			for (int j = i + s; j < Math.min(i + t + 1, MAXK); j++) {
				reach[j] = true;
			}
			if (!reach[i]) {
				cnt = 0;
			} else {
				cnt++;
			}
			if (cnt == t) {
				ans = i;
				break;
			}
		}
		return ans;
	}

}
