package class099;

// 播放列表的数量
// 给定三个参数，n、l、k
// 你的音乐播放器里有n首不同的歌
// 在旅途中你的旅伴想要听l首歌
// 听得歌曲不一定不同，即允许歌曲重复
// 请你为她按如下两条规则创建一个播放列表
// 1) 每首歌至少播放一次
// 2) 一首歌只有在其他k首歌播放完之后才能再次播放
// 返回可以满足要求的播放列表的数量
// 结果可能很大对1000000007取模
// 测试链接 : https://leetcode.cn/problems/number-of-music-playlists/
public class Code06_NumberOfMusicPlaylists {

	public static int MOD = 1000000007;

	public static int LIMIT = 100;

	public static long[] fac = new long[LIMIT + 1];

	public static long[] inv = new long[LIMIT + 1];

	static {
		fac[0] = 1;
		for (int i = 1; i <= LIMIT; i++) {
			fac[i] = ((long) i * fac[i - 1]) % MOD;
		}
		inv[LIMIT] = power(fac[LIMIT], MOD - 2);
		for (int i = LIMIT - 1; i >= 0; i--) {
			inv[i] = ((long) (i + 1) * inv[i + 1]) % MOD;
		}
	}

	public static long power(long x, int n) {
		long ans = 1;
		while (n > 0) {
			if ((n & 1) == 1) {
				ans = (ans * x) % MOD;
			}
			x = (x * x) % MOD;
			n >>= 1;
		}
		return ans;
	}

	public static int numMusicPlaylists(int n, int l, int k) {
		long cur, ans = 0, sign = 1;
		for (int i = 0; i < n - k; i++, sign = sign == 1 ? (MOD - 1) : 1) {
			cur = (sign * power(n - i - k, l - k)) % MOD;
			cur = (cur * fac[n]) % MOD;
			cur = (cur * inv[i]) % MOD;
			cur = (cur * inv[n - i - k]) % MOD;
			ans = (ans + cur) % MOD;
		}
		return (int) ans;
	}

}
