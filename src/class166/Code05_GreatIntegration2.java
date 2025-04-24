package class166;

// 大融合，C++版
// 一共有n个点，一共有q条操作，每条操作是如下两种类型中的一种
// 操作 A x y : 点x和点y之间连一条边，保证之前x和y是不联通的
// 操作 Q x y : 打印点x和点y之间这条边的负载，保证x和y之间有一条边
// 边负载定义为，这条边两侧端点各自连通区大小的乘积
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4219
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Event {
//    int x, y, t;
//};
//
//bool EventCmp(Event a, Event b) {
//    if (a.x != b.x) {
//        return a.x < b.x;
//    } else if (a.y != b.y) {
//        return a.y < b.y;
//    } else {
//        return a.t < b.t;
//    }
//}
//
//const int MAXN = 100001;
//const int MAXT = 3000001;
//int n, q;
//
//int op[MAXN];
//int u[MAXN];
//int v[MAXN];
//
//Event event[MAXN];
//
//int father[MAXN];
//int siz[MAXN];
//int rollback[MAXN][2];
//int opsize = 0;
//
//int head[MAXN << 2];
//int nxt[MAXT];
//int tox[MAXT];
//int toy[MAXT];
//int cnt = 0;
//
//long long ans[MAXN];
//
//void addEdge(int i, int x, int y) {
//    nxt[++cnt] = head[i];
//    tox[cnt] = x;
//    toy[cnt] = y;
//    head[i] = cnt;
//}
//
//int find(int i) {
//    while (i != father[i]) {
//        i = father[i];
//    }
//    return i;
//}
//
//void Union(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (siz[fx] < siz[fy]) {
//        int tmp = fx;
//        fx = fy;
//        fy = tmp;
//    }
//    father[fy] = fx;
//    siz[fx] += siz[fy];
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//}
//
//void undo() {
//    int fx = rollback[opsize][0];
//    int fy = rollback[opsize--][1];
//    father[fy] = fy;
//    siz[fx] -= siz[fy];
//}
//
//void add(int jobl, int jobr, int jobx, int joby, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx, joby);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobx, joby, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobx, joby, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i) {
//    int unionCnt = 0;
//    for (int ei = head[i], fx, fy; ei > 0; ei = nxt[ei]) {
//        fx = find(tox[ei]);
//        fy = find(toy[ei]);
//        if (fx != fy) {
//            Union(fx, fy);
//            unionCnt++;
//        }
//    }
//    if (l == r) {
//        if (op[l] == 2) {
//            ans[l] = 1LL * siz[find(u[l])] * siz[find(v[l])];
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    for (int k = 1; k <= unionCnt; k++) {
//        undo();
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        siz[i] = 1;
//    }
//    for (int i = 1; i <= q; i++) {
//        event[i].x = u[i];
//        event[i].y = v[i];
//        event[i].t = i;
//    }
//    sort(event + 1, event + q + 1, EventCmp);
//    for (int l = 1, r = 1; l <= q; l = ++r) {
//        int x = event[l].x, y = event[l].y, t = event[l].t;
//        while (r + 1 <= q && event[r + 1].x == x && event[r + 1].y == y) {
//            r++;
//        }
//        for (int j = l + 1; j <= r; j++) {
//            add(t, event[j].t - 1, x, y, 1, q, 1);
//            t = event[j].t + 1;
//        }
//        if (t <= q) {
//            add(t, q, x, y, 1, q, 1);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    char t;
//    int x, y;
//    for (int i = 1; i <= q; i++) {
//        cin >> t >> x >> y;
//        op[i] = (t == 'A') ? 1 : 2;
//        u[i] = min(x, y);
//        v[i] = max(x, y);
//    }
//    prepare();
//    dfs(1, q, 1);
//    for (int i = 1; i <= q; i++) {
//        if (op[i] == 2) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}