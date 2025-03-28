package class164;

// 走过z个点的最大边权最小值，C++版
// 测试链接 : https://www.luogu.com.cn/problem/AT_agc002_d
// 测试链接 : https://atcoder.jp/contests/agc002/tasks/agc002_d
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v, w;
//};
//
//const int MAXK = 200001;
//const int MAXM = 100001;
//const int MAXH = 20;
//int n, m, q;
//Edge arr[MAXM];
//
//int head[MAXK];
//int nxt[MAXK];
//int to[MAXK];
//int cntg;
//
//int father[MAXK];
//int nodeKey[MAXK];
//int cntu;
//
//int dep[MAXK];
//int siz[MAXK];
//int stjump[MAXK][MAXH];
//
//bool cmp(Edge x, Edge y) {
//    return x.w < y.w;
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int find(int i) {
//    if (i != father[i]) {
//        father[i] = find(father[i]);
//    }
//    return father[i];
//}
//
//void kruskalRebuild() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//    }
//    sort(arr + 1, arr + m + 1, cmp);
//    cntu = n;
//    for (int i = 1, fx, fy; i <= m; i++) {
//        fx = find(arr[i].u);
//        fy = find(arr[i].v);
//        if (fx != fy) {
//            father[fx] = father[fy] = ++cntu;
//            father[cntu] = cntu;
//            nodeKey[cntu] = arr[i].w;
//            addEdge(cntu, fx);
//            addEdge(cntu, fy);
//        }
//    }
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        dfs(to[e], u);
//    }
//    if (u <= n) {
//        siz[u] = 1;
//    } else {
//        siz[u] = 0;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        siz[u] += siz[to[e]];
//    }
//}
//
//bool check(int x, int y, int z, int limit) {
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[x][p] > 0 && nodeKey[stjump[x][p]] <= limit) {
//            x = stjump[x][p];
//        }
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[y][p] > 0 && nodeKey[stjump[y][p]] <= limit) {
//            y = stjump[y][p];
//        }
//    }
//    if (x == y) {
//        return siz[x] >= z;
//    } else {
//        return siz[x] + siz[y] >= z;
//    }
//}
//
//int query(int x, int y, int z) {
//    int l = 1, r = m, ans = 0;
//    while (l <= r) {
//        int mid = (l + r) / 2;
//        if (check(x, y, z, mid)) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//int main(){
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> arr[i].u >> arr[i].v;
//        arr[i].w = i;
//    }
//    kruskalRebuild();
//    dfs(cntu, 0);
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        int x, y, z;
//        cin >> x >> y >> z;
//        cout << query(x, y, z) << "\n";
//    }
//    return 0;
//}