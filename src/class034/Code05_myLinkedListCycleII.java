package class034;

// 返回链表的第一个入环节点
// 测试链接 : https://leetcode.cn/problems/linked-list-cycle-ii/
public class Code05_myLinkedListCycleII {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	// 提交如下的方法
	public static ListNode detectCycle(ListNode head) {
		// 1- 考虑边界情况
		if(head == null || head.next == null || head.next.next == null){
			return null;
		}

		// 2- 普通情况, 先让快指针以2倍速度领先慢指针，直到两个遇到
		ListNode fast = head.next.next;
		ListNode slow = head.next;
		while(fast != slow){
			if(fast == null){
				return null;
			}
			fast = fast.next.next;
			slow = slow.next;
		}

		// 3- 让head重头开始
		fast = head;
		while(fast != slow){
			fast = fast.next;
			slow = slow.next;
		}
		return slow;
	}

}
