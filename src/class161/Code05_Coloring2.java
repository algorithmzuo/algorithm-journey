package class161;

// 染色，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2486
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//int arr[MAXN];
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//int sum[MAXN << 2];
//int lcolor[MAXN << 2];
//int rcolor[MAXN << 2];
//int change[MAXN << 2];
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dfn[MAXN];
//int seg[MAXN];
//int cntd = 0;
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
//    seg[cntd] = u;
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
//    if (rcolor[i << 1] == lcolor[(i << 1) | 1]) {
//    	sum[i]--;
//    }
//    lcolor[i] = lcolor[i << 1];
//    rcolor[i] = rcolor[(i << 1) | 1];
//}
//
//void lazy(int i, int v) {
//    sum[i] = 1;
//    lcolor[i] = v;
//    rcolor[i] = v;
//    change[i] = v;
//}
//
//void down(int i) {
//    if (change[i] != 0) {
//        lazy(i << 1, change[i]);
//        lazy((i << 1) | 1, change[i]);
//        change[i] = 0;
//    }
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        sum[i] = 1;
//        lcolor[i] = arr[seg[l]];
//        rcolor[i] = arr[seg[l]];
//    } else {
//        int mid = (l + r) / 2;
//        build(l, mid, i << 1);
//        build(mid + 1, r, (i << 1) | 1);
//        up(i);
//    }
//}
//
//void update(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        lazy(i, jobv);
//    } else {
//        down(i);
//        int mid = (l + r) / 2;
//        if (jobl <= mid) {
//            update(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            update(jobl, jobr, jobv, mid + 1, r, (i << 1) | 1);
//        }
//        up(i);
//    }
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    }
//    down(i);
//    int mid = (l + r) / 2;
//    if (jobr <= mid) {
//        return query(jobl, jobr, l, mid, i << 1);
//    } else if (jobl > mid) {
//        return query(jobl, jobr, mid + 1, r, (i << 1) | 1);
//    } else {
//        int ans = query(jobl, jobr, l, mid, i << 1)
//                + query(jobl, jobr, mid + 1, r, (i << 1) | 1);
//        if (rcolor[i << 1] == lcolor[(i << 1) | 1]) {
//            ans--;
//        }
//        return ans;
//    }
//}
//
//int pointColor(int jobi, int l, int r, int i) {
//    if (l == r) {
//        return lcolor[i];
//    }
//    down(i);
//    int mid = (l + r) / 2;
//    if (jobi <= mid) {
//        return pointColor(jobi, l, mid, i << 1);
//    } else {
//        return pointColor(jobi, mid + 1, r, (i << 1) | 1);
//    }
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
//int pathColors(int x, int y) {
//    int ans = 0, sonc, fac;
//    while (top[x] != top[y]) {
//        if (dep[top[x]] <= dep[top[y]]) {
//            ans += query(dfn[top[y]], dfn[y], 1, n, 1);
//            sonc = pointColor(dfn[top[y]], 1, n, 1);
//            fac = pointColor(dfn[fa[top[y]]], 1, n, 1);
//            y = fa[top[y]];
//        } else {
//            ans += query(dfn[top[x]], dfn[x], 1, n, 1);
//            sonc = pointColor(dfn[top[x]], 1, n, 1);
//            fac = pointColor(dfn[fa[top[x]]], 1, n, 1);
//            x = fa[top[x]];
//        }
//        if (sonc == fac) {
//            ans--;
//        }
//    }
//    ans += query(min(dfn[x], dfn[y]), max(dfn[x], dfn[y]), 1, n, 1);
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    build(1, n, 1);
//    string op;
//    int x, y;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        cin >> x >> y;
//        if (op == "C") {
//            int z;
//            cin >> z;
//            pathUpdate(x, y, z);
//        } else {
//            cout << pathColors(x, y) << "\n";
//        }
//    }
//    return 0;
//}