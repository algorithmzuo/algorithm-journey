package class193;

// 点双连通分量模版题1，C++版
// 给定一张无向图，一共n个点、m条边，可能存在度数为0的孤立点
// 打印点双连通分量的个数
// 打印每个点双连通分量的大小和节点编号
// 打印顺序随意
// 1 <= n <= 5 * 10^5
// 1 <= m <= 2 * 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P8435
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXM = 2000001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//int vbccSiz[MAXN];
//int vbccArr[MAXN << 1];
//int vbccl[MAXN];
//int vbccr[MAXN];
//int idx;
//int vbccCnt;
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
//            if (low[v] >= dfn[u]) {
//                vbccCnt++;
//                vbccSiz[vbccCnt] = 1;
//                vbccArr[++idx] = u;
//                vbccl[vbccCnt] = idx;
//                int pop;
//                do {
//                    pop = sta[top--];
//                    vbccSiz[vbccCnt]++;
//                    vbccArr[++idx] = pop;
//                } while (pop != v);
//                vbccr[vbccCnt] = idx;
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        if (u != v) {
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            if (head[i] == 0) {
//                vbccCnt++;
//                vbccSiz[vbccCnt] = 1;
//                vbccArr[++idx] = i;
//                vbccl[vbccCnt] = vbccr[vbccCnt] = idx;
//            } else {
//                tarjan(i);
//            }
//        }
//    }
//    cout << vbccCnt << "\n";
//    for (int i = 1; i <= vbccCnt; i++) {
//        cout << vbccSiz[i] << "\n";
//        for (int j = vbccl[i]; j <= vbccr[i]; j++) {
//            cout << vbccArr[j] << " ";
//        }
//        cout << "\n";
//    }
//    return 0;
//}