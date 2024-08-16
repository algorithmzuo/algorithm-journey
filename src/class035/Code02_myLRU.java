package class035;

import java.util.HashMap;

// 实现LRU结构
public class Code02_myLRU {

	// 测试链接 : https://leetcode.cn/problems/lru-cache/
	static class LRUCache {

		class Node{
			int key;
			int val;
			Node pre;
			Node next;

			public Node(int key, int value) {
				this.key = key;
				this.val = value;
			}


		}

		class DoubleLinkedList {
			Node head;
			Node tail;

			public DoubleLinkedList() {
			}

			public int addNodeToTail(Node node){
				if(node == null)
					return -1;
				if(head == null){
					head = node;
					tail = node;
				}else{
					tail.next = node;
					node.pre = tail;
					tail = tail.next;
				}

				return 1;
			}

			public Node removeNode(Node node){
				if(node == null){
					return null;
				}
				if(node == head){
					return removeHeadNode();
				}
				if(node == tail){
					Node removedNode = tail;
					tail = tail.pre;
					removedNode.pre = null;
					tail.next = null;
					return removedNode;
				}
				node.pre.next= node.next;
				node.next.pre = node.pre;
				return node;
			}

			public Node removeHeadNode(){
				if(head!=null){
					Node removedHeadNode = head;
					head = head.next;
					removedHeadNode.next = null;
					if(head == null){
						tail = null;
						return removedHeadNode;
					}
					head.pre = null;
					return removedHeadNode;
				}
				return null;
			}


		}

		private HashMap<Integer, Node> keyNodeMap;
		private DoubleLinkedList nodeList;

		private final int capacity;

		private int size = 0;

		public LRUCache(int cap) {
			capacity = cap;
			keyNodeMap = new HashMap<>();
			nodeList = new DoubleLinkedList();
		}

		public int get(int key) {
			if(keyNodeMap.containsKey(key)){
				// get the value node
				Node node = keyNodeMap.get(key);
				nodeList.removeNode(node);
				nodeList.addNodeToTail(node);
			}

			return -1;
		}

		public void put(int key, int value) {
			Node node = new Node(key, value);
			// 1- if current map does not contain such key
			if(!keyNodeMap.containsKey(key)){
				if(size == capacity){
					Node removedNode = nodeList.removeHeadNode();
					keyNodeMap.remove(removedNode.key);
					size--;
				}
				size++;
				nodeList.addNodeToTail(node);
				keyNodeMap.put(key, node);
			}else{
			 // 2- if contains,then update
				Node oldNode = keyNodeMap.get(key);
				oldNode.val = value;
				nodeList.removeNode(oldNode);
				nodeList.addNodeToTail(oldNode);
			}

		}

	}
	public static void printList(LRUCache.Node node){
		for (; node!= null; node = node.next){
			System.out.print(node.val+ "-> ");
		}
		System.out.println("null");

	}
	public static void main(String[] args) {
		LRUCache cache = new LRUCache(2);
		cache.get(2);
		cache.put(2,6);
		printList(cache.nodeList.head);
		cache.get(1);
		cache.put(1,5);
		printList(cache.nodeList.head);
		cache.put(1,2);
//		System.out.println(cache.keyNodeMap.keySet());
//		System.out.println(cache.get(2));
//		cache.put(4,4);
//		System.out.println(cache.get(1));
//		System.out.println(cache.get(3));
//		System.out.println(cache.get(4));




	}

}
