package class187;

// 通道，C++版
// 三棵树t1、t2、t3都有n个节点，各自给定n-1条边以及每条边的边权，边权没有负数
// 点对(x, y)，要求两点必须不同，两点在树上的距离就是简单路径上边权的累加和
// 点对(x, y)，在t1、t2、t3的距离，分别记为dis1(x, y)、dis2(x, y)、dis3(x, y)
// 打印最大的dis1(x, y) + dis2(x, y) + dis3(x, y)
// 2 <= n <= 10^5
// 0 <= 边权 <= 10^12
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
//const int MAXP = 20;
//const int MAXT = MAXN * 20;
//const ll INF = 1LL << 60;
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
//ll dist2[MAXN];
//ll dist3[MAXN];
//
//bool vis[MAXM];
//int siz[MAXM];
//
//int dfn[MAXN];
//int lg2[MAXN];
//int rmq[MAXN][MAXP];
//int cntd;
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
//void dfsTree2(int u, int fa, ll dist) {
//    dist2[u] = dist;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dfsTree2(v, u, dist + weight2[e]);
//        }
//    }
//}
//
//void rebuildTree1(int u, int fa) {
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
//            rebuildTree1(v, u);
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
//    int half = total >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head1[u]; e > 0; e = next1[e]) {
//            int v = to1[e];
//            if (v != fa && !vis[e >> 1] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    int best = 0, edge = 0;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        if (!vis[e >> 1]) {
//            int v = to1[e];
//            int sub = (v == fa ? (total - siz[u]) : siz[v]);
//            if (sub > best) {
//                best = sub;
//                edge = e;
//            }
//        }
//    }
//    return edge;
//}
//
//void dfsTree1(int u, int fa, ll dist, int op) {
//    if (u <= n) {
//        if (up[u] == 0) {
//            up[u] = ++cntt;
//            root[u] = cntt;
//        }
//        int cur = up[u];
//        int nxt = ++cntt;
//        ll val = dist + dist2[u];
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
//            dfsTree1(v, u, dist + weight1[e], op);
//        }
//    }
//}
//
//void solveTree1(int u) {
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        int v1 = to1[edge];
//        int v2 = to1[edge ^ 1];
//        dfsTree1(v1, 0, 0, 0);
//        dfsTree1(v2, 0, weight1[edge], 1);
//        solveTree1(v1);
//        solveTree1(v2);
//    }
//}
//
//int getUp(int x, int y) {
//    return dfn[x] < dfn[y] ? x : y;
//}
//
//void dfsTree3(int u, int fa, ll dist) {
//    dfn[u] = ++cntd;
//    rmq[dfn[u]][0] = fa;
//    dist3[u] = dist;
//    for (int e = head3[u]; e > 0; e = next3[e]) {
//        int v = to3[e];
//        if (v != fa) {
//            dfsTree3(v, u, dist + weight3[e]);
//        }
//    }
//}
//
//void rmqTree3() {
//    dfsTree3(1, 0, 0);
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
//int lcaTree3(int x, int y) {
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
//ll getDist3(int x, int y) {
//    int l = lcaTree3(x, y);
//    return dist3[x] + dist3[y] - dist3[l] * 2;
//}
//
//ll getDist(int x, int y, ll xv, ll yv) {
//    if (x == y) {
//        return 0;
//    }
//    return getDist3(x, y) + xv + yv;
//}
//
//int a, b, c, d;
//ll av, bv, cv, dv;
//
//void getInfo(int i, int j, int iop, int jop) {
//    if (iop == 0) {
//        a = lx[i]; b = ly[i]; av = lxv[i]; bv = lyv[i];
//    } else {
//        a = rx[i]; b = ry[i]; av = rxv[i]; bv = ryv[i];
//    }
//    if (jop == 0) {
//        c = lx[j]; d = ly[j]; cv = lxv[j]; dv = lyv[j];
//    } else {
//        c = rx[j]; d = ry[j]; cv = rxv[j]; dv = ryv[j];
//    }
//}
//
//ll bestCross(int i, int j, int iop, int jop) {
//    getInfo(i, j, iop, jop);
//    ll p1 = getDist(a, c, av, cv);
//    ll p2 = getDist(a, d, av, dv);
//    ll p3 = getDist(b, c, bv, cv);
//    ll p4 = getDist(b, d, bv, dv);
//    return max(max(p1, p2), max(p3, p4));
//}
//
//int x, y;
//ll xv, yv;
//ll bestDist;
//
//void better(int curx, int cury, ll curxv, ll curyv) {
//    if (curx == 0 || cury == 0) {
//        return;
//    }
//    ll curDist = getDist(curx, cury, curxv, curyv);
//    if (curDist > bestDist) {
//        x = curx; y = cury; xv = curxv; yv = curyv;
//        bestDist = curDist;
//    }
//}
//
//void mergeInfo(int i, int j, int op) {
//    getInfo(i, j, op, op);
//    x = y = 0;
//    xv = yv = 0;
//    bestDist = -INF;
//    better(a, b, av, bv);
//    better(c, d, cv, dv);
//    better(a, c, av, cv);
//    better(a, d, av, dv);
//    better(b, c, bv, cv);
//    better(b, d, bv, dv);
//    if (op == 0) {
//        lx[i] = x; ly[i] = y; lxv[i] = xv; lyv[i] = yv;
//    } else {
//        rx[i] = x; ry[i] = y; rxv[i] = xv; ryv[i] = yv;
//    }
//}
//
//int mergeTree(int i, int j, ll add) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    if (ls[i] > 0 && rs[j] > 0) {
//        ans = max(ans, bestCross(i, j, 0, 1) + add);
//    }
//    if (rs[i] > 0 && ls[j] > 0) {
//        ans = max(ans, bestCross(i, j, 1, 0) + add);
//    }
//    mergeInfo(i, j, 0);
//    mergeInfo(i, j, 1);
//    ls[i] = mergeTree(ls[i], ls[j], add);
//    rs[i] = mergeTree(rs[i], rs[j], add);
//    return i;
//}
//
//void compute(int u, int fa) {
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            compute(v, u);
//            root[u] = mergeTree(root[u], root[v], -dist2[u] * 2);
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
//    cntn = n;
//    cnt1 = 1;
//    ans = -INF;
//    dfsTree2(1, 0, 0);
//    rebuildTree1(1, 0);
//    solveTree1(1);
//    rmqTree3();
//    compute(1, 0);
//    cout << ans << '\n';
//    return 0;
//}