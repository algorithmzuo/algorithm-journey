package class046;

import java.util.HashMap;

// 用类描述实现前缀树，路径是哈希表结构
// 测试链接 : https://leetcode.cn/problems/implement-trie-ii-prefix-tree/
public class Code03_TrieTree {

	class Trie {

		class TrieNode {
			public int pass;
			public int end;
			HashMap<Integer, TrieNode> nexts;

			public TrieNode() {
				pass = 0;
				end = 0;
				nexts = new HashMap<>();
			}
		}

		private TrieNode root;

		public Trie() {
			root = new TrieNode();
		}

		public void insert(String word) {
			TrieNode node = root;
			node.pass++;
			for (int i = 0, path; i < word.length(); i++) { // 从左往右遍历字符
				path = word.charAt(i);
				if (!node.nexts.containsKey(path)) {
					node.nexts.put(path, new TrieNode());
				}
				node = node.nexts.get(path);
				node.pass++;
			}
			node.end++;
		}

		public void erase(String word) {
			if (countWordsEqualTo(word) > 0) {
				TrieNode node = root;
				TrieNode next;
				node.pass--;
				for (int i = 0, path; i < word.length(); i++) {
					path = word.charAt(i);
					next = node.nexts.get(path);
					if (--next.pass == 0) {
						node.nexts.remove(path);
						return;
					}
					node = next;
				}
				node.end--;
			}
		}

		public int countWordsEqualTo(String word) {
			TrieNode node = root;
			for (int i = 0, path; i < word.length(); i++) {
				path = word.charAt(i);
				if (!node.nexts.containsKey(path)) {
					return 0;
				}
				node = node.nexts.get(path);
			}
			return node.end;
		}

		public int countWordsStartingWith(String pre) {
			TrieNode node = root;
			for (int i = 0, path; i < pre.length(); i++) {
				path = pre.charAt(i);
				if (!node.nexts.containsKey(path)) {
					return 0;
				}
				node = node.nexts.get(path);
			}
			return node.pass;
		}

	}

}