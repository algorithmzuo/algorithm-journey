package class045;

import java.util.Arrays;

// 牛牛和他的朋友们约定了一套接头密匙系统，用于确认彼此身份
// 密匙由一组数字序列表示，两个密匙被认为是一致的，如果满足以下条件：
// 密匙 b 的长度不超过密匙 a 的长度。
// 对于任意 0 <= i < length(b)，有b[i+1] - b[i] == a[i+1] - a[i]
// 现在给定了m个密匙 b 的数组，以及n个密匙 a 的数组
// 请你返回一个长度为 m 的结果数组 ans，表示每个密匙b都有多少一致的密匙
// 数组 a 和数组 b 中的元素个数均不超过 10^5
// 1 <= m, n <= 1000
// 测试链接 : https://www.nowcoder.com/practice/c552d3b4dfda49ccb883a6371d9a6932
public class Code01_CountConsistentKeys {

	public static int[] countConsistentKeys(int[][] b, int[][] a) {
		build();
		StringBuilder builder = new StringBuilder();
		// [3,6,50,10] -> "3#44#-40#"
		for (int[] nums : a) {
			builder.setLength(0);
			for (int i = 1; i < nums.length; i++) {
				builder.append(String.valueOf(nums[i] - nums[i - 1]) + "#");
			}
			insert(builder.toString());
		}
		int[] ans = new int[b.length];
		for (int i = 0; i < b.length; i++) {
			builder.setLength(0);
			int[] nums = b[i];
			for (int j = 1; j < nums.length; j++) {
				builder.append(String.valueOf(nums[j] - nums[j - 1]) + "#");
			}
			ans[i] = count(builder.toString());
		}
		clear();
		return ans;
	}

	// 如果将来增加了数据量，就改大这个值
	public static int MAXN = 2000001;

	public static int[][] tree = new int[MAXN][12];

	public static int[] pass = new int[MAXN];

	public static int cnt;

	public static void build() {
		cnt = 1;
	}

	// '0' ~ '9' 10个 0~9
	// '#' 10
	// '-' 11
	public static int path(char cha) {
		if (cha == '#') {
			return 10;
		} else if (cha == '-') {
			return 11;
		} else {
			return cha - '0';
		}
	}

	public static void insert(String word) {
		int cur = 1;
		pass[cur]++;
		for (int i = 0, path; i < word.length(); i++) {
			path = path(word.charAt(i));
			if (tree[cur][path] == 0) {
				tree[cur][path] = ++cnt;
			}
			cur = tree[cur][path];
			pass[cur]++;
		}
	}

	public static int count(String pre) {
		int cur = 1;
		for (int i = 0, path; i < pre.length(); i++) {
			path = path(pre.charAt(i));
			if (tree[cur][path] == 0) {
				return 0;
			}
			cur = tree[cur][path];
		}
		return pass[cur];
	}

	public static void clear() {
		for (int i = 1; i <= cnt; i++) {
			Arrays.fill(tree[i], 0);
			pass[i] = 0;
		}
	}

}
