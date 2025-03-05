package class162;

// 月下毛景树，C++版
// 一共有n个节点，节点编号从1到n，所有节点连成一棵树
// 给定n-1条边，边的编号从1到n-1，每条边给定初始边权
// 会进行若干次操作，每条操作的类型是如下4种类型中的一种
// 操作 Change x v  : 第x条边的边权改成v
// 操作 Cover x y v : x号点到y号点的路径上，所有边权改成v
// 操作 Add x y v   : x号点到y号点的路径上，所有边权增加v
// 操作 Max x y     : x号点到y号点的路径上，打印最大的边权
// 1 <= n <= 10^5
// 任何时候的边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4315
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n;
//int arr[MAXN][3];
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
//int maxv[MAXN << 2];
//int addTag[MAXN << 2];
//bool updateTag[MAXN << 2];
//int change[MAXN << 2];
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
//    maxv[i] = max(maxv[i << 1], maxv[i << 1 | 1]);
//}
//
//void addLazy(int i, int v) {
//    maxv[i] += v;
//    addTag[i] += v;
//}
//
//void updateLazy(int i, int v) {
//    maxv[i] = v;
//    addTag[i] = 0;
//    updateTag[i] = true;
//    change[i] = v;
//}
//
//void down(int i) {
//    if (updateTag[i]) {
//        updateLazy(i << 1, change[i]);
//        updateLazy(i << 1 | 1, change[i]);
//        updateTag[i] = false;
//    }
//    if (addTag[i] != 0) {
//        addLazy(i << 1, addTag[i]);
//        addLazy(i << 1 | 1, addTag[i]);
//        addTag[i] = 0;
//    }
//}
//
//void update(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        updateLazy(i, jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        down(i);
//        if (jobl <= mid) {
//            update(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//void add(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addLazy(i, jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        down(i);
//        if (jobl <= mid) {
//        	add(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//        	add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return maxv[i];
//    }
//    int mid = (l + r) >> 1;
//    down(i);
//    int ans = INT_MIN;
//    if (jobl <= mid) {
//        ans = max(ans, query(jobl, jobr, l, mid, i << 1));
//    }
//    if (jobr > mid) {
//        ans = max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
//    }
//    return ans;
//}
//
//void edgeUpdate(int ei, int val) {
//    int x = arr[ei][0];
//    int y = arr[ei][1];
//    int downv = max(dfn[x], dfn[y]);
//    update(downv, downv, val, 1, n, 1);
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
//    update(min(dfn[x], dfn[y]) + 1, max(dfn[x], dfn[y]), v, 1, n, 1);
//}
//
//void pathAdd(int x, int y, int v) {
//    while (top[x] != top[y]) {
//        if (dep[top[x]] <= dep[top[y]]) {
//        	add(dfn[top[y]], dfn[y], v, 1, n, 1);
//            y = fa[top[y]];
//        } else {
//        	add(dfn[top[x]], dfn[x], v, 1, n, 1);
//            x = fa[top[x]];
//        }
//    }
//    add(min(dfn[x], dfn[y]) + 1, max(dfn[x], dfn[y]), v, 1, n, 1);
//}
//
//int pathMax(int x, int y) {
//    int ans = INT_MIN;
//    while (top[x] != top[y]) {
//        if (dep[top[x]] <= dep[top[y]]) {
//            ans = max(ans, query(dfn[top[y]], dfn[y], 1, n, 1));
//            y = fa[top[y]];
//        } else {
//            ans = max(ans, query(dfn[top[x]], dfn[x], 1, n, 1));
//            x = fa[top[x]];
//        }
//    }
//    ans = max(ans, query(min(dfn[x], dfn[y]) + 1, max(dfn[x], dfn[y]), 1, n, 1));
//    return ans;
//}
//
//void prepare() {
//    for (int i = 1; i < n; i++) {
//        addEdge(arr[i][0], arr[i][1]);
//        addEdge(arr[i][1], arr[i][0]);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    for (int i = 1; i < n; i++) {
//        edgeUpdate(i, arr[i][2]);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i < n; i++) {
//        cin >> arr[i][0] >> arr[i][1] >> arr[i][2];
//    }
//    prepare();
//    string op;
//    cin >> op;
//    int x, y, v;
//    while (op != "Stop") {
//        if (op == "Change") {
//            cin >> x >> v;
//            edgeUpdate(x, v);
//        } else if (op == "Cover") {
//            cin >> x >> y >> v;
//            pathUpdate(x, y, v);
//        } else if (op == "Add") {
//            cin >> x >> y >> v;
//            pathAdd(x, y, v);
//        } else {
//            cin >> x >> y;
//            cout << pathMax(x, y) << "\n";
//        }
//        cin >> op;
//    }
//    return 0;
//}