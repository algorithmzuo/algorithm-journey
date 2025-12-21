package class186;

// 暴力写挂，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4565
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 366667;
//const int MAXM = MAXN << 1;
//const int MAXT = 10000001;
//const long long INF = 1LL << 50;
//
//int n, cntn;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int weight1[MAXN << 1];
//int cnt1;
//
//long long dis1[MAXN];
//
//int latest[MAXM];
//int head2[MAXM];
//int next2[MAXM << 1];
//int to2[MAXM << 1];
//int weight2[MAXM << 1];
//int cnt2;
//
//int head3[MAXN];
//int next3[MAXN << 1];
//int to3[MAXN << 1];
//int weight3[MAXN << 1];
//int cnt3;
//
//bool vis[MAXM];
//int siz[MAXM];
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//long long lmax[MAXT];
//long long rmax[MAXT];
//int cntt;
//
//long long ans;
//
//void addEdge1(int u, int v, int w) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    weight1[cnt1] = w;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, int w) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    head2[u] = cnt2;
//}
//
//void addEdge3(int u, int v, int w) {
//    next3[++cnt3] = head3[u];
//    to3[cnt3] = v;
//    weight3[cnt3] = w;
//    head3[u] = cnt3;
//}
//
//void getDist(int u, int fa, long long dist1) {
//    dis1[u] = dist1;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa) {
//            getDist(v, u, dist1 + weight1[e]);
//        }
//    }
//}
//
//void rebuild(int u, int fa) {
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        int w = weight1[e];
//        if (v != fa) {
//            if (latest[u] == 0) {
//                latest[u] = u;
//                addEdge2(u, v, w);
//                addEdge2(v, u, w);
//            } else {
//                int add = ++cntn;
//                addEdge2(latest[u], add, 0);
//                addEdge2(add, latest[u], 0);
//                addEdge2(add, v, w);
//                addEdge2(v, add, w);
//                latest[u] = add;
//            }
//            rebuild(v, u);
//        }
//    }
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
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
//        for (int e = head2[u]; e > 0; e = next2[e]) {
//            int v = to2[e];
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
//void dfs(int u, int fa, long long dist, int op) {
//    if (u <= n) {
//        if (latest[u] == 0) {
//            latest[u] = ++cntt;
//            root[u] = cntt;
//        }
//        int cur = latest[u];
//        int nxt = ++cntt;
//        if (op == 0) {
//            ls[cur] = nxt;
//            lmax[cur] = dis1[u] + dist;
//        } else {
//            rs[cur] = nxt;
//            rmax[cur] = dis1[u] + dist;
//        }
//        latest[u] = nxt;
//    }
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa && !vis[e >> 1]) {
//            dfs(v, u, dist + weight2[e], op);
//        }
//    }
//}
//
//void solve(int u) {
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        int v1 = to2[edge];
//        int v2 = to2[edge ^ 1];
//        dfs(v1, 0, 0, 0);
//        dfs(v2, 0, weight2[edge], 1);
//        solve(v1);
//        solve(v2);
//    }
//}
//
//int mergeTree(int x, int y, long long t) {
//    if (x == 0 || y == 0) {
//        return x + y;
//    }
//    ans = max(ans, max(lmax[x] + rmax[y], lmax[y] + rmax[x]) + t * 2);
//    lmax[x] = max(lmax[x], lmax[y]);
//    rmax[x] = max(rmax[x], rmax[y]);
//    ls[x] = mergeTree(ls[x], ls[y], t);
//    rs[x] = mergeTree(rs[x], rs[y], t);
//    return x;
//}
//
//void compute(int u, int fa, long long dist2) {
//    ans = max(ans, (dis1[u] - dist2) * 2);
//    for (int e = head3[u]; e > 0; e = next3[e]) {
//        int v = to3[e];
//        if (v != fa) {
//            compute(v, u, dist2 + weight3[e]);
//            root[u] = mergeTree(root[u], root[v], -dist2);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge1(u, v, w);
//        addEdge1(v, u, w);
//    }
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge3(u, v, w);
//        addEdge3(v, u, w);
//    }
//    for (int i = 1; i < MAXT; i++) {
//        lmax[i] = rmax[i] = -INF;
//    }
//    cntn = n;
//    cnt2 = 1;
//    ans = -INF;
//    getDist(1, 0, 0);
//    rebuild(1, 0);
//    for (int i = 1; i <= n; i++) {
//        latest[i] = 0;
//    }
//    solve(1);
//    compute(1, 0, 0);
//    cout << (ans >> 1) << '\n';
//    return 0;
//}