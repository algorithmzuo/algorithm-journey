package class199;

// 有向图的基环树找环，java版
// 这个文件课上没讲，有向图中找到基环，非常容易理解
// 无向图的入环节点u，可以发现环的末尾节点v，因为边是无向的
// 于是用dfn[u] < dfn[v]来判断环
// 但是对有向图来说，假设出现的环如下
// 入环节点a -> b -> c -> 环的末尾节点d -> 入环节点a
// 不能放在入环节点a处判断，因为环的末尾节点d，不一定在a的邻居列表里
// 边是有向的，也许只有d到a的边，不一定有a到d的边
// 所以有向图的基环树找环，判断的时机，放在环的末尾节点处，具体说明如下
// 节点一共三种状态，0表示没有访问，1表示在递归路径上，2表示递归执行完了
// 依然记录每个点，在递归路径的前一个节点，也就是课上讲的from信息
// 来到节点u时，判断儿子节点v，看看是否在递归路径上
// 是的话，从u开始通过from走回v，环路就找全了，v是入环节点，u是环末尾点
// 调用时扫过所有节点，没有访问过的节点去调用dfs，直到环被找到

public class FollowUp1 {

	public static int MAXN = 100001;

	// 建图
	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg;

	// 0表示没访问过，1表示在递归路径上，2表示递归执行完了
	public static int[] state = new int[MAXN];

	// 记录每个点在递归路径的前一个节点
	public static int[] from = new int[MAXN];

	// 标记环上节点
	public static boolean[] cycle = new boolean[MAXN];

	// 返回值表示是否找到了环
	public static boolean dfs(int u) {
		state[u] = 1;
		boolean find = false;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (state[v] == 0) {
				from[v] = u;
				if (dfs(v)) {
					find = true;
				}
			} else if (state[v] == 1) {
				// u -> v，此时v在递归路径上，说明发现了环
				// v是入环节点，u是环末尾点，利用from得到整个环
				// 这里执行的操作是标记环上节点，当然也可以生成环的其他信息
				cycle[v] = true;
				for (int i = u; i != v; i = from[i]) {
					cycle[i] = true;
				}
				find = true;
			}
			if (find) {
				break;
			}
		}
		state[u] = 2;
		return find;
	}

}
