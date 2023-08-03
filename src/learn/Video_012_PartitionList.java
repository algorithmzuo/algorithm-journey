package learn;

public class Video_012_PartitionList {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;

		public ListNode(int val) {
			this.val = val;
		}

		public ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}

	// 测试链接 : https://leetcode.cn/problems/partition-list/
	class Solution {

		public static ListNode partition(ListNode head, int x) {
			ListNode leftHead = null, leftTail = null; // < x的区域
			ListNode rightHead = null, rightTail = null; // >=x的区域
			ListNode next = null;
			while (head != null) {
				// 6  5     3      4 2 1 1 7  x = 4
				//    h    next
				// <  x 区域 ： 3 2 1 1
				// >= x 区域 ： 6 5 4 7
				next = head.next;
				head.next = null;
				if (head.val < x) {
					if (leftHead == null) {
						leftHead = head;
					} else {
						leftTail.next = head;
					}
					leftTail = head;
				} else {
					if (rightHead == null) {
						rightHead = head;
					} else {
						rightTail.next = head;
					}
					rightTail = head;
				}
				head = next;
			}
			if (leftHead == null) {
				return rightHead;
			}
			// < x的区域有内容！
			leftTail.next = rightHead;
			return leftHead;
		}

	}

}
