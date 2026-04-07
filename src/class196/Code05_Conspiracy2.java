package class196;

// 密谋，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3513
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 5001;
//const int MAXM = 30000001;
//const int MAXS = MAXN << 1;
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
//int arr1[MAXN];
//int arr2[MAXN];
//bool set1[MAXN];
//int cnt1, cnt2;
//
//int conflict[MAXN];
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
//            arr1[++cnt1] = i;
//            set1[i] = true;
//        } else {
//            arr2[++cnt2] = i;
//        }
//    }
//    int ans = cnt1 > 0 && cnt2 > 0 ? 1 : 0;
//    for (int i = 1; i <= cnt1; i++) {
//        int x = arr1[i];
//        for (int j = 1; j <= cnt2; j++) {
//            int y = arr2[j];
//            if (know[x][y]) {
//                conflict[x]++;
//                other[x] = y;
//            } else {
//                conflict[y]++;
//                other[y] = x;
//            }
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (conflict[i] == 0) {
//            if ((set1[i] && cnt1 > 1) || (!set1[i] && cnt2 > 1)) {
//                ans++;
//            }
//        }
//        if (conflict[i] == 1) {
//            if (conflict[other[i]] == 0) {
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
//    for (int i = 1; i <= (n << 1); i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    int ans = compute();
//    cout << ans << "\n";
//    return 0;
//}