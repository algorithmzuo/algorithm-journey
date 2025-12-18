package class186;

// 暴力写挂，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4565
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1000001;
//const int MAXM = 10000001;
//const long long INF = 1LL << 50;
//int n, cntn;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int weight1[MAXN << 1];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN << 1];
//int to2[MAXN << 1];
//int weight2[MAXN << 1];
//int cnt2;
//
//long long dis1[MAXN];
//
//int sonCnt[MAXN];
//int heads[MAXN];
//int nexts[MAXM];
//int sons[MAXM];
//int weights[MAXM];
//int cnts;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//int root[MAXN];
//int ls[MAXM];
//int rs[MAXM];
//long long lmax[MAXM];
//long long rmax[MAXM];
//int cntt;
//
//int latest[MAXN];
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
//void addSon(int u, int v, int w) {
//    sonCnt[u]++;
//    nexts[++cnts] = heads[u];
//    sons[cnts] = v;
//    weights[cnts] = w;
//    heads[u] = cnts;
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
//void buildSon(int u, int fa) {
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa) {
//            addSon(u, v, weight1[e]);
//            buildSon(v, u);
//        }
//    }
//}
//
//void rebuildTree() {
//    buildSon(1, 0);
//    cntn = n;
//    cnt1 = 1;
//    for (int i = 1; i <= cntn; i++) {
//        head1[i] = 0;
//    }
//    for (int u = 1; u <= cntn; u++) {
//        if (sonCnt[u] <= 2) {
//            for (int e = heads[u]; e > 0; e = nexts[e]) {
//                int v = sons[e];
//                int w = weights[e];
//                addEdge1(u, v, w);
//                addEdge1(v, u, w);
//            }
//        } else {
//            int node1 = ++cntn;
//            int node2 = ++cntn;
//            addEdge1(u, node1, 0);
//            addEdge1(node1, u, 0);
//            addEdge1(u, node2, 0);
//            addEdge1(node2, u, 0);
//            bool add1 = true;
//            for (int e = heads[u]; e > 0; e = nexts[e]) {
//                int v = sons[e];
//                int w = weights[e];
//                if (add1) {
//                    addSon(node1, v, w);
//                } else {
//                    addSon(node2, v, w);
//                }
//                add1 = !add1;
//            }
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
//                    best = cur;
//                    edge = e;
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
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa && !vis[e >> 1]) {
//            dfs(v, u, dist + weight1[e], op);
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
//        dfs(v1, 0, 0, 0);
//        dfs(v2, 0, weight1[edge], 1);
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
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            compute(v, u, dist2 + weight2[e]);
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
//        addEdge2(u, v, w);
//        addEdge2(v, u, w);
//    }
//    for (int i = 1; i < MAXM; i++) {
//        lmax[i] = rmax[i] = -INF;
//    }
//    ans = -INF;
//    getDist(1, 0, 0);
//    rebuildTree();
//    solve(1);
//    compute(1, 0, 0);
//    cout << (ans >> 1) << '\n';
//    return 0;
//}