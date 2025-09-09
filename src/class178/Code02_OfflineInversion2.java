package class178;

// 离线查询逆序对，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5047
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, id;
//};
//
//struct Offline {
//    int pos, id, l, r, op;
//};
//
//const int MAXN = 100002;
//const int MAXB = 401;
//int n, m;
//int arr[MAXN];
//int sorted[MAXN];
//int cntv;
//
//Query query1[MAXN];
//Offline loffline[MAXN];
//Offline roffline[MAXN];
//int cntl, cntr;
//
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int tree[MAXN];
//long long pre[MAXN];
//long long suf[MAXN];
//
//long long cnt[MAXN];
//long long lazy[MAXB];
//
//long long ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//bool OfflineCmp(Offline &a, Offline &b) {
//    return a.pos < b.pos;
//}
//
//int kth(int num) {
//    int left = 1, right = cntv, ret = 0;
//    while (left <= right) {
//        int mid = (left + right) >> 1;
//        if (sorted[mid] <= num) {
//            ret = mid;
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return ret;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= cntv) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void clear() {
//    memset(tree + 1, 0, cntv * sizeof(int));
//}
//
//void addLeftOffline(int pos, int id, int l, int r, int op) {
//    loffline[++cntl].pos = pos;
//    loffline[cntl].id = id;
//    loffline[cntl].l = l;
//    loffline[cntl].r = r;
//    loffline[cntl].op = op;
//}
//
//void addRightOffline(int pos, int id, int l, int r, int op) {
//    roffline[++cntr].pos = pos;
//    roffline[cntr].id = id;
//    roffline[cntr].l = l;
//    roffline[cntr].r = r;
//    roffline[cntr].op = op;
//}
//
//void addLeftCnt(int val) {
//    if (val <= 0) {
//        return;
//    }
//    for (int i = bl[bi[val]]; i <= val; i++) {
//        cnt[i]++;
//    }
//    for (int b = 1; b <= bi[val] - 1; b++) {
//        lazy[b]++;
//    }
//}
//
//void addRightCnt(int val) {
//    if (val > cntv) {
//        return;
//    }
//    for (int i = val; i <= br[bi[val]]; i++) {
//        cnt[i]++;
//    }
//    for (int b = bi[val] + 1; b <= bi[cntv]; b++) {
//        lazy[b]++;
//    }
//}
//
//long long getCnt(int val) {
//    return cnt[val] + lazy[bi[val]];
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    cntv = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[cntv] != sorted[i]) {
//            sorted[++cntv] = sorted[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//    }
//    int blen = (int)sqrt(n);
//    int bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, cntv);
//    }
//    sort(query1 + 1, query1 + m + 1, QueryCmp);
//}
//
//void compute() {
//    for (int i = 1; i <= n; i++) {
//        pre[i] = pre[i - 1] + sum(cntv) - sum(arr[i]);
//        add(arr[i], 1);
//    }
//    clear();
//    for (int i = n; i >= 1; i--) {
//        suf[i] = suf[i + 1] + sum(arr[i] - 1);
//        add(arr[i], 1);
//    }
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query1[i].l;
//        int jobr = query1[i].r;
//        int id = query1[i].id;
//        if (winr < jobr) {
//            addLeftOffline(winl - 1, id, winr + 1, jobr, -1);
//            ans[id] += pre[jobr] - pre[winr];
//        }
//        if (winr > jobr) {
//            addLeftOffline(winl - 1, id, jobr + 1, winr, 1);
//            ans[id] -= pre[winr] - pre[jobr];
//        }
//        winr = jobr;
//        if (winl > jobl) {
//            addRightOffline(winr + 1, id, jobl, winl - 1, -1);
//            ans[id] += suf[jobl] - suf[winl];
//        }
//        if (winl < jobl) {
//            addRightOffline(winr + 1, id, winl, jobl - 1, 1);
//            ans[id] -= suf[winl] - suf[jobl];
//        }
//        winl = jobl;
//    }
//    sort(loffline + 1, loffline + cntl + 1, OfflineCmp);
//    sort(roffline + 1, roffline + cntr + 1, OfflineCmp);
//    for (int pos = 0, qi = 1; pos <= n && qi <= cntl; pos++) {
//        if (pos >= 1) {
//            addLeftCnt(arr[pos] - 1);
//        }
//        for (; qi <= cntl && loffline[qi].pos == pos; qi++) {
//            int id = loffline[qi].id, l = loffline[qi].l, r = loffline[qi].r, op = loffline[qi].op;
//            long long ret = 0;
//            for (int j = l; j <= r; j++) {
//                ret += getCnt(arr[j]);
//            }
//            ans[id] += ret * op;
//        }
//    }
//    memset(lazy, 0, sizeof(lazy));
//    memset(cnt, 0, sizeof(cnt));
//    for (int pos = n + 1, qi = cntr; pos >= 1 && qi >= 1; pos--) {
//        if (pos <= n) {
//            addRightCnt(arr[pos] + 1);
//        }
//        for (; qi >= 1 && roffline[qi].pos == pos; qi--) {
//            int id = roffline[qi].id, l = roffline[qi].l, r = roffline[qi].r, op = roffline[qi].op;
//            long long ret = 0;
//            for (int j = l; j <= r; j++) {
//                ret += getCnt(arr[j]);
//            }
//            ans[id] += ret * op;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> query1[i].l >> query1[i].r;
//        query1[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 2; i <= m; i++) {
//        ans[query1[i].id] += ans[query1[i - 1].id];
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}