package class202;

// 树点涂色，C++版
// 一共n个节点，所有节点组成一棵树，规定1号节点是根
// 初始所有节点的颜色都不同，路径中的颜色数量叫做路径权值
// 接下来有m条操作，操作有三种类型，具体格式如下
// 操作 1 x   : 根到点x的路径上，所有点染上新的颜色
// 操作 2 x y : 查询点x到点y的路径权值
// 操作 3 x   : x为头的子树中选择一个点，希望该点到根节点的路径权值最大，打印这个值
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3703
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
//int cntg;
//
//int pa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dfn[MAXN];
//int seg[MAXN];
//int cntd;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int mostl[MAXN];
//
//int maxCnt[MAXN << 2];
//int addTag[MAXN << 2];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    pa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e != 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//            siz[u] += siz[v];
//            if (siz[v] > siz[son[u]]) {
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
//    if (son[u] != 0) {
//        dfs2(son[u], t);
//    }
//    for (int e = head[u], v; e != 0; e = nxt[e]) {
//        v = to[e];
//        if (v != pa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//int lca(int x, int y) {
//    while (top[x] != top[y]) {
//        if (dep[top[x]] < dep[top[y]]) {
//            y = pa[top[y]];
//        } else {
//            x = pa[top[x]];
//        }
//    }
//    return dep[x] < dep[y] ? x : y;
//}
//
//void up(int x) {
//    mostl[x] = ls[x] == 0 ? x : mostl[ls[x]];
//}
//
//bool isroot(int x) {
//    return ls[fa[x]] != x && rs[fa[x]] != x;
//}
//
//int lr(int x) {
//    return ls[fa[x]] == x ? 0 : 1;
//}
//
//void rotate(int x) {
//    int f = fa[x], g = fa[f];
//    if (lr(x) == 0) {
//        ls[f] = rs[x];
//        if (ls[f] != 0) {
//            fa[ls[f]] = f;
//        }
//        rs[x] = f;
//    } else {
//        rs[f] = ls[x];
//        if (rs[f] != 0) {
//            fa[rs[f]] = f;
//        }
//        ls[x] = f;
//    }
//    if (!isroot(f)) {
//        if (lr(f) == 0) {
//            ls[g] = x;
//        } else {
//            rs[g] = x;
//        }
//    }
//    fa[f] = x;
//    fa[x] = g;
//    up(f);
//    up(x);
//}
//
//void splay(int x) {
//    while (!isroot(x)) {
//        int f = fa[x];
//        if (!isroot(f)) {
//            if (lr(x) == lr(f)) {
//                rotate(f);
//            } else {
//                rotate(x);
//            }
//        }
//        rotate(x);
//    }
//}
//
//void upSeg(int i) {
//    maxCnt[i] = max(maxCnt[i << 1], maxCnt[i << 1 | 1]);
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        maxCnt[i] = dep[seg[l]];
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        upSeg(i);
//    }
//}
//
//void lazy(int i, int v) {
//    maxCnt[i] += v;
//    addTag[i] += v;
//}
//
//void down(int i) {
//    if (addTag[i] != 0) {
//        lazy(i << 1, addTag[i]);
//        lazy(i << 1 | 1, addTag[i]);
//        addTag[i] = 0;
//    }
//}
//
//void add(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        lazy(i, jobv);
//    } else {
//        down(i);
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        upSeg(i);
//    }
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return maxCnt[i];
//    }
//    down(i);
//    int mid = (l + r) >> 1;
//    int ans = 0;
//    if (jobl <= mid) {
//        ans = max(ans, query(jobl, jobr, l, mid, i << 1));
//    }
//    if (jobr > mid) {
//        ans = max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
//    }
//    return ans;
//}
//
//void add(int x, int v) {
//    add(dfn[x], dfn[x] + siz[x] - 1, v, 1, n, 1);
//}
//
//void access(int x) {
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        if (rs[x] != 0) {
//            add(mostl[rs[x]], 1);
//        }
//        if (y != 0) {
//            add(mostl[y], -1);
//        }
//        rs[x] = y;
//        up(x);
//    }
//}
//
//int pathQuery(int x, int y) {
//    int xylca = lca(x, y);
//    int a = query(dfn[x], dfn[x], 1, n, 1);
//    int b = query(dfn[y], dfn[y], 1, n, 1);
//    int c = query(dfn[xylca], dfn[xylca], 1, n, 1);
//    return a + b - (2 * c) + 1;
//}
//
//int subtreeQuery(int x) {
//    return query(dfn[x], dfn[x] + siz[x] - 1, 1, n, 1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    n = 0;
//    m = 0;
//    cin >> n >> m;
//    for (int i = 1, x, y; i < n; i++) {
//        cin >> x >> y;
//        addEdge(x, y);
//        addEdge(y, x);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    build(1, n, 1);
//    for (int i = 1; i <= n; i++) {
//        fa[i] = pa[i];
//        mostl[i] = i;
//    }
//    for (int i = 1, op, x, y; i <= m; i++) {
//        cin >> op;
//        cin >> x;
//        if (op == 1) {
//            access(x);
//        } else if (op == 2) {
//            cin >> y;
//            cout << pathQuery(x, y) << "\n";
//        } else {
//            cout << subtreeQuery(x) << "\n";
//        }
//    }
//    return 0;
//}