package class183;

// 点分治，也叫重心分治，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3806
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXV = 20000001;
//int n, m, total;
//int query[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//bool vis[MAXN];
//int siz[MAXN];
//int maxp[MAXN];
//int centroid;
//
//int dis[MAXN];
//int valArr[MAXV];
//int cntv;
//int que[MAXV];
//int cntq;
//bool judge[MAXV];
//
//bool ans[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void getCentroid(int u, int fa) {
//    siz[u] = 1;
//    maxp[u] = 0;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getCentroid(v, u);
//            siz[u] += siz[v];
//            maxp[u] = max(maxp[u], siz[v]);
//        }
//    }
//    maxp[u] = max(maxp[u], total - siz[u]);
//    if (maxp[u] < maxp[centroid]) {
//        centroid = u;
//    }
//}
//
//void getDistance(int u, int fa, int w) {
//    dis[u] = dis[fa] + w;
//    valArr[++cntv] = dis[u];
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getDistance(v, u, weight[e]);
//        }
//    }
//}
//
//void calc(int u) {
//    dis[u] = 0;
//    cntq = 0;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (!vis[v]) {
//            cntv = 0;
//            getDistance(v, u, w);
//            for (int k = cntv; k > 0; k--) {
//                for (int l = 1; l <= m; l++) {
//                    if (query[l] >= valArr[k]) {
//                        ans[l] |= judge[query[l] - valArr[k]];
//                    }
//                }
//            }
//            for (int k = cntv; k > 0; k--) {
//                que[++cntq] = valArr[k];
//                judge[valArr[k]] = true;
//            }
//        }
//    }
//    for (int i = cntq; i > 0; i--) {
//        judge[que[i]] = false;
//    }
//}
//
//void solve(int u) {
//    vis[u] = true;
//    judge[0] = true;
//    calc(u);
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            total = siz[v];
//            centroid = 0;
//            maxp[centroid] = n;
//            getCentroid(v, u);
//            solve(centroid);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> query[i];
//    }
//    total = n;
//    centroid = 0;
//    maxp[centroid] = n;
//    getCentroid(1, 0);
//    solve(centroid);
//    for (int i = 1; i <= m; i++) {
//        if (ans[i]) {
//            cout << "AYE" << '\n';
//        } else {
//            cout << "NAY" << '\n';
//        }
//    }
//    return 0;
//}