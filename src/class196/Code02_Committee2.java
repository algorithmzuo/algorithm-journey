package class196;

// 和平委员会，C++版
// 一共n个团体，每个团体有两个代表，所以有2n个代表
// 第i号团体两个代表的编号为2i−1和2i，然后给定m个讨厌关系
// 格式 x y : 编号x和编号y这两个代表，相互讨厌，不可共事
// 现在要选出n个人组成委员会，每个团体的两个代表，有且仅有1人入选
// 同时要求选出的委员会中，没有相互讨厌的代表，如果做不到，打印"NIE"
// 如果能做到，按升序打印组成委员会的n个人的编号，任何一种方案都可以
// 1 <= n <= 8 * 10^3
// 0 <= m <= 2 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P5782
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 20001;
//const int MAXM = 50001;
//int n, m;
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
//int other(int x) {
//    return (x & 1) == 0 ? (x - 1) : (x + 1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge(u, other(v));
//        addEdge(v, other(u));
//    }
//    for (int i = 1; i <= (n << 1); i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    bool check = true;
//    for (int i = 1, a, b; i <= n; i++) {
//        a = i << 1;
//        b = a - 1;
//        if (belong[a] == belong[b]) {
//            check = false;
//        }
//    }
//    if (check) {
//        for (int i = 1, a, b; i <= n; i++) {
//            a = i << 1;
//            b = a - 1;
//            if (belong[a] < belong[b]) {
//                cout << a << "\n";
//            } else {
//                cout << b << "\n";
//            }
//        }
//    } else {
//        cout << "NIE" << "\n";
//    }
//    return 0;
//}