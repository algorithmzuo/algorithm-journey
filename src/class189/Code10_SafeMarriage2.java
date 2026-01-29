package class189;

// 稳定婚姻，C++版
// 一共有n对婚姻关系，每对婚姻关系给定女方名、男方名，一共2n个名字
// 一共有m对暧昧关系，每对暧昧关系给定女方名、男方名，没有新名字
// 如果婚姻关系(A, B)破裂，那么男方或者女方，可能会找暧昧关系的对象私奔
// 私奔会制造更多破裂婚姻，并且产生进一步的私奔，事情好像多米诺骨牌一样展开
// 所有涉事男女都能重新搭配，如果存在这种可能性，那么婚姻关系(A, B)就是不稳定的
// 比如婚姻关系(A, B)、(C, D)，同时有暧昧关系(B, C)、(A, D)
// 如果A和B婚姻破裂，导致B和C私奔了，导致C和D婚姻破裂，导致D和A私奔了，这就是重新搭配的情况
// 检查每对婚姻关系，如果不稳定打印"Unsafe"，如果稳定打印"Safe"
// 1 <= n <= 4000    0 <= m <= 20000
// 测试链接 : https://www.luogu.com.cn/problem/P1407
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXM = 30001;
//int n, m;
//
//int b[MAXN];
//int g[MAXN];
//int cntn;
//unordered_map<string, int> nameId;
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
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    string boy, girl;
//    for (int i = 1; i <= n; i++) {
//        cin >> boy >> girl;
//        b[i] = ++cntn;
//        g[i] = ++cntn;
//        nameId[boy] = b[i];
//        nameId[girl] = g[i];
//        addEdge(b[i], g[i]);
//    }
//    cin >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> boy >> girl;
//        addEdge(nameId[girl], nameId[boy]);
//    }
//    for (int i = 1; i <= cntn; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (belong[b[i]] == belong[g[i]]) {
//            cout << "Unsafe" << "\n";
//        } else {
//            cout << "Safe" << "\n";
//        }
//    }
//    return 0;
//}