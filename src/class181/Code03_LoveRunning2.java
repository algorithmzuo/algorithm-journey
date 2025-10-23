package class181;

// 天天爱跑步，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1600
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//const int MAXT = MAXN * 50;
//const int MAXP = 20;
//int n, m;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dep[MAXN];
//int stjump[MAXN][MAXP];
//int rootl[MAXN];
//int rootr[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int sum[MAXT];
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
//    sum[i] = sum[ls[i]] + sum[rs[i]];
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = ++cntt;
//    }
//    if (l == r) {
//        sum[rt] += jobv;
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
//        sum[t1] += sum[t2];
//    } else {
//        int mid = (l + r) >> 1;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2]);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
//        up(t1);
//    }
//    return t1;
//}
//
//int query(int jobi, int l, int r, int i) {
//    if (jobi < l || jobi > r || i == 0) {
//        return 0;
//    }
//    if (l == r) {
//        return sum[i];
//    }
//    int mid = (l + r) >> 1;
//    if (jobi <= mid) {
//        return query(jobi, l, mid, ls[i]);
//    } else {
//        return query(jobi, mid + 1, r, rs[i]);
//    }
//}
//
//void calc(int u, int fa) {
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            calc(v, u);
//            rootl[u] = merge(1, n, rootl[u], rootl[v]);
//            rootr[u] = merge(-n, n, rootr[u], rootr[v]);
//        }
//    }
//    ans[u] = query(dep[u] + arr[u], 1, n, rootl[u]) + query(dep[u] - arr[u], -n, n, rootr[u]);
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
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    dfs(1, 0);
//    for (int i = 1, x, y; i <= m; i++) {
//        cin >> x >> y;
//        int lca = getLca(x, y);
//        int lcafa = stjump[lca][0];
//        rootl[x] = add(dep[x], 1, 1, n, rootl[x]);
//        rootl[lca] = add(dep[x], -1, 1, n, rootl[lca]);
//        rootr[y] = add(2 * dep[lca] - dep[x], 1, -n, n, rootr[y]);
//        rootr[lcafa] = add(2 * dep[lca] - dep[x], -1, -n, n, rootr[lcafa]);
//    }
//    calc(1, 0);
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] << ' ';
//    }
//    return 0;
//}