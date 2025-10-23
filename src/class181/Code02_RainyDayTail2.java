package class181;

// 雨天的尾巴，C++版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树
// 给定m条路径，格式 x y v，表示x到y路径上的每个点都收到一个数字v
// 打印第i号点上，收到次数最多的数字，如果不止一种，打印值最小的数字，一共n条打印
// 1 <= n、m、v <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4556
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXV = 100000;
//const int MAXT = MAXN * 50;
//const int MAXP = 20;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dep[MAXN];
//int stjump[MAXN][MAXP];
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int maxCnt[MAXT];
//int cntt;
//
//int ans[MAXN];
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
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
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
//void up(int i) {
//    maxCnt[i] = max(maxCnt[ls[i]], maxCnt[rs[i]]);
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = ++cntt;
//    }
//    if (l == r) {
//        maxCnt[rt] += jobv;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = add(jobi, jobv, l, mid, ls[rt]);
//        } else {
//            rs[rt] = add(jobi, jobv, mid + 1, r, rs[rt]);
//        }
//        up(rt);
//    }
//    return rt;
//}
//
//int merge(int l, int r, int t1, int t2) {
//    if (t1 == 0 || t2 == 0) {
//        return t1 + t2;
//    }
//    if (l == r) {
//        maxCnt[t1] += maxCnt[t2];
//    } else {
//        int mid = (l + r) >> 1;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2]);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
//        up(t1);
//    }
//    return t1;
//}
//
//int query(int l, int r, int i) {
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) >> 1;
//    if (maxCnt[i] == maxCnt[ls[i]]) {
//        return query(l, mid, ls[i]);
//    } else {
//        return query(mid + 1, r, rs[i]);
//    }
//}
//
//void calc(int u, int fa) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            calc(v, u);
//            root[u] = merge(1, MAXV, root[u], root[v]);
//        }
//    }
//    if (root[u] && maxCnt[root[u]] > 0) {
//        ans[u] = query(1, MAXV, root[u]);
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
//    dfs(1, 0);
//    for (int i = 1, x, y, v; i <= m; i++) {
//        cin >> x >> y >> v;
//        int lca = getLca(x, y);
//        int lcafa = stjump[lca][0];
//        root[x] = add(v, 1, 1, MAXV, root[x]);
//        root[y] = add(v, 1, 1, MAXV, root[y]);
//        root[lca] = add(v, -1, 1, MAXV, root[lca]);
//        root[lcafa] = add(v, -1, 1, MAXV, root[lcafa]);
//    }
//    calc(1, 0);
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}