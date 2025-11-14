package class183;

// 距离<=k的点对数量，C++版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定数字k，求出树上两点距离<=k的点对数量
// 1 <= n <= 4 * 10^4
// 0 <= 边权 <= 10^3
// 0 <= k <= 2 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P4178
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//int n, k;
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
//int disArr[MAXN];
//int cnta;
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
//    if (dis > k) {
//        return;
//    }
//    disArr[++cnta] = dis;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, dis + weight[e]);
//        }
//    }
//}
//
//long long calc(int u, int dis) {
//    cnta = 0;
//    dfs(u, 0, dis);
//    long long ans = 0;
//    sort(disArr + 1, disArr + cnta + 1);
//    for (int l = 1, r = cnta; l <= r; ) {
//        if (disArr[l] + disArr[r] <= k) {
//            ans += r - l;
//            l++;
//        } else {
//            r--;
//        }
//    }
//    return ans;
//}
//
//long long solve(int u) {
//    long long ans = 0;
//    ans += calc(u, 0);
//    vis[u] = true;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            ans -= calc(v, weight[e]);
//            total = siz[v];
//            centroid = 0;
//            getCentroid(v, 0);
//            ans += solve(centroid);
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    cin >> k;
//    total = n;
//    centroid = 0;
//    getCentroid(1, 0);
//    cout << solve(centroid) << '\n';
//    return 0;
//}