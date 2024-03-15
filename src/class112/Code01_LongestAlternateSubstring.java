package class112;

// 最长LR交替子串
// 给定一个长度为n的字符串，一开始字符串中全是'L'字符
// 有q次修改，每次指定一个位置i
// 如果i位置是'L'字符那么改成'R'字符
// 如果i位置是'R'字符那么改成'L'字符
// 如果一个子串是两种字符不停交替出现的样子，也就是LRLR... 或者RLRL...
// 那么说这个子串是有效子串
// 每次修改后，都打印当前整个字符串中最长交替子串的长度
// 测试链接 : https://www.luogu.com.cn/problem/P6492
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_LongestAlternateSubstring {

	public static int MAXN = 200001;

	public static int[] arr = new int[MAXN];

	public static int[] len = new int[MAXN << 2];

	public static int[] pre = new int[MAXN << 2];

	public static int[] pos = new int[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			len[rt] = 1;
			pre[rt] = 1;
			pos[rt] = 1;
		} else {
			int mid = (l + r) / 2;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			up(l, r, rt);
		}
	}

	public static void up(int l, int r, int rt) {
		len[rt] = Math.max(len[rt << 1], len[rt << 1 | 1]);
		pre[rt] = pre[rt << 1];
		pos[rt] = pos[rt << 1 | 1];
		int mid = (l + r) / 2;
		int ln = mid - l + 1;
		int rn = r - mid;
		if (arr[mid] != arr[mid + 1]) {
			len[rt] = Math.max(len[rt], pos[rt << 1] + pre[rt << 1 | 1]);
			if (len[rt << 1] == ln) {
				pre[rt] = ln + pre[rt << 1 | 1];
			}
			if (len[rt << 1 | 1] == rn) {
				pos[rt] = rn + pos[rt << 1];
			}
		}
	}

	public static void change(int index, int l, int r, int rt) {
		if (l == r && l == index) {
			arr[index] ^= 1;
		} else {
			int mid = (l + r) / 2;
			if (index <= mid) {
				change(index, l, mid, rt << 1);
			} else {
				change(index, mid + 1, r, rt << 1 | 1);
			}
			up(l, r, rt);
		}
	}

	public static int query() {
		return len[1];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int q = (int) in.nval;
			build(1, n, 1);
			for (int i = 1, index; i <= q; i++) {
				in.nextToken();
				index = (int) in.nval;
				change(index, 1, n, 1);
				out.println(query());
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
