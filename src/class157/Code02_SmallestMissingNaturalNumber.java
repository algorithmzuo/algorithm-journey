package class157;

// 区间内没有出现的最小自然数
// 给定一个长度为n的数组arr，一共有m次询问
// 每次查询一个区间[l, r]内没有出现过的最小自然数
// 注意0是自然数
// 测试链接 : https://www.luogu.com.cn/problem/P4137
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_SmallestMissingNaturalNumber {

	public static int MAXN = 200001;

	public static int MAXM = MAXN * 22;

	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	// last[i] : l~r的信息存在last[i]
	// 表示该范围每个数字最后出现的位置中，最左在哪
	public static int[] lateLeft = new int[MAXM];

	public static int n, q, cnt;

	public static int update(int pre, int l, int r, int v, int pos) {
		int rt = ++cnt;
		left[rt] = left[pre];
		right[rt] = right[pre];
		lateLeft[rt] = lateLeft[pre];
		if (l == r) {
			lateLeft[rt] = pos;
		} else {
			int mid = (l + r) / 2;
			if (v <= mid) {
				left[rt] = update(left[pre], l, mid, v, pos);
			} else {
				right[rt] = update(right[pre], mid + 1, r, v, pos);
			}
			lateLeft[rt] = Math.min(lateLeft[left[rt]], lateLeft[right[rt]]);
		}
		return rt;
	}

	public static int query(int rt, int l, int r, int pos) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		if (lateLeft[left[rt]] < pos) {
			// l...mid范围上，每个数字最晚出现的位置中
			// 最左的位置如果在pos以左，说明l...mid范围上，一定有缺失的数字
			return query(left[rt], l, mid, pos);
		} else {
			// 缺失的数字一定在mid+1....r范围
			// 因为l...r一定有缺失的数字才会来到这个范围的
			// 如果左侧不缺失，那缺失的数字一定在右侧范围上
			return query(right[rt], mid + 1, r, pos);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		q = (int) in.nval;
		cnt = 0;
		for (int i = 1, v; i <= n; i++) {
			in.nextToken();
			v = (int) in.nval;
			if (v >= n) {
				root[i] = root[i - 1];
			} else {
				root[i] = update(root[i - 1], 0, n, v, i);
			}
		}
		for (int i = 1, l, r; i <= q; i++) {
			in.nextToken();
			l = (int) in.nval;
			in.nextToken();
			r = (int) in.nval;
			out.println(query(root[r], 0, n, l));
		}
		out.flush();
		out.close();
		br.close();
	}

}
