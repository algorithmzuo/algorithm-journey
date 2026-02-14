package class192;

// 旅行者，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P7924
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXM = 2000001;
//const int MAXP = 20;
//int n, m, q;
//int arr[MAXN];
//int a[MAXM];
//int b[MAXM];
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
//int belong[MAXN];
//int val[MAXN];
//int ebccCnt;
//
//int lg2[MAXN];
//int rmq[MAXN][MAXP];
//
//int pass[MAXN];
//int ans;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            low[u] = min(low[u], low[v]);
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//    if (dfn[u] == low[u]) {
//        ebccCnt++;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = ebccCnt;
//            val[ebccCnt] += arr[pop];
//        } while (pop != u);
//    }
//}
//
//void condense() {
//    cntg = 0;
//    for (int i = 1; i <= ebccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= m; i++) {
//        int ebcc1 = belong[a[i]];
//        int ebcc2 = belong[b[i]];
//        if (ebcc1 != ebcc2) {
//            addEdge(ebcc1, ebcc2);
//            addEdge(ebcc2, ebcc1);
//        }
//    }
//}
//
//int getUp(int x, int y) {
//    return dfn[x] < dfn[y] ? x : y;
//}
//
//void dfs(int u, int fa) {
//    dfn[u] = ++cntd;
//    rmq[dfn[u]][0] = fa;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//void buildRmq() {
//    cntd = 0;
//    dfs(1, 0);
//    for (int i = 2; i <= ebccCnt; i++) {
//        lg2[i] = lg2[i >> 1] + 1;
//    }
//    for (int pre = 0, cur = 1; cur <= lg2[ebccCnt]; pre++, cur++) {
//        for (int i = 1; i + (1 << cur) - 1 <= ebccCnt; i++) {
//            rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
//        }
//    }
//}
//
//int getFather(int x) {
//    return rmq[dfn[x]][0];
//}
//
//int getLCA(int x, int y) {
//    if (x == y) {
//        return x;
//    }
//    x = dfn[x];
//    y = dfn[y];
//    if (x > y) {
//        swap(x, y);
//    }
//    x++;
//    int k = lg2[y - x + 1];
//    return getUp(rmq[x][k], rmq[y - (1 << k) + 1][k]);
//}
//
//void getAns(int u, int fa) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            getAns(v, u);
//            pass[u] += pass[v];
//        }
//    }
//    if (pass[u] > 0) {
//        ans += val[u];
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cntg = 1;
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//        addEdge(b[i], a[i]);
//    }
//    tarjan(1, 0);
//    condense();
//    buildRmq();
//    cin >> q;
//    for (int i = 1, x, y, xylca, lcafa; i <= q; i++) {
//        cin >> x >> y;
//        x = belong[x];
//        y = belong[y];
//        xylca = getLCA(x, y);
//        lcafa = getFather(xylca);
//        pass[x]++;
//        pass[y]++;
//        pass[xylca]--;
//        pass[lcafa]--;
//    }
//    getAns(1, 0);
//    cout << ans << "\n";
//    return 0;
//}