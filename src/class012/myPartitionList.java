package class012;

// 给你一个链表的头节点 head 和一个特定值 x
// 请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
// 你应当 保留 两个分区中每个节点的初始相对位置
// 测试链接 : https://leetcode.cn/problems/partition-list/
public class myPartitionList {

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

		public static ListNode partition(ListNode head, int x) {
			// 1- create two listNodes which is bigger and less
			ListNode lHead = new ListNode(1);
			ListNode geHead = new ListNode(1);
			ListNode pl = lHead;
			ListNode pge = geHead;
			// 2- iterate the linkedlist and add the node to each respective linked list
			ListNode p = head;
			while(p!=null){
				ListNode next = p.next;
				p.next = null;
				if(p.val < x){
					pl.next = p;
					pl = pl.next;
				}else{
					pge.next = p;
					pge = pge.next;
				}
				p = next;
			}
			// 2- after collection link the last node to the 1st node of bigger list
			printList(lHead);
			printList(geHead);
			pl.next = geHead.next;
			return lHead.next;
		}

		public static void printList(ListNode head){
			int count = 0;
			while(head!=null){
				System.out.print(head.val + "->");
				head = head.next;
				if(count > 5){
					break;
				}
				count++;
			}
			System.out.println("null");

		}




	}
	public static void main(String[] args) {
		ListNode head = new ListNode(1);
		ListNode node= new ListNode(4);
		ListNode node1 = new ListNode(3);
		head.next= node;
		node.next = node1;

		Solution.printList(head);
		System.out.println("-----------");
		ListNode head2= Solution.partition(head, 4);
		Solution.printList(head2);
	}

}
