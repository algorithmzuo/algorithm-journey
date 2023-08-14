package code;

// 返回两个无环链表相交的第一个节点
// 测试链接 : https://leetcode.cn/problems/intersection-of-two-linked-lists/
public class Video_034_1_IntersectionOfTwoLinkedLists {

	// 提交时不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	// 提交如下的方法
	public static ListNode getIntersectionNode(ListNode h1, ListNode h2) {
		if (h1 == null || h2 == null) {
			return null;
		}
		ListNode x1 = h1, x2 = h2;
		int diff = 0;
		while (x1.next != null) {
			x1 = x1.next;
			diff++;
		}
		while (x2.next != null) {
			x2 = x2.next;
			diff--;
		}
		if (x1 != x2) {
			return null;
		}
		if (diff >= 0) {
			x1 = h1;
			x2 = h2;
		} else {
			x1 = h2;
			x2 = h1;
		}
		diff = Math.abs(diff);
		while (diff != 0) {
			x1 = x1.next;
			diff--;
		}
		while (x1 != x2) {
			x1 = x1.next;
			x2 = x2.next;
		}
		return x1;
	}

}
