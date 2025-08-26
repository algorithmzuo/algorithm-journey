package class177;

// 到达阈值的最小众数，java版
// 测试链接 : https://leetcode.cn/problems/threshold-majority-queries/

import java.util.Arrays;
import java.util.Comparator;

public class Code02_ThresholdMajority1 {

	public static int MAXN = 10001;
	public static int MAXM = 50001;
	public static int MAXB = 301;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[][] query = new int[MAXM][4];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] br = new int[MAXB];

	public static int[] forceCnt = new int[MAXN];
	public static int[] cnt = new int[MAXN];

	public static int maxCnt;
	public static int numAns;
	public static int[] ans = new int[MAXM];

	public static class QueryCmp implements Comparator<int[]> {

		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			return a[1] - b[1];
		}

	}

	public static int kth(int num) {
		int left = 1, right = cntv, mid, ret = 0;
		while (left <= right) {
			mid = (left + right) / 2;
			if (sorted[mid] <= num) {
				ret = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return ret;
	}

	public static int force(int l, int r, int k) {
		int maxCnt = 0;
		int numAns = 0;
		for (int i = l; i <= r; i++) {
			forceCnt[arr[i]]++;
		}
		for (int i = l; i <= r; i++) {
			if (forceCnt[arr[i]] > maxCnt || (forceCnt[arr[i]] == maxCnt && arr[i] < numAns)) {
				maxCnt = forceCnt[arr[i]];
				numAns = arr[i];
			}
		}
		for (int i = l; i <= r; i++) {
			forceCnt[arr[i]]--;
		}
		return maxCnt >= k ? sorted[numAns] : -1;
	}

	public static void del(int num) {
		cnt[num]--;
	}

	public static void add(int num) {
		cnt[num]++;
		if (cnt[num] > maxCnt || (cnt[num] == maxCnt && num < numAns)) {
			maxCnt = cnt[num];
			numAns = num;
		}
	}

	public static void compute() {
		for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
			maxCnt = 0;
			numAns = 0;
			Arrays.fill(cnt, 1, cntv + 1, 0);
			int winl = br[block] + 1;
			int winr = br[block];
			for (; qi <= m && bi[query[qi][0]] == block; qi++) {
				int jobl = query[qi][0];
				int jobr = query[qi][1];
				int jobk = query[qi][2];
				int id = query[qi][3];
				if (jobr <= br[block]) {
					ans[id] = force(jobl, jobr, jobk);
				} else {
					while (winr < jobr) {
						add(arr[++winr]);
					}
					int backupCnt = maxCnt;
					int backupNum = numAns;
					while (winl > jobl) {
						add(arr[--winl]);
					}
					if (maxCnt >= jobk) {
						ans[id] = sorted[numAns];
					} else {
						ans[id] = -1;
					}
					maxCnt = backupCnt;
					numAns = backupNum;
					while (winl <= br[block]) {
						del(arr[winl++]);
					}
				}
			}
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sorted[i] = arr[i];
		}
		Arrays.sort(sorted, 1, n + 1);
		cntv = 1;
		for (int i = 2; i <= n; i++) {
			if (sorted[cntv] != sorted[i]) {
				sorted[++cntv] = sorted[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(arr[i]);
		}
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			br[i] = Math.min(i * blen, n);
		}
		Arrays.sort(query, 1, m + 1, new QueryCmp());
	}

	public static int[] subarrayMajority(int[] nums, int[][] queries) {
		n = nums.length;
		m = queries.length;
		for (int i = 1, j = 0; i <= n; i++, j++) {
			arr[i] = nums[j];
		}
		for (int i = 1, j = 0; i <= m; i++, j++) {
			query[i][0] = queries[j][0] + 1;
			query[i][1] = queries[j][1] + 1;
			query[i][2] = queries[j][2];
			query[i][3] = i;
		}
		prepare();
		compute();
		int[] ret = new int[m];
		for (int i = 1, j = 0; i <= m; i++, j++) {
			ret[j] = ans[i];
		}
		return ret;
	}

}
