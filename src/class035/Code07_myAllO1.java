package class035;

import java.util.HashMap;
import java.util.HashSet;

// 全O(1)的数据结构
public class Code07_myAllO1 {

	// 测试链接 : https://leetcode.cn/problems/all-oone-data-structure/
	class AllOne {

		class Bucket {
			int cnt;
			public HashSet<String> set;
			Bucket pre;
			Bucket next;

			public Bucket(String s, int cnt) {
				set = new HashSet<>();
				set.add(s);
				this.cnt = cnt;
			}
		}
		Bucket head;

		Bucket tail;

		HashMap<String, Bucket> map;
		public AllOne() {
			head = new Bucket("",0);
			tail = new Bucket("", Integer.MAX_VALUE);
			head.next = tail;
			tail.pre = head;
			map = new HashMap<>();
		}

		private void insert(Bucket cur, Bucket pos) {
			pos.next = cur.next;
			cur.next= pos;
			pos.pre = cur;
			pos.next.pre = pos;
		}

		private void remove(Bucket cur) {
			Bucket pre = cur.pre;
			Bucket next = cur.next;
			pre.next = next;
			next.pre = pre;
		}


		public void inc(String key) {
			Bucket newBucket = null;
			// 1- if key exists
			if(map.containsKey(key)){
				// 1-1- find its bucket
				Bucket bucket  = map.get(key);
				// 1-3- add the key to its next bucket (if the bucket not exists then create one
				if(bucket.next.cnt == bucket.cnt + 1){
					newBucket = bucket.next;
					newBucket.set.add(key);
				}else{
					newBucket = new Bucket(key, bucket.cnt +1);
					insert(bucket , newBucket );
				}

				// 1-2- remove the key in its original bucket
				bucket.set.remove(key);
				if(bucket.set.isEmpty()){
					remove(bucket);
				}
			}else{
				// 2- if key not exists
				if(head.next.cnt == 1){
					newBucket = head.next;
					newBucket.set.add(key);
				}else{
					newBucket = new Bucket(key ,1);
					insert(head, newBucket);
				}
			}
			map.put(key,newBucket);
		}

		public void dec(String key) {
			// 1- get its bucket addr
			Bucket bucket = map.get(key);

			if(bucket.cnt == 1){
				map.remove(key);
			}else{
				if(bucket.pre.cnt == bucket.cnt -1){
					bucket.pre.set.add(key);
					map.put(key, bucket.pre);
				}else{
					Bucket newBucket =  new Bucket(key, bucket.cnt -1);
					map.put(key, newBucket);

					insert(bucket, newBucket);
				}
			}

			// 2- insert to its previous count bucket
			bucket.set.remove(key);
			if(bucket.set.isEmpty()){
				remove(bucket);
			}



		}

		public String getMaxKey() {
			return tail.pre.set.iterator().next();
		}

		public String getMinKey() {
			return head.next.set.iterator().next();
		}

	}

}
