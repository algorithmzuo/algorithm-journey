package class193;

// 割点模版题，C++版
// 给定一张无向图，一共n个点、m条边，不保证所有点连通
// 打印图中割点的数量，然后从小到大打印所有割点的编号
// 1 <= n <= 2 * 10^4
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3388
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 20001;
//const int MAXM = 100001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//bool cutVertex[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, bool root) {
//    dfn[u] = low[u] = ++cntd;
//    int son = 0;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            son++;
//            tarjan(v, false);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                if (!root || son >= 2) {
//                    cutVertex[u] = true;
//                }
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i, true);
//        }
//    }
//    int cutCnt = 0;
//    for (int i = 1; i <= n; i++) {
//        if (cutVertex[i]) {
//            cutCnt++;
//        }
//    }
//    cout << cutCnt << "\n";
//    for (int i = 1; i <= n; i++) {
//        if (cutVertex[i]) {
//            cout << i << " ";
//        }
//    }
//    cout << "\n";
//    return 0;
//}