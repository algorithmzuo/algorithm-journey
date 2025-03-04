package class161;

// 遥远的国度，C++版
// 一共有n个节点，给定n-1条边，节点连成一棵树，给定树的初始头节点，给定每个点的点权
// 一共有m条操作，每种操作是如下3种类型中的一种
// 操作 1 x     : 树的头节点变成x，树的形态重新组织
// 操作 2 x y v : x到y的路径上，所有节点的值改成v
// 操作 3 x     : 当前树的形态下，打印x的子树最小值
// 1 <= n、m <= 10^5
// 任何时候节点值一定是正数
// 测试链接 : https://www.luogu.com.cn/problem/P3979
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//int arr[MAXN];
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
//int seg[MAXN];
//int cntd = 0;
//
//int minv[MAXN << 2];
//int change[MAXN << 2];
//
//void addEdge(int u, int v) {
//    ++cntg;
//    nxt[cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
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
//    seg[cntd] = u;
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void up(int i) {
//    minv[i] = min(minv[i << 1], minv[i << 1 | 1]);
//}
//
//void lazy(int i, int v) {
//    minv[i] = v;
//    change[i] = v;
//}
//
//void down(int i) {
//    if (change[i] != 0) {
//        lazy(i << 1, change[i]);
//        lazy(i << 1 | 1, change[i]);
//        change[i] = 0;
//    }
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        minv[i] = arr[seg[l]];
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//void update(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        lazy(i, jobv);
//    } else {
//        down(i);
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//        	update(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//        	update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return minv[i];
//    }
//    down(i);
//    int mid = (l + r) >> 1;
//    int ans = INT_MAX;
//    if (jobl <= mid) {
//        ans = min(ans, query(jobl, jobr, l, mid, i << 1));
//    }
//    if (jobr > mid) {
//        ans = min(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
//    }
//    return ans;
//}
//
//void pathUpdate(int x, int y, int v) {
//    while (top[x] != top[y]) {
//        if (dep[top[x]] <= dep[top[y]]) {
//            update(dfn[top[y]], dfn[y], v, 1, n, 1);
//            y = fa[top[y]];
//        } else {
//            update(dfn[top[x]], dfn[x], v, 1, n, 1);
//            x = fa[top[x]];
//        }
//    }
//    update(min(dfn[x], dfn[y]), max(dfn[x], dfn[y]), v, 1, n, 1);
//}
//
//int find(int root, int u) {
//    if (u == root) {
//        return -1;
//    }
//    if (dfn[root] < dfn[u] || dfn[u] + siz[u] <= dfn[root]) {
//        return 0;
//    }
//    while (top[root] != top[u]) {
//        if (fa[top[root]] == u) {
//            return top[root];
//        }
//        root = fa[top[root]];
//    }
//    return son[u];
//}
//
//int treeQuery(int root, int u) {
//    int uson = find(root, u);
//    if (uson == -1) {
//        return minv[1];
//    } else if (uson == 0) {
//        return query(dfn[u], dfn[u] + siz[u] - 1, 1, n, 1);
//    } else {
//        int ans = INT_MAX;
//        if (1 <= dfn[uson] - 1) {
//            ans = min(ans, query(1, dfn[uson] - 1, 1, n, 1));
//        }
//        if (dfn[uson] + siz[uson] <= n) {
//            ans = min(ans, query(dfn[uson] + siz[uson], n, 1, n, 1));
//        }
//        return ans;
//    }
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
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    build(1, n, 1);
//    int root;
//    cin >> root;
//    for (int i = 1, op, x, y, v; i <= m; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> root;
//        } else if (op == 2) {
//            cin >> x >> y >> v;
//            pathUpdate(x, y, v);
//        } else {
//            cin >> x;
//            cout << treeQuery(root, x) << "\n";
//        }
//    }
//    return 0;
//}