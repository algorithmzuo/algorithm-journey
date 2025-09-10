package class178;

// 查询Abbi值，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5501
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
//const int MAXN = 500001;
//const int MAXV = 100000;
//const int MAXB = 401;
//
//int n, m;
//int arr[MAXN];
//long long presum[MAXN];
//int bi[MAXN];
//int br[MAXB];
//
//Query query[MAXN];
//int headq[MAXN];
//int nextq[MAXN << 1];
//int ql[MAXN << 1];
//int qr[MAXN << 1];
//int qop[MAXN << 1];
//int qid[MAXN << 1];
//int cntq;
//
//long long treeCnt[MAXV + 1];
//long long treeSum[MAXV + 1];
//
//long long pre[MAXN];
//int cnt1[MAXB];
//int cnt2[MAXN];
//long long sum1[MAXB];
//long long sum2[MAXN];
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
//void addOffline(int x, int l, int r, int op, int id) {
//    nextq[++cntq] = headq[x];
//    headq[x] = cntq;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//    qid[cntq] = id;
//}
//
//int lowbit(int x) {
//    return x & -x;
//}
//
//void add(long long *tree, int x, long long v) {
//    while (x <= MAXV) {
//        tree[x] += v;
//        x += lowbit(x);
//    }
//}
//
//long long sum(long long *tree, int x) {
//    long long ret = 0;
//    while (x > 0) {
//        ret += tree[x];
//        x -= lowbit(x);
//    }
//    return ret;
//}
//
//void addVal(int val) {
//    if (val <= 0) {
//        return;
//    }
//    for (int b = bi[val]; b <= bi[MAXV]; b++) {
//        cnt1[b]++;
//        sum1[b] += val;
//    }
//    for (int i = val; i <= br[bi[val]]; i++) {
//        cnt2[i]++;
//        sum2[i] += val;
//    }
//}
//
//long long getSum(int x) {
//    if (x <= 0) {
//        return 0;
//    }
//    return sum1[bi[x] - 1] + sum2[x];
//}
//
//int getCnt(int x) {
//    if (x <= 0) {
//        return 0;
//    }
//    return cnt1[bi[x] - 1] + cnt2[x];
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        presum[i] = presum[i - 1] + arr[i];
//    }
//    int blen = (int)sqrt(MAXV);
//    int bnum = (MAXV + blen - 1) / blen;
//    for (int i = 1; i <= MAXV; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int b = 1; b <= bnum; b++) {
//        br[b] = min(b * blen, MAXV);
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
//}
//
//void compute() {
//    for (int i = 1; i <= n; i++) {
//        pre[i] = pre[i - 1] + sum(treeCnt, arr[i] - 1) * arr[i] + sum(treeSum, MAXV) - sum(treeSum, arr[i]);
//        add(treeCnt, arr[i], 1);
//        add(treeSum, arr[i], arr[i]);
//    }
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id = query[i].id;
//        if (winr < jobr) {
//            addOffline(winl - 1, winr + 1, jobr, -1, id);
//            ans[id] += pre[jobr] - pre[winr];
//        }
//        if (winr > jobr) {
//            addOffline(winl - 1, jobr + 1, winr, 1, id);
//            ans[id] -= pre[winr] - pre[jobr];
//        }
//        winr = jobr;
//        if (winl > jobl) {
//            addOffline(winr, jobl, winl - 1, 1, id);
//            ans[id] -= pre[winl - 1] - pre[jobl - 1];
//        }
//        if (winl < jobl) {
//            addOffline(winr, winl, jobl - 1, -1, id);
//            ans[id] += pre[jobl - 1] - pre[winl - 1];
//        }
//        winl = jobl;
//    }
//    long long sum = 0;
//    long long tmp;
//    for (int i = 0; i <= n; i++) {
//        if (i >= 1) {
//            addVal(arr[i]);
//            sum += arr[i];
//        }
//        for (int q = headq[i]; q > 0; q = nextq[q]) {
//            int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
//            for (int j = l; j <= r; j++) {
//                tmp = 1LL * getCnt(arr[j] - 1) * arr[j] + sum - getSum(arr[j]);
//                if (op == 1) {
//                    ans[id] += tmp;
//                } else {
//                    ans[id] -= tmp;
//                }
//            }
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
//        ans[query[i].id] += presum[query[i].r] - presum[query[i].l - 1];
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}