package class183;

// 相近点对的数量，C++版
// 一共有n个节点，所有节点组成一棵树，1号节点是树头
// 从2号点开始，给定每个点的父节点编号、与父节点之间无向边的边权
// 给定两个整数limitl、limitw，如下为(a, b)是相近点对的定义
// 首先a < b，其次两者简单路径的边数不超过limitl、权值和不超过limitw
// 打印树上有多少相近点对
// 1 <= limitl <= n <= 10^5
// 0 <= limitw <= 10^9
// 0 <= 边权 <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/CF293E
// 测试链接 : https://codeforces.com/problemset/problem/293/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int dis, dep;
//};
//
//bool NodeCmp(Node a, Node b) {
//    return a.dis < b.dis;
//}
//
//const int MAXN = 100002;
//int n, limitl, limitw;
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
//Node arr[MAXN];
//int cnta;
//
//int tree[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= limitl + 1) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
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
//void dfs(int u, int fa, int dis, int dep) {
//    if (dis > limitw || dep > limitl + 1) {
//        return;
//    }
//    arr[++cnta] = { dis, dep };
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, dis + weight[e], dep + 1);
//        }
//    }
//}
//
//long long calc(int u, int dis, int dep) {
//    cnta = 0;
//    dfs(u, 0, dis, dep);
//    sort(arr + 1, arr + cnta + 1, NodeCmp);
//    for (int i = 1; i <= cnta; i++) {
//        add(arr[i].dep, 1);
//    }
//    long long ret = 0;
//    for (int l = 1, r = cnta; l <= r; ) {
//        if (arr[l].dis + arr[r].dis <= limitw) {
//            add(arr[l].dep, -1);
//            ret += sum(limitl - arr[l].dep + 2);
//            l++;
//        } else {
//            add(arr[r].dep, -1);
//            r--;
//        }
//    }
//    return ret;
//}
//
//long long solve(int u) {
//    vis[u] = true;
//    long long ans = calc(u, 0, 1);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            ans -= calc(v, weight[e], 2);
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
//    cin >> n >> limitl >> limitw;
//    for (int i = 2, fa, w; i <= n; i++) {
//        cin >> fa >> w;
//        addEdge(i, fa, w);
//        addEdge(fa, i, w);
//    }
//    total = n;
//    centroid = 0;
//    getCentroid(1, 0);
//    cout << solve(centroid) << '\n';
//    return 0;
//}