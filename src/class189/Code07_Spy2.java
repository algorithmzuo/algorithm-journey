package class189;

// 间谍网络，C++版
// 一共有n个间谍，其中有p个间谍可以贿赂，给定各自的价格，剩下的间谍不能贿赂
// 然后给定m个控制关系，控制关系是单向的，也是可传递的
// 如果间谍a能控制间谍b，间谍b能控制间谍c，那么间谍a就能控制间谍c
// 当你贿赂了某个间谍，该间谍连同他能控制的所有人，都能被你控制
// 如果你不能控制所有间谍，打印"NO"，然后打印不能控制的间谍中，最小的编号
// 如果你可以控制所有间谍，打印"YES"，然后打印需要花费的最少钱数
// 1 <= n <= 3000    0 <= 各自的价格 <= 20000    1 <= m <= 8000
// 测试链接 : https://www.luogu.com.cn/problem/P1262
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 3001;
//const int MAXM = 8001;
//const int INF = 1000000001;
//int n, p, m;
//
//int cost[MAXN];
//int a[MAXM];
//int b[MAXM];
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int minVal[MAXN];
//int sccCnt;
//
//int indegree[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//        } else {
//            if (belong[v] == 0) {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//    if (dfn[u] == low[u]) {
//        sccCnt++;
//        minVal[sccCnt] = INF;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            minVal[sccCnt] = min(minVal[sccCnt], cost[pop]);
//        } while (pop != u);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> p;
//    for (int i = 1; i <= n; i++) {
//        cost[i] = INF;
//    }
//    for (int i = 1, u, c; i <= p; i++) {
//        cin >> u >> c;
//        cost[u] = c;
//    }
//    cin >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (cost[i] != INF && dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    bool check = true;
//    int ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (belong[i] == 0) {
//            check = false;
//            ans = i;
//            break;
//        }
//    }
//    if (!check) {
//        cout << "NO" << "\n";
//        cout << ans << "\n";
//    } else {
//        for (int i = 1; i <= m; i++) {
//            int scc1 = belong[a[i]];
//            int scc2 = belong[b[i]];
//            if (scc1 != scc2) {
//                indegree[scc2]++;
//            }
//        }
//        for (int i = 1; i <= sccCnt; i++) {
//            if (indegree[i] == 0) {
//                ans += minVal[i];
//            }
//        }
//        cout << "YES" << "\n";
//        cout << ans << "\n";
//    }
//    return 0;
//}