package class093;

import java.util.PriorityQueue;

// 最大平均通过率
// 一所学校里有一些班级，每个班级里有一些学生，现在每个班都会进行一场期末考试
// 给你一个二维数组classes，其中classes[i]=[passi, totali]
// 表示你提前知道了第i个班级总共有totali个学生
// 其中只有 passi 个学生可以通过考试
// 给你一个整数extraStudents，表示额外有extraStudents个聪明的学生，一定能通过期末考
// 你需要给这extraStudents个学生每人都安排一个班级，使得所有班级的平均通过率最大
// 一个班级的 通过率 等于这个班级通过考试的学生人数除以这个班级的总人数
// 平均通过率 是所有班级的通过率之和除以班级数目
// 请你返回在安排这extraStudents个学生去对应班级后的最大平均通过率
// 测试链接 : https://leetcode.cn/problems/maximum-average-pass-ratio/
public class Code02_MaximumAveragePassRatio {

	public static double maxAverageRatio(int[][] classes, int extraStudents) {
		int n = classes.length;
		PriorityQueue<double[]> pq = new PriorityQueue<>((o1, o2) -> o2[2] - o1[2] > 0 ? 1 : -1);
		for (int[] aClass : classes) {
			double x = aClass[0], y = aClass[1];
			pq.offer(new double[] { x, y, (x + 1) / (y + 1) - x / y });
		}
		while (extraStudents-- > 0) {
			double[] cur = pq.poll();
			double x = cur[0] + 1, y = cur[1] + 1;
			pq.offer(new double[] { x, y, (x + 1) / (y + 1) - x / y });
		}
		double ans = 0;
		while (!pq.isEmpty()) {
			double[] cur = pq.poll();
			ans += cur[0] / cur[1];
		}
		return ans / n;
	}

}
