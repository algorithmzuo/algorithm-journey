package class035;

import java.util.*;

// 插入、删除和获取随机元素O(1)时间且允许有重复数字的结构
public class Code04_myInsertDeleteRandomDuplicatesAllowed {

	// 测试链接 :
	// https://leetcode.cn/problems/insert-delete-getrandom-o1-duplicates-allowed/
	static class RandomizedCollection {
		HashMap<Integer, HashSet<Integer>> map;
		List<Integer> arr;


		public RandomizedCollection() {
			map = new HashMap<>();
			arr = new ArrayList<>();
		}

		public boolean insert(int val) {
			boolean isNotPresent = false;
			if(!map.containsKey(val)){
				isNotPresent = true;
			}
			// 1- add the value into arr
			arr.add(val);
			// 2- deal with hashmap part
			// 2-1- get the set and add the index into
			HashSet<Integer> set = map.getOrDefault(val, new HashSet<Integer>());
			set.add(arr.size()-1);
			map.put(val, set);
			return isNotPresent;
		}

		public boolean remove(int val) {
			if(!map.containsKey(val)){
				return false;
			}
			// 2- deal with the array ---- remove the value from the arraylist
			int endVal = arr.get(arr.size() -1);
			HashSet<Integer> valSet = map.get(val);

			if(endVal == val){
				valSet.remove(arr.size()-1);
			}else{
				int valIndex = valSet.iterator().next();
				valSet.remove(valIndex);

				arr.set(valIndex, endVal);
				HashSet<Integer> endValSet = map.get(endVal);
				endValSet.remove(arr.size() -1);
				endValSet.add(valIndex);
				map.put(val,valSet);

			}

			arr.remove(arr.size()-1);
			if(valSet.isEmpty()){
				map.remove(val);
			}
			return true;
		}

		public int getRandom() {
			return arr.get((int)(Math.random() * arr.size()));
		}

	}

	public static void main(String[] args) {
		RandomizedCollection collection = new RandomizedCollection();
		System.out.println(collection.insert(1));
		System.out.println(collection.remove(2));


	}



}
