package class191;

// 割边模版题1，C++版
// 给定一张无向图，一共n个点、m条边，求出图中所有割边
// 先打印割边的数量，然后从小到大打印所有割边的序号
// 请保证原图即使有重边，答案依然正确
// 测试链接 : https://www.luogu.com.cn/problem/U582665
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXM = 2000001;
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
//bool cutEdge[MAXM];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            low[u] = min(low[u], low[v]);
//            if (low[v] > dfn[u]) {
//                cutEdge[e >> 1] = true;
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
//    cntg = 1;
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i, 0);
//        }
//    }
//    int ansCnt = 0;
//    for (int i = 1; i <= m; i++) {
//        if (cutEdge[i]) {
//            ansCnt++;
//        }
//    }
//    cout << ansCnt << "\n";
//    for (int i = 1; i <= m; i++) {
//        if (cutEdge[i]) {
//            cout << i << " ";
//        }
//    }
//    cout << "\n";
//    return 0;
//}