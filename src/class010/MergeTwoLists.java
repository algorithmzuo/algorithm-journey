package class010;

// 将两个升序链表合并为一个新的 升序 链表并返回
// 新链表是通过拼接给定的两个链表的所有节点组成的
// 测试链接 : https://leetcode.cn/problems/merge-two-sorted-lists/
public class MergeTwoLists {

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

	class Solution {

		public static ListNode mergeTwoLists(ListNode head1, ListNode head2) {
			if (head1 == null || head2 == null) {
				return head1 == null ? head2 : head1;
			}
			ListNode head = head1.val <= head2.val ? head1 : head2;
			ListNode cur1 = head.next;
			ListNode cur2 = head == head1 ? head2 : head1;
			ListNode pre = head;
			while (cur1 != null && cur2 != null) {
				if (cur1.val <= cur2.val) {
					pre.next = cur1;
					cur1 = cur1.next;
				} else {
					pre.next = cur2;
					cur2 = cur2.next;
				}
				pre = pre.next;
			}
			pre.next = cur1 != null ? cur1 : cur2;
			return head;
		}

	}

}
