package class200;

// 仙人掌遍历和找环的过程，java版

public class ShowDetail1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int[] weight = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] fromWeight = new int[MAXN];

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) continue;
			int v = to[e], w = weight[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				fromWeight[v] = w; // 设置v的from信息
				if (low[v] < dfn[u]) { // 没有扎起口袋
					low[u] = Math.min(low[u], low[v]);
				} else if (low[v] > dfn[u]) {
					// 发现割边，实现有关割边的处理
					top--;
				} else {
					// 发现了环，实现有关环的处理
				}
			} else {
				if (dfn[v] < dfn[u]) { // 发现回边
					fromWeight[v] = w; // 闭合边的信息给入环节点
					low[u] = Math.min(low[u], dfn[v]);
				}
				// 如果发现弃边，一般什么也不做
			}
		}
	}

}
