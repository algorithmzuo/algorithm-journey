package class196;

// 密谋，C++版
// 一共n个人，编号1~n，分配每个人进入交流队或者保密队中的一个
// 交流队要求内部任意两人都认识，保密队要求内部任意两人都不认识
// 给定每人的认识列表，如果x的认识列表里有y，那么y的认识列表里有x
// 分配还要求交流队和保密队都不能为空，计算n个人分成两组的方法数
// 如果不存在任何方法，打印0
// 2 <= n <= 5000
// 所有认识列表的总人数 <= n * (n - 1)
// 测试链接 : https://www.luogu.com.cn/problem/P3513
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 5001;
//const int MAXS = MAXN << 1;
//const int MAXM = MAXN * MAXN;
//int n, k, x;
//bool know[MAXN][MAXN];
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
//int set1[MAXN];
//int set2[MAXN];
//bool in1[MAXN];
//int cnt1, cnt2;
//
//int conflict[MAXN];
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
//int compute() {
//    for (int i = 1; i <= n; i++) {
//        if (belong[i] == belong[i + n]) {
//            return 0;
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (belong[i] < belong[i + n]) {
//            set1[++cnt1] = i;
//            in1[i] = true;
//        } else {
//            set2[++cnt2] = i;
//        }
//    }
//    int ans = cnt1 > 0 && cnt2 > 0 ? 1 : 0;
//    for (int i = 1; i <= cnt1; i++) {
//        int x = set1[i];
//        for (int j = 1; j <= cnt2; j++) {
//            int y = set2[j];
//            if (know[x][y]) {
//                conflict[x] = conflict[x] == 0 ? y : -1;
//            } else {
//                conflict[y] = conflict[y] == 0 ? x : -1;
//            }
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (conflict[i] == 0) {
//            if ((in1[i] && cnt1 > 1) || (!in1[i] && cnt2 > 1)) {
//                ans++;
//            }
//        } else if (conflict[i] >= 1) {
//            int replace = conflict[i];
//            if (conflict[replace] == 0) {
//                ans++;
//            }
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> k;
//        for (int j = 1; j <= k; j++) {
//            cin >> x;
//            know[i][x] = true;
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        for (int j = i + 1; j <= n; j++) {
//            if (know[i][j]) {
//                addEdge(i + n, j);
//                addEdge(j + n, i);
//            } else {
//                addEdge(i, j + n);
//                addEdge(j, i + n);
//            }
//        }
//    }
//    for (int i = 1; i <= n << 1; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    int ans = compute();
//    cout << ans << "\n";
//    return 0;
//}