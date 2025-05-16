package class169;

// 网络，C++版
// 一共有n个服务器，给定n-1条边，所有服务器连成一棵树
// 某两个服务器之间的路径上，可能接受一条请求，路径上的所有服务器都需要保存该请求的重要度
// 一共有m条操作，每条操作是如下3种类型中的一种，操作依次发生，第i条操作发生的时间为i
// 操作 0 x y v : x号服务器到y号服务器的路径上，增加了一个重要度为v的请求
// 操作 1 t     : 当初时间为t的操作，一定是增加请求的操作，现在这个请求结束了
// 操作 2 x     : 当前时间下，和x号服务器无关的所有请求中，打印最大的重要度
// 关于操作2，如果当前时间下，没有任何请求、或者所有请求都和x号服务器有关，打印-1
// 2 <= n <= 10^5    1 <= m <= 2 * 10^5    1 <= 重要度 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3250
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Event {
//    int op, x, y, v;
//};
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//const int MAXH = 20;
//const int INF = 1000000001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int dfn[MAXN];
//int stjump[MAXN][MAXH];
//int cntd = 0;
//
//int tree[MAXN];
//
//Event event[MAXM];
//int cntq = 0;
//
//Event lset[MAXM];
//Event rset[MAXM];
//
//int ans[MAXM];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    dfn[u] = ++cntd;
//    stjump[u][0] = f;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e != 0; e = nxt[e]) {
//        if (to[e] != f) {
//            dfs(to[e], u);
//        }
//    }
//    for (int e = head[u]; e != 0; e = nxt[e]) {
//        if (to[e] != f) {
//            siz[u] += siz[to[e]];
//        }
//    }
//}
//
//int lca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        int tmp = a;
//        a = b;
//        b = tmp;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//void add(int i, int v) {
//    for (; i <= n; i += i & -i) {
//        tree[i] += v;
//    }
//}
//
//int query(int i) {
//    int sum = 0;
//    for (; i > 0; i -= i & -i) {
//        sum += tree[i];
//    }
//    return sum;
//}
//
//void pathAdd(int x, int y, int v) {
//    int xylca = lca(x, y);
//    int lcafa = fa[xylca];
//    add(dfn[x], v);
//    add(dfn[y], v);
//    add(dfn[xylca], -v);
//    if (lcafa != 0) {
//        add(dfn[lcafa], -v);
//    }
//}
//
//int pointQuery(int x) {
//    return query(dfn[x] + siz[x] - 1) - query(dfn[x] - 1);
//}
//
//void compute(int ql, int qr, int vl, int vr) {
//    if (ql > qr) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = ql; i <= qr; i++) {
//            if (event[i].op == 2) {
//                ans[event[i].y] = vl;
//            }
//        }
//    } else {
//        int mid = (vl + vr) / 2;
//        int lsiz = 0, rsiz = 0, request = 0;
//        for (int i = ql; i <= qr; i++) {
//            if (event[i].op == 0) {
//                if (event[i].v <= mid) {
//                    lset[++lsiz] = event[i];
//                } else {
//                    pathAdd(event[i].x, event[i].y, 1);
//                    request++;
//                    rset[++rsiz] = event[i];
//                }
//            } else if (event[i].op == 1) {
//                if (event[i].v <= mid) {
//                    lset[++lsiz] = event[i];
//                } else {
//                    pathAdd(event[i].x, event[i].y, -1);
//                    request--;
//                    rset[++rsiz] = event[i];
//                }
//            } else {
//                if (pointQuery(event[i].x) == request) {
//                    lset[++lsiz] = event[i];
//                } else {
//                    rset[++rsiz] = event[i];
//                }
//            }
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            if (rset[i].op == 0 && rset[i].v > mid) {
//                pathAdd(rset[i].x, rset[i].y, -1);
//            }
//            if (rset[i].op == 1 && rset[i].v > mid) {
//                pathAdd(rset[i].x, rset[i].y, 1);
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            event[ql + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            event[ql + lsiz + i - 1] = rset[i];
//        }
//        compute(ql, ql + lsiz - 1, vl, mid);
//        compute(ql + lsiz, qr, mid + 1, vr);
//    }
//}
//
//void prepare() {
//    dfs(1, 0);
//    for (int i = 1; i <= m; i++) {
//        if (event[i].op == 1) {
//            event[i] = event[event[i].x];
//            event[i].op = 1;
//        }
//        if (event[i].op == 2){
//            event[i].y = ++cntq;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> event[i].op >> event[i].x;
//        if (event[i].op == 0) {
//            cin >> event[i].y >> event[i].v;
//        }
//    }
//    prepare();
//    compute(1, m, 0, INF);
//    for (int i = 1; i <= cntq; i++) {
//        if (ans[i] == 0) {
//            cout << -1 << '\n';
//        } else {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}