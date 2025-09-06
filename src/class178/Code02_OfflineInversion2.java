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
//const int MAXQ = 200001;
//const int MAXB = 401;
//int n, m;
//int arr[MAXN];
//Query query[MAXN];
//
//int sorted[MAXN];
//int cntv;
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
//int headl[MAXN];
//int headr[MAXN];
//int nxt[MAXQ];
//int qid[MAXQ];
//int ql[MAXQ];
//int qr[MAXQ];
//int qop[MAXQ];
//int cntq;
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
//    fill(tree + 1, tree + cntv + 1, 0);
//}
//
//void addLeftQuery(int pos, int id, int l, int r, int op) {
//    nxt[++cntq] = headl[pos];
//    qid[cntq] = id;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//    headl[pos] = cntq;
//}
//
//void addRightQuery(int pos, int id, int l, int r, int op) {
//    nxt[++cntq] = headr[pos];
//    qid[cntq] = id;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//    headr[pos] = cntq;
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
//            addLeftQuery(winl - 1, id, winr + 1, jobr, -1);
//            ans[id] += pre[jobr] - pre[winr];
//        }
//        if (winr > jobr) {
//            addLeftQuery(winl - 1, id, jobr + 1, winr, 1);
//            ans[id] -= pre[winr] - pre[jobr];
//        }
//        if (winl > jobl) {
//            addRightQuery(jobr + 1, id, jobl, winl - 1, -1);
//            ans[id] += suf[jobl] - suf[winl];
//        }
//        if (winl < jobl) {
//            addRightQuery(jobr + 1, id, winl, jobl - 1, 1);
//            ans[id] -= suf[winl] - suf[jobl];
//        }
//        winl = jobl;
//        winr = jobr;
//    }
//    for (int i = 1; i <= n; i++) {
//        addLeftCnt(arr[i] - 1);
//        for (int q = headl[i]; q > 0; q = nxt[q]) {
//            int id = qid[q], l = ql[q], r = qr[q], op = qop[q];
//            long long ret = 0;
//            for (int j = l; j <= r; j++) {
//                ret += getCnt(arr[j]);
//            }
//            ans[id] += ret * op;
//        }
//    }
//    memset(cnt,  0, sizeof(cnt));
//    memset(lazy, 0, sizeof(lazy));
//    for (int i = n; i >= 1; i--) {
//        addRightCnt(arr[i] + 1);
//        for (int q = headr[i]; q > 0; q = nxt[q]) {
//            int id = qid[q], l = ql[q], r = qr[q], op = qop[q];
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
//    	ans[query[i].id] += ans[query[i - 1].id];
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}