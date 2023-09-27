package class062;

import java.util.Arrays;
import java.util.List;

// 为高尔夫比赛砍树
// 你被请来给一个要举办高尔夫比赛的树林砍树。树林由一个 m * n 的矩阵表示， 在这个矩阵中：
// 0 表示障碍，无法触碰
// 1 表示地面，可以行走
// 比 1 大的数 表示有树的单元格，可以行走，数值表示树的高度
// 每一步，你都可以向上、下、左、右四个方向之一移动一个单位，如果你站的地方有一棵树
// 那么你可以决定是否要砍倒它。
// 你需要按照树的高度从低向高砍掉所有的树
// 每砍过一颗树，该单元格的值变为 1（即变为地面）
// 你将从 (0, 0) 点开始工作，返回你砍完所有树需要走的最小步数。 如果你无法砍完所有的树，返回 -1 。
// 可以保证的是，没有两棵树的高度是相同的，并且你至少需要砍倒一棵树。
// 测试链接 : https://leetcode.cn/problems/cut-off-trees-for-golf-event/
public class Code03_CutOffTreesForGolfEvent {

	public static int MAXN = 51;

	public static int MAXM = 51;

	public static int LIMIT = MAXN * MAXM;

	public static int[][] visited = new int[MAXN][MAXM];

	public static int[][] arr = new int[LIMIT][3];

	// 0:上，1:右，2:下，3:左
	public static int[] move = new int[] { -1, 0, 1, 0, -1 };

	public static int n, m;

	// 为了快，手撸双端队列
	public static int[][] deque = new int[LIMIT][3];
	// 双端队列的头、尾、大小
	public static int l, r, size;

	// 初始化双端队列
	public static void buildDeque() {
		l = -1;
		r = -1;
		size = 0;
	}

	// 双端队列从头部弹出
	public static int[] pollFirst() {
		int[] ans = deque[l];
		if (l < LIMIT - 1) {
			l++;
		} else {
			l = 0;
		}
		size--;
		if (size == 0) {
			l = r = -1;
		}
		return ans;
	}

	// 双端队列从头部加入
	public static void offerFirst(int x, int y, int d) {
		if (l == -1) {
			deque[0][0] = x;
			deque[0][1] = y;
			deque[0][2] = d;
			l = r = 0;
		} else {
			int fill = l == 0 ? (LIMIT - 1) : (l - 1);
			deque[fill][0] = x;
			deque[fill][1] = y;
			deque[fill][2] = d;
			l = fill;
		}
		size++;
	}

	// 双端队列从尾部加入
	public static void offerLast(int x, int y, int d) {
		if (l == -1) {
			deque[0][0] = x;
			deque[0][1] = y;
			deque[0][2] = d;
			l = r = 0;
		} else {
			int fill = (r == LIMIT - 1) ? 0 : (r + 1);
			deque[fill][0] = x;
			deque[fill][1] = y;
			deque[fill][2] = d;
			r = fill;
		}
		size++;
	}

	public static int cutOffTree(List<List<Integer>> forest) {
		n = forest.size();
		m = forest.get(0).size();
		int cnt = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int value = forest.get(i).get(j);
				visited[i][j] = value > 0 ? 1 : 0;
				if (value > 1) {
					arr[cnt][0] = value;
					arr[cnt][1] = i;
					arr[cnt++][2] = j;
				}
			}
		}
		Arrays.sort(arr, 0, cnt, (a, b) -> a[0] - b[0]);
		int ans = 0;
		for (int i = 0, x = 0, y = 0, block = 2; i < cnt; i++, block++) {
			int toX = arr[i][1];
			int toY = arr[i][2];
			int step = walk(x, y, toX, toY, block);
			if (step == -1) {
				return -1;
			}
			ans += step;
			x = toX;
			y = toY;
		}
		return ans;
	}

	public static int walk(int a, int b, int c, int d, int block) {
		buildDeque();
		offerFirst(a, b, 0);
		while (size > 0) {
			int[] cur = pollFirst();
			int x = cur[0];
			int y = cur[1];
			int distance = cur[2];
			if (visited[x][y] != block) {
				visited[x][y] = block;
				if (x == c && y == d) {
					return distance;
				}
				for (int i = 0; i < 4; i++) {
					int nx = x + move[i];
					int ny = y + move[i + 1];
					if (nx == -1 || nx == n || ny == -1 || ny == m || visited[nx][ny] == 0
							|| visited[nx][ny] == block) {
						continue;
					}
					if ((i == 0 && x > c) || (i == 1 && y < d) || (i == 2 && x < c) || (i == 3 && y > d)) {
						// 离的更近
						offerFirst(nx, ny, distance + 1);
					} else {
						// 离的更远
						offerLast(nx, ny, distance + 1);
					}
				}
			}
		}
		return -1;
	}

}
