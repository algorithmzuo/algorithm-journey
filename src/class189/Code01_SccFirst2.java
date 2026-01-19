package class189;

// 强连通分量模版题1，C++版
// 测试链接 : https://www.luogu.com.cn/problem/B3609
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXM = 100001;
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
//bool ins[MAXN];
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int sccArr[MAXN];
//int sccl[MAXN];
//int sccr[MAXN];
//int idx;
//int sccCnt;
//
//bool sccPrint[MAXN];
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
//        sccl[++sccCnt] = idx + 1;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            sccArr[++idx] = pop;
//            ins[pop] = false;
//        } while (pop != u);
//        sccr[sccCnt] = idx;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    cout << sccCnt << "\n";
//    for (int i = 1; i <= sccCnt; i++) {
//        sort(sccArr + sccl[i], sccArr + sccr[i] + 1);
//    }
//    for (int i = 1; i <= n; i++) {
//        int scc = belong[i];
//        if (!sccPrint[scc]) {
//            sccPrint[scc] = true;
//            for (int j = sccl[scc]; j <= sccr[scc]; j++) {
//                cout << sccArr[j] << " ";
//            }
//            cout << "\n";
//        }
//    }
//    return 0;
//}