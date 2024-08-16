package class034;

// 复制带随机指针的链表
// 测试链接 : https://leetcode.cn/problems/copy-list-with-random-pointer/
public class Code03_myCopyListWithRandomPointer {

	// 不要提交这个类
	public static class Node {
		public int val;
		public Node next;
		public Node random;

		public Node(int v) {
			val = v;
		}

		@Override
		public String toString() {
			return  String.format("%d -> %s", val, next);
		}



	}

	// 提交如下的方法
	public static Node copyRandomList(Node head) {
		// 特殊情况
		if(head == null){
			return null;
		}
		// 1 -> 2 -> 3 -> ...
		// 变成 : 1 -> 1' -> 2 -> 2' -> 3 -> 3' -> ...
		Node cur = head;
		Node next = null;

		while(cur != null){
			next = cur.next;
			cur.next = new Node(cur.val);
			cur.next.next = next;
			cur = next;
		}
		// 利用上面新老节点的结构关系，设置每一个新节点的random指针

		cur = head;
		while (cur!=null){
			if(cur.random != null){
				cur.next.random = cur.random.next;
			}
			cur = cur.next.next;
		}
		// 新老链表分离 : 老链表重新连在一起，新链表重新连在一起
		cur = head;
		Node ansHead = head.next;
		Node curAns = ansHead;
		while(cur != null){
			cur.next = cur.next.next != null ? cur.next.next : null;
			curAns.next = cur.next !=null ? curAns.next.next : null;
			cur = cur.next;
			curAns = curAns.next;

		}
		// 返回新链表的头节点
		return ansHead;
	}

	public static void main(String[] args) {
		Node node1= new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		node1.next = node2;
		node1.random = node3;
		node2.next = node3;
		node3.random = node2;
		System.out.println(node1);

		node1 = copyRandomList(node1);
		System.out.println(node1);

	}

}
