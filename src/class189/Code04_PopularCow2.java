package class189;

// 受欢迎的牛，C++版
// 一共有n只牛，牛和牛之间存在喜欢关系，喜欢关系是有向的
// 喜欢关系可以传递，如果a喜欢b，b喜欢c，那么a也喜欢c
// 每只牛都喜欢自己，如果某只牛被所有牛喜欢，那么这只牛是明星
// 给定m个喜欢关系，打印明星的数量
// 1 <= n <= 10^4
// 1 <= m <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P2341
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXM = 50001;
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
//bool ins[MAXN];
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int sccSiz[MAXN];
//int sccCnt;
//
//int outdegree[MAXN];
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
//            sccSiz[sccCnt]++;
//            ins[pop] = false;
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
//            outdegree[scc1]++;
//        }
//    }
//    int num = 0, siz = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        if (outdegree[i] == 0) {
//            num++;
//            siz = sccSiz[i];
//        }
//        if (num > 1) {
//            siz = 0;
//            break;
//        }
//    }
//    cout << siz << "\n";
//    return 0;
//}