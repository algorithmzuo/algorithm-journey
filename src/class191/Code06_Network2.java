package class191;

// 网络，C++版
// 给定一张无向图，一共n个点、m条边，保证所有点连通
// 一共q条操作，格式 x y : 点x和点y之间新增一条边，打印此时割边的数量
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=2460
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//int t, n, m, q;
//int a[MAXM];
//int b[MAXM];
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
//int up[MAXN];
//int dep[MAXN];
//int fa[MAXN];
//
//void prepare() {
//    cntg = 1;
//    cntd = top = ebccCnt = 0;
//    for (int i = 1; i <= n; i++) {
//        head[i] = dfn[i] = low[i] = 0;
//    }
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
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
//        int ebcc1 = belong[a[i]];
//        int ebcc2 = belong[b[i]];
//        if (ebcc1 != ebcc2) {
//            addEdge(ebcc1, ebcc2);
//            addEdge(ebcc2, ebcc1);
//        }
//    }
//}
//
//void dfs(int u, int f) {
//    dep[u] = dep[f] + 1;
//    up[u] = f;
//    fa[u] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs(v, u);
//        }
//    }
//}
//
//int find(int i) {
//    if (i != fa[i]) {
//        fa[i] = find(fa[i]);
//    }
//    return fa[i];
//}
//
//void Union(int x, int y) {
//    x = find(x);
//    y = find(y);
//    if (x != y) {
//        if (dep[x] < dep[y]) {
//            fa[y] = x;
//        } else {
//            fa[x] = y;
//        }
//    }
//}
//
//void link(int x, int y) {
//    x = find(belong[x]);
//    y = find(belong[y]);
//    while (x != y) {
//        if (dep[x] >= dep[y]) {
//            Union(x, up[x]);
//            x = find(x);
//        } else {
//            Union(y, up[y]);
//            y = find(y);
//        }
//        ebccCnt--;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    t = 0;
//    cin >> n >> m;
//    while (n != 0 || m != 0) {
//        prepare();
//        for (int i = 1; i <= m; i++) {
//            cin >> a[i] >> b[i];
//            addEdge(a[i], b[i]);
//            addEdge(b[i], a[i]);
//        }
//        tarjan(1, 0);
//        condense();
//        dfs(1, 0);
//        cout << "Case " << (++t) << ":\n";
//        cin >> q;
//        for (int i = 1, x, y; i <= q; i++) {
//            cin >> x >> y;
//            link(x, y);
//            cout << ebccCnt - 1 << "\n";
//        }
//        cout << "\n";
//        cin >> n >> m;
//    }
//    return 0;
//}