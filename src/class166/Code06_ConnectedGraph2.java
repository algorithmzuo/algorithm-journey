package class166;

// 连通图，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5227
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Event {
//    int ei, t;
//};
//
//bool EventCmp(Event a, Event b) {
//    if (a.ei != b.ei) {
//        return a.ei < b.ei;
//    } else {
//        return a.t < b.t;
//    }
//}
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//const int MAXE = 400001;
//const int MAXT = 10000001;
//
//int n, m, k;
//
//int u[MAXM];
//int v[MAXM];
//
//Event event[MAXE];
//int ecnt = 0;
//bool vis[MAXM];
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
//bool ans[MAXN];
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
//    bool check = false;
//    int unionCnt = 0;
//    for (int ei = head[i]; ei > 0; ei = nxt[ei]) {
//        int x = tox[ei], y = toy[ei], fx = find(x), fy = find(y);
//        if (fx != fy) {
//            Union(fx, fy);
//            unionCnt++;
//        }
//        if (siz[find(fx)] == n) {
//            check = true;
//            break;
//        }
//    }
//    if (check) {
//        for (int j = l; j <= r; j++) {
//            ans[j] = true;
//        }
//    } else {
//        if (l == r) {
//            ans[l] = false;
//        } else {
//            int mid = (l + r) >> 1;
//            dfs(l, mid, i << 1);
//            dfs(mid + 1, r, i << 1 | 1);
//        }
//    }
//    for (int j = 1; j <= unionCnt; j++) {
//        undo();
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        siz[i] = 1;
//    }
//    sort(event + 1, event + ecnt + 1, EventCmp);
//    for (int l = 1, r = 1, eid; l <= ecnt; l = ++r) {
//        eid = event[l].ei;
//        vis[eid] = true;
//        while (r + 1 <= ecnt && event[r + 1].ei == eid) {
//            r++;
//        }
//        int t = 1;
//        for (int i = l; i <= r; i++) {
//            if (t <= event[i].t - 1) {
//                add(t, event[i].t - 1, u[eid], v[eid], 1, k, 1);
//            }
//            t = event[i].t + 1;
//        }
//        if (t <= k) {
//            add(t, k, u[eid], v[eid], 1, k, 1);
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        if (!vis[i]) {
//            add(1, k, u[i], v[i], 1, k, 1);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> u[i] >> v[i];
//    }
//    cin >> k;
//    for (int i = 1, s; i <= k; i++) {
//        cin >> s;
//        for (int j = 1; j <= s; j++) {
//            cin >> event[++ecnt].ei;
//            event[ecnt].t = i;
//        }
//    }
//    prepare();
//    dfs(1, k, 1);
//    for (int i = 1; i <= k; i++) {
//        if (ans[i]) {
//            cout << "Connected" << "\n";
//        } else {
//            cout << "Disconnected" << "\n";
//        }
//    }
//    return 0;
//}