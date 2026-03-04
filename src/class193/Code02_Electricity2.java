package class193;

// 电力，C++版
// 给定一张无向图，一共n个点、m条边，不保证所有点连通
// 点的编号0 ~ n-1，只能删除一个点的话，连通块最多有多少
// 1 <= n <= 10^4
// 1 <= m <= 2 * 10^4
// 测试链接 : https://loj.ac/p/10103
// 测试链接 : http://poj.org/problem?id=2117
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <algorithm>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXM = 20001;
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
//int block, ans;
//
//void prepare() {
//    cntg = cntd = block = ans = 0;
//    for (int i = 1; i <= n; i++) {
//        head[i] = dfn[i] = low[i] = 0;
//    }
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, bool root) {
//    dfn[u] = low[u] = ++cntd;
//    int curAns = root ? 0 : 1;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, false);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                curAns++;
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//    ans = max(ans, curAns);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(0);
//    cin >> n >> m;
//    while (n != 0 || m != 0) {
//        prepare();
//        for (int i = 1, u, v; i <= m; i++) {
//            cin >> u >> v;
//            u++;
//            v++;
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//        for (int i = 1; i <= n; i++) {
//            if (dfn[i] == 0) {
//                block++;
//                tarjan(i, true);
//            }
//        }
//        cout << (block - 1 + ans) << "\n";
//        cin >> n >> m;
//    }
//    return 0;
//}