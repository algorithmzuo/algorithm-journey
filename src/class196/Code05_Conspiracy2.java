package class196;

// 同谋者，C++版
// 一共n个人，编号1~n，分配每个人进入交流队或者保密队中的一个
// 交流队要求内部任意两人都认识，保密队要求内部任意两人都不认识
// 给定每人的认识列表，如果x的认识列表里有y，那么y的认识列表里有x
// 分配还要求交流队和保密队都不能为空，计算n个人分成两组的方法数
// 如果不存在任何方法，打印0
// 2 <= n <= 5000
// 所有认识列表的总人数 <= n * (n - 1)
// 测试链接 : https://www.luogu.com.cn/problem/P3513
// 测试链接 : https://loj.ac/p/2155
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 5001;
//const int MAXT = MAXN << 1;
//const int MAXM = MAXN * MAXN;
//int n, k, x;
//bool know[MAXN][MAXN];
//
//int head[MAXT];
//int nxt[MAXM];
//int to[MAXM];
//int cntg;
//
//int dfn[MAXT];
//int low[MAXT];
//int cntd;
//
//int sta[MAXT];
//int top;
//
//int belong[MAXT];
//int sccCnt;
//
//int team1[MAXN];
//int team2[MAXN];
//int cnt1, cnt2;
//
//int other[MAXN];
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
//            team1[++cnt1] = i;
//        } else {
//            team2[++cnt2] = i;
//        }
//    }
//    int ans = cnt1 > 0 && cnt2 > 0 ? 1 : 0;
//    for (int i = 1, a, b; i <= cnt1; i++) {
//        a = team1[i];
//        for (int j = 1; j <= cnt2; j++) {
//            b = team2[j];
//            if (know[a][b]) {
//                other[a] = other[a] == 0 ? b : -1;
//            } else {
//                other[b] = other[b] == 0 ? a : -1;
//            }
//        }
//    }
//    if (cnt1 > 1) {
//        for (int i = 1; i <= cnt1; i++) {
//            if (other[team1[i]] == 0) {
//                ans++;
//            }
//        }
//    }
//    if (cnt2 > 1) {
//        for (int i = 1; i <= cnt2; i++) {
//            if (other[team2[i]] == 0) {
//                ans++;
//            }
//        }
//    }
//    for (int i = 1, a, b; i <= cnt1; i++) {
//        a = team1[i];
//        for (int j = 1; j <= cnt2; j++) {
//            b = team2[j];
//            if (know[a][b]) {
//                if (other[a] == b && other[b] == 0) {
//                    ans++;
//                }
//            } else {
//                if (other[b] == a && other[a] == 0) {
//                    ans++;
//                }
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