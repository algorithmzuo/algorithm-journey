package class127;

// 过河踩过的最少石子数
// 在河上有一座独木桥，一只青蛙想沿着独木桥从河的一侧跳到另一侧
// 在桥上有一些石子，青蛙很讨厌踩在这些石子上
// 我们可以把独木桥上青蛙可能到达的点看成数轴上的一串整点0...n
// 其中n是桥的长度，坐标为0的点表示桥的起点，坐标为n的点表示桥的终点
// 青蛙从桥的起点开始，不停的向终点方向跳跃，一次跳跃的距离是[s,t]之间的任意正整数
// 当青蛙跳到或跳过坐标为n的点时，就算青蛙已经跳出了独木桥
// 题目给出独木桥的长度n，青蛙跳跃的距离范围s、t，题目还给定m个桥上石子的位置
// 你的任务是确定青蛙要想过河，最少需要踩到的石子数
// 1 <= n <= 10^7
// 1 <= s <= t <= 10
// 1 <= m <= 100
// 测试链接 : https://www.luogu.com.cn/problem/P1052
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code06_FrogCrossRiver {

	public static int MAXN = 101;

	public static int MAXL = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] where = new int[MAXN];

	public static int[] dp = new int[MAXL];

	public static boolean[] stone = new boolean[MAXL];

	public static int n, s, t, m, safe;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
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
		Arrays.sort(arr, 1, m + 1);
		if (s == t) {
			int ans = 0;
			for (int i = 1; i <= m; i++) {
				if (arr[i] % s == 0) {
					ans++;
				}
			}
			return ans;
		} else { // s < t
			// 可以直接给一个足够安全的距离，不需要算
			// 因为s和t不大，都<= 10
			// safe = 201;
			safe = reduce(s, t);
			for (int i = 1; i <= m; i++) {
				where[i] = where[i - 1] + Math.min(arr[i] - arr[i - 1], safe);
				stone[where[i]] = true;
			}
			// where[m] + safe : 最后一颗石子 + 安全距离
			// 过了这个安全距离之后，dp值不会再有变化，也不会再遇到石子
			n = where[m] + safe;
			Arrays.fill(dp, 1, n + 1, MAXN);
			for (int i = 1; i <= n; i++) {
				for (int j = Math.max(i - t, 0); j <= i - s; j++) {
					dp[i] = Math.min(dp[i], dp[j] + (stone[i] ? 1 : 0));
				}
			}
			int ans = MAXN;
			for (int i = where[m] + 1; i <= n; i++) {
				ans = Math.min(ans, dp[i]);
			}
			return ans;
		}
	}

	public static int MAXK = 201;

	public static boolean[] reach = new boolean[MAXK];

	// 一旦s和t定了，那么距离多远就可以缩减呢？
	// 做实验！
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
