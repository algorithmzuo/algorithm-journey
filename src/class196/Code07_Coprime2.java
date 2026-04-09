package class196;

// 互质，C++版
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc210_f
// 测试链接 : https://atcoder.jp/contests/abc210/tasks/abc210_f
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int p, i;
//};
//
//bool NodeCmp(Node a, Node b) {
//    if (a.p != b.p) {
//        return a.p < b.p;
//    }
//    return a.i < b.i;
//}
//
//const int MAXN = 1000001;
//const int MAXV = 2000001;
//const int MAXM = 3000001;
//int n, cntt, maxv;
//int ab[MAXN];
//
//int minp[MAXV];
//int pval[MAXV];
//int cntp;
//
//Node arr[MAXN];
//int cnta;
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
//int other(int x) {
//    return x <= n ? (x + n) : (x - n);
//}
//
//void euler() {
//    for (int i = 2; i <= maxv; i++) {
//        if (minp[i] == 0) {
//            minp[i] = i;
//            pval[++cntp] = i;
//        }
//        for (int j = 1; j <= cntp; j++) {
//            if (i * pval[j] > maxv) {
//                break;
//            }
//            minp[i * pval[j]] = pval[j];
//            if (i % pval[j] == 0) {
//                break;
//            }
//        }
//    }
//}
//
//void decompose() {
//    for (int i = 1; i <= n << 1; i++) {
//        for (int v = ab[i], p = minp[v]; v > 1; p = minp[v]) {
//            arr[++cnta].p = p;
//            arr[cnta].i = i;
//            while (v % p == 0) {
//                v /= p;
//            }
//        }
//    }
//}
//
//void link() {
//    sort(arr + 1, arr + cnta + 1, NodeCmp);
//    for (int l = 1, r = 1; l <= cnta; l = ++r) {
//        while (r + 1 <= cnta && arr[l].p == arr[r + 1].p) {
//            r++;
//        }
//        cntt++;
//        addEdge(cntt, other(arr[l].i));
//        for (int i = l + 1; i <= r; i++) {
//            cntt++;
//            addEdge(cntt, other(arr[i].i));
//            addEdge(arr[i].i, cntt - 1);
//            addEdge(cntt, cntt - 1);
//        }
//        cntt++;
//        addEdge(cntt, other(arr[r].i));
//        for (int i = r - 1; i >= l; i--) {
//            cntt++;
//            addEdge(cntt, other(arr[i].i));
//            addEdge(arr[i].i, cntt - 1);
//            addEdge(cntt, cntt - 1);
//        }
//    }
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
//    cntt = n << 1;
//    for (int i = 1, a, b; i <= n; i++) {
//        cin >> a >> b;
//        ab[i] = a;
//        ab[i + n] = b;
//        maxv = max(maxv, max(a, b));
//    }
//    euler();
//    decompose();
//    link();
//    for (int i = 1; i <= cntt; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    bool check = true;
//    for (int i = 1; i <= n; i++) {
//        if (belong[i] == belong[i + n]) {
//            check = false;
//            break;
//        }
//    }
//    cout << (check ? "Yes" : "No") << "\n";
//    return 0;
//}