package class194;

// 游客，C++版
// 一共n个城市、m条双向道路，所有城市都连通，商品只有一种
// 每个城市给定商品报价，接下来有q条操作，操作类型有两种
// 操作 C x y : 城市x的商品报价改成y
// 操作 A x y : 从x到y可自由选路，但不能有重复城市，打印能遇到的最低报价
// 1 <= n、m、q <= 10^5
// 1 <= 商品报价 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF487E
// 测试链接 : https://codeforces.com/problemset/problem/487/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 100001;
//const int INF = 1000000001;
//int n, m, q, cntn;
//int arr[MAXN];
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
//int fa[MAXN << 1];
//int dep[MAXN << 1];
//int siz[MAXN << 1];
//int son[MAXN << 1];
//int top[MAXN << 1];
//int nid[MAXN << 1];
//int cnti;
//
//unordered_map<int, map<int, int>> maps;
//int val[MAXN << 1];
//int minv[MAXN << 3];
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
//void addNum(int u, int num) {
//    if (!maps.count(u)) {
//        maps.emplace(u, map<int, int>());
//    }
//    auto &mp = maps[u];
//    auto it = mp.find(num);
//    if (it == mp.end()) {
//        mp.emplace(num, 1);
//    } else {
//        it->second++;
//    }
//}
//
//void delNum(int u, int num) {
//    auto &mp = maps[u];
//    int cnt = mp[num];
//    if (cnt == 1) {
//        mp.erase(num);
//    } else {
//        mp[num] = cnt - 1;
//    }
//}
//
//int getMin(int u) {
//    return maps[u].begin()->first;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++cnts] = u;
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
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != f) {
//            dfs1(v, u);
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//            if (u > n) {
//                addNum(u, arr[v]);
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    nid[u] = ++cnti;
//    val[nid[u]] = u <= n ? arr[u] : getMin(u);
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
//void up(int i) {
//    minv[i] = min(minv[i << 1], minv[i << 1 | 1]);
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        minv[i] = val[l];
//    } else {
//        int mid = (l + r) / 2;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//void update(int jobi, int jobv, int l, int r, int i) {
//    if (l == r) {
//        minv[i] = jobv;
//    } else {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//            update(jobi, jobv, l, mid, i << 1);
//        } else {
//            update(jobi, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return minv[i];
//    }
//    int mid = (l + r) / 2;
//    int ans = INF;
//    if (jobl <= mid) {
//        ans = min(ans, query(jobl, jobr, l, mid, i << 1));
//    }
//    if (jobr > mid) {
//        ans = min(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
//    }
//    return ans;
//}
//
//int pathMin(int x, int y) {
//    int ans = INF;
//    while (top[x] != top[y]) {
//        if (dep[top[x]] < dep[top[y]]) {
//        	swap(x, y);
//        }
//        ans = min(ans, query(nid[top[x]], nid[x], 1, cnti, 1));
//        x = fa[top[x]];
//    }
//    if (dep[x] < dep[y]) {
//        swap(x, y);
//    }
//    ans = min(ans, query(nid[y], nid[x], 1, cnti, 1));
//    if (y > n) {
//        ans = min(ans, arr[fa[y]]);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    cntn = n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    tarjan(1);
//    dfs1(1, 0);
//    dfs2(1, 1);
//    build(1, cnti, 1);
//    for (int i = 1; i <= q; i++) {
//        char op;
//        int x, y;
//        cin >> op >> x >> y;
//        if (op == 'C') {
//            int father = fa[x];
//            if (father > 0) {
//                delNum(father, arr[x]);
//                addNum(father, y);
//                update(nid[father], getMin(father), 1, cnti, 1);
//            }
//            arr[x] = y;
//            update(nid[x], y, 1, cnti, 1);
//        } else {
//            cout << pathMin(x, y) << "\n";
//        }
//    }
//    return 0;
//}