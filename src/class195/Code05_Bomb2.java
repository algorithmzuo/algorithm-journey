package class195;

// 炸弹，C++版
// 一共有n个炸弹，所有炸弹排成一条直线，给定每个炸弹的坐标xi、爆炸半径ri
// 炸弹A引爆时，如果炸弹B在其影响范围里，那么炸弹B也会引爆，进而引发一连串的爆炸
// 炸弹i如果作为初始引爆的炸弹，最终会引爆多少个炸弹记为query(i)
// 计算i = 1 2 .. n时，i * query(i)的累加和，答案对 1000000007 取余
// 1 <= n <= 5 * 10^5
// -(10^18) <= xi <= +(10^18)，题目依次输入的坐标保证严格递增
// 0 <= ri <= 2 * 10^18
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
//void saveEdge(int u, int v) {
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
//        addEdge(ls[rt], rt);
//        addEdge(rs[rt], rt);
//        saveEdge(ls[rt], rt);
//        saveEdge(rs[rt], rt);
//    }
//    rangel[rt] = l;
//    ranger[rt] = r;
//    return rt;
//}
//
//void rangeToX(int jobl, int jobr, int jobx, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx);
//        saveEdge(i, jobx);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            rangeToX(jobl, jobr, jobx, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            rangeToX(jobl, jobr, jobx, mid + 1, r, rs[i]);
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
//            addEdge(scc1, scc2);
//        }
//    }
//}
//
//void dpOnDAG() {
//    for (int u = sccCnt; u > 0; u--) {
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
//        rangeToX(l, r, i, 1, n, root);
//    }
//    for (int i = 1; i <= cntt; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    condense();
//    dpOnDAG();
//    ll ans = 0;
//    for (int i = 1; i <= n; i++) {
//        ans = (ans + 1LL * query(i) * i) % MOD;
//    }
//    cout << ans << "\n";
//    return 0;
//}