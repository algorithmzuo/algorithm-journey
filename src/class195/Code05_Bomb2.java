package class195;

// 炸弹，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5025
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 500001;
//const int MAXT = MAXN * 5;
//const int MAXE = MAXN * 20;
//const int INF = 1 << 30;
//const int MOD = 1000000007;
//int n;
//ll location[MAXN];
//ll radius[MAXN];
//
//int a[MAXE];
//int b[MAXE];
//int cnte;
//
//int head[MAXT];
//int nxt[MAXE];
//int to[MAXE];
//int cntg;
//
//int rangel[MAXT];
//int ranger[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int root;
//int cntt;
//
//int dfn[MAXT];
//int low[MAXT];
//int cntd;
//int sta[MAXT];
//int top;
//
//int belong[MAXT];
//int mostl[MAXT];
//int mostr[MAXT];
//int sccCnt;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void addSaveEdge(int u, int v) {
//    addEdge(u, v);
//    a[++cnte] = u;
//    b[cnte] = v;
//}
//
//int lower(ll num) {
//    int l = 1, r = n, mid, ans = n + 1;
//    while (l <= r) {
//        mid = (l + r) >> 1;
//        if (location[mid] >= num) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//int build(int l, int r) {
//    int rt;
//    if (l == r) {
//        rt = l;
//    } else {
//        rt = ++cntt;
//        int mid = (l + r) >> 1;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
//        addSaveEdge(rt, ls[rt]);
//        addSaveEdge(rt, rs[rt]);
//    }
//    rangel[rt] = l;
//    ranger[rt] = r;
//    return rt;
//}
//
//void xToRange(int jobx, int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addSaveEdge(jobx, i);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            xToRange(jobx, jobl, jobr, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            xToRange(jobx, jobl, jobr, mid + 1, r, rs[i]);
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
//        mostl[sccCnt] = INF;
//        mostr[sccCnt] = -INF;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            mostl[sccCnt] = min(mostl[sccCnt], rangel[pop]);
//            mostr[sccCnt] = max(mostr[sccCnt], ranger[pop]);
//        } while (pop != u);
//    }
//}
//
//void condense() {
//    cntg = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= cnte; i++) {
//        int scc1 = belong[a[i]];
//        int scc2 = belong[b[i]];
//        if (scc1 != scc2) {
//            addEdge(scc2, scc1);
//        }
//    }
//}
//
//void dpOnDAG() {
//    for (int u = 1; u <= sccCnt; u++) {
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            mostl[v] = min(mostl[v], mostl[u]);
//            mostr[v] = max(mostr[v], mostr[u]);
//        }
//    }
//}
//
//int query(int u) {
//    int scc = belong[u];
//    int num = mostr[scc] - mostl[scc] + 1;
//    return num;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    cntt = n;
//    for (int i = 1; i <= n; i++) {
//        cin >> location[i] >> radius[i];
//    }
//    root = build(1, n);
//    for (int i = 1; i <= n; i++) {
//        int l = lower(location[i] - radius[i]);
//        int r = lower(location[i] + radius[i] + 1) - 1;
//        xToRange(i, l, r, 1, n, root);
//    }
//    tarjan(root);
//    condense();
//    dpOnDAG();
//    ll ans = 0;
//    for (int i = 1; i <= n; i++) {
//        ans = (ans + 1LL * query(i) * i) % MOD;
//    }
//    cout << ans << "\n";
//    return 0;
//}