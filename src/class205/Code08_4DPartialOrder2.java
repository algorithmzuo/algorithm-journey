package class205;

// 四维偏序最长链，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3769
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct ABCD {
//    int a, b, c, d;
//};
//
//bool ABCDCmp(ABCD x, ABCD y) {
//    if (x.a != y.a) return x.a < y.a;
//    if (x.b != y.b) return x.b < y.b;
//    if (x.c != y.c) return x.c < y.c;
//    return x.d < y.d;
//}
//
//struct BI {
//    int b, i;
//};
//
//bool BICmp(BI x, BI y) {
//    if (x.b != y.b) return x.b < y.b;
//    return x.i < y.i;
//}
//
//struct CD {
//    int c, d;
//};
//
//const int MAXN = 50001;
//const int MAXT = 500001;
//const int INF = 1 << 30;
//int n, cntn;
//
//ABCD abcd[MAXN];
//
//BI bi[MAXN];
//int ranking[MAXN];
//
//CD cd[MAXN];
//
//CD kdtcd[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int cmin[MAXT];
//int cmax[MAXT];
//int dmin[MAXT];
//int dmax[MAXT];
//
//int dp[MAXT];
//int maxdp[MAXT];
//int tag[MAXT];
//
//int root[MAXN];
//
//int first, last;
//
//void partition(int l, int r, int pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        int cur = dimension == 0 ? cd[i].c : cd[i].d;
//        if (cur == pivot) {
//            i++;
//        } else if (cur < pivot) {
//            swap(cd[first++], cd[i++]);
//        } else {
//            swap(cd[i], cd[last--]);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        int idx = l + rand() % (r - l + 1);
//        int pivot = dimension == 0 ? cd[idx].c : cd[idx].d;
//        partition(l, r, pivot, dimension);
//        if (i < first) {
//            r = first - 1;
//        } else if (i > last) {
//            l = last + 1;
//        } else {
//            break;
//        }
//    }
//}
//
//void maintain(int i) {
//    maxdp[i] = max(dp[i], max(maxdp[ls[i]], maxdp[rs[i]]));
//    cmin[i] = min(kdtcd[i].c, min(cmin[ls[i]], cmin[rs[i]]));
//    cmax[i] = max(kdtcd[i].c, max(cmax[ls[i]], cmax[rs[i]]));
//    dmin[i] = min(kdtcd[i].d, min(dmin[ls[i]], dmin[rs[i]]));
//    dmax[i] = max(kdtcd[i].d, max(dmax[ls[i]], dmax[rs[i]]));
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    int rt = ++cntn;
//    randSelect(l, r, mid, dimension);
//    kdtcd[rt].c = cd[mid].c;
//    kdtcd[rt].d = cd[mid].d;
//    ls[rt] = build(l, mid - 1, dimension ^ 1);
//    rs[rt] = build(mid + 1, r, dimension ^ 1);
//    maintain(rt);
//    return rt;
//}
//
//void lazy(int i, int v) {
//    if (i != 0) {
//        dp[i] = max(dp[i], v);
//        maxdp[i] = max(maxdp[i], v);
//        tag[i] = max(tag[i], v);
//    }
//}
//
//void down(int i) {
//    if (tag[i] != 0) {
//        lazy(ls[i], tag[i]);
//        lazy(rs[i], tag[i]);
//        tag[i] = 0;
//    }
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//int queryAns;
//
//void updateAns(int c, int d, int i) {
//    if (i == 0) {
//        return;
//    }
//    if (cmin[i] > c || dmin[i] > d) {
//        return;
//    }
//    if (maxdp[i] <= queryAns) {
//        return;
//    }
//    if (cmax[i] <= c && dmax[i] <= d) {
//        queryAns = max(queryAns, maxdp[i]);
//        return;
//    }
//    down(i);
//    if (kdtcd[i].c <= c && kdtcd[i].d <= d) {
//        queryAns = max(queryAns, dp[i]);
//    }
//    updateAns(c, d, ls[i]);
//    updateAns(c, d, rs[i]);
//}
//
//int query(int rank, int c, int d) {
//    queryAns = 0;
//    for (int i = rank; i > 0; i -= lowbit(i)) {
//        updateAns(c, d, root[i]);
//    }
//    return queryAns;
//}
//
//void update(int c, int d, int v, int i) {
//    if (i == 0) {
//        return;
//    }
//    if (c < cmin[i] || c > cmax[i] || d < dmin[i] || d > dmax[i]) {
//        return;
//    }
//    if (cmin[i] == c && cmax[i] == c && dmin[i] == d && dmax[i] == d) {
//        lazy(i, v);
//        return;
//    }
//    down(i);
//    if (kdtcd[i].c == c && kdtcd[i].d == d) {
//        dp[i] = max(dp[i], v);
//    }
//    update(c, d, v, ls[i]);
//    update(c, d, v, rs[i]);
//    maxdp[i] = max(dp[i], max(maxdp[ls[i]], maxdp[rs[i]]));
//}
//
//void add(int rank, int c, int d, int v) {
//    for (int i = rank; i <= n; i += lowbit(i)) {
//        update(c, d, v, root[i]);
//    }
//}
//
//void prepare() {
//    sort(abcd + 1, abcd + n + 1, ABCDCmp);
//    for (int i = 1; i <= n; i++) {
//        bi[i].b = abcd[i].b;
//        bi[i].i = i;
//    }
//    sort(bi + 1, bi + n + 1, BICmp);
//    for (int i = 1; i <= n; i++) {
//        ranking[bi[i].i] = i;
//    }
//    cmin[0] = dmin[0] = INF;
//    cmax[0] = dmax[0] = -INF;
//    for (int i = 1; i <= n; i++) {
//        int siz = lowbit(i);
//        int l = i - siz + 1;
//        for (int j = 1; j <= siz; j++) {
//            int idx = bi[l + j - 1].i;
//            cd[j].c = abcd[idx].c;
//            cd[j].d = abcd[idx].d;
//        }
//        root[i] = build(1, siz, 0);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand((unsigned)time(nullptr));
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> abcd[i].a >> abcd[i].b >> abcd[i].c >> abcd[i].d;
//    }
//    prepare();
//    int ans = 0;
//    for (int i = 1; i <= n; i++) {
//        int cur = query(ranking[i], abcd[i].c, abcd[i].d) + 1;
//        ans = max(ans, cur);
//        add(ranking[i], abcd[i].c, abcd[i].d, cur);
//    }
//    cout << ans << "\n";
//    return 0;
//}