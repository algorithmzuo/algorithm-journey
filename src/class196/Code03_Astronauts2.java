package class196;

// 宇航员，C++版
// 一共n个人，一共A、B、C三个任务，所有人都能执行C任务，给定每人的年龄，假设平均年龄为x
// 年龄 >= x 的人可以执行A任务，年龄 < x 的人可以执行B任务
// 给定m个厌恶关系，格式 x y，代表编号x和编号y的两人相互讨厌
// 每个人必须选择一个任务，相互讨厌的人不能分配相同任务，允许某个任务无人执行
// 如果存在分配任务的方案，打印每个人分配了什么任务，任何一种方案都可以
// 如果不存在方案，打印"No solution."
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/UVA1391
// 测试链接 : https://vjudge.net/problem/UVA-1391
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXM = 500001;
//int n, m;
//int age[MAXN];
//int sumAge;
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
//void prepare() {
//    sumAge = cntg = cntd = sccCnt = 0;
//    top = 0;
//    for (int i = 1; i <= n << 1; i++) {
//        head[i] = dfn[i] = low[i] = belong[i] = 0;
//    }
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//bool older(int x) {
//    return age[x] * n >= sumAge;
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
//    while (n != 0 && m != 0) {
//        prepare();
//        for (int i = 1; i <= n; i++) {
//            cin >> age[i];
//            sumAge += age[i];
//        }
//        for (int i = 1, x, y; i <= m; i++) {
//            cin >> x >> y;
//            addEdge(x + n, y);
//            addEdge(y + n, x);
//            if (older(x) == older(y)) {
//                addEdge(x, y + n);
//                addEdge(y, x + n);
//            }
//        }
//        for (int i = 1; i <= n << 1; i++) {
//            if (dfn[i] == 0) {
//                tarjan(i);
//            }
//        }
//        bool check = true;
//        for (int i = 1; i <= n; i++) {
//            if (belong[i] == belong[i + n]) {
//                check = false;
//                break;
//            }
//        }
//        if (check) {
//            for (int i = 1; i <= n; i++) {
//                if (belong[i] < belong[i + n]) {
//                    if (older(i)) {
//                        cout << "A" << "\n";
//                    } else {
//                        cout << "B" << "\n";
//                    }
//                } else {
//                    cout << "C" << "\n";
//                }
//            }
//        } else {
//            cout << "No solution." << "\n";
//        }
//        cin >> n >> m;
//    }
//    return 0;
//}