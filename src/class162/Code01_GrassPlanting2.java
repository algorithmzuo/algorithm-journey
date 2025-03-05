package class162;

// 边权转化为点权的模版题，C++版
// 一共有n个节点，给定n-1条边，节点连成一棵树，初始时所有边的权值为0
// 一共有m条操作，每条操作是如下2种类型中的一种
// 操作 P x y : x到y的路径上，每条边的权值增加1
// 操作 Q x y : x和y保证是直接连接的，查询他们之间的边权
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3038
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
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
//int son[MAXN];
//int top[MAXN];
//int dfn[MAXN];
//int cntd = 0;
//
//int sum[MAXN << 2];
//int addTag[MAXN << 2];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    dfn[u] = ++cntd;
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void up(int i) {
//    sum[i] = sum[i << 1] + sum[i << 1 | 1];
//}
//
//void lazy(int i, int v, int len) {
//    sum[i] += v * len;
//    addTag[i] += v;
//}
//
//void down(int i, int ln, int rn) {
//    if (addTag[i] != 0) {
//        lazy(i << 1, addTag[i], ln);
//        lazy(i << 1 | 1, addTag[i], rn);
//        addTag[i] = 0;
//    }
//}
//
//void add(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        lazy(i, jobv, r - l + 1);
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        if (jobl <= mid) {
//            add(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//int query(int jobi, int l, int r, int i) {
//    if (l == r) {
//        return sum[i];
//    }
//    int mid = (l + r) >> 1;
//    down(i, mid - l + 1, r - mid);
//    if (jobi <= mid) {
//        return query(jobi, l, mid, i << 1);
//    } else {
//        return query(jobi, mid + 1, r, i << 1 | 1);
//    }
//}
//
//void pathAdd(int x, int y, int v) {
//    while (top[x] != top[y]) {
//        if (dep[top[x]] <= dep[top[y]]) {
//            add(dfn[top[y]], dfn[y], v, 1, n, 1);
//            y = fa[top[y]];
//        } else {
//            add(dfn[top[x]], dfn[x], v, 1, n, 1);
//            x = fa[top[x]];
//        }
//    }
//    add(min(dfn[x], dfn[y]) + 1, max(dfn[x], dfn[y]), v, 1, n, 1);
//}
//
//int edgeQuery(int x, int y) {
//    int down = max(dfn[x], dfn[y]);
//    return query(down, 1, n, 1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i < n; i++) {
//        int u, v;
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    char op;
//    for (int i = 1, x, y; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 'P') {
//            pathAdd(x, y, 1);
//        } else {
//            cout << edgeQuery(x, y) << "\n";
//        }
//    }
//    return 0;
//}