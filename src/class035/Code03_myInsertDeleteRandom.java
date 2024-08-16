package class035;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// 插入、删除和获取随机元素O(1)时间的结构
public class Code03_myInsertDeleteRandom {

	// 测试链接 : https://leetcode.cn/problems/insert-delete-getrandom-o1/
	static class RandomizedSet {

		public HashMap<Integer,Integer> map;
		public ArrayList<Integer> arr;

		public RandomizedSet() {
			map =  new HashMap<>();
			arr =  new ArrayList<>();
		}

		public boolean insert(int val) {
			if(map.containsKey(val)){
				return  false;
			}
			// 1- add  (val, arrindex) to the map
			map.put(val, arr.size());
			// 2- then add the value to the array
			arr.add(val);
			return true;

		}

		// the value gets removed will be replaced by the last value
		public boolean remove(int val) {
			if(!map.containsKey(val)){
				return false;
			}
			// 1- first get the index of the removed index
			int valIndex = map.get(val);

			// 2- get the last value and fill it in the empty index where the element just got removed
			// then update the map
			int lastVal = arr.get(arr.size()-1);
			arr.set(valIndex, lastVal);
			map.put(lastVal, valIndex);

			// 4- remove the element in the map
			map.remove(val);
			// 5- need to remove the elements in the array
			arr.remove(arr.size()-1);
			return true;
		}

		public int getRandom() {
			return arr.get((int) (Math.random()* arr.size()));
		}

	}

	public static void main(String[] args) {
		RandomizedSet set = new RandomizedSet();
		set.insert(1);
		System.out.println(set.insert(1));
		System.out.println(set.insert(2));
		System.out.println(set.insert(3));
		System.out.println(set.getRandom());
		System.out.println(set.getRandom());
		System.out.println(set.getRandom());
		System.out.println(set.getRandom());
		System.out.println(set.getRandom());
		System.out.println(set.getRandom());
		System.out.println(set.getRandom());
		System.out.println(set.getRandom());
	}

}
