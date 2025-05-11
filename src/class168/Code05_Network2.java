package class168;

// 网络，C++版
// 一共有n个服务器，给定n-1条边，所有服务器连成一棵树
// 某两个服务器之间的路径上，可能接受一条请求，路径上的所有服务器都需要保存该请求的重要度
// 一共有m条操作，每条操作是如下3种类型中的一种，操作依次发生，第i条操作发生的时间为i
// 操作 0 a b v : a号服务器到b号服务器的路径上，增加了一个重要度为v的请求
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
//int events[MAXM][4];
//
//int lset[MAXM][4];
//int rset[MAXM][4];
//int ans[MAXM];
//int cntans = 0;
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
//void clone(int* e1, int* e2) {
//    e1[0] = e2[0];
//    e1[1] = e2[1];
//    e1[2] = e2[2];
//    e1[3] = e2[3];
//}
//
//void prepare() {
//    dfs(1, 0);
//    for (int i = 1; i <= m; i++) {
//        if (events[i][0] == 1) {
//            clone(events[i], events[events[i][1]]);
//            events[i][0] = -1;
//        } else if (events[i][0] == 2){
//            events[i][0] = ++cntans;
//        }
//    }
//}
//
//void compute(int ql, int qr, int vl, int vr) {
//    if (ql > qr) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = ql; i <= qr; i++) {
//            if (events[i][0] > 0) {
//                ans[events[i][0]] = vl;
//            }
//        }
//    } else {
//        int mid = (vl + vr) / 2;
//        int lsize = 0, rsize = 0, request = 0;
//        for (int i = ql; i <= qr; i++) {
//            if (events[i][0] == 0) {
//                if (events[i][3] > mid) {
//                    pathAdd(events[i][1], events[i][2], 1);
//                    clone(rset[++rsize], events[i]);
//                    request++;
//                } else {
//                    clone(lset[++lsize], events[i]);
//                }
//            } else if (events[i][0] == -1) {
//                if (events[i][3] > mid) {
//                    pathAdd(events[i][1], events[i][2], -1);
//                    clone(rset[++rsize], events[i]);
//                    request--;
//                } else {
//                    clone(lset[++lsize], events[i]);
//                }
//            } else {
//                if (pointQuery(events[i][1]) != request) {
//                    clone(rset[++rsize], events[i]);
//                } else {
//                    clone(lset[++lsize], events[i]);
//                }
//            }
//        }
//        for (int i = 1; i <= rsize; i++) {
//            if (rset[i][0] == 0 && rset[i][3] > mid) {
//                pathAdd(rset[i][1], rset[i][2], -1);
//            }
//            if (rset[i][0] == -1 && rset[i][3] > mid) {
//                pathAdd(rset[i][1], rset[i][2], 1);
//            }
//        }
//        for (int i = ql, j = 1; j <= lsize; i++, j++) {
//            clone(events[i], lset[j]);
//        }
//        for (int i = ql + lsize, j = 1; j <= rsize; i++, j++) {
//            clone(events[i], rset[j]);
//        }
//        compute(ql, ql + lsize - 1, vl, mid);
//        compute(ql + lsize, qr, mid + 1, vr);
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
//        cin >> events[i][0] >> events[i][1];
//        if (events[i][0] == 0) {
//            cin >> events[i][2] >> events[i][3];
//        }
//    }
//    prepare();
//    compute(1, m, 0, INF);
//    for (int i = 1; i <= cntans; i++) {
//        if (ans[i] == 0) {
//            cout << -1 << '\n';
//        } else {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}