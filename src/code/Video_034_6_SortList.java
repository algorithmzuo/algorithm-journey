package code;

// 排序链表
// 要求时间复杂度O(n*logn)，额外空间复杂度O(1)，还要求稳定性
// 数组排序做不到，链表排序可以
// 测试链接 : https://leetcode.cn/problems/sort-list/
public class Video_034_6_SortList {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	// 提交如下的方法
	// mergeSort排序链表
	// 时间复杂度O(n*logn)，额外空间复杂度O(1)，有稳定性
	// 注意为了额外空间复杂度O(1)不能使用递归，因为mergeSort递归需要O(log n)的额外空间
	public static ListNode sortList(ListNode head) {
		int n = 0;
		ListNode cur = head;
		while (cur != null) {
			n++;
			cur = cur.next;
		}
		// lastTeamEnd : 上一组的结尾
		// curTeamFirst : 当前组的开头
		ListNode lastTeamEnd, curTeamFirst;
		for (int step = 1; step < n; step <<= 1) {
			// 第一组很特别，因为要决定整条链表的头节点
			merge(head, step);
			head = first;
			lastTeamEnd = last;
			curTeamFirst = last.next;
			// 接下来根据步长去一组一组merge
			while (merge(curTeamFirst, step)) {
				lastTeamEnd.next = first;
				lastTeamEnd = last;
				curTeamFirst = last.next;
			}
		}
		return head;
	}

	public static ListNode first;

	public static ListNode last;

	// 一组的开始是cur
	// cur开始数s个是左部分 -> 再往下数s个是右部分 -> 下一组的开头
	// merge的调整逻辑是 : cur开始数2*s个节点都变成有序的部分 -> 下一组的开头
	// 比如s = 4
	// 4 -> 7 -> 9 -> 13 -> 3 -> 5 -> 8 -> 11 -> 下一组的开头
	// 4 -> 7 -> 9 -> 13是左部分，3 -> 5 -> 8 -> 11是右部分
	// 调整后变成 :
	// 3 -> 4 -> 5 -> 7 -> 8 -> 9 -> 11 -> 13 -> 下一组的开头
	// 并且把全局变量first设置成3节点，全局变量last设置成13节点，供上游方法使用
	// 如果发现没有右部分，说明merge过程没有真的发生，也说明链表到达了结尾，返回false
	// 如果merge过程真的发生了，返回true
	// 这个返回值是上游判断是否继续寻找下一组、是否继续去merge的关键
	public static boolean merge(ListNode cur, int s) {
		// 变量解释
		// next : 没有实际含义，就是记录cur下一个节点，方便往后遍历
		// left : 左部分的开头，比如上面例子里的4节点
		// right : 右部分的开头，比如上面例子里的3节点
		// end : 当前这一组的最后节点，比如上面例子里的11节点
		ListNode next = null, left = cur, right = null, end = null;
		int cnt = 1;
		// 如下的while跑完之后，left、right、end变成注释里的含义
		while (cur != null && cnt <= (s * 2)) {
			next = cur.next;
			if (cnt == s) {
				right = next;
				cur.next = null;
			}
			if (cnt > s) {
				end = cur;
			}
			cur = next;
			cnt++;
		}
		if (right == null) {
			// 如果发现没有右组，说明merge没有真的发生，返回false
			return false;
		}
		// end目前的含义为当前这一组的最后节点，比如上面例子里的11节点
		// 跑完如下三行逻辑，end将变成"下一组的开头"
		next = end.next;
		end.next = null;
		end = next;
		// 经历上面的过程之后，当前组变成 :
		// 左部分 : 4 -> 7 -> 9 -> 13 -> null
		// 右部分 : 3 -> 5 -> 8 -> 11 -> null
		// 也就是左、右部分各自最后节点的next指针都指向null
		// 并且下一组的开头已经被end记录了
		// 下面的代码，就是merge过程，把左右两部分合并变成
		// 3 -> 4 -> 5 -> 7 -> 8 -> 9 -> 11 -> 13 -> null
		// 并且让全局变量first设置成3节点，全局变量last设置成13节点
		ListNode pre;
		if (left.val <= right.val) {
			first = left;
			pre = left;
			left = left.next;
		} else {
			first = right;
			pre = right;
			right = right.next;
		}
		while (left != null || right != null) {
			if (right == null || (left != null && left.val <= right.val)) {
				pre.next = left;
				pre = left;
				left = left.next;
			} else {
				// right != null && (left == null || left.val > right.val)
				pre.next = right;
				pre = right;
				right = right.next;
			}
		}
		// 经历上面的过程，链表已经变成
		// 3 -> 4 -> 5 -> 7 -> 8 -> 9 -> 11 -> 13 -> null
		// 让13节点的next指针重新连接上下一组的开头，变成 :
		// 3 -> 4 -> 5 -> 7 -> 8 -> 9 -> 11 -> 13 -> 下一组的开头
		// 并且全局变量first和last已经如预期设置好了
		last = pre;
		last.next = end;
		return true;
	}

}
