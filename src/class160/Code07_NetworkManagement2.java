package class160;

// 网络管理，C++版
// 一共有n个点，编号1~n，每个点给定点权，给定n-1条边，所有点连成一棵树
// 实现如下类型的操作，操作一共发生m次
// 操作 0 x y : x号点的点权变成y
// 操作 k x y : 保证k > 0，点x到点y的路径上，打印第k大的值
//              如果路径上不够k个点，打印"invalid request!"
// 1 <= n、m <= 8 * 10^4
// 点权 <= 10^8
// 测试链接 : https://www.luogu.com.cn/problem/P4175
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 80001;
//const int MAXT = MAXN * 110;
//const int MAXH = 18;
//int n, m, s;
//
//int arr[MAXN];
//int ques[MAXN][3];
//int sorted[MAXN << 1];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int sum[MAXT];
//int cntt;
//
//int deep[MAXN];
//int size[MAXN];
//int dfn[MAXN];
//int stjump[MAXN][MAXH];
//int cntd;
//
//int addTree[MAXN];
//int minusTree[MAXN];
//int cntadd, cntminus;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int kth(int num) {
//    int ls = 1, rs = s, mid;
//    while (ls <= rs) {
//        mid = (ls + rs) / 2;
//        if (sorted[mid] == num) return mid;
//        else if (sorted[mid] < num) ls = mid + 1;
//        else rs = mid - 1;
//    }
//    return -1;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void dfs(int u, int fa) {
//    deep[u] = deep[fa] + 1;
//    size[u] = 1;
//    dfn[u] = ++cntd;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        if (to[e] != fa) dfs(to[e], u);
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        if (to[e] != fa) size[u] += size[to[e]];
//    }
//}
//
//int lca(int a, int b) {
//    if (deep[a] < deep[b]) swap(a, b);
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (deep[stjump[a][p]] >= deep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) return a;
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int innerAdd(int jobi, int jobv, int l, int r, int i) {
//    if (i == 0) i = ++cntt;
//    if (l == r) {
//        sum[i] += jobv;
//    } else {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//            ls[i] = innerAdd(jobi, jobv, l, mid, ls[i]);
//        } else {
//            rs[i] = innerAdd(jobi, jobv, mid + 1, r, rs[i]);
//        }
//        sum[i] = sum[ls[i]] + sum[rs[i]];
//    }
//    return i;
//}
//
//int innerQuery(int jobk, int l, int r) {
//    if (l == r) return l;
//    int mid = (l + r) / 2;
//    int leftsum = 0;
//    for (int i = 1; i <= cntadd; i++) {
//        leftsum += sum[ls[addTree[i]]];
//    }
//    for (int i = 1; i <= cntminus; i++) {
//        leftsum -= sum[ls[minusTree[i]]];
//    }
//    if (jobk <= leftsum) {
//        for (int i = 1; i <= cntadd; i++) {
//            addTree[i] = ls[addTree[i]];
//        }
//        for (int i = 1; i <= cntminus; i++) {
//            minusTree[i] = ls[minusTree[i]];
//        }
//        return innerQuery(jobk, l, mid);
//    } else {
//        for (int i = 1; i <= cntadd; i++) {
//            addTree[i] = rs[addTree[i]];
//        }
//        for (int i = 1; i <= cntminus; i++) {
//            minusTree[i] = rs[minusTree[i]];
//        }
//        return innerQuery(jobk - leftsum, mid + 1, r);
//    }
//}
//
//void add(int i, int kth, int val) {
//    for (; i <= n; i += lowbit(i)) {
//        root[i] = innerAdd(kth, val, 1, s, root[i]);
//    }
//}
//
//void update(int i, int v) {
//    add(dfn[i], arr[i], -1);
//    add(dfn[i] + size[i], arr[i], 1);
//    arr[i] = kth(v);
//    add(dfn[i], arr[i], 1);
//    add(dfn[i] + size[i], arr[i], -1);
//}
//
//int query(int x, int y, int k) {
//    int xylca = lca(x, y);
//    int lcafa = stjump[xylca][0];
//    int num = deep[x] + deep[y] - deep[xylca] - deep[lcafa];
//    if (num < k) return -1;
//    cntadd = cntminus = 0;
//    for (int i = dfn[x]; i; i -= lowbit(i)) {
//        addTree[++cntadd] = root[i];
//    }
//    for (int i = dfn[y]; i; i -= lowbit(i)) {
//        addTree[++cntadd] = root[i];
//    }
//    for (int i = dfn[xylca]; i; i -= lowbit(i)) {
//        minusTree[++cntminus] = root[i];
//    }
//    for (int i = dfn[lcafa]; i; i -= lowbit(i)) {
//        minusTree[++cntminus] = root[i];
//    }
//    return sorted[innerQuery(num - k + 1, 1, s)];
//}
//
//void prepare() {
//    s = 0;
//    for (int i = 1; i <= n; i++) sorted[++s] = arr[i];
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 0) sorted[++s] = ques[i][2];
//    }
//    sort(sorted + 1, sorted + s + 1);
//    s = unique(sorted + 1, sorted + s + 1) - sorted - 1;
//    for (int i = 1; i <= n; i++) arr[i] = kth(arr[i]);
//    dfs(1, 0);
//    for (int i = 1; i <= n; i++) {
//        add(dfn[i], arr[i], 1);
//        add(dfn[i] + size[i], arr[i], -1);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) cin >> arr[i];
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= m; i++) cin >> ques[i][0] >> ques[i][1] >> ques[i][2];
//    prepare();
//    for (int i = 1, k, x, y; i <= m; i++) {
//        k = ques[i][0];
//        x = ques[i][1];
//        y = ques[i][2];
//        if (k == 0) {
//        	update(x, y);
//        } else {
//            int ans = query(x, y, k);
//            if(ans == -1) {
//            	cout << "invalid request!" << "\n";
//            } else {
//            	cout << ans << "\n";
//            }
//        }
//    }
//    return 0;
//}