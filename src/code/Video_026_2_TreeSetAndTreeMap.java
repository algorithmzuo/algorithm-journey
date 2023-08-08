package code;

import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class Video_026_2_TreeSetAndTreeMap {

	public static void main(String[] args) {
		TreeMap<Integer, String> treeMap = new TreeMap<>();
		treeMap.put(5, "这是5");
		treeMap.put(7, "这是7");
		treeMap.put(1, "这是1");
		treeMap.put(2, "这是2");
		treeMap.put(3, "这是3");
		treeMap.put(4, "这是4");
		treeMap.put(8, "这是8");

		System.out.println(treeMap.containsKey(1));
		System.out.println(treeMap.containsKey(10));
		System.out.println(treeMap.get(4));
		treeMap.put(4, "张三是4");
		System.out.println(treeMap.get(4));

		treeMap.remove(4);
		System.out.println(treeMap.get(4));

		System.out.println(treeMap.firstKey());
		System.out.println(treeMap.lastKey());
		System.out.println(treeMap.floorKey(4));
		System.out.println(treeMap.ceilingKey(4));

		System.out.println("========");

		TreeSet<Integer> set = new TreeSet<>();
		set.add(3);
		set.add(3);
		set.add(4);
		set.add(4);
		System.out.println("有序表大小 : " + set.size());
		while (!set.isEmpty()) {
			System.out.println(set.pollFirst());
			// System.out.println(set.pollLast());
		}
		
		// 堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		heap.add(3);
		heap.add(3);
		heap.add(4);
		heap.add(4);
		System.out.println("堆大小 : " + heap.size());
		while(!heap.isEmpty()) {
			System.out.println(heap.poll());
		}

	}

}
