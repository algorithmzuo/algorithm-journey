package class167;

// 异或最短路，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF938G
// 测试链接 : https://codeforces.com/problemset/problem/938/G
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Event {
//    int x, y, t, w;
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
//const int MAXN = 200001;
//const int MAXT = 5000001;
//const int LIMIT = 20;
//const int BIT = 29;
//int n, m, q;
//
//Event event[MAXN << 1];
//int eventCnt = 0;
//
//int op[MAXN];
//int x[MAXN];
//int y[MAXN];
//int d[MAXN];
//
//int basis[BIT + 1];
//int backup[LIMIT][BIT + 1];
//
//int head[MAXN << 2];
//int nxt[MAXT];
//int tox[MAXT];
//int toy[MAXT];
//int tow[MAXT];
//int cnt = 0;
//
//int father[MAXN];
//int siz[MAXN];
//int eor[MAXN];
//int rollbackArr[MAXN][2];
//int opsize = 0;
//
//int ans[MAXN];
//
//void insert(int num) {
//    for (int i = BIT; i >= 0; --i) {
//        if (num >> i == 1) {
//            if (basis[i] == 0) {
//                basis[i] = num;
//                return;
//            }
//            num ^= basis[i];
//        }
//    }
//}
//
//int minEor(int num) {
//    for (int i = BIT; i >= 0; --i) {
//        num = min(num, num ^ basis[i]);
//    }
//    return num;
//}
//
//int find(int i) {
//    while (i != father[i]) {
//        i = father[i];
//    }
//    return i;
//}
//
//int getEor(int i) {
//    int res = 0;
//    while (i != father[i]) {
//        res ^= eor[i];
//        i = father[i];
//    }
//    return res;
//}
//
//bool Union(int a, int b, int w) {
//    int eora = getEor(a);
//    int eorb = getEor(b);
//    int fa = find(a);
//    int fb = find(b);
//    w = eora ^ eorb ^ w;
//    if (fa == fb) {
//        insert(w);
//        return false;
//    }
//    if (siz[fa] < siz[fb]) {
//        int tmp = fa;
//        fa = fb;
//        fb = tmp;
//    }
//    father[fb] = fa;
//    siz[fa] += siz[fb];
//    eor[fb] = w;
//    rollbackArr[++opsize][0] = fa;
//    rollbackArr[opsize][1] = fb;
//    return true;
//}
//
//void undo() {
//    int fa = rollbackArr[opsize][0];
//    int fb = rollbackArr[opsize--][1];
//    father[fb] = fb;
//    eor[fb] = 0;
//    siz[fa] -= siz[fb];
//}
//
//void addEdge(int idx, int u, int v, int w) {
//    nxt[++cnt] = head[idx];
//    tox[cnt] = u;
//    toy[cnt] = v;
//    tow[cnt] = w;
//    head[idx] = cnt;
//}
//
//void add(int jobl, int jobr, int jobx, int joby, int jobw, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx, joby, jobw);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobx, joby, jobw, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobx, joby, jobw, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i, int dep) {
//    memcpy(backup[dep], basis, sizeof(backup[dep]));
//    int unionCnt = 0;
//    for (int e = head[i]; e; e = nxt[e]) {
//        if (Union(tox[e], toy[e], tow[e])) {
//            unionCnt++;
//        }
//    }
//    if (l == r) {
//        if (op[l] == 3) {
//        	ans[l] = minEor(getEor(x[l]) ^ getEor(y[l]));
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1, dep + 1);
//        dfs(mid + 1, r, i << 1 | 1, dep + 1);
//    }
//    memcpy(basis, backup[dep], sizeof(basis));
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
//    sort(event + 1, event + eventCnt + 1, EventCmp);
//    int x, y, start, end, d;
//    for (int l = 1, r = 1; l <= eventCnt; l = ++r) {
//        x = event[l].x;
//        y = event[l].y;
//        while (r + 1 <= eventCnt && event[r + 1].x == x && event[r + 1].y == y) {
//            r++;
//        }
//        for (int i = l; i <= r; i += 2) {
//            start = event[i].t;
//            end = (i + 1 <= r) ? (event[i + 1].t - 1) : q;
//            d = event[i].w;
//            add(start, end, x, y, d, 0, q, 1);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> event[i].x >> event[i].y >> event[i].w;
//        event[i].t = 0;
//    }
//    eventCnt = m;
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> op[i] >> x[i] >> y[i];
//        if (op[i] == 1) {
//            cin >> d[i];
//        }
//        if (op[i] != 3) {
//            event[++eventCnt].x = x[i];
//            event[eventCnt].y = y[i];
//            event[eventCnt].t = i;
//            event[eventCnt].w = d[i];
//        }
//    }
//    prepare();
//    dfs(0, q, 1, 0);
//    for (int i = 1; i <= q; i++) {
//        if (op[i] == 3) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}