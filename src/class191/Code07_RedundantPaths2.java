package class191;

// 冗余路径，C++版
// 给定一张无向图，一共n个点、m条边，保证所有点连通
// 打印至少添加几条边可以让整张图变成一个边双连通分量
// 1 <= n <= 5000
// 1 <= m <= 10000
// 测试链接 : https://www.luogu.com.cn/problem/P2860
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 5001;
//const int MAXM = 10001;
//int n, m;
//int a[MAXM];
//int b[MAXM];
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
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int ebccCnt;
//
//int degree[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            low[u] = min(low[u], low[v]);
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//    if (dfn[u] == low[u]) {
//        ebccCnt++;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = ebccCnt;
//        } while (pop != u);
//    }
//}
//
//int getLeaf() {
//    for (int i = 1; i <= m; i++) {
//        int ebcc1 = belong[a[i]];
//        int ebcc2 = belong[b[i]];
//        if (ebcc1 != ebcc2) {
//            degree[ebcc1]++;
//            degree[ebcc2]++;
//        }
//    }
//    int ans = 0;
//    for (int i = 1; i <= ebccCnt; i++) {
//        if (degree[i] == 1) {
//            ans++;
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cntg = 1;
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//        addEdge(b[i], a[i]);
//    }
//    tarjan(1, 0);
//    int leafCnt = getLeaf();
//    cout << (leafCnt + 1) / 2 << "\n";
//    return 0;
//}