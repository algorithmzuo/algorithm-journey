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
//const int BIT = 30;
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
//int basis[BIT];
//int inspos[BIT];
//int basiz = 0;
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
//int rollback[MAXN][2];
//int opsize = 0;
//
//int ans[MAXN];
//
//void insert(int num) {
//    for (int i = BIT - 1; i >= 0; --i) {
//        if (num >> i == 1) {
//            if (basis[i] == 0) {
//                basis[i] = num;
//                inspos[basiz++] = i;
//                return;
//            }
//            num ^= basis[i];
//        }
//    }
//}
//
//int minEor(int num) {
//    for (int i = BIT - 1; i >= 0; --i) {
//        num = min(num, num ^ basis[i]);
//    }
//    return num;
//}
//
//void cancel(int oldsiz) {
//    while (basiz > oldsiz) {
//        basis[inspos[--basiz]] = 0;
//    }
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
//void Union(int u, int v, int w) {
//    int fu = find(u);
//    int fv = find(v);
//    w = getEor(u) ^ getEor(v) ^ w;
//    if (siz[fu] < siz[fv]) {
//        int tmp = fu;
//        fu = fv;
//        fv = tmp;
//    }
//    father[fv] = fu;
//    siz[fu] += siz[fv];
//    eor[fv] = w;
//    rollback[++opsize][0] = fu;
//    rollback[opsize][1] = fv;
//}
//
//void undo() {
//    int fu = rollback[opsize][0];
//    int fv = rollback[opsize--][1];
//    father[fv] = fv;
//    eor[fv] = 0;
//    siz[fu] -= siz[fv];
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
//void dfs(int l, int r, int i) {
//    int oldsiz = basiz;
//    int unionCnt = 0;
//    int u, v, w, fu, fv, eoru, eorv;
//    for (int e = head[i]; e; e = nxt[e]) {
//        u = tox[e];
//        v = toy[e];
//        w = tow[e];
//        fu = find(u);
//        fv = find(v);
//        eoru = getEor(u);
//        eorv = getEor(v);
//        if (fu == fv) {
//            insert(eoru ^ eorv ^ w);
//        } else {
//            Union(u, v, w);
//            unionCnt++;
//        }
//    }
//    if (l == r) {
//        if (op[l] == 3) {
//        	ans[l] = minEor(getEor(x[l]) ^ getEor(y[l]));
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    cancel(oldsiz);
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
//    dfs(0, q, 1);
//    for (int i = 1; i <= q; i++) {
//        if (op[i] == 3) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}