package class177;

// 糖果公园，C++版
// 一共有n个公园，给定n-1条边，所有公园连成一棵树，c[i]为i号公园的糖果型号
// 一共有m种糖果，v[y]表示y号糖果的美味指数，给定长度为n的数组w，用于计算愉悦值
// 假设游客当前遇到了y号糖果，并且是第x次遇到，那么愉悦值会增加 v[y] * w[x]
// 随着游客遇到各种各样的糖果，愉悦值会不断上升，接下来有q条操作，操作类型如下
// 操作 0 x y : 第x号公园的糖果型号改成y
// 操作 1 x y : 游客从点x出发走过简单路径到达y，依次遇到每个公园的糖果，打印最终的愉悦值
// 1 <= n、m、q <= 10^5
// 1 <= v[i]、w[i] <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4074
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, t, lca, id;
//};
//
//struct Update {
//    int pos, val;
//};
//
//const int MAXN = 100001;
//const int MAXP = 20;
//int n, m, q;
//int v[MAXN];
//int w[MAXN];
//int c[MAXN];
//
//int head[MAXN];
//int to[MAXN << 1];
//int nxt[MAXN << 1];
//int cntg;
//
//Query query[MAXN];
//Update update[MAXN];
//int cntq, cntu;
//
//int dep[MAXN];
//int seg[MAXN << 1];
//int st[MAXN];
//int ed[MAXN];
//int stjump[MAXN][MAXP];
//int cntd;
//
//int bi[MAXN << 1];
//bool vis[MAXN];
//int cnt[MAXN];
//long long happy;
//long long ans[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
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
//    if (bi[a.r] != bi[b.r]) {
//        return bi[a.r] < bi[b.r];
//    }
//    return a.t < b.t;
//}
//
//void invert(int node) {
//    int candy = c[node];
//    if (vis[node]) {
//        happy -= 1LL * v[candy] * w[cnt[candy]--];
//    } else {
//        happy += 1LL * v[candy] * w[++cnt[candy]];
//    }
//    vis[node] = !vis[node];
//}
//
//void moveTime(int tim) {
//    int pos = update[tim].pos;
//    int oldVal = c[pos];
//    int newVal = update[tim].val;
//    if (vis[pos]) {
//        invert(pos);
//        c[pos] = newVal;
//        update[tim].val = oldVal;
//        invert(pos);
//    } else {
//        c[pos] = newVal;
//        update[tim].val = oldVal;
//    }
//}
//
//void compute() {
//    int winl = 1, winr = 0, wint = 0;
//    for (int i = 1; i <= cntq; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int jobt = query[i].t;
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
//        while (wint < jobt) {
//            moveTime(++wint);
//        }
//        while (wint > jobt) {
//            moveTime(wint--);
//        }
//        if (lca > 0) {
//            invert(lca);
//        }
//        ans[id] = happy;
//        if (lca > 0) {
//            invert(lca);
//        }
//    }
//}
//
//void prapare() {
//    int blen = max(1, (int)pow(cntd, 2.0 / 3.0));
//    for (int i = 1; i <= cntd; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + cntq + 1, QueryCmp);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    for (int i = 1; i <= m; i++) {
//        cin >> v[i];
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> w[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> c[i];
//    }
//    dfs(1, 0);
//    for (int i = 1, op, x, y; i <= q; i++) {
//        cin >> op >> x >> y;
//        if (op == 0) {
//            cntu++;
//            update[cntu].pos = x;
//            update[cntu].val = y;
//        } else {
//            if (st[x] > st[y]) {
//                swap(x, y);
//            }
//            int xylca = lca(x, y);
//            if (x == xylca) {
//                query[++cntq] = {st[x], st[y], cntu, 0, cntq};
//            } else {
//                query[++cntq] = {ed[x], st[y], cntu, xylca, cntq};
//            }
//        }
//    }
//    prapare();
//    compute();
//    for (int i = 1; i <= cntq; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}