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
//const int MAXV = 10000001;
//int n, m, maxq, total;
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
//int maxPart[MAXN];
//int centroid;
//
//int dis[MAXN];
//int arr[MAXV];
//int que[MAXV];
//bool pre[MAXV];
//int cnta;
//int cntq;
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
//    maxPart[u] = 0;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getCentroid(v, u);
//            siz[u] += siz[v];
//            maxPart[u] = max(maxPart[u], siz[v]);
//        }
//    }
//    maxPart[u] = max(maxPart[u], total - siz[u]);
//    if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
//        centroid = u;
//    }
//}
//
//void getDistance(int u, int fa, int w) {
//    dis[u] = dis[fa] + w;
//    if (dis[u] > maxq) {
//        return;
//    }
//    arr[++cnta] = dis[u];
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
//            cnta = 0;
//            getDistance(v, u, w);
//            for (int i = 1; i <= m; i++) {
//                for (int j = 1; j <= cnta; j++) {
//                    if (query[i] - arr[j] >= 0) {
//                        ans[i] |= pre[query[i] - arr[j]];
//                    }
//                }
//            }
//            for (int i = 1; i <= cnta; i++) {
//                que[++cntq] = arr[i];
//                pre[arr[i]] = true;
//            }
//        }
//    }
//    for (int i = 1; i <= cntq; i++) {
//        pre[que[i]] = false;
//    }
//}
//
//void compute(int u) {
//    vis[u] = true;
//    pre[0] = true;
//    calc(u);
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            total = siz[v];
//            centroid = 0;
//            getCentroid(v, u);
//            compute(centroid);
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
//        maxq = max(maxq, query[i]);
//    }
//    total = n;
//    centroid = 0;
//    getCentroid(1, 0);
//    compute(centroid);
//    for (int i = 1; i <= m; i++) {
//        if (ans[i]) {
//            cout << "AYE" << '\n';
//        } else {
//            cout << "NAY" << '\n';
//        }
//    }
//    return 0;
//}