package class034;

// 判断链表是否是回文结构
// 测试链接 : https://leetcode.cn/problems/palindrome-linked-list/
public class Code04_PalindromeLinkedList {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	// 提交如下的方法
	public static boolean isPalindrome(ListNode head) {
		if (head == null || head.next == null) {
			return true;
		}
		ListNode slow = head, fast = head;
		// 找中点
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		// 现在中点就是slow，从中点开始往后的节点逆序
		ListNode pre = slow;
		ListNode cur = pre.next;
		ListNode next = null;
		pre.next = null;
		while (cur != null) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		// 上面的过程已经把链表调整成从左右两侧往中间指
		// head -> ... -> slow <- ... <- pre
		boolean ans = true;
		ListNode left = head;
		ListNode right = pre;
		// left往右、right往左，每一步比对值是否一样，如果某一步不一样答案就是false
		while (left != null && right != null) {
			if (left.val != right.val) {
				ans = false;
				break;
			}
			left = left.next;
			right = right.next;
		}
		// 本着不坑的原则，把链表调整回原来的样子再返回判断结果
		cur = pre.next;
		pre.next = null;
		next = null;
		while (cur != null) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		return ans;
	}

}
