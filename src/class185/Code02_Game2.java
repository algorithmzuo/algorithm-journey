package class185;

// 烁烁的游戏，C++版
// 树上有n个点，每个点的初始点权是0，给定n-1条边，边权都是1
// 接下来有m条操作，每条操作是如下两种类型中的一种
// 操作 M x k v : 点x出发，距离<=k的所有点，点权都加上v
// 操作 Q x     : 打印点x的点权
// 1 <= n、m <= 10^5
// -10000 <= v <= +10000
// 测试链接 : https://www.luogu.com.cn/problem/P10603
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXH = 18;
//const int MAXT = 20000001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dep[MAXN];
//int stjump[MAXN][MAXH];
//
//bool vis[MAXN];
//int siz[MAXN];
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
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        swap(a, b);
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
//void add(int x, int k, int v) {
//    addTree[x] = add(k, v, 0, n - 1, addTree[x]);
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        if (k - dist >= 0) {
//            addTree[fa] = add(k - dist, v, 0, n - 1, addTree[fa]);
//            minusTree[cur] = add(k - dist, v, 0, n - 1, minusTree[cur]);
//        }
//    }
//}
//
//int query(int x) {
//    int ans = query(0, n - 1, 0, n - 1, addTree[x]);
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        ans += query(dist, n - 1, 0, n - 1, addTree[fa]);
//        ans -= query(dist, n - 1, 0, n - 1, minusTree[cur]);
//    }
//    return ans;
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
//    dfs(1, 0);
//    centroidTree(getCentroid(1, 0), 0);
//    char op;
//    int x, k, v;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == 'M') {
//            cin >> x >> k >> v;
//            add(x, k, v);
//        } else {
//            cin >> x;
//            cout << query(x) << '\n';
//        }
//    }
//    return 0;
//}