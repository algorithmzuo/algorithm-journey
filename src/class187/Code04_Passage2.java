package class187;

// 通道，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4220
// 测试链接 : https://loj.ac/p/2339
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 100001;
//const int MAXM = MAXN << 1;
//const int MAXH = 20;
//const int MAXT = MAXN * 20;
//int n, cntn;
//
//int head0[MAXN];
//int next0[MAXN << 1];
//int to0[MAXN << 1];
//ll weight0[MAXN << 1];
//int cnt0;
//
//int head1[MAXM];
//int next1[MAXM << 1];
//int to1[MAXM << 1];
//ll weight1[MAXM << 1];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN << 1];
//int to2[MAXN << 1];
//ll weight2[MAXN << 1];
//int cnt2;
//
//int head3[MAXN];
//int next3[MAXN << 1];
//int to3[MAXN << 1];
//ll weight3[MAXN << 1];
//int cnt3;
//
//int dfn[MAXN];
//int lg2[MAXN];
//int rmq[MAXN][MAXH];
//int cntd;
//
//ll dist2[MAXN];
//ll dist3[MAXN];
//
//bool vis[MAXM];
//int siz[MAXM];
//
//int up[MAXN];
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int lx[MAXT];
//int ly[MAXT];
//ll lxv[MAXT];
//ll lyv[MAXT];
//int rx[MAXT];
//int ry[MAXT];
//ll rxv[MAXT];
//ll ryv[MAXT];
//int cntt;
//
//ll ans;
//
//void addEdge0(int u, int v, ll w) {
//    next0[++cnt0] = head0[u];
//    to0[cnt0] = v;
//    weight0[cnt0] = w;
//    head0[u] = cnt0;
//}
//
//void addEdge1(int u, int v, ll w) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    weight1[cnt1] = w;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, ll w) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    head2[u] = cnt2;
//}
//
//void addEdge3(int u, int v, ll w) {
//    next3[++cnt3] = head3[u];
//    to3[cnt3] = v;
//    weight3[cnt3] = w;
//    head3[u] = cnt3;
//}
//
//int getUp(int x, int y) {
//    return dfn[x] <= dfn[y] ? x : y;
//}
//
//void dfsTree2(int u, int fa, ll dis2) {
//    dfn[u] = ++cntd;
//    rmq[dfn[u]][0] = fa;
//    dist2[u] = dis2;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dfsTree2(v, u, dis2 + weight2[e]);
//        }
//    }
//}
//
//void rmqTree2() {
//    dfsTree2(1, 0, 0);
//    for (int i = 2; i <= n; i++) {
//        lg2[i] = lg2[i >> 1] + 1;
//    }
//    for (int pre = 0, cur = 1; cur <= lg2[n]; pre++, cur++) {
//        for (int i = 1; i + (1 << cur) - 1 <= n; i++) {
//            rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
//        }
//    }
//}
//
//int lcaTree2(int x, int y) {
//    if (x == y) {
//        return x;
//    }
//    x = dfn[x];
//    y = dfn[y];
//    if (x > y) {
//        swap(x, y);
//    }
//    x++;
//    int k = lg2[y - x + 1];
//    return getUp(rmq[x][k], rmq[y - (1 << k) + 1][k]);
//}
//
//void rebuild(int u, int fa) {
//    int last = 0;
//    for (int e = head0[u]; e > 0; e = next0[e]) {
//        int v = to0[e];
//        ll w = weight0[e];
//        if (v != fa) {
//            if (last == 0) {
//                last = u;
//                addEdge1(u, v, w);
//                addEdge1(v, u, w);
//            } else {
//                int add = ++cntn;
//                addEdge1(last, add, 0);
//                addEdge1(add, last, 0);
//                addEdge1(add, v, w);
//                addEdge1(v, add, w);
//                last = add;
//            }
//            rebuild(v, u);
//        }
//    }
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa && !vis[e >> 1]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroidEdge(int u, int fa) {
//    getSize(u, fa);
//    int total = siz[u];
//    int edge = 0;
//    int best = total;
//    while (u > 0) {
//        int nextu = 0, nextfa = 0;
//        for (int e = head1[u]; e > 0; e = next1[e]) {
//            int v = to1[e];
//            if (v != fa && !vis[e >> 1]) {
//                int cur = max(total - siz[v], siz[v]);
//                if (cur < best) {
//                    edge = e;
//                    best = cur;
//                    nextfa = u;
//                    nextu = v;
//                }
//            }
//        }
//        fa = nextfa;
//        u = nextu;
//    }
//    return edge;
//}
//
//void dfsTree1(int u, int fa, ll dis1, int op) {
//    if (u <= n) {
//        if (up[u] == 0) {
//            up[u] = ++cntt;
//            root[u] = cntt;
//        }
//        int cur = up[u];
//        int nxt = ++cntt;
//        ll val = dis1 + dist3[u];
//        if (op == 0) {
//            ls[cur] = nxt;
//            lx[cur] = ly[cur] = u;
//            lxv[cur] = lyv[cur] = val;
//        } else {
//            rs[cur] = nxt;
//            rx[cur] = ry[cur] = u;
//            rxv[cur] = ryv[cur] = val;
//        }
//        up[u] = nxt;
//    }
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa && !vis[e >> 1]) {
//            dfsTree1(v, u, dis1 + weight1[e], op);
//        }
//    }
//}
//
//void solve(int u) {
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        int v1 = to1[edge];
//        int v2 = to1[edge ^ 1];
//        dfsTree1(v1, 0, 0, 0);
//        dfsTree1(v2, 0, weight1[edge], 1);
//        solve(v1);
//        solve(v2);
//    }
//}
//
//void distTree3(int u, int fa, ll dist) {
//    dist3[u] = dist;
//    for (int e = head3[u]; e > 0; e = next3[e]) {
//        int v = to3[e];
//        if (v != fa) {
//            distTree3(v, u, dist + weight3[e]);
//        }
//    }
//}
//
//ll getDist(int x, int y, ll xv, ll yv) {
//    if (x == y) {
//        return 0;
//    }
//    int l = lcaTree2(x, y);
//    return dist2[x] + dist2[y] - dist2[l] * 2 + xv + yv;
//}
//
//ll bestCross(int x1, int y1, ll x1v, ll y1v, int x2, int y2, ll x2v, ll y2v) {
//    ll p1 = getDist(x1, x2, x1v, x2v);
//    ll p2 = getDist(x1, y2, x1v, y2v);
//    ll p3 = getDist(y1, x2, y1v, x2v);
//    ll p4 = getDist(y1, y2, y1v, y2v);
//    return max(max(p1, p2), max(p3, p4));
//}
//
//void mergeInfo(int a, int b, int op) {
//    if (op == 0) {
//        if (lx[b] == 0) {
//            return;
//        }
//        if (lx[a] == 0) {
//            lx[a] = lx[b]; ly[a] = ly[b]; lxv[a] = lxv[b]; lyv[a] = lyv[b];
//            return;
//        }
//    } else {
//        if (rx[b] == 0) {
//            return;
//        }
//        if (rx[a] == 0) {
//            rx[a] = rx[b]; ry[a] = ry[b]; rxv[a] = rxv[b]; ryv[a] = ryv[b];
//            return;
//        }
//    }
//    int ax, ay, bx, by;
//    ll axv, ayv, bxv, byv;
//    if (op == 0) {
//        ax = lx[a]; ay = ly[a]; axv = lxv[a]; ayv = lyv[a];
//        bx = lx[b]; by = ly[b]; bxv = lxv[b]; byv = lyv[b];
//    } else {
//        ax = rx[a]; ay = ry[a]; axv = rxv[a]; ayv = ryv[a];
//        bx = rx[b]; by = ry[b]; bxv = rxv[b]; byv = ryv[b];
//    }
//    int cx = ax, cy = ay; ll cxv = axv, cyv = ayv;
//    ll best = getDist(ax, ay, axv, ayv);
//    ll pk = getDist(bx, by, bxv, byv);
//    if (pk > best) {
//        best = pk; cx = bx; cy = by; cxv = bxv; cyv = byv;
//    }
//    pk = getDist(ax, bx, axv, bxv);
//    if (pk > best) {
//        best = pk; cx = ax; cy = bx; cxv = axv; cyv = bxv;
//    }
//    pk = getDist(ax, by, axv, byv);
//    if (pk > best) {
//        best = pk; cx = ax; cy = by; cxv = axv; cyv = byv;
//    }
//    pk = getDist(ay, bx, ayv, bxv);
//    if (pk > best) {
//        best = pk; cx = ay; cy = bx; cxv = ayv; cyv = bxv;
//    }
//    pk = getDist(ay, by, ayv, byv);
//    if (pk > best) {
//        cx = ay; cy = by; cxv = ayv; cyv = byv;
//    }
//    if (op == 0) {
//        lx[a] = cx; ly[a] = cy; lxv[a] = cxv; lyv[a] = cyv;
//    } else {
//        rx[a] = cx; ry[a] = cy; rxv[a] = cxv; ryv[a] = cyv;
//    }
//}
//
//int mergeTree(int a, int b, ll add) {
//    if (a == 0 || b == 0) {
//        return a + b;
//    }
//    if (lx[a] > 0 && rx[b] > 0) {
//        ans = max(ans, bestCross(lx[a], ly[a], lxv[a], lyv[a], rx[b], ry[b], rxv[b], ryv[b]) + add);
//    }
//    if (rx[a] > 0 && lx[b] > 0) {
//        ans = max(ans, bestCross(rx[a], ry[a], rxv[a], ryv[a], lx[b], ly[b], lxv[b], lyv[b]) + add);
//    }
//    mergeInfo(a, b, 0);
//    mergeInfo(a, b, 1);
//    ls[a] = mergeTree(ls[a], ls[b], add);
//    rs[a] = mergeTree(rs[a], rs[b], add);
//    return a;
//}
//
//void compute(int u, int fa) {
//    for (int e = head3[u]; e > 0; e = next3[e]) {
//        int v = to3[e];
//        if (v != fa) {
//            compute(v, u);
//            root[u] = mergeTree(root[u], root[v], -dist3[u] * 2);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    int u, v;
//    ll w;
//    for (int i = 1; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge0(u, v, w);
//        addEdge0(v, u, w);
//    }
//    for (int i = 1; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge2(u, v, w);
//        addEdge2(v, u, w);
//    }
//    for (int i = 1; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge3(u, v, w);
//        addEdge3(v, u, w);
//    }
//    rmqTree2();
//    distTree3(1, 0, 0);
//    cntn = n;
//    cnt1 = 1;
//    rebuild(1, 0);
//    solve(1);
//    ans = -(1LL << 60);
//    compute(1, 0);
//    cout << ans << '\n';
//    return 0;
//}