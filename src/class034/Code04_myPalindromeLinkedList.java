package class034;

// 判断链表是否是回文结构
// 测试链接 : https://leetcode.cn/problems/palindrome-linked-list/
public class Code04_myPalindromeLinkedList {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;

		public ListNode(int val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.format("%d -> %s", val, next);
		}
	}

	// 提交如下的方法
	public static boolean isPalindrome(ListNode head) {
		// 0- 判断临界条件 (boundary conditions)
		if(head == null || head.next ==null){
			return true;
		}

		// 1- 找中点
		ListNode slow = head;
		ListNode fast = head;
		while(fast.next !=  null && fast.next.next != null){
			slow = slow.next;
			fast = fast.next.next;
		}

		// 2- 将中间节点之后的节点reverse
		ListNode pre = slow;
		ListNode cur = slow.next;
		ListNode next;
		slow.next = null; // 避免回环
		while(cur != null){
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		System.out.println("---head---");
		System.out.println(head);
		System.out.println("---last---");
		System.out.println(pre);
		// 4- 左右指针分别向头尾中间移，移到中间节点，如若节点都相等则是回文结构
		ListNode left = head;
		ListNode right = pre;
		boolean isPali = true;
		while( left != null && right != null ){
			if(left.val != right.val){
				return false;
			}
			left = left.next;
			right = right.next;
		}

		// 5- 本着不坑的原则，把链表调整回原来的样子再返回判断结果
		cur = pre;
		pre = null;
		while(cur != null){
			next = cur.next;
			cur.next = pre;
			pre = cur ;
			cur = next;
		}
		return isPali;
	}

	public static void main(String[] args) {
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(2);
		//ListNode node4 = new ListNode(2);
		ListNode node5 = new ListNode(1);
		node1.next = node2;
		node2.next = node3;
		node3.next = node5;
		//node4.next = node5;
		System.out.println(node1);

		boolean isPali = isPalindrome(node1);
		System.out.println(isPali);
		System.out.println(node1);


	}

}
