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
//const int MAXN = 100002;
//const int MAXB = 401;
//
//int n, m;
//int arr[MAXN];
//int sorted[MAXN];
//int cntv;
//
//Query query[MAXN];
//int headl[MAXN];
//int headr[MAXN];
//int nextq[MAXN << 1];
//int ql[MAXN << 1];
//int qr[MAXN << 1];
//int qop[MAXN << 1];
//int qid[MAXN << 1];
//int cntq;
//
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int tree[MAXN];
//long long pre[MAXN];
//long long suf[MAXN];
//
//long long cnt1[MAXB];
//long long cnt2[MAXN];
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
//void addLeftOffline(int x, int l, int r, int op, int id) {
//    nextq[++cntq] = headl[x];
//    headl[x] = cntq;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//    qid[cntq] = id;
//}
//
//void addRightOffline(int x, int l, int r, int op, int id) {
//    nextq[++cntq] = headr[x];
//    headr[x] = cntq;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//    qid[cntq] = id;
//}
//
//void addLeftCnt(int val) {
//    if (val <= 0) {
//        return;
//    }
//    for (int b = 1; b <= bi[val] - 1; b++) {
//        cnt1[b]++;
//    }
//    for (int i = bl[bi[val]]; i <= val; i++) {
//        cnt2[i]++;
//    }
//}
//
//void addRightCnt(int val) {
//    if (val > cntv) {
//        return;
//    }
//    for (int b = bi[val] + 1; b <= bi[cntv]; b++) {
//        cnt1[b]++;
//    }
//    for (int i = val; i <= br[bi[val]]; i++) {
//        cnt2[i]++;
//    }
//}
//
//long long getCnt(int val) {
//    return cnt1[bi[val]] + cnt2[val];
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
//    sort(query + 1, query + m + 1, QueryCmp);
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
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id = query[i].id;
//        if (winr < jobr) {
//            addLeftOffline(winl - 1, winr + 1, jobr, -1, id);
//            ans[id] += pre[jobr] - pre[winr];
//        }
//        if (winr > jobr) {
//            addLeftOffline(winl - 1, jobr + 1, winr, 1, id);
//            ans[id] -= pre[winr] - pre[jobr];
//        }
//        winr = jobr;
//        if (winl > jobl) {
//            addRightOffline(winr + 1, jobl, winl - 1, -1, id);
//            ans[id] += suf[jobl] - suf[winl];
//        }
//        if (winl < jobl) {
//            addRightOffline(winr + 1, winl, jobl - 1, 1, id);
//            ans[id] -= suf[winl] - suf[jobl];
//        }
//        winl = jobl;
//    }
//    for (int i = 0; i <= n; i++) {
//        if (i >= 1) {
//            addLeftCnt(arr[i] - 1);
//        }
//        for (int q = headl[i]; q > 0; q = nextq[q]) {
//            int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
//            long long ret = 0;
//            for (int j = l; j <= r; j++) {
//                ret += getCnt(arr[j]);
//            }
//            ans[id] += ret * op;
//        }
//    }
//    memset(cnt1, 0, sizeof(cnt1));
//    memset(cnt2, 0, sizeof(cnt2));
//    for (int i = n + 1; i >= 1; i--) {
//        if (i <= n) {
//            addRightCnt(arr[i] + 1);
//        }
//        for (int q = headr[i]; q > 0; q = nextq[q]) {
//            int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
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
//        cin >> query[i].l >> query[i].r;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 2; i <= m; i++) {
//        ans[query[i].id] += ans[query[i - 1].id];
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}