package class189;

// 消息扩散，C++版
// 一共有n个城市，给定m条道路，道路可以传递消息，但道路是单向
// 你有一个消息，需要让所有城市都收到，计算至少要在几个城市发布该消息
// 1 <= n <= 10^5
// 1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2002
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 500001;
//int n, m;
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
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//        } while (pop != u);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        int scc1 = belong[a[i]];
//        int scc2 = belong[b[i]];
//        if (scc1 != scc2) {
//            indegree[scc2]++;
//        }
//    }
//    int ans = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        if (indegree[i] == 0) {
//            ans++;
//        }
//    }
//    cout << ans << "\n";
//    return 0;
//}