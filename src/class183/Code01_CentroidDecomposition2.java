package class183;

// 点分治，也叫重心分解，C++版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 一共有m条查询，每条查询给定数字k，打印树上距离为k的点对是否存在
// 1 <= n <= 10^4    1 <= 边权 <= 10^4
// 1 <= m <= 100     1 <= k <= 10^7
// 测试链接 : https://www.luogu.com.cn/problem/P3806
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXM = 101;
//const int MAXV = 10000001;
//int n, m;
//
//int query[MAXM];
//int maxq;
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
//int total;
//int centroid;
//
//int curDis[MAXN];
//int cntc;
//
//int allDis[MAXN];
//int cnta;
//
//bool check[MAXV];
//
//bool ans[MAXM];
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
//    for (int e = head[u]; e; e = nxt[e]) {
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
//void dfs(int u, int fa, int dis) {
//    if (dis > maxq) {
//        return;
//    }
//    curDis[++cntc] = dis;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, dis + weight[e]);
//        }
//    }
//}
//
//void calc(int u) {
//    cnta = 0;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (!vis[v]) {
//            cntc = 0;
//            dfs(v, u, w);
//            for (int i = 1; i <= m; i++) {
//                for (int j = 1; j <= cntc; j++) {
//                    if (query[i] - curDis[j] >= 0) {
//                        ans[i] |= check[query[i] - curDis[j]];
//                    }
//                }
//            }
//            for (int i = 1; i <= cntc; i++) {
//                allDis[++cnta] = curDis[i];
//                check[curDis[i]] = true;
//            }
//        }
//    }
//    for (int i = 1; i <= cnta; i++) {
//        check[allDis[i]] = false;
//    }
//}
//
//void solve(int u) {
//    vis[u] = true;
//    check[0] = true;
//    calc(u);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            total = siz[v];
//            centroid = 0;
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
//        maxq = max(maxq, query[i]);
//    }
//    total = n;
//    centroid = 0;
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