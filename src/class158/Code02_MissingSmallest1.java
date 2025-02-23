package class158;

// 区间内没有出现的最小自然数，java版
// 给定一个长度为n的数组arr，下标1~n，一共有m条查询
// 每条查询 l r : 打印arr[l..r]内没有出现过的最小自然数，注意0是自然数
// 请用在线算法解决该问题，因为可以设计强制在线的要求，让离线算法失效
// 1 <= n、m <= 2 * 10^5
// 0 <= arr[i] <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4137
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_MissingSmallest1 {

	public static int MAXN = 200001;

	public static int MAXT = MAXN * 22;

	public static int n, m;

	public static int[] arr = new int[MAXN];

	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	// 数字范围中，每个数字出现的最晚位置中，最左的位置在哪
	public static int[] lateLeft = new int[MAXT];

	public static int cnt;

	public static int build(int l, int r) {
		int rt = ++cnt;
		lateLeft[rt] = 0;
		if (l < r) {
			int mid = (l + r) / 2;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
		}
		return rt;
	}

	// 数字范围l~r，信息在i号节点
	// 数字范围上，jobi这个数字，最晚出现的位置更新为jobv
	// 返回新的头节点编号
	public static int update(int jobi, int jobv, int l, int r, int i) {
		int rt = ++cnt;
		left[rt] = left[i];
		right[rt] = right[i];
		lateLeft[rt] = lateLeft[i];
		if (l == r) {
			lateLeft[rt] = jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[rt] = update(jobi, jobv, l, mid, left[rt]);
			} else {
				right[rt] = update(jobi, jobv, mid + 1, r, right[rt]);
			}
			lateLeft[rt] = Math.min(lateLeft[left[rt]], lateLeft[right[rt]]);
		}
		return rt;
	}

	// 数字在l~r范围上，没有出现的最小自然数，课上重点图解
	public static int query(int pos, int l, int r, int i) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		if (lateLeft[left[i]] < pos) {
			// l...mid范围上，每个数字最晚出现的位置中
			// 最左的位置如果在pos以左，说明l...mid范围上，一定有缺失的数字
			return query(pos, l, mid, left[i]);
		} else {
			// 缺失的数字一定在mid+1....r范围
			// 因为l...r一定有缺失的数字才会来到这个范围的
			// 如果左侧不缺失，那缺失的数字一定在右侧范围上
			return query(pos, mid + 1, r, right[i]);
		}
	}

	public static void prepare() {
		cnt = 0;
		root[0] = build(0, n);
		for (int i = 1; i <= n; i++) {
			if (arr[i] > n || arr[i] < 0) {
				root[i] = root[i - 1];
			} else {
				root[i] = update(arr[i], i, 0, n, root[i - 1]);
			}
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
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		prepare();
		for (int i = 1, l, r; i <= m; i++) {
			in.nextToken();
			l = (int) in.nval;
			in.nextToken();
			r = (int) in.nval;
			out.println(query(l, 0, n, root[r]));
		}
		out.flush();
		out.close();
		br.close();
	}

}
