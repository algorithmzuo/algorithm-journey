package class173;

// 树上分块模版题，重链剖分 + 随机撒点，C++版
// 一共有n个节点，每个节点有点权，给定n-1条边，所有节点连成一棵树
// 接下来有m条操作，每条操作都要打印两个答案，描述如下
// 操作 k x1 y1 x2 y2 .. (一共k个点对) 
// 每个点对(x, y)，在树上都有从x到y的路径，那么k个点对就有k条路径
// 先打印k条路径上不同点权的数量，再打印点权集合中没有出现的最小非负数(mex)
// 1 <= n、m <= 10^5    点权 <= 30000
// 题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P3603
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXB = 301;
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
//
//int bnum;
//bool vis[MAXN];
//int tag[MAXN];
//int spe[MAXN];
//int up[MAXN];
//bitset<MAXV> bitSet[MAXB][MAXB];
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
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
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
//int lca(int a, int b) {
//    while (top[a] != top[b]) {
//        if (dep[top[a]] <= dep[top[b]]) {
//            b = fa[top[b]];
//        } else {
//            a = fa[top[a]];
//        }
//    }
//    return dep[a] <= dep[b] ? a : b;
//}
//
//void query(int x, int xylca) {
//    while (spe[x] == 0 && x != xylca) {
//    	ans[arr[x]] = 1;
//        x = fa[x];
//    }
//    int backup = x;
//    while (up[x] && dep[up[x]] > dep[xylca]) {
//        x = up[x];
//    }
//    ans |= bitSet[spe[backup]][spe[x]];
//    while (x != xylca) {
//    	ans[arr[x]] = 1;
//        x = fa[x];
//    }
//}
//
//void updateAns(int x, int y) {
//    int xylca = lca(x, y);
//    ans[arr[xylca]] = 1;
//    query(x, xylca);
//    query(y, xylca);
//}
//
//void prepare() {
//    dfs1(1, 0);
//    dfs2(1, 1);
//    int blen = (int)sqrt(20.0 * n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1, pick; i <= bnum; i++) {
//        do {
//            pick = rand() % n + 1;
//        } while (vis[pick]);
//        vis[pick] = true;
//        tag[i] = pick;
//        spe[pick] = i;
//    }
//    for (int i = 1, cur; i <= bnum; i++) {
//        tmp.reset();
//        tmp[arr[tag[i]]] = 1;
//        cur = fa[tag[i]];
//        while (cur != 0) {
//            tmp[arr[cur]] = 1;
//            if (spe[cur] > 0) {
//                bitSet[i][spe[cur]] |= tmp;
//                if (up[tag[i]] == 0) {
//                    up[tag[i]] = cur;
//                }
//            }
//            cur = fa[cur];
//        }
//    }	
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand(time(0));
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
//    for (int i = 1, last = 0; i <= m; i++) {
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
//        int ans2 = ans._Find_first();
//        cout << ans1 << ' ' << ans2 << '\n';
//        last = ans1 + ans2;
//    }
//    return 0;
//}