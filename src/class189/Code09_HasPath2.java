package class189;

// 任意两点都有路，C++版
// 给定一张n个点，m条边的有向图
// 两点u、v，不管是从u出发能到达v，还是从v出发能到达u，都叫两点间有路
// 判断这个有向图是否能做到，任意两点都有路，能打印"Yes"，不能打印"No"
// 1 <= n <= 1000
// 1 <= m <= 6000
// 测试链接 : https://www.luogu.com.cn/problem/P10944
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1001;
//const int MAXM = 6001;
//int t, n, m;
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
//bool ins[MAXN];
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int sccCnt;
//
//int indegree[MAXN];
//int que[MAXN];
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
//    ins[u] = true;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//        } else {
//            if (ins[v]) {
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
//            ins[pop] = false;
//        } while (pop != u);
//    }
//}
//
//void condense() {
//    cntg = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= m; i++) {
//        int scc1 = belong[a[i]];
//        int scc2 = belong[b[i]];
//        if (scc1 != scc2) {
//            indegree[scc2]++;
//            addEdge(scc1, scc2);
//        }
//    }
//}
//
//bool topo() {
//    int l = 1, r = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        if (indegree[i] == 0) {
//            que[++r] = i;
//        }
//    }
//    while (l <= r) {
//        int siz = r - l + 1;
//        if (siz > 1) {
//            return false;
//        }
//        int u = que[l++];
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            if (--indegree[v] == 0) {
//                que[++r] = v;
//            }
//        }
//    }
//    return true;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n >> m;
//        cntg = cntd = sccCnt = 0;
//        top = 0;
//        for (int i = 1; i <= n; i++) {
//            head[i] = dfn[i] = indegree[i] = 0;
//        }
//        for (int i = 1; i <= m; i++) {
//            cin >> a[i] >> b[i];
//            addEdge(a[i], b[i]);
//        }
//        for (int i = 1; i <= n; i++) {
//            if (dfn[i] == 0) {
//                tarjan(i);
//            }
//        }
//        condense();
//        bool ans = topo();
//        cout << (ans ? "Yes" : "No") << "\n";
//    }
//    return 0;
//}