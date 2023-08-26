package class051;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 找出知晓秘密的所有专家
// 给你一个整数 n ，表示有 n 个专家从 0 到 n - 1 编号
// 另外给你一个下标从 0 开始的二维整数数组 meetings
// 其中 meetings[i] = [xi, yi, timei] 表示专家 xi 和专家 yi 在时间 timei 要开一场会
// 一个专家可以同时参加 多场会议 。最后，给你一个整数 firstPerson
// 专家 0 有一个 秘密 ，最初，他在时间 0 将这个秘密分享给了专家 firstPerson
// 接着，这个秘密会在每次有知晓这个秘密的专家参加会议时进行传播
// 更正式的表达是，每次会议，如果专家 xi 在时间 timei 时知晓这个秘密
// 那么他将会与专家 yi 分享这个秘密，反之亦然。秘密共享是 瞬时发生 的
// 也就是说，在同一时间，一个专家不光可以接收到秘密，还能在其他会议上与其他专家分享
// 在所有会议都结束之后，返回所有知晓这个秘密的专家列表
// 你可以按 任何顺序 返回答案
// 链接测试 : https://leetcode.cn/problems/find-all-people-with-secret/
public class Code03_FindAllPeopleWithSecret {

	public static List<Integer> findAllPeople(int n, int[][] meetings, int first) {
		build(n, first);
		int m = meetings.length;
		Arrays.sort(meetings, (a, b) -> a[2] - b[2]);
		int[] help = new int[m << 1];
		help[0] = meetings[0][0];
		help[1] = meetings[0][1];
		int size = 2;
		for (int i = 1; i < m; i++) {
			if (meetings[i][2] != meetings[i - 1][2]) {
				share(help, size);
				help[0] = meetings[i][0];
				help[1] = meetings[i][1];
				size = 2;
			} else {
				help[size++] = meetings[i][0];
				help[size++] = meetings[i][1];
			}
		}
		share(help, size);
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (know(i)) {
				ans.add(i);
			}
		}
		return ans;
	}

	public static void share(int[] help, int size) {
		for (int i = 0; i < size; i += 2) {
			union(help[i], help[i + 1]);
		}
		for (int i = 0; i < size; i++) {
			if (!know(help[i])) {
				isolate(help[i]);
			}
		}
	}

	public static int MAXN = 100001;

	public static int[] father = new int[MAXN];

	public static int[] stack = new int[MAXN];

	public static boolean[] secret = new boolean[MAXN];

	public static void build(int n, int first) {
		for (int i = 0; i < n; i++) {
			father[i] = i;
			secret[i] = false;
		}
		father[first] = 0;
		secret[0] = true;
	}

	public static int find(int i) {
		int size = 0;
		while (i != father[i]) {
			stack[size++] = i;
			i = father[i];
		}
		while (size > 0) {
			father[stack[--size]] = i;
		}
		return i;
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			father[fy] = fx;
			secret[fx] |= secret[fy];
		}
	}

	public static boolean know(int i) {
		return secret[find(i)];
	}

	public static void isolate(int i) {
		father[i] = i;
	}

}
