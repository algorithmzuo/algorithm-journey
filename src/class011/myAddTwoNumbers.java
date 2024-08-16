package class011;

// 给你两个 非空 的链表，表示两个非负的整数
// 它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字
// 请你将两个数相加，并以相同形式返回一个表示和的链表。
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头
// 测试链接：https://leetcode.cn/problems/add-two-numbers/
public class myAddTwoNumbers {

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

		public static ListNode addTwoNumbers1(ListNode h1, ListNode h2){
			ListNode ansHead = null;
			ListNode p = ansHead;
			int carry = 0;
			//只要listnode有一方有值我就继续做
			for(ListNode p1 =h1, p2= h2;
				p1!=null || p2!=null ;
				p1 = p1 == null ? null: p1.next, p2 = p2==null ? null :p2.next ){

				int sum = ((p1 == null) ? 0: p1.val )+ ((p2 == null) ? 0 : p2.val) + carry;
				ListNode node = new ListNode(sum%10);
				carry  = sum/10;
				if(ansHead == null){
					ansHead =  node;
					p = ansHead;
				}else{
					p.next = node;
					p = p.next;
				}
			}
			if(carry != 0){
				p.next = new ListNode(1);
			}
			return ansHead;
		}

		// 也可以复用老链表
		// 不过这个实现没有这么做，都是生成的新节点(为了教学好懂)
		public static ListNode addTwoNumbers(ListNode h1, ListNode h2) {
			if(h1 == null || h2 ==null){
				return h1 == null ? h2 : h1;
			}
			// 0- get the longer linkedlist && and shorter list
			ListNode lpointer = getLength(h1) >= getLength(h2) ? h1: h2;
			ListNode spointer = lpointer == h1 ? h2: h1;
			// 1- iterate two linkedlists
			ListNode head = new ListNode((lpointer.val + spointer.val) % 10);
			int carry = (lpointer.val + spointer.val) / 10;
			ListNode p = head;

			while(spointer != null) {
				ListNode ans = new ListNode((lpointer.val + spointer.val + carry) % 10);
				carry = (lpointer.val + spointer.val + carry) / 10;
				p.next = ans;
				spointer = spointer.next;
				lpointer = lpointer.next;
				p = p.next;
			}

			// 2- iterate the longer  linkedlist
			while(lpointer != null) {
				ListNode ans = new ListNode((lpointer.val  + carry) % 10);
				carry = (lpointer.val + carry) / 10;
				p.next = ans;
				lpointer = lpointer.next;
				p =p.next;
			}

			// 3- see if there's any carry
			if(carry!=0){
				p.next =  new ListNode(1);
			}
			return head;
		}

		public static int getLength(ListNode head){
			int len = 0 ;
			while(head!=null){
				len++;
				head = head.next;
			}
			return len;
		}

	}

}
