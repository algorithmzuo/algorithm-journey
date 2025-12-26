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
//const int MAXN = 100001;
//const int MAXM = 200001;
//const int MAXP = 20;
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
//int stjump[MAXN][MAXP];
//int cntd = 0;
//
//int tree[MAXN];
//
//int eid[MAXM];
//int op[MAXM];
//int x[MAXM];
//int y[MAXM];
//int v[MAXM];
//int cntq = 0;
//
//int lset[MAXM];
//int rset[MAXM];
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
//    for (int p = 1; p < MAXP; p++) {
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
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= n) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int query(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
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
//void compute(int el, int er, int vl, int vr) {
//    if (el > er) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = el; i <= er; i++) {
//            int id = eid[i];
//            if (op[id] == 2) {
//                ans[y[id]] = vl;
//            }
//        }
//    } else {
//        int mid = (vl + vr) / 2;
//        int lsiz = 0, rsiz = 0, request = 0;
//        for (int i = el; i <= er; i++) {
//            int id = eid[i];
//            if (op[id] == 0) {
//                if (v[id] <= mid) {
//                    lset[++lsiz] = id;
//                } else {
//                    pathAdd(x[id], y[id], 1);
//                    request++;
//                    rset[++rsiz] = id;
//                }
//            } else if (op[id] == 1) {
//                if (v[id] <= mid) {
//                    lset[++lsiz] = id;
//                } else {
//                    pathAdd(x[id], y[id], -1);
//                    request--;
//                    rset[++rsiz] = id;
//                }
//            } else {
//                if (pointQuery(x[id]) == request) {
//                    lset[++lsiz] = id;
//                } else {
//                    rset[++rsiz] = id;
//                }
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            eid[el + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            eid[el + lsiz + i - 1] = rset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            int id = rset[i];
//            if (op[id] == 0 && v[id] > mid) {
//                pathAdd(x[id], y[id], -1);
//            }
//            if (op[id] == 1 && v[id] > mid) {
//                pathAdd(x[id], y[id], 1);
//            }
//        }
//        compute(el, el + lsiz - 1, vl, mid);
//        compute(el + lsiz, er, mid + 1, vr);
//    }
//}
//
//void prepare() {
//    dfs(1, 0);
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 1) {
//            int pre = x[i];
//            x[i] = x[pre];
//            y[i] = y[pre];
//            v[i] = v[pre];
//        }
//        if (op[i] == 2) {
//            y[i] = ++cntq;
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        eid[i] = i;
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
//        cin >> op[i] >> x[i];
//        if (op[i] == 0) {
//            cin >> y[i] >> v[i];
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