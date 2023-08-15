package code;

// 用类描述实现前缀树
// 测试链接 :
// https://leetcode.cn/problems/implement-trie-ii-prefix-tree/
public class  Video_038_2_TrieTree {

	class Trie {

		class TrieNode {
			public int pass;
			public int end;
			public TrieNode[] nexts;

			public TrieNode() {
				pass = 0;
				end = 0;
				nexts = new TrieNode[26];
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
				path = word.charAt(i) - 'a'; // 由字符，对应成走向哪条路
				if (node.nexts[path] == null) {
					node.nexts[path] = new TrieNode();
				}
				node = node.nexts[path];
				node.pass++;
			}
			node.end++;
		}

		public void erase(String word) {
			if (countWordsEqualTo(word) > 0) {
				TrieNode node = root;
				node.pass--;
				for (int i = 0, path; i < word.length(); i++) {
					path = word.charAt(i) - 'a';
					if (--node.nexts[path].pass == 0) {
						node.nexts[path] = null;
						return;
					}
					node = node.nexts[path];
				}
				node.end--;
			}
		}

		public int countWordsEqualTo(String word) {
			TrieNode node = root;
			for (int i = 0, path; i < word.length(); i++) {
				path = word.charAt(i) - 'a';
				if (node.nexts[path] == null) {
					return 0;
				}
				node = node.nexts[path];
			}
			return node.end;
		}

		public int countWordsStartingWith(String pre) {
			TrieNode node = root;
			for (int i = 0, path; i < pre.length(); i++) {
				path = pre.charAt(i) - 'a';
				if (node.nexts[path] == null) {
					return 0;
				}
				node = node.nexts[path];
			}
			return node.pass;
		}

	}

}