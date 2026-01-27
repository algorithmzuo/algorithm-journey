package class189;

// 强连通分量模版题3，C++版
// 一共有n个节点，给定m条边，边的格式 a b t
// 如果t为1，表示a到b的单向边，如果t为2，表示a到b的双向边
// 找到图中最大的强连通分量，先打印大小，然后打印包含的节点，编号从小到大输出
// 如果有多个最大的强连通分量，打印字典序最小的结果
// 1 <= n <= 5 * 10^3
// 0 <= m <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P1726
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 5001;
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
//int sccSiz[MAXN];
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
//    for (int i = 1, a, b, t; i <= m; i++) {
//        cin >> a >> b >> t;
//        if (t == 1) {
//            addEdge(a, b);
//        } else {
//            addEdge(a, b);
//            addEdge(b, a);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    int largest = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        largest = max(largest, sccSiz[i]);
//    }
//    cout << largest << "\n";
//    for (int i = 1; i <= n; i++) {
//        if (sccSiz[belong[i]] == largest) {
//            int scc = belong[i];
//            for (int j = i; j <= n; j++) {
//                if (belong[j] == scc) {
//                    cout << j << " ";
//                }
//            }
//            break;
//        }
//    }
//    cout << "\n";
//    return 0;
//}