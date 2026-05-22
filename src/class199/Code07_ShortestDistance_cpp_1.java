package class199;

// 最短距离，巧妙的解法，C++版
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
//int node1, node2, skipEdge;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//
//int tree[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int preEdge) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (e != (preEdge ^ 1)) {
//            if (dfn[v] == 0) {
//                dfs1(v, e);
//            } else if (dfn[u] < dfn[v]) {
//                node1 = u;
//                node2 = v;
//                skipEdge = e >> 1;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if ((e >> 1) != skipEdge && v != f) {
//            dfs2(v, u);
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
//        if ((e >> 1) != skipEdge && v != fa[u] && v != son[u]) {
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
//    while (i <= n) {
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
//void prepare() {
//    dfs1(1, 0);
//    cntd = 0;
//    dfs2(1, 0);
//    dfs3(1, 1);
//    for (int i = 1; i <= n; i++) {
//        if (i != skipEdge) {
//            add(fa[u[i]] == v[i] ? dfn[u[i]] : dfn[v[i]], w[i]);
//        }
//    }
//}
//
//void setEdge(int edge, int val) {
//    if (edge != skipEdge) {
//        add(fa[u[edge]] == v[edge] ? dfn[u[edge]] : dfn[v[edge]], val - w[edge]);
//    }
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
//    int p1 = jump(x, y);
//    int p2 = jump(x, node1) + w[skipEdge] + jump(node2, y);
//    int p3 = jump(x, node2) + w[skipEdge] + jump(node1, y);
//    return min(p1, min(p2, p3));
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntg = 1;
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