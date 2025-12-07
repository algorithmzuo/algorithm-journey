package class185;

// 震波，C++版
// 树上有n个点，每个点有点权，给定n-1条边，边权都是1
// 接下来有m条操作，每条操作是如下两种类型中的一种
// 操作 0 x y : 点x出发，距离<=y的所有点，打印点权累加和
// 操作 1 x y : 点x的点权变成y
// 1 <= n、m <= 10^5
// 1 <= 点权 <= 10^4
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P6329
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = 10000001;
//int n, m;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//
//bool vis[MAXN];
//int centfa[MAXN];
//
//int addTree[MAXN];
//int minusTree[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int sum[MAXT];
//int cntt;
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
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int ei = head[u], v; ei; ei = nxt[ei]) {
//        v = to[ei];
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
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    while (top[a] != top[b]) {
//        if (dep[top[a]] <= dep[top[b]]) {
//            b = fa[top[b]];
//        } else {
//            a = fa[top[a]];
//        }
//    }
//    return dep[a] <= dep[b] ? a : b;
//}
//
//int getDist(int x, int y) {
//    return dep[x] + dep[y] - (dep[getLca(x, y)] << 1);
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int fa) {
//    getSize(u, fa);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void centroidTree(int u, int fa) {
//    centfa[u] = fa;
//    vis[u] = true;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            centroidTree(getCentroid(v, u), u);
//        }
//    }
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    if (i == 0) {
//        i = ++cntt;
//    }
//    if (l == r) {
//        sum[i] += jobv;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[i] = add(jobi, jobv, l, mid, ls[i]);
//        } else {
//            rs[i] = add(jobi, jobv, mid + 1, r, rs[i]);
//        }
//        sum[i] = sum[ls[i]] + sum[rs[i]];
//    }
//    return i;
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    }
//    int mid = (l + r) >> 1;
//    int ans = 0;
//    if (jobl <= mid) {
//        ans += query(jobl, jobr, l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//        ans += query(jobl, jobr, mid + 1, r, rs[i]);
//    }
//    return ans;
//}
//
//void add(int x, int v) {
//    addTree[x] = add(0, v, 0, n - 1, addTree[x]);
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        addTree[fa] = add(dist, v, 0, n - 1, addTree[fa]);
//        minusTree[cur] = add(dist, v, 0, n - 1, minusTree[cur]);
//    }
//}
//
//int query(int x, int k) {
//    int ans = query(0, k, 0, n - 1, addTree[x]);
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        if (k - dist >= 0) {
//            ans += query(0, k - dist, 0, n - 1, addTree[fa]);
//            ans -= query(0, k - dist, 0, n - 1, minusTree[cur]);
//        }
//    }
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
//    centroidTree(getCentroid(1, 0), 0);
//    for (int i = 1; i <= n; i++) {
//        add(i, arr[i]);
//    }
//    int lastAns = 0;
//    for (int i = 1, op, x, y; i <= m; i++) {
//        cin >> op >> x >> y;
//        x ^= lastAns;
//        y ^= lastAns;
//        if (op == 0) {
//            lastAns = query(x, y);
//            cout << lastAns << '\n';
//        } else {
//            add(x, y - arr[x]);
//            arr[x] = y;
//        }
//    }
//    return 0;
//}