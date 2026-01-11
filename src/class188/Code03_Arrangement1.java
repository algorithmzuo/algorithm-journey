package class188;

// 合法重新排列数对，java版
// 测试链接 : https://leetcode.cn/problems/valid-arrangement-of-pairs/
// 提交以下代码中的Solution类，可以通过所有测试用例

import java.util.Arrays;

public class Code03_Arrangement1 {

	class Solution {

		public static int MAXN = 200001;
		public static int n, m;
		public static int[][] pair;
		public static int[] sortv = new int[MAXN];

		public static int[] head = new int[MAXN];
		public static int[] nxt = new int[MAXN];
		public static int[] to = new int[MAXN];
		public static int cntg;

		public static int[] cur = new int[MAXN];
		public static int[] outDeg = new int[MAXN];
		public static int[] inDeg = new int[MAXN];

		public static int[] path = new int[MAXN];
		public static int cntp;

		public static void addEdge(int u, int v) {
			nxt[++cntg] = head[u];
			to[cntg] = v;
			head[u] = cntg;
		}

		public static int kth(int num) {
			int l = 1, r = n, ans = 0;
			while (l <= r) {
				int mid = (l + r) >>> 1;
				if (sortv[mid] >= num) {
					ans = mid;
					r = mid - 1;
				} else {
					l = mid + 1;
				}
			}
			return ans;
		}

		public static void prepare() {
			int len = 0;
			for (int i = 0; i < m; i++) {
				sortv[++len] = pair[i][0];
				sortv[++len] = pair[i][1];
			}
			Arrays.sort(sortv, 1, len + 1);
			n = 1;
			for (int i = 2; i <= len; i++) {
				if (sortv[n] != sortv[i]) {
					sortv[++n] = sortv[i];
				}
			}
			cntg = cntp = 0;
			for (int i = 1; i <= n; i++) {
				head[i] = outDeg[i] = inDeg[i] = 0;
			}
		}

		public static void connect() {
			for (int i = 0, u, v; i < m; i++) {
				u = kth(pair[i][0]);
				v = kth(pair[i][1]);
				outDeg[u]++;
				inDeg[v]++;
				addEdge(u, v);
			}
			for (int i = 1; i <= n; i++) {
				cur[i] = head[i];
			}
		}

		public static int directedStart() {
			int start = -1, end = -1;
			for (int i = 1; i <= n; i++) {
				int v = outDeg[i] - inDeg[i];
				if (v < -1 || v > 1 || (v == 1 && start != -1) || (v == -1 && end != -1)) {
					return -1;
				}
				if (v == 1) {
					start = i;
				}
				if (v == -1) {
					end = i;
				}
			}
			if ((start == -1) ^ (end == -1)) {
				return -1;
			}
			if (start != -1) {
				return start;
			}
			for (int i = 1; i <= n; i++) {
				if (outDeg[i] > 0) {
					return i;
				}
			}
			return -1;
		}

		public static void euler(int u) {
			for (int e = cur[u]; e > 0; e = cur[u]) {
				cur[u] = nxt[e];
				euler(to[e]);
			}
			path[++cntp] = u;
		}

		public int[][] validArrangement(int[][] pairs) {
			m = pairs.length;
			pair = pairs;
			prepare();
			connect();
			int start = directedStart();
			if (start == -1) {
				return null;
			}
			euler(start);
			if (cntp != m + 1) {
				return null;
			}
			int[][] ans = new int[m][2];
			for (int i = 0, j = cntp; i < m; i++, j--) {
				ans[i][0] = sortv[path[j]];
				ans[i][1] = sortv[path[j - 1]];
			}
			return ans;
		}

	}

}
