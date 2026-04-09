package class196;

// 约束条件，C++版
// 你需要构造长度为n的数组arr，每个元素的值在1~k之间，数组是非递减的
// 接下来给定m个限制，格式如下
// 限制 1 i x   : arr[i] != x
// 限制 2 i j x : arr[i] + arr[j] <= x
// 限制 3 i j x : arr[i] + arr[j] >= x
// 如果不存在构造方案打印-1，存在方案就打印arr，任何一种方案都可以
// 2 <= n <= 2 * 10^4
// 0 <= m <= 2 * 10^4
// 2 <= k <= 10
// 测试链接 : https://www.luogu.com.cn/problem/CF1697F
// 测试链接 : https://codeforces.com/problemset/problem/1697/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 20001;
//const int MAXM = 2000001;
//const int MAXK = 12;
//const int MAXS = MAXN * MAXK * 2;
//int t, n, m, k;
//
//int id[MAXN][MAXK][2];
//int cnti;
//
//int head[MAXS];
//int nxt[MAXM];
//int to[MAXM];
//int cntg;
//
//int dfn[MAXS];
//int low[MAXS];
//int cntd;
//
//int sta[MAXS];
//int top;
//
//int belong[MAXS];
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
//void prepare() {
//    cnti = 0;
//    for (int i = 1; i <= n; i++) {
//        for (int v = 1; v <= k + 1; v++) {
//            id[i][v][0] = ++cnti;
//            id[i][v][1] = ++cnti;
//        }
//    }
//    cntg = cntd = sccCnt = 0;
//    for (int i = 1; i <= cnti; i++) {
//        head[i] = dfn[i] = low[i] = belong[i] = 0;
//    }
//    for (int i = 1; i <= n; i++) {
//        for (int v = 1; v <= k; v++) {
//            addEdge(id[i][v][0], id[i][v + 1][0]);
//            addEdge(id[i][v + 1][1], id[i][v][1]);
//        }
//        addEdge(id[i][1][0], id[i][1][1]);
//        addEdge(id[i][k + 1][1], id[i][k + 1][0]);
//    }
//    for (int i = 1; i < n; ++i) {
//        for (int v = 1; v <= k; ++v) {
//            addEdge(id[i + 1][v][0], id[i][v][0]);
//            addEdge(id[i][v][1], id[i + 1][v][1]);
//        }
//    }
//}
//
//void notEqual(int i, int v) {
//    addEdge(id[i][v][1], id[i][v + 1][1]);
//    addEdge(id[i][v + 1][0], id[i][v][0]);
//}
//
//void lessEqual(int i, int j, int v) {
//    for (int w = 1; w <= k; w++) {
//        if (w >= v) {
//            notEqual(i, w);
//        } else {
//            if (v - w + 1 <= k) {
//                addEdge(id[i][w][1], id[j][v - w + 1][0]);
//                addEdge(id[j][v - w + 1][1], id[i][w][0]);
//            }
//        }
//    }
//}
//
//void moreEqual(int i, int j, int v) {
//    for (int w = 1; w <= k; w++) {
//        if (w + k < v) {
//            notEqual(i, w);
//        } else {
//            if (v - w > 1) {
//                addEdge(id[i][w + 1][0], id[j][v - w][1]);
//                addEdge(id[j][v - w][0], id[i][w + 1][1]);
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n >> m >> k;
//        prepare();
//        for (int i = 1, op, x, y, v; i <= m; i++) {
//            cin >> op;
//            if (op == 1) {
//                cin >> x >> v;
//                notEqual(x, v);
//            } else if (op == 2) {
//                cin >> x >> y >> v;
//                lessEqual(x, y, v);
//                swap(x, y);
//                lessEqual(x, y, v);
//            } else {
//                cin >> x >> y >> v;
//                moreEqual(x, y, v);
//                swap(x, y);
//                moreEqual(x, y, v);
//            }
//        }
//        for (int i = 1; i <= cnti; i++) {
//            if (dfn[i] == 0) {
//                tarjan(i);
//            }
//        }
//        bool check = true;
//        for (int i = 1; i <= n; i++) {
//            for (int v = 1; v <= k + 1; v++) {
//                if (belong[id[i][v][0]] == belong[id[i][v][1]]) {
//                    check = false;
//                    break;
//                }
//            }
//            if (!check) {
//                break;
//            }
//        }
//        if (check) {
//            for (int i = 1; i <= n; i++) {
//                for (int v = k; v >= 1; v--) {
//                    if (belong[id[i][v][1]] < belong[id[i][v][0]]) {
//                        cout << v << " ";
//                        break;
//                    }
//                }
//            }
//            cout << "\n";
//        } else {
//            cout << -1 << "\n";
//        }
//    }
//    return 0;
//}