package class194;

// 国土规划，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P10517
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//const int MAXP = 20;
//int n, m, q, cntn;
//bool arr[MAXN];
//
//int head1[MAXN];
//int next1[MAXM << 1];
//int to1[MAXM << 1];
//int cnt1;
//
//int head2[MAXN << 1];
//int next2[MAXM << 2];
//int to2[MAXM << 2];
//int cnt2;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//int nid[MAXN << 1];
//int seg[MAXN << 1];
//int dist[MAXN << 1];
//int dep[MAXN << 1];
//int stjump[MAXN << 1][MAXP];
//int cnti;
//
//set<int> st;
//set<int>::iterator it;
//int sumv;
//
//void addEdge1(int u, int v) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    head2[u] = cnt2;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                cntn++;
//                addEdge2(cntn, u);
//                addEdge2(u, cntn);
//                int pop;
//                do {
//                    pop = sta[top--];
//                    addEdge2(cntn, pop);
//                    addEdge2(pop, cntn);
//                } while (pop != v);
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//void dfs(int u, int fa) {
//    nid[u] = ++cnti;
//    seg[cnti] = u;
//    dist[u] = dist[fa] + (u <= n ? 1 : 0);
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        swap(a, b);
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int getDist(int x, int y) {
//    return dist[x] + dist[y] - 2 * dist[getLca(x, y)];
//}
//
//int compute(int u) {
//    int id = nid[u];
//    if (!arr[u]) {
//        arr[u] = true;
//        st.insert(id);
//    } else {
//        arr[u] = false;
//        st.erase(id);
//    }
//    if (st.size() <= 1) {
//        sumv = 0;
//    } else {
//        int low = seg[(it = st.lower_bound(id)) == st.begin() ? *--st.end() : *--it];
//        int high = seg[(it = st.upper_bound(id)) == st.end() ? *st.begin() : *it];
//        int delta = getDist(u, low) + getDist(u, high) - getDist(low, high);
//        if (arr[u]) {
//            sumv += delta;
//        } else {
//            sumv -= delta;
//        }
//    }
//    if (st.empty()) {
//        return 0;
//    }
//    int extra = getLca(seg[*st.begin()], seg[*st.rbegin()]) <= n ? 1 : 0;
//    return sumv / 2 + extra;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    cntn = n;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    tarjan(1);
//    dfs(1, 0);
//    for (int i = 1, x; i <= q; i++) {
//        cin >> x;
//        int ans = n - compute(x);
//        cout << ans << "\n";
//    }
//    return 0;
//}