package class173;

// 树上分块模版题，随机撒点分块，C++版
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
//const int LIMIT = 17;
//int n, m, f, k;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dep[MAXN];
//int stjump[MAXN][LIMIT];
//
//int bnum;
//bool vis[MAXN];
//int capital[MAXN];
//int belong[MAXN];
//int top[MAXN];
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
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < LIMIT; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        if (to[e] != fa) {
//            dfs(to[e], u);
//        }
//    }
//}
//
//int lca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        swap(a, b);
//    }
//    for (int p = LIMIT - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = LIMIT - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//void query(int x, int xylca) {
//    while (belong[x] == 0 && x != xylca) {
//    	ans[arr[x]] = 1;
//        x = stjump[x][0];
//    }
//    int backup = x;
//    while (top[x] && dep[top[x]] > dep[xylca]) {
//        x = top[x];
//    }
//    ans |= bitSet[belong[backup]][belong[x]];
//    while (x != xylca) {
//    	ans[arr[x]] = 1;
//        x = stjump[x][0];
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
//    dfs(1, 0);
//    int blen = (int)sqrt(20.0 * n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1, pick; i <= bnum; i++) {
//        do {
//            pick = rand() % n + 1;
//        } while (vis[pick]);
//        vis[pick] = true;
//        capital[i] = pick;
//        belong[pick] = i;
//    }
//    for (int i = 1, cur; i <= bnum; i++) {
//        tmp.reset();
//        tmp[arr[capital[i]]] = 1;
//        cur = stjump[capital[i]][0];
//        while (cur != 0) {
//            tmp[arr[cur]] = 1;
//            if (belong[cur] > 0) {
//                bitSet[i][belong[cur]] |= tmp;
//                if (top[capital[i]] == 0) {
//                    top[capital[i]] = cur;
//                }
//            }
//            cur = stjump[cur][0];
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