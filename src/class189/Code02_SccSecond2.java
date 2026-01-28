package class189;

// 强连通分量模版题2，C++版
// 给定一张n个点，m条边的有向图
// 求出所有强连通分量，先打印强连通分量的数量
// 每个强连通分量先打印大小，然后打印节点编号，顺序随意
// 1 <= n <= 5 * 10^4
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/U224391
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
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
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int sccArr[MAXN];
//int sccSiz[MAXN];
//int sccl[MAXN];
//int sccr[MAXN];
//int idx;
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
//        sccl[sccCnt] = idx + 1;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            sccArr[++idx] = pop;
//            sccSiz[sccCnt]++;
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
//        cout << sccSiz[i] << " ";
//        for (int j = sccl[i]; j <= sccr[i]; j++) {
//            cout << sccArr[j] << " ";
//        }
//        cout << "\n";
//    }
//    return 0;
//}