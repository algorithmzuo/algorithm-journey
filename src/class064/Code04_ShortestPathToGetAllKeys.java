package class064;

// 获取所有钥匙的最短路径
// 给定一个二维网格 grid ，其中：
// '.' 代表一个空房间、'#' 代表一堵、'@' 是起点
// 小写字母代表钥匙、大写字母代表锁
// 从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间
// 不能在网格外面行走，也无法穿过一堵墙
// 如果途经一个钥匙，我们就把它捡起来。除非我们手里有对应的钥匙，否则无法通过锁。
// 假设 k 为 钥匙/锁 的个数，且满足 1 <= k <= 6，
// 字母表中的前 k 个字母在网格中都有自己对应的一个小写和一个大写字母
// 换言之，每个锁有唯一对应的钥匙，每个钥匙也有唯一对应的锁
// 另外，代表钥匙和锁的字母互为大小写并按字母顺序排列
// 返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
// 测试链接：https://leetcode.cn/problems/shortest-path-to-get-all-keys
public class Code04_ShortestPathToGetAllKeys {

	public static int MAXN = 31;

	public static int MAXM = 31;

	public static int MAXK = 6;

	// 0:上，1:右，2:下，3:左
	public static int[] move = new int[] { -1, 0, 1, 0, -1 };

	public static char[][] grid = new char[MAXN][];

	public static boolean[][][] visited = new boolean[MAXN][MAXM][1 << MAXK];

	// 0 : 行
	// 1 : 列
	// 2 : 收集钥匙的状态
	public static int[][] queue = new int[MAXN * MAXM * (1 << MAXK)][3];

	public static int l, r, n, m, key;

	public static void build(String[] g) {
		l = r = key = 0;
		n = g.length;
		m = g[0].length();
		for (int i = 0; i < n; i++) {
			grid[i] = g[i].toCharArray();
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == '@') {
					queue[r][0] = i;
					queue[r][1] = j;
					// 0 : 000000
					queue[r++][2] = 0;
				}
				if (grid[i][j] >= 'a' && grid[i][j] <= 'f') {
					key |= 1 << (grid[i][j] - 'a');
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int s = 0; s <= key; s++) {
					visited[i][j][s] = false;
				}
			}
		}
	}

	public static int shortestPathAllKeys(String[] g) {
		build(g);
		int level = 1;
		while (l < r) {
			for (int k = 0, size = r - l, x, y, s; k < size; k++) {
				x = queue[l][0];
				y = queue[l][1];
				s = queue[l++][2];
				for (int i = 0, nx, ny, ns; i < 4; i++) {
					nx = x + move[i];
					ny = y + move[i + 1];
					ns = s;
					if (nx < 0 || nx == n || ny < 0 || ny == m || grid[nx][ny] == '#') {
						// 越界或者障碍
						continue;
					}
					if (grid[nx][ny] >= 'A' && grid[nx][ny] <= 'F' && ((ns & (1 << (grid[nx][ny] - 'A'))) == 0)) {
						// 是锁，又没有对应的钥匙
						continue;
					}
					if (grid[nx][ny] >= 'a' && grid[nx][ny] <= 'f') {
						// 是某一把钥匙
						ns |= (1 << (grid[nx][ny] - 'a'));
					}
					if (ns == key) {
						// 常见剪枝
						// 发现终点直接返回
						// 不用等都结束
						return level;
					}
					if (!visited[nx][ny][ns]) {
						visited[nx][ny][ns] = true;
						queue[r][0] = nx;
						queue[r][1] = ny;
						queue[r++][2] = ns;
					}
				}
			}
			level++;
		}
		return -1;
	}

}
