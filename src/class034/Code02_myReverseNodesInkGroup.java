package class034;


import utils.BaseListNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// 每k个节点一组翻转链表
// 测试链接：https://leetcode.cn/problems/reverse-nodes-in-k-group/
public class Code02_myReverseNodesInkGroup extends BaseListNode {


	// 提交如下的方法
	public static ListNode reverseKGroup(ListNode head, int k) {
		if(head == null){
			return null;
		}
		/**
		 * 1- 初始化
		 *    得到组内头部节点
		 *
		 * 2- 把所有头部节点串联起来
		 */
		List<ListNode[]> lists = new LinkedList<>();
		ListNode start = head;
		ListNode end = null;
		while(start != null){
			end = start;
			int i = k;
			while(i > 0 && end !=null){
				end = end.next;
				i--;
			}
			if(i == 0){
				lists.add(reverseNodeInGroup(start, end));
			}else{
				lists.add(new ListNode[]{start,null});
			}
			start = end;
        }

		System.out.println(lists);
		ListNode p = lists.get(0)[1];
		for (int i = 1; i < lists.size(); i++) {
            p.next = lists.get(i)[0];
			p = lists.get(i)[1];
		}

		return lists.get(0)[0];
	}

	public static ListNode getGroupEnd(ListNode head, int k){
		ListNode end = head;
		for (int i = 0; i < k; i++) {
			end = end.next;
			if(end == null){
				return null;
			}
		}
		return end;
	}

	// reverse in group [start, end)
	// (1,4)  would be like
	// 1 -> 2 -> 3 -> 4   1 <- 2 <- 3 -> 4
	public static ListNode[] reverseNodeInGroup(ListNode start, ListNode end){
		ListNode previous = null;
		ListNode current = start;
		ListNode next = null;
		while(current != end){
			// 暂时存储next的值
			next = current.next;

			//switch
			current.next = previous;
			previous = current;
			current = next;
		}
		return new ListNode[]{previous, start};
	}


	public static void main(String[] args) {
		ListNode head  = createList(5);
        printListNode(head);
		//System.out.println(getGroupEnd(head,3).val);

		System.out.println("--------------------");

		head = reverseKGroup(head, 2);
		printListNode(head);
//		head = reverseNodeInGroup(head, head.next.next.next);
//		printListNode(head);


	}



}
