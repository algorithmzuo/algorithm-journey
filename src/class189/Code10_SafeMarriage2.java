package class189;

// 稳定婚姻，C++版
// 一共有n对婚姻关系，每对婚姻关系给定(女方名, 男方名)，一共2n个名字
// 一共有m对暧昧关系，每对暧昧关系给定(女方名, 男方名)，不会出现新名字
// 如果一对婚姻关系破裂，那么女方或者男方，可能会找暧昧关系的对象私奔
// 私奔会制造更多的婚姻破裂，并且产生进一步的私奔，事情好像多米诺骨牌一样展开
// 如果所有涉事男女都能重新搭配，只要存在这种可能性，那么这对婚姻关系就是不稳定的
// 比如A为女，B为男，婚姻关系(A1, B1)、(A2, B2)，同时暧昧关系(A1, B2)、(A2, B1)
// A1和B1婚姻破裂，导致B1和A2私奔了，导致A2和B2婚姻破裂，导致B2和A1私奔了，这就是重新搭配
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
//int a[MAXN];
//int b[MAXN];
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
//    string girl, boy;
//    for (int i = 1; i <= n; i++) {
//        cin >> girl >> boy;
//        a[i] = ++cntn;
//        b[i] = ++cntn;
//        nameId[girl] = a[i];
//        nameId[boy] = b[i];
//        addEdge(a[i], b[i]);
//    }
//    cin >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> girl >> boy;
//        addEdge(nameId[boy], nameId[girl]);
//    }
//    for (int i = 1; i <= cntn; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (belong[a[i]] == belong[b[i]]) {
//            cout << "Unsafe" << "\n";
//        } else {
//            cout << "Safe" << "\n";
//        }
//    }
//    return 0;
//}