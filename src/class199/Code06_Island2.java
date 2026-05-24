package class199;

// 岛屿，C++版
// 图中有n个点、n条无向边，每条边有边权
// 图中可能有多个连通块，保证每个连通块都是一棵基环树
// 对每个连通块，计算点不重复的路径能得到的最大边权和，即基环树直径
// 图中每棵基环树的直径累加起来，打印这个结果
// 2 <= n <= 10^6
// 1 <= 边权 <= 10^8
// 测试链接 : https://www.luogu.com.cn/problem/P4381
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 1000001;
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
//ll sum[MAXN << 1];
//ll height[MAXN << 1];
//int que[MAXN << 1];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void dfs(int u) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            fromNode[v] = u;
//            fromWeight[v] = weight[e];
//            dfs(v);
//        } else if (dfn[u] < dfn[v]) {
//            cycle[u] = true;
//            arr[++cnta] = u;
//            val[cnta] = weight[e];
//            for (int i = v; i != u; i = fromNode[i]) {
//                cycle[i] = true;
//                arr[++cnta] = i;
//                val[cnta] = fromWeight[i];
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
//ll compute(int root) {
//    cnta = 0;
//    dfs(root);
//    sum[1] = 0;
//    for (int i = 2, j = 1; j <= cnta; i++, j++) {
//        sum[i] = val[j];
//    }
//    for (int i = cnta + 2; i <= cnta * 2; i++) {
//        sum[i] = sum[i - cnta];
//    }
//    for (int i = 1; i <= cnta * 2; i++) {
//        sum[i] += sum[i - 1];
//    }
//    ll ans1 = 0;
//    for (int i = 1; i <= cnta; i++) {
//        diameter = 0;
//        dpOnTree(arr[i], 0);
//        ans1 = max(ans1, diameter);
//        height[i] = dist[arr[i]];
//        height[i + cnta] = height[i];
//    }
//    ll ans2 = 0;
//    int ql = 1, qr = 0;
//    for (int i = 1; i <= cnta * 2; i++) {
//        while (ql <= qr && que[ql] <= i - cnta) {
//            ql++;
//        }
//        if (ql <= qr) {
//            ans2 = max(ans2, height[i] + height[que[ql]] + sum[i] - sum[que[ql]]);
//        }
//        while (ql <= qr && height[que[qr]] - sum[que[qr]] <= height[i] - sum[i]) {
//            qr--;
//        }
//        que[++qr] = i;
//    }
//    return max(ans1, ans2);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int u = 1, v, w; u <= n; u++) {
//        cin >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    ll ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            ans += compute(i);
//        }
//    }
//    cout << ans << "\n";
//    return 0;
//}