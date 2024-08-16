package class010;

import java.util.List;

// 将两个升序链表合并为一个新的 升序 链表并返回
// 新链表是通过拼接给定的两个链表的所有节点组成的
// 测试链接 : https://leetcode.cn/problems/merge-two-sorted-lists/
public class myMergeTwoLists {

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
			if(head1 ==null || head2 == null){
				return head1 ==null? head2: head1;
			}
			ListNode head = new ListNode(1);
			ListNode p =head;
			ListNode p1 = head1;
			ListNode p2 =head2;
			while(p1 != null && p2!=null){
				if(p1.val<= p2.val){
					p.next = p1;
					p1 = p1.next;
				}else{
					p.next = p2;
					p2 = p2.next;
				}
				p = p.next;
			}
			p.next = p1!=null ? p1:p2;
			return head.next;
		}

	}

	public static void printListNode(ListNode head){
		while(head!=null){
			System.out.print(head.val+" ->");
			head = head.next;
		}
		System.out.println("null");
	}

	public static void main(String[] args) {
		ListNode head1 = new ListNode(1);
		head1.next = new ListNode(3);
		head1.next.next =new ListNode(5);
		ListNode head2  = new ListNode(2);
		head2.next = new ListNode(4);
		printListNode(Solution.mergeTwoLists(head2 ,head1));

	}

}
