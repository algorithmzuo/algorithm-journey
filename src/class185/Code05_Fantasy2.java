package class185;

// 幻想乡战略游戏，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3345
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//
//int headg[MAXN];
//int nxtg[MAXN << 1];
//int tog[MAXN << 1];
//int weightg[MAXN << 1];
//int cntg;
//
//int headc[MAXN];
//int nxtc[MAXN << 1];
//int toc[MAXN << 1];
//int centc[MAXN << 1];
//int cntc;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dist[MAXN];
//
//bool vis[MAXN];
//int centfa[MAXN];
//
//long long num[MAXN];
//long long xsum[MAXN];
//long long fsum[MAXN];
//
//void addEdgeG(int u, int v, int w) {
//    nxtg[++cntg] = headg[u];
//    tog[cntg] = v;
//    weightg[cntg] = w;
//    headg[u] = cntg;
//}
//
//void addEdgeC(int u, int v, int cen) {
//    nxtc[++cntc] = headc[u];
//    toc[cntc] = v;
//    centc[cntc] = cen;
//    headc[u] = cntc;
//}
//
//void dfs1(int u, int f, int dis) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    dist[u] = dis;
//    siz[u] = 1;
//    for (int e = headg[u]; e; e = nxtg[e]) {
//        int v = tog[e];
//        int w = weightg[e];
//        if (v != f) {
//            dfs1(v, u, dis + w);
//        }
//    }
//    for (int ei = headg[u], v; ei; ei = nxtg[ei]) {
//        v = tog[ei];
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
//    for (int e = headg[u], v; e; e = nxtg[e]) {
//        v = tog[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void getSize(int u, int f) {
//    siz[u] = 1;
//    for (int e = headg[u]; e; e = nxtg[e]) {
//        int v = tog[e];
//        if (v != f && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
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
//    return dist[x] + dist[y] - (dist[getLca(x, y)] << 1);
//}
//
//int getCentroid(int u, int f) {
//    getSize(u, f);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = headg[u]; e; e = nxtg[e]) {
//            int v = tog[e];
//            if (v != f && !vis[v] && siz[v] > half) {
//                f = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void centroidTree(int u, int f) {
//    centfa[u] = f;
//    vis[u] = true;
//    for (int e = headg[u]; e; e = nxtg[e]) {
//        int v = tog[e];
//        if (!vis[v]) {
//            int nextCent = getCentroid(v, u);
//            addEdgeC(u, v, nextCent);
//            centroidTree(nextCent, u);
//        }
//    }
//}
//
//void add(int x, int v) {
//    num[x] += v;
//    for (int cur = x, f = centfa[cur]; f > 0; cur = f, f = centfa[cur]) {
//        int dist = getDist(x, f);
//        num[f] += v;
//        xsum[f] += 1LL * v * dist;
//        fsum[cur] += 1LL * v * dist;
//    }
//}
//
//long long query(int x) {
//    long long ans = xsum[x];
//    for (int cur = x, f = centfa[cur]; f > 0; cur = f, f = centfa[cur]) {
//        int dist = getDist(x, f);
//        ans += (num[f] - num[cur]) * dist;
//        ans += (xsum[f] - fsum[cur]);
//    }
//    return ans;
//}
//
//long long compute(int u) {
//    long long ans = query(u);
//    for (int e = headc[u]; e; e = nxtc[e]) {
//        int v = toc[e];
//        if (query(v) < ans) {
//            return compute(centc[e]);
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdgeG(u, v, w);
//        addEdgeG(v, u, w);
//    }
//    dfs1(1, 0, 0);
//    dfs2(1, 1);
//    int root = getCentroid(1, 0);
//    centroidTree(root, 0);
//    for (int i = 1, x, v; i <= m; i++) {
//        cin >> x >> v;
//        add(x, v);
//        cout << compute(root) << '\n';
//    }
//    return 0;
//}