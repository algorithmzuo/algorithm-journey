package class199;

// 树的直径最小化，C++版
// 图中有n个点、n条无向边，每条边有边权，图是一棵基环树
// 可以任意删掉环上的一条边，让图变成树，希望让树的直径最小
// 第一个测试链接，计算树的直径最小值
// 第二个测试链接，等价于计算树的直径最小值 / 2，结果保留一位小数
// 1 <= n <= 2 * 10^5
// 1 <= 边权 <= 10^9
// 测试链接 : https://codeforces.com/problemset/problem/835/F
// 测试链接 : https://www.luogu.com.cn/problem/P1399
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//using namespace std;
//using ll = long long;
//
//const int MAXN = 200001;
//int n;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int cntd;
//
//int fromNode[MAXN];
//int fromWeight[MAXN];
//bool cycle[MAXN];
//int arr[MAXN];
//int val[MAXN];
//int cnta;
//
//ll dist[MAXN];
//ll diameter;
//
//ll height[MAXN];
//
//ll preMax[MAXN];
//ll preDiameter[MAXN];
//
//ll sufMax[MAXN + 1];
//ll sufDiameter[MAXN + 1];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void dfs(int u, int preEdge) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (e != (preEdge ^ 1)) {
//            if (dfn[v] == 0) {
//                fromNode[v] = u;
//                fromWeight[v] = weight[e];
//                dfs(v, e);
//            } else if (dfn[u] < dfn[v]) {
//                cycle[u] = true;
//                arr[++cnta] = u;
//                val[cnta] = weight[e];
//                for (int i = v; i != u; i = fromNode[i]) {
//                    cycle[i] = true;
//                    arr[++cnta] = i;
//                    val[cnta] = fromWeight[i];
//                }
//            }
//        }
//    }
//}
//
//void dpOnTree(int u, int fa) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa && !cycle[v]) {
//            dpOnTree(v, u);
//            diameter = max(diameter, dist[u] + dist[v] + w);
//            dist[u] = max(dist[u], dist[v] + w);
//        }
//    }
//}
//
//ll dpOnCycle() {
//    ll sum = 0;
//    ll best = 0;
//    for (int i = 1; i <= cnta; i++) {
//        preMax[i] = max(preMax[i - 1], height[i] + sum);
//        preDiameter[i] = max(preDiameter[i - 1], sum + height[i] + best);
//        best = max(best, height[i] - sum);
//        sum += val[i];
//    }
//    sum = val[cnta];
//    best = -val[cnta];
//    for (int i = cnta; i >= 1; i--) {
//        sufMax[i] = max(sufMax[i + 1], height[i] + sum);
//        sufDiameter[i] = max(sufDiameter[i + 1], sum + height[i] + best);
//        best = max(best, height[i] - sum);
//        sum += val[i - 1];
//    }
//    ll ans = preDiameter[cnta];
//    for (int i = 1; i < cnta; i++) {
//        ans = min(ans, max(preMax[i] + sufMax[i + 1], max(preDiameter[i], sufDiameter[i + 1])));
//    }
//    return ans;
//}
//
//ll compute() {
//    dfs(1, 0);
//    ll ans1 = 0;
//    for (int i = 1; i <= cnta; i++) {
//        diameter = 0;
//        dpOnTree(arr[i], 0);
//        ans1 = max(ans1, diameter);
//        height[i] = dist[arr[i]];
//    }
//    ll ans2 = dpOnCycle();
//    return max(ans1, ans2);
//}
//
//int main() {
//    scanf("%d", &n);
//    cntg = 1;
//    for (int i = 1, u, v, w; i <= n; i++) {
//        scanf("%d%d%d", &u, &v, &w);
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    ll ans = compute();
//
//    // 王国道路，CF835F，使用如下打印
//    printf("%lld\n", ans);
//
//    // 快餐店，洛谷P1399，使用如下打印
//    // printf("%.1f\n", (double)ans / 2.0);
//
//    return 0;
//}