package class193;

// 圆桌骑士，C++版
// 一共n个骑士，有m条厌恶关系，每条厌恶关系代表两个骑士相互讨厌对方
// 你要组织圆桌会议，有厌恶关系的骑士无法在圆桌中相邻
// 圆桌会议的骑士数量必须是大于1的奇数，以防止赞同票和反对票一样多
// 也许有的骑士，不管怎么安排都无法参加任何一场圆桌会议，打印这个数量
// 1 <= n <= 10^3
// 1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/SP2878
// 测试链接 : https://www.spoj.com/problems/KNIGHTS/
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1001;
//const int MAXM = 1000001;
//int n, m;
//bool hate[MAXN][MAXN];
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
//int vbccArr[MAXN << 1];
//int vbccl[MAXN];
//int vbccr[MAXN];
//int idx;
//int vbccCnt;
//
//int color[MAXN];
//bool block[MAXN];
//bool ok[MAXN];
//
//void prepare() {
//    cntg = cntd = top = idx = vbccCnt = 0;
//    for (int i = 1; i <= n; i++) {
//        head[i] = dfn[i] = low[i] = 0;
//        ok[i] = false;
//        for (int j = 1; j <= n; j++) {
//            hate[i][j] = false;
//        }
//    }
//}
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
//                vbccArr[++idx] = u;
//                vbccl[vbccCnt] = idx;
//                int pop;
//                do {
//                    pop = sta[top--];
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
//bool oddLoop(int u, int c) {
//    color[u] = c;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (block[v]) {
//            if (color[v] == 0 && oddLoop(v, c == 1 ? 2 : 1)) {
//                return true;
//            }
//            if (color[v] == c) {
//                return true;
//            }
//        }
//    }
//    return false;
//}
//
//int compute() {
//    for (int i = 1; i <= vbccCnt; i++) {
//        for (int j = vbccl[i]; j <= vbccr[i]; j++) {
//            color[vbccArr[j]] = 0;
//            block[vbccArr[j]] = true;
//        }
//        bool check = oddLoop(vbccArr[vbccl[i]], 1);
//        for (int j = vbccl[i]; j <= vbccr[i]; j++) {
//            block[vbccArr[j]] = false;
//            ok[vbccArr[j]] = ok[vbccArr[j]] | check;
//        }
//    }
//    int ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (!ok[i]) {
//            ans++;
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    while (n != 0 || m != 0) {
//        prepare();
//        for (int i = 1, u, v; i <= m; i++) {
//            cin >> u >> v;
//            hate[u][v] = true;
//            hate[v][u] = true;
//        }
//        for (int u = 1; u <= n; u++) {
//            for (int v = u + 1; v <= n; v++) {
//                if (!hate[u][v]) {
//                    addEdge(u, v);
//                    addEdge(v, u);
//                }
//            }
//        }
//        for (int i = 1; i <= n; i++) {
//            if (dfn[i] == 0) {
//                tarjan(i);
//            }
//        }
//        int ans = compute();
//        cout << ans << "\n";
//        cin >> n >> m;
//    }
//    return 0;
//}