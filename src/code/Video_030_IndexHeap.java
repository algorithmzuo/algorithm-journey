package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class Video_030_IndexHeap {

	// 索引堆的实现
	// 可以把T变成你想要的类型，这里泛型实现了
	public static class IndexHeap<T> {
		// 堆结构
		private ArrayList<T> heap;
		// 索引表
		private HashMap<T, Integer> indies;
		// 堆大小
		private int size;
		// 一定要给定一个T类型对象的比较器，否则堆无法工作
		private Comparator<? super T> cmp;

		public IndexHeap(Comparator<? super T> compare) {
			heap = new ArrayList<>();
			indies = new HashMap<>();
			size = 0;
			cmp = compare;
		}

		// o1是不是比o2更好
		// 如果是，返回true，说明o1一定比o2更值得去堆顶的方向
		// 如果不是，返回false
		private boolean better(T o1, T o2) {
			return cmp.compare(o1, o2) < 0;
		}

		private void heapInsert(int i) {
			while (better(heap.get(i), heap.get((i - 1) / 2))) {
				swap(i, (i - 1) / 2);
				i = (i - 1) / 2;
			}
		}

		private void heapify(int i) {
			int l = i * 2 + 1;
			while (l < size) {
				int best = l + 1 < size && better(heap.get(l + 1), heap.get(l)) ? (l + 1) : l;
				best = better(heap.get(best), heap.get(i)) ? best : i;
				if (best == i) {
					break;
				}
				swap(best, i);
				i = best;
				l = i * 2 + 1;
			}
		}

		private void swap(int i, int j) {
			T o1 = heap.get(i);
			T o2 = heap.get(j);
			heap.set(i, o2);
			heap.set(j, o1);
			indies.put(o2, i);
			indies.put(o1, j);
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public T peek() {
			return heap.get(0);
		}

		public void add(T obj) {
			heap.add(obj);
			indies.put(obj, size);
			heapInsert(size++);
		}

		public T poll() {
			T ans = heap.get(0);
			remove(ans);
			return ans;
		}

		public void remove(T obj) {
			int index = indies.get(obj);
			swap(index, size - 1);
			heap.remove(size - 1);
			indies.remove(obj);
			size--;
			if (index < size) {
				recover(heap.get(index));
			}
		}

		public void recover(T obj) {
			heapInsert(indies.get(obj));
			heapify(indies.get(obj));
		}

	}

	// 一个学生的类，为了展示用法
	public static class Student {
		public int age;
		public String name;

		public Student(int a, String b) {
			age = a;
			name = b;
		}
	}

	public static void main(String[] args) {
		// 非基础类型对象使用索引堆示例
		Student s1 = new Student(17, "A同学");
		Student s2 = new Student(10, "B同学");
		Student s3 = new Student(29, "C同学");
		Student s4 = new Student(33, "D同学");
		Student s5 = new Student(54, "E同学");
		Student s6 = new Student(93, "F同学");
		// 生成一个索引堆
		// 排序策略是年龄小的放在堆顶，堆天然不会去重
		// 所以排序策略只和年龄有关即可
		IndexHeap<Student> indexHeap1 = new IndexHeap<>((a, b) -> a.age - b.age);
		// 把所有学生加入堆
		indexHeap1.add(s1);
		indexHeap1.add(s2);
		indexHeap1.add(s3);
		indexHeap1.add(s4);
		indexHeap1.add(s5);
		indexHeap1.add(s6);
		// 加入之后
		// 可以把某个同学的年龄改了
		// 比如把s5，也就是E同学，年龄从54改成了4
		s5.age = 4;
		// 此时堆被破坏了，因为你擅自改了一个同学的年龄
		// 只需要调用recover方法，就能让堆恢复成年龄小根堆
		// 而且时间复杂度是O(log n)，很快的
		// 系统提供的堆做不到这么快，索引堆可以
		indexHeap1.recover(s5);
		// 依次弹出所有学生
		// 会发现年龄从小到大依次弹出，说明顺序是正确的
		while (!indexHeap1.isEmpty()) {
			Student cur = indexHeap1.poll();
			System.out.println("年龄 : " + cur.age + " , 名字 : " + cur.name);
		}
		System.out.println("==索引堆处理学生信息修改==");
		// 非基础类型对象使用有序表示例
		Student s7 = new Student(17, "A同学");
		Student s8 = new Student(10, "B同学");
		Student s9 = new Student(29, "C同学");
		Student s10 = new Student(33, "D同学");
		Student s11 = new Student(54, "E同学");
		Student s12 = new Student(93, "F同学");
		// 生成一个有序表
		// 排序策略是年龄小的放在前面
		// 同时要注意，有序表会把比较结果相同的key合并的，所以排序不能完全按照年龄
		// 年龄一样的，应该加入防止去重的逻辑，比如姓名也参与比较
		// 但是如果两个学生，年龄一样、姓名也一样，还希望都保留的话
		// 那就再考虑加入实例之间的内存地址比较
		TreeSet<Student> treeSet1 = new TreeSet<>(
				(a, b) -> (a.age != b.age) ? (a.age - b.age) : (a.name.compareTo(b.name)));
		// 把所有学生加入有序表
		treeSet1.add(s7);
		treeSet1.add(s8);
		treeSet1.add(s9);
		treeSet1.add(s10);
		treeSet1.add(s11);
		treeSet1.add(s12);
		// 加入之后
		// 可以把某个同学的年龄修改
		// 比如把s11，也就是E同学，年龄从54改成了4
		// 在有序表里，先把它删了，然后改完年龄再加入
		treeSet1.remove(s11);
		s11.age = 4;
		treeSet1.add(s11);
		// 依次弹出所有学生
		// 会发现年龄从小到大依次弹出，说明顺序是正确的
		while (!treeSet1.isEmpty()) {
			Student cur = treeSet1.pollFirst();
			System.out.println("年龄 : " + cur.age + " , 名字 : " + cur.name);
		}
		System.out.println("==有序表处理学生信息修改==");

		// 基础类型对象使用索引堆示例
		int[] arr1 = { 4, 3, 2, 5, 1 };
		// 任何基础类型的元素，天生一定会自带一些类似下标的身份信息的！这是一定的！
		// 生成一个索引堆，堆里放入下标即可，因为通过下标可以从arr里找到数字
		// 排序策略是 :
		// 数字小的下标，在堆顶
		IndexHeap<Integer> indexHeap2 = new IndexHeap<>((i, j) -> arr1[i] - arr1[j]);
		// 把数组所有的下标加入堆，就等于加入了所有数字
		indexHeap2.add(0);
		indexHeap2.add(1);
		indexHeap2.add(2);
		indexHeap2.add(3);
		indexHeap2.add(4);
		// 加入之后
		// 可以把某个下标上的数字改了
		// arr[1]原来是3，现在变成了-9
		arr1[1] = -9;
		// 此时堆被破坏了，因为你擅自改了一个下标的数字
		// 只需要调用recover方法，就能让堆恢复
		// 而且时间复杂度是O(log n)，很快的
		// 系统提供的堆做不到这么快，索引堆可以
		// 调用resign方法
		indexHeap2.recover(1);
		// 依次弹出所有下标
		// 会发现数字越小的下标越早弹出，顺序是正确的
		while (!indexHeap2.isEmpty()) {
			int index = indexHeap2.poll();
			System.out.println("下标 : " + index + " , 数字 :" + arr1[index]);
		}
		System.out.println("==索引堆处理数组中的数字修改==");

		// 基础类型对象使用有序表示例
		int[] arr2 = { 4, 3, 2, 5, 1 };
		// 任何基础类型的元素，天生一定会自带一些类似下标的身份信息的！这是一定的！
		// 生成一个有序表，有序表放入下标即可，因为通过下标可以从arr里找到数字
		// 但是有序表会去重！
		// 比如arr[3] = 5, arr[7] = 5，因为值都是5，所以3和7下标在有序表只会保留一个
		// 为了不去重，先比较数值，数值一样的再比较下标，就都保留了
		TreeSet<Integer> treeSet2 = new TreeSet<>((i, j) -> arr2[i] != arr2[j] ? (arr2[i] - arr2[j]) : (i - j));
		// 把数组所有的下标加入有序表，就等于加入了所有数字
		treeSet2.add(0);
		treeSet2.add(1);
		treeSet2.add(2);
		treeSet2.add(3);
		treeSet2.add(4);
		// 加入之后
		// 可以把某个下标上的数字改了
		// arr[1]原来是3，现在变成了-9
		// 在有序表里，先删掉1下标，修改值，再加入
		treeSet2.remove(1);
		arr2[1] = -9;
		treeSet2.add(1);
		// 依次弹出所有下标
		// 会发现数字越小的下标越早弹出，顺序是正确的
		while (!treeSet2.isEmpty()) {
			int index = treeSet2.pollFirst();
			System.out.println("下标 : " + index + " , 数字 :" + arr2[index]);
		}
		System.out.println("==有序表处理数组中的数字修改==");
	}

}
