package class173;

// 树上分块模版题，重链剖分后序列分块，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3603
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXB = 1001;
//const int MAXV = 30001;
//int n, m, f, k;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dfn[MAXN];
//int val[MAXN];
//int cntd;
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//bitset<MAXV> bitSet[MAXB];
//
//bitset<MAXV> tmp;
//bitset<MAXV> ans;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            siz[u] += siz[v];
//            if (!son[u] || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    dfn[u] = ++cntd;
//    val[cntd] = arr[u];
//    if (!son[u]) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void query(int l, int r) {
//    tmp.reset();
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//        	tmp[val[i]] = 1;
//        }
//    } else {
//        for (int i = l; i <= br[bi[l]]; i++) {
//        	tmp[val[i]] = 1;
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//        	tmp[val[i]] = 1;
//        }
//        for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
//        	tmp |= bitSet[i];
//        }
//    }
//}
//
//void updateAns(int x, int y) {
//    while (top[x] != top[y]) {
//        if (dep[top[x]] < dep[top[y]]) {
//            swap(x, y);
//        }
//        query(dfn[top[x]], dfn[x]);
//        ans |= tmp;
//        x = fa[top[x]];
//    }
//    query(min(dfn[x], dfn[y]), max(dfn[x], dfn[y]));
//    ans |= tmp;
//}
//
//void prepare() {
//    dfs1(1, 0);
//    dfs2(1, 1);
//    blen = (int)sqrt(n * 20);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//        for (int j = bl[i]; j <= br[i]; j++) {
//            bitSet[i][val[j]] = 1;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> f;
//    for (int i = 1; i <= n; ++i) {
//        cin >> arr[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    prepare();
//    int last = 0;
//    for (int i = 1; i <= m; i++) {
//        ans.reset(); 
//        cin >> k;
//        for (int j = 1, x, y; j <= k; j++) {
//            cin >> x >> y;
//            if (f) {
//                x ^= last;
//                y ^= last;
//            }
//            updateAns(x, y);
//        }
//        int ans1 = ans.count();
//        ans.flip();
//		int ans2 = ans._Find_first();
//        cout << ans1 << ' ' << ans2 << '\n';
//        last = ans1 + ans2;
//    }
//    return 0;
//}