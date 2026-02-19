package class192;

// 追寻文物，C++版
// 给定一张无向图，一共n个点、m条边，保证所有点连通
// 每条边除了端点之外，还有一个属性，值为1表示该边上有商店，值为0表示该边上无商店
// 给定起点s和终点t，路途怎么走随意，但是沿途每条边只能经过一次
// 从s到t的路途中能遇到商店打印"YES"，否则打印"NO"
// 1 <= n、m <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF652E
// 测试链接 : https://codeforces.com/problemset/problem/652/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//const int MAXM = 300001;
//int n, m, s, t;
//int a[MAXM];
//int b[MAXM];
//int c[MAXM];
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int weight[MAXM << 1];
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
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
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
//        int w = c[i];
//        if (ebcc1 == ebcc2) {
//            if (w == 1) {
//                val[ebcc1] = 1;
//            }
//        } else {
//            addEdge(ebcc1, ebcc2, w);
//            addEdge(ebcc2, ebcc1, w);
//        }
//    }
//}
//
//bool check(int u, int fa, bool ok) {
//    ok |= val[u] > 0;
//    if (u == t) {
//        return ok;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa) {
//            if (check(v, u, ok || w > 0)) {
//                return true;
//            }
//        }
//    }
//    return false;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cntg = 1;
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i] >> c[i];
//        addEdge(a[i], b[i], 0);
//        addEdge(b[i], a[i], 0);
//    }
//    tarjan(1, 0);
//    condense();
//    cin >> s >> t;
//    s = belong[s];
//    t = belong[t];
//    if (check(s, 0, false)) {
//        cout << "YES\n";
//    } else {
//        cout << "NO\n";
//    }
//    return 0;
//}