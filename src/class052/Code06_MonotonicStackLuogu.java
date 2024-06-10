package class052;

// 课上没讲的代码，单调栈在洛谷上的测试，原理是一样的
// 洛谷上这道题对java特别不友好，不这么写通过不了，注意看注释，非常极限
// 建议看看就好，现在的笔试和比赛时，不会这么极限的
// 给定一个长度为n的数组，打印每个位置的右侧，大于该位置数字的最近位置
// 测试链接 : https://www.luogu.com.cn/problem/P5788
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Code06_MonotonicStackLuogu {

	public static void main(String[] args) throws IOException {
		int n = nextInt();
		int[] arr = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			arr[i] = nextInt();
		}
		// 单调栈中保证 : 左 >= 右
		int[] stack = new int[n + 1];
		int r = 0;
		// 注意，这里为了省空间，直接复用了arr
		// 比如一个位置x，如果从stack中弹出，并且是当前的i位置让其弹出的
		// 那么令arr[x] = i，也就是代码36行
		// 此时arr[x]不再表示原始数组x位置的值
		// 而去表示，原始数组中，x的右边，大于arr[x]，最近的位置
		// 也就是说，重新复用arr，让其变成答案数组
		// 为啥这么节省？为啥不单独弄一个答案数组
		// 没办法，不这么节省通过不了测试，空间卡的非常极限
		for (int i = 1; i <= n; i++) {
			while (r > 0 && arr[stack[r - 1]] < arr[i]) {
				arr[stack[--r]] = i;
			}
			stack[r++] = i;
		}
		while (r > 0) {
			arr[stack[--r]] = 0;
		}
		out.print(arr[1]);
		for (int i = 2; i <= n; i++) {
			out.print(" " + arr[i]);
		}
		out.println();
		out.flush();
	}

	// 用如下的方式读数据其实并不推荐
	// 但是这道题特别卡空间
	// 需要这么读数据让内存开销最小
	// 一般笔试、比赛时不需要这么写
	public static InputStream in = new BufferedInputStream(System.in);

	public static PrintWriter out = new PrintWriter(System.out);

	public static int nextInt() throws IOException {
		int ch, sign = 1, ans = 0;
		while (!Character.isDigit(ch = in.read())) {
			if (ch == '-')
				sign = -1;
		}
		do {
			ans = ans * 10 + ch - '0';
		} while (Character.isDigit(ch = in.read()));
		return (ans * sign);
	}

}
