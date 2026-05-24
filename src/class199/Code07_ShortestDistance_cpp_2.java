package class199;

// 最短距离，好想的解法，C++版
// 图中有n个点、n条无向边，每条边有边权，图是一棵基环树
// 一共有m条操作，每条操作是如下两种类型中的一种
// 操作 1 x y : 第x号边的边权修改为y
// 操作 2 x y : 查询点x和点y之间的最短距离
// 1 <= n <= 10^5 + 6
// 1 <= m <= 10^5
// 0 <= 边权 <= 5000
// 测试链接 : https://www.luogu.com.cn/problem/P4949
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100007;
//int n, m;
//int u[MAXN];
//int v[MAXN];
//int w[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int cntd;
//
//int from[MAXN];
//bool cycle[MAXN];
//int arr[MAXN];
//int cnta;
//
//int edgeTo[MAXN];
//int cycleId[MAXN];
//
//int belong[MAXN];
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//
//int tree[MAXN << 1];
//int len;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            from[v] = u;
//            edgeTo[(e + 1) >> 1] = v;
//            dfs1(v);
//        } else if (dfn[u] < dfn[v]) {
//            cycle[u] = true;
//            arr[++cnta] = u;
//            edgeTo[(e + 1) >> 1] = u;
//            for (int i = v; i != u; i = from[i]) {
//                cycle[i] = true;
//                arr[++cnta] = i;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int f, int h) {
//    belong[u] = h;
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (!cycle[v] && v != f) {
//            edgeTo[(e + 1) >> 1] = v;
//            dfs2(v, u, h);
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs3(int u, int t) {
//    top[u] = t;
//    dfn[u] = ++cntd;
//    if (son[u] != 0) {
//        dfs3(son[u], t);
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (!cycle[v] && v != fa[u] && v != son[u]) {
//            dfs3(v, v);
//        }
//    }
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= len) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int query(int i) {
//    int ans = 0;
//    while (i >= 1) {
//        ans += tree[i];
//        i -= lowbit(i);
//    }
//    return ans;
//}
//
//int sum(int l, int r) {
//    if (l > r) {
//        return 0;
//    }
//    return query(r) - query(l - 1);
//}
//
//int edgeToNode(int i) {
//    if (cycle[u[i]] && cycle[v[i]]) {
//        return cycleId[edgeTo[i]];
//    } else {
//        return dfn[edgeTo[i]];
//    }
//}
//
//void prepare() {
//    dfs1(1);
//    cntd = 0;
//    for (int i = 1; i <= cnta; i++) {
//        dfs2(arr[i], 0, arr[i]);
//        dfs3(arr[i], arr[i]);
//    }
//    for (int i = 1, id = n + 1; i <= cnta; i++, id++) {
//        cycleId[arr[i]] = id;
//    }
//    len = n + cnta;
//    for (int i = 1; i <= n; i++) {
//        add(edgeToNode(i), w[i]);
//    }
//}
//
//void setEdge(int edge, int val) {
//    add(edgeToNode(edge), val - w[edge]);
//    w[edge] = val;
//}
//
//int jump(int x, int y) {
//    int ans = 0;
//    while (top[x] != top[y]) {
//        if (dep[top[x]] < dep[top[y]]) {
//            ans += sum(dfn[top[y]], dfn[y]);
//            y = fa[top[y]];
//        } else {
//            ans += sum(dfn[top[x]], dfn[x]);
//            x = fa[top[x]];
//        }
//    }
//    if (dfn[x] < dfn[y]) {
//        ans += sum(dfn[x] + 1, dfn[y]);
//    } else {
//        ans += sum(dfn[y] + 1, dfn[x]);
//    }
//    return ans;
//}
//
//int getDistance(int x, int y) {
//    if (belong[x] == belong[y]) {
//        return jump(x, y);
//    } else {
//        int bx = cycleId[belong[x]];
//        int by = cycleId[belong[y]];
//        if (bx > by) {
//            int tmp = bx;
//            bx = by;
//            by = tmp;
//        }
//        int p1 = sum(bx, by - 1);
//        int p2 = sum(n + 1, len) - p1;
//        return jump(x, belong[x]) + jump(y, belong[y]) + min(p1, p2);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> u[i] >> v[i] >> w[i];
//        addEdge(u[i], v[i]);
//        addEdge(v[i], u[i]);
//    }
//    prepare();
//    for (int i = 1, op, x, y; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 1) {
//            setEdge(x, y);
//        } else {
//            cout << getDistance(x, y) << "\n";
//        }
//    }
//    return 0;
//}