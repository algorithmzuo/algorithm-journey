package class184;

// 首都，C++版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树
// 给定长度为n的数组color，color[i]表示i号节点的颜色，颜色有k种
// 你需要在树上找到一个连通区，连通区内出现的每种颜色，在连通区外不存在
// 这样的连通区可能有多个，希望包含的颜色数量尽量少，打印(最少颜色数 - 1)的结果
// 1 <= n、k <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P7215
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int INF = 1000000001;
//int n, k;
//int color[MAXN];
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int headc[MAXN];
//int nextc[MAXN];
//int toc[MAXN];
//int cntc;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//int father[MAXN];
//int nodeRoot[MAXN];
//
//int que[MAXN];
//bool nodeVis[MAXN];
//bool colorVis[MAXN];
//
//void addEdge(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addNode(int color, int node) {
//    nextc[++cntc] = headc[color];
//    toc[cntc] = node;
//    headc[color] = cntc;
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
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
//        for (int e = headg[u]; e; e = nextg[e]) {
//            int v = tog[e];
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
//void dfs(int u, int fa, int rt) {
//    father[u] = fa;
//    nodeRoot[u] = rt;
//    nodeVis[u] = false;
//    colorVis[color[u]] = false;
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, rt);
//        }
//    }
//}
//
//int calc(int u) {
//    dfs(u, 0, u);
//    int l = 1, r = 0;
//    que[++r] = u;
//    nodeVis[u] = true;
//    int ans = 0;
//    while (l <= r) {
//        int cur = que[l++];
//        if (cur != u && !nodeVis[father[cur]]) {
//            que[++r] = father[cur];
//            nodeVis[father[cur]] = true;
//        }
//        if (!colorVis[color[cur]]) {
//            colorVis[color[cur]] = true;
//            ans++;
//            for (int e = headc[color[cur]]; e; e = nextc[e]) {
//                int v = toc[e];
//                if (nodeRoot[v] != u) {
//                    return INF;
//                }
//                if (!nodeVis[v]) {
//                    que[++r] = v;
//                    nodeVis[v] = true;
//                }
//            }
//        }
//    }
//    return ans;
//}
//
//int solve(int u) {
//    vis[u] = true;
//    int ans = calc(u);
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (!vis[v]) {
//            ans = min(ans, solve(getCentroid(v, u)));
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> k;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//        addNode(color[i], i);
//    }
//    int ans = solve(getCentroid(1, 0));
//    cout << (ans - 1) << '\n';
//    return 0;
//}