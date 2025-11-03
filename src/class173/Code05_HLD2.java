package class173;

// 树上分块模版题，重链序列分块，C++版
// 一共有n个节点，每个节点有点权，给定n-1条边，所有节点连成一棵树
// 接下来有m条操作，每条操作都要打印两个答案，描述如下
// 操作 k x1 y1 x2 y2 .. (一共k个点对) 
// 每个点对(x, y)，在树上都有从x到y的路径，那么k个点对就有k条路径
// 先打印k条路径上不同点权的数量，再打印点权集合中没有出现的最小非负数(mex)
// 1 <= n、点对总数 <= 10^5    点权 <= 30000
// 题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P3603
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXB = 401;
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
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//            ans[val[i]] = 1;
//        }
//    } else {
//        for (int i = l; i <= br[bi[l]]; i++) {
//            ans[val[i]] = 1;
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            ans[val[i]] = 1;
//        }
//        for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
//            ans |= bitSet[i];
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
//        x = fa[top[x]];
//    }
//    query(min(dfn[x], dfn[y]), max(dfn[x], dfn[y]));
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
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    prepare();
//    for (int i = 1, lastAns = 0; i <= m; i++) {
//        ans.reset(); 
//        cin >> k;
//        for (int j = 1, x, y; j <= k; j++) {
//            cin >> x >> y;
//            if (f) {
//                x ^= lastAns;
//                y ^= lastAns;
//            }
//            updateAns(x, y);
//        }
//        int ans1 = ans.count();
//        int ans2 = MAXV;
//        for (int i = 0; i < MAXV; i++) {
//            if (ans[i] == 0) {
//                ans2 = i;
//                break;
//            }
//        }
//        cout << ans1 << ' ' << ans2 << '\n';
//        lastAns = ans1 + ans2;
//    }
//    return 0;
//}