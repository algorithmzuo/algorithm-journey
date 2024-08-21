package class140;

// 机器人的移动区域
// 二维网格中只有x和y的值都为整数的坐标，才叫格点
// 某个机器人从格点(0,0)出发，每次机器人都走直线到达(x + dx, y + dy)的格点
// 一共移动n次，每次的(dx, dy)都给定，输入保证机器人最终回到(0,0)位置
// 机器人走的路线所围成的区域一定是多边形，并且机器人一定沿着逆时针方向行动
// 机器人从起点出发最终回到起点，途中路线不会相交
// 返回多边形的内部一共几个格点，多边形的边上一共几个格点，多边形的面积
// 3 <= n <= 100
// -100 <= dx、dy <= 100
// 测试链接 : http://poj.org/problem?id=1265
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_Area {

	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			int n = (int) in.nval;
			int edges = 0;
			double area = 0;
			for (int i = 1, x1 = 0, y1 = 0, x2, y2; i <= n; i++) {
				in.nextToken();
				x2 = x1 + (int) in.nval;
				in.nextToken();
				y2 = y1 + (int) in.nval;
				edges += gcd(Math.abs(x1 - x2), Math.abs(y1 - y2));
				// 鞋带公式
				area += x2 * y1 - x1 * y2;
				x1 = x2;
				y1 = y2;
			}
			// 课上讲的鞋带公式是顺时针方向移动，本题是逆时针方向
			// 所以求出的面积是负数形式，所以取相反数，最后还要/2
			area = (-area) / 2;
			// pick定理
			// 如果一个多边形的顶点都是格点(坐标都为整数)
			// 多边形面积 = 边界上格点数/2 + 内部格点数 - 1
			// 内部格点数 = 多边形面积 - 边界上格点数/2 + 1
			int inners = (int) (area) - edges / 2 + 1;
			out.println("Scenario #" + t + ":");
			out.print(inners + " ");
			out.print(edges + " ");
			out.printf("%.1f", area);
			out.println();
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

}
