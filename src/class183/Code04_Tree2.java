package class183;

// 距离<=k的点对数量，C++版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定数字k，求出树上两点距离<=k的点对数量
// 本题规定(x, x)不是点对，(x, y)和(y, x)认为是同一个点对
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
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int fa) {
//    getSize(u, fa);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
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
//    vis[u] = true;
//    long long ans = calc(u, 0);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            ans -= calc(v, weight[e]);
//            ans += solve(getCentroid(v, u));
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
//    cout << solve(getCentroid(1, 0)) << '\n';
//    return 0;
//}