package class196;

// 2-SAT模版题，C++版
// 一共n个变量，变量的编号1~n，每个变量的取值不是0就是1
// 给定m条限制，每条限制的格式 x1 v1 x2 v2，含义如下
// 编号为x1的变量取值为v1 或者 编号为x2的变量取值为v2
// 决定每个变量的值，来满足所有限制，如果无法做到，打印"IMPOSSIBLE"
// 如果可以做到，先打印"POSSIBLE"，然后打印每个变量的值，任何一种方案都可以
// 1 <= n、m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4782
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 2000001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
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
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, x1, v1, x2, v2; i <= m; i++) {
//        cin >> x1 >> v1 >> x2 >> v2;
//        if (v1 == 1 && v2 == 1) {
//            addEdge(x1 + n, x2);
//            addEdge(x2 + n, x1);
//        } else if (v1 == 0 && v2 == 1) {
//            addEdge(x1, x2);
//            addEdge(x2 + n, x1 + n);
//        } else if (v1 == 1 && v2 == 0) {
//            addEdge(x2, x1);
//            addEdge(x1 + n, x2 + n);
//        } else {
//            addEdge(x1, x2 + n);
//            addEdge(x2, x1 + n);
//        }
//    }
//    for (int i = 1; i <= n << 1; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    bool check = true;
//    for (int i = 1; i <= n; i++) {
//        if (belong[i] == belong[i + n]) {
//            check = false;
//            break;
//        }
//    }
//    if (check) {
//        cout << "POSSIBLE" << "\n";
//        for (int i = 1; i <= n; i++) {
//            if (belong[i] < belong[i + n]) {
//                cout << "1 ";
//            } else {
//                cout << "0 ";
//            }
//        }
//    } else {
//        cout << "IMPOSSIBLE" << "\n";
//    }
//    return 0;
//}