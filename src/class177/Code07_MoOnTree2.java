package class177;

// 树上莫队入门题，C++版
// 一共有n个节点，每个节点给定颜色值，给定n-1条边，所有节点连成一棵树
// 一共有m条查询，格式 u v : 打印点u到点v的简单路径上，有几种不同的颜色
// 1 <= n <= 4 * 10^4
// 1 <= m <= 10^5
// 1 <= 颜色值 <= 2 * 10^9
// 测试链接 : https://www.luogu.com.cn/problem/SP10707
// 测试链接 : https://www.spoj.com/problems/COT2/
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, lca, id;
//};
//
//const int MAXN = 40001;
//const int MAXM = 100001;
//const int MAXP = 20;
//
//int n, m;
//int color[MAXN];
//int sorted[MAXN];
//int cntv;
//
//Query query[MAXM];
//
//int head[MAXN];
//int to[MAXN << 1];
//int nxt[MAXN << 1];
//int cntg;
//
//int dep[MAXN];
//int seg[MAXN << 1];
//int st[MAXN];
//int ed[MAXN];
//int stjump[MAXN][MAXP];
//int cntd;
//
//int bi[MAXN << 1];
//
//bool vis[MAXN];
//int cnt[MAXN];
//int kind;
//
//int ans[MAXM];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int kth(int num) {
//    int left = 1, right = cntv, mid, ret = 0;
//    while (left <= right) {
//        mid = (left + right) >> 1;
//        if (sorted[mid] <= num) {
//            ret = mid;
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return ret;
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    seg[++cntd] = u;
//    st[u] = cntd;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//    seg[++cntd] = u;
//    ed[u] = cntd;
//}
//
//int lca(int a, int b) {
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
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//void invert(int node) {
//    int val = color[node];
//    if (vis[node]) {
//        if (--cnt[val] == 0) {
//            kind--;
//        }
//    } else {
//        if (++cnt[val] == 1) {
//            kind++;
//        }
//    }
//    vis[node] = !vis[node];
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int lca = query[i].lca;
//        int id = query[i].id;
//        while (winl > jobl) {
//            invert(seg[--winl]);
//        }
//        while (winr < jobr) {
//            invert(seg[++winr]);
//        }
//        while (winl < jobl) {
//            invert(seg[winl++]);
//        }
//        while (winr > jobr) {
//            invert(seg[winr--]);
//        }
//        if (lca > 0) {
//            invert(lca);
//        }
//        ans[id] = kind;
//        if (lca > 0) {
//            invert(lca);
//        }
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = color[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    cntv = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[cntv] != sorted[i]) {
//            sorted[++cntv] = sorted[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        color[i] = kth(color[i]);
//    }
//    int blen = (int) sqrt(cntd);
//    for (int i = 1; i <= cntd; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    for (int i = 1, u, v, uvlca; i <= m; i++) {
//        cin >> u >> v;
//        if (st[v] < st[u]) {
//            swap(u, v);
//        }
//        uvlca = lca(u, v);
//        if (u == uvlca) {
//            query[i].l = st[u];
//            query[i].r = st[v];
//            query[i].lca = 0;
//        } else {
//            query[i].l = ed[u];
//            query[i].r = st[v];
//            query[i].lca = uvlca;
//        }
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}