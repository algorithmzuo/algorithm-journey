package class194;

// 警方，C++版
// 输入保证无重边
// 测试链接 : https://www.luogu.com.cn/problem/P4334
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 500001;
//int n, m, q, cntn;
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
//int cnts;
//
//map<pair<int, int>, int> cutMap;
//
//int fa[MAXN << 1];
//int dep[MAXN << 1];
//int siz[MAXN << 1];
//int son[MAXN << 1];
//int top[MAXN << 1];
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
//void addCut(int x, int y, int cut) {
//    int a = min(x, y);
//    int b = max(x, y);
//    cutMap[{a, b}] = cut;
//}
//
//int getCut(int x, int y) {
//    int a = min(x, y);
//    int b = max(x, y);
//    auto ans = cutMap.find({a, b});
//    return ans == cutMap.end() ? 0 : ans -> second;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++cnts] = u;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to1[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                cntn++;
//                if (low[v] > dfn[u]) {
//                    addCut(u, v, cntn);
//                }
//                addEdge2(cntn, u);
//                addEdge2(u, cntn);
//                int pop;
//                do {
//                    pop = sta[cnts--];
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
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head2[u], v; e > 0; e = next2[e]) {
//        v = to2[e];
//        if (v != f) {
//            dfs1(v, u);
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head2[u], v; e > 0; e = next2[e]) {
//        v = to2[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//bool mustPass(int a, int b, int c) {
//    while (top[a] != top[b]) {
//        if (dep[top[a]] < dep[top[b]]) {
//            swap(a, b);
//        }
//        if (top[c] == top[a] && dep[c] <= dep[a]) {
//            return true;
//        }
//        a = fa[top[a]];
//    }
//    if (dep[a] < dep[b]) {
//        swap(a, b);
//    }
//    return top[a] == top[c] && dep[b] <= dep[c] && dep[c] <= dep[a];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntn = n;
//    cnt1 = 1;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    tarjan(1, 0);
//    dfs1(1, 0);
//    dfs2(1, 1);
//    cin >> q;
//    for (int i = 1, op, a, b, c, d; i <= q; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> a >> b >> c >> d;
//            int cut = getCut(c, d);
//            if (cut == 0) {
//                cout << "yes\n";
//            } else {
//                cout << (mustPass(a, b, cut) ? "no" : "yes") << "\n";
//            }
//        } else {
//            cin >> a >> b >> c;
//            cout << (mustPass(a, b, c) ? "no" : "yes") << "\n";
//        }
//    }
//    return 0;
//}