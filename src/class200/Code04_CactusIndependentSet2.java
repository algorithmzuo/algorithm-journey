package class200;

// 仙人掌最大独立集，C++版
// 给定n个点、m条边的无向连通图，输入保证图是仙人掌
// 你可以选择一些点，但是图中任意相邻两点不能同时选择
// 计算能得到的最大点权累加和
// 第一个测试链接，给定每个点的点权
// 第二个测试链接，认为每个点的点权是1
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4410
// 测试链接 : https://www.luogu.com.cn/problem/P10779
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//const int INF = 1000000000;
//int n, m;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int top;
//
//int cycle[MAXN];
//int tmp[MAXN][2];
//int dp[MAXN][2];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dpOnCycle(int u, int v) {
//    int siz = 0;
//    int pop;
//    do {
//        pop = sta[top--];
//        cycle[++siz] = pop;
//    } while (pop != v);
//    for (int i = 1; i <= siz; i++) {
//        int x = cycle[i];
//        int fa = i == siz ? u : cycle[i + 1];
//        tmp[fa][0] = dp[fa][0] - max(dp[x][0], dp[x][1]);
//        tmp[fa][1] = dp[fa][1] - dp[x][0];
//    }
//    tmp[cycle[1]][0] = dp[cycle[1]][0];
//    tmp[cycle[1]][1] = -INF;
//    for (int i = 1; i <= siz; i++) {
//        int x = cycle[i];
//        int fa = i == siz ? u : cycle[i + 1];
//        tmp[fa][0] += max(tmp[x][0], tmp[x][1]);
//        tmp[fa][1] += tmp[x][0];
//    }
//    dp[u][1] = tmp[u][1];
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    dp[u][0] = 0;
//    dp[u][1] = arr[u];
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            dp[u][0] += max(dp[v][0], dp[v][1]);
//            dp[u][1] += dp[v][0];
//            if (low[v] < dfn[u]) {
//                low[u] = min(low[u], low[v]);
//            } else if (low[v] > dfn[u]) {
//                top--;
//            } else {
//                dpOnCycle(u, v);
//            }
//        } else if (dfn[v] < dfn[u]) {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntg = 1;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//
//    // 洛谷P4410，使用如下的点权设置
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//
//    // 洛谷P10779，使用如下的点权设置
//    // for (int i = 1; i <= n; i++) {
//    //     arr[i] = 1;
//    // }
//
//    tarjan(1, 0);
//    cout << max(dp[1][0], dp[1][1]) << "\n";
//    return 0;
//}