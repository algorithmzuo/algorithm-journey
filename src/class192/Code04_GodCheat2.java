package class192;

// 神会作弊，C++版
// 给定一张无向图，一共n个点、m条边，保证所有点连通，两点间出现重边只连一次
// 边双连通分量缩点后，如果点x和点y属于同一个边双连通分量，认为两点距离是1
// 如果不属于同一个边双连通分量，距离为缩点后的树上，两点简单路径上的节点个数
// 一共有q条查询，格式 x y : 计算点x和点y的距离，打印距离的二进制形式
// 1 <= n <= 10000
// 1 <= m <= 50000
// 测试链接 : https://www.luogu.com.cn/problem/P2783
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v;
//};
//
//bool EdgeCmp(Edge e1, Edge e2) {
//    if (e1.u != e2.u) {
//        return e1.u < e2.u;
//    }
//    return e1.v < e2.v;
//}
//
//const int MAXN = 10001;
//const int MAXM = 50001;
//const int MAXP = 15;
//int n, m, q;
//Edge edgeArr[MAXM];
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int ebccCnt;
//
//int dep[MAXN];
//int stjump[MAXN][MAXP];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void buildGraph() {
//    sort(edgeArr + 1, edgeArr + m + 1, EdgeCmp);
//    int k = 1;
//    for (int i = 2; i <= m; i++) {
//        if (edgeArr[k].u != edgeArr[i].u || edgeArr[k].v != edgeArr[i].v) {
//            edgeArr[++k] = edgeArr[i];
//        }
//    }
//    for (int i = 1; i <= k; i++) {
//        addEdge(edgeArr[i].u, edgeArr[i].v);
//        addEdge(edgeArr[i].v, edgeArr[i].u);
//    }
//    m = k;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            low[u] = min(low[u], low[v]);
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//    if (dfn[u] == low[u]) {
//        ebccCnt++;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = ebccCnt;
//        } while (pop != u);
//    }
//}
//
//void condense() {
//    cntg = 0;
//    for (int i = 1; i <= ebccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= m; i++) {
//        int ebcc1 = belong[edgeArr[i].u];
//        int ebcc2 = belong[edgeArr[i].v];
//        if (ebcc1 != ebcc2) {
//            addEdge(ebcc1, ebcc2);
//            addEdge(ebcc2, ebcc1);
//        }
//    }
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        swap(a, b);
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int getDist(int x, int y) {
//    return dep[x] + dep[y] - 2 * dep[getLca(x, y)];
//}
//
//string toBinaryString(int x) {
//    if (x == 0) {
//        return "0";
//    }
//    string s;
//    while (x > 0) {
//        s.push_back((x & 1) ? '1' : '0');
//        x >>= 1;
//    }
//    reverse(s.begin(), s.end());
//    return s;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cntg = 1;
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        edgeArr[i].u = min(u, v);
//        edgeArr[i].v = max(u, v);
//    }
//    buildGraph();
//    tarjan(1, 0);
//    condense();
//    dfs(1, 0);
//    cin >> q;
//    for (int i = 1, x, y; i <= q; i++) {
//        cin >> x >> y;
//        x = belong[x];
//        y = belong[y];
//        cout << toBinaryString(getDist(x, y) + 1) << "\n";
//    }
//    return 0;
//}