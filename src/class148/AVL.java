package class148;

// AVL树的实现
// 测试链接 : https://www.luogu.com.cn/problem/P3369
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class AVL {

	public static int MAXN = 100001;

	public static int cnt;

	public static int head;

	public static int[] key = new int[MAXN];

	public static int[] count = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] height = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static void build() {
		cnt = head = 0;
	}

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + count[i];
		height[i] = Math.max(height[left[i]], height[right[i]]) + 1;
	}

	public static int leftRotate(int i) {
		int r = right[i];
		right[i] = left[r];
		left[r] = i;
		up(i);
		up(r);
		return r;
	}

	public static int rightRotate(int i) {
		int l = left[i];
		left[i] = right[l];
		right[l] = i;
		up(i);
		up(l);
		return l;
	}

	public static int maintain(int i) {
		int lh = height[left[i]];
		int rh = height[right[i]];
		if (lh - rh > 1) {
			int llh = height[left[left[i]]];
			int lrh = height[right[left[i]]];
			if (llh >= lrh) {
				i = rightRotate(i);
			} else {
				left[i] = leftRotate(left[i]);
				i = rightRotate(i);
			}
		} else if (rh - lh > 1) {
			int rrh = height[right[right[i]]];
			int rlh = height[left[right[i]]];
			if (rrh >= rlh) {
				i = leftRotate(i);
			} else {
				right[i] = rightRotate(right[i]);
				i = leftRotate(i);
			}
		}
		return i;
	}

	public static void add(int k) {
		head = add(head, k);
	}

	public static int add(int i, int k) {
		if (i == 0) {
			key[++cnt] = k;
			count[cnt] = size[cnt] = height[cnt] = 1;
			return cnt;
		}
		if (key[i] == k) {
			count[i]++;
		} else if (key[i] > k) {
			left[i] = add(left[i], k);
		} else {
			right[i] = add(right[i], k);
		}
		up(i);
		return maintain(i);
	}

	public static void remove(int k) {
		if (rank(k) != rank(k + 1)) {
			head = remove(head, k);
		}
	}

	public static int remove(int i, int k) {
		if (key[i] < k) {
			right[i] = remove(right[i], k);
		} else if (key[i] > k) {
			left[i] = remove(left[i], k);
		} else {
			if (count[i] > 1) {
				count[i]--;
			} else {
				if (left[i] == 0 && right[i] == 0) {
					return 0;
				} else if (left[i] != 0 && right[i] == 0) {
					i = left[i];
				} else if (left[i] == 0 && right[i] != 0) {
					i = right[i];
				} else {
					int mostLeft = right[i];
					while (left[mostLeft] != 0) {
						mostLeft = left[mostLeft];
					}
					right[i] = removeMostLeft(right[i], mostLeft);
					left[mostLeft] = left[i];
					right[mostLeft] = right[i];
					i = mostLeft;
				}
			}
		}
		up(i);
		return maintain(i);
	}

	public static int removeMostLeft(int i, int mostLeft) {
		if (i == mostLeft) {
			return right[i];
		} else {
			left[i] = removeMostLeft(left[i], mostLeft);
			up(i);
			return maintain(i);
		}
	}

	public static int rank(int k) {
		return small(head, k) + 1;
	}

	public static int small(int i, int k) {
		if (i == 0) {
			return 0;
		}
		if (key[i] >= k) {
			return small(left[i], k);
		} else {
			return size[left[i]] + count[i] + small(right[i], k);
		}
	}

	public static int index(int x) {
		return index(head, x);
	}

	public static int index(int i, int x) {
		if (size[left[i]] >= x) {
			return index(left[i], x);
		} else if (size[left[i]] + count[i] < x) {
			return index(right[i], x - size[left[i]] - count[i]);
		}
		return key[i];
	}

	public static int pre(int k) {
		return pre(head, k);
	}

	public static int pre(int i, int k) {
		if (i == 0) {
			return Integer.MIN_VALUE;
		}
		if (key[i] >= k) {
			return pre(left[i], k);
		} else {
			return Math.max(key[i], pre(right[i], k));
		}
	}

	public static int post(int k) {
		return post(head, k);
	}

	public static int post(int i, int k) {
		if (i == 0) {
			return Integer.MAX_VALUE;
		}
		if (key[i] <= k) {
			return post(right[i], k);
		} else {
			return Math.min(key[i], post(left[i], k));
		}
	}

	public static void main(String[] args) throws IOException {
		build();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1, op, x; i <= n; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			if (op == 1) {
				add(x);
			} else if (op == 2) {
				remove(x);
			} else if (op == 3) {
				out.println(rank(x));
			} else if (op == 4) {
				out.println(index(x));
			} else if (op == 5) {
				out.println(pre(x));
			} else {
				out.println(post(x));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
