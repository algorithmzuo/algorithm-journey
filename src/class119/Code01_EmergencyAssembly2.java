package class119;

// 紧急集合
// 一共有n个节点，编号1 ~ n，一定有n-1条边连接形成一颗树
// 从一个点到另一个点的路径上有几条边，就需要耗费几个金币
// 每条查询(a, b, c)表示有三个人分别站在a、b、c点上
// 他们想集合在树上的某个点，并且想花费的金币总数最少
// 一共有m条查询，打印m个答案
// 1 <= n <= 5 * 10^5
// 1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4281
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <algorithm>
//#include <cmath>
//#include <cstdio>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int LIMIT = 19;
//
//int power;
//int head[MAXN];
//int edgeNext[MAXN << 1];
//int edgeTo[MAXN << 1];
//int cnt;
//int deep[MAXN];
//int stjump[MAXN][LIMIT];
//int togather;
//long long cost;
//
//int log2(int n) {
//    int ans = 0;
//    while ((1 << ans) <= (n >> 1)) {
//        ans++;
//    }
//    return ans;
//}
//
//void build(int n) {
//    power = log2(n);
//    cnt = 1;
//    fill(head, head + n + 1, 0);
//}
//
//void addEdge(int u, int v) {
//	edgeNext[cnt] = head[u];
//	edgeTo[cnt] = v;
//    head[u] = cnt++;
//}
//
//void dfs(int u, int f) {
//    deep[u] = deep[f] + 1;
//    stjump[u][0] = f;
//    for (int p = 1; p <= power; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e != 0; e = edgeNext[e]) {
//        if (edgeTo[e] != f) {
//            dfs(edgeTo[e], u);
//        }
//    }
//}
//
//int lca(int a, int b) {
//    if (deep[a] < deep[b]) swap(a, b);
//    for (int p = power; p >= 0; p--) {
//        if (deep[stjump[a][p]] >= deep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) return a;
//    for (int p = power; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//void compute(int a, int b, int c) {
//	int h1 = lca(a, b), h2 = lca(a, c), h3 = lca(b, c);
//	int high = h1 != h2 ? (deep[h1] < deep[h2] ? h1 : h2) : h1;
//	int low = h1 != h2 ? (deep[h1] > deep[h2] ? h1 : h2) : h3;
//	togather = low;
//	cost = (long) deep[a] + deep[b] + deep[c] - deep[high] * 2 - deep[low];
//}
//
//int main() {
//    int n, m;
//    scanf("%d %d", &n, &m);
//    build(n);
//    for (int i = 1, u, v; i < n; i++) {
//        scanf("%d %d", &u, &v);
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    for (int i = 1, a, b, c; i <= m; i++) {
//        scanf("%d %d %d", &a, &b, &c);
//        compute(a, b, c);
//        printf("%d %lld\n", togather, cost);
//    }
//    return 0;
//}