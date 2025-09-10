package class178;

// 查询倍数，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5398
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
//const int MAXF = 5000001;
//const int LIMIT = 80;
//
//int n, m, maxv;
//int arr[MAXN];
//int bi[MAXN];
//
//int headf[MAXN];
//int nextf[MAXF];
//int fac[MAXF];
//int cntf;
//
//Query query[MAXN];
//int headq[MAXN];
//int nextq[MAXN << 1];
//int qx[MAXN << 1];
//int ql[MAXN << 1];
//int qr[MAXN << 1];
//int qop[MAXN << 1];
//int qid[MAXN << 1];
//int cntq;
//
//int fcnt[MAXN];
//int xcnt[MAXN];
//long long pre[MAXN];
//
//int cnt1[MAXN];
//int cnt2[MAXN];
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
//void addFactors(int num) {
//    if (headf[num] == 0) {
//        for (int f = 1; f * f <= num; f++) {
//            if (num % f == 0) {
//                nextf[++cntf] = headf[num];
//                fac[cntf] = f;
//                headf[num] = cntf;
//            }
//        }
//    }
//}
//
//void addOffline(int x, int l, int r, int op, int id) {
//    nextq[++cntq] = headq[x];
//    headq[x] = cntq;
//    qx[cntq] = x;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//    qid[cntq] = id;
//}
//
//void compute() {
//    for (int i = 1, x; i <= n; i++) {
//        pre[i] = pre[i - 1];
//        x = arr[i];
//        for (int e = headf[x], f, other; e > 0; e = nextf[e]) {
//            f = fac[e];
//            other = x / f;
//            fcnt[f]++;
//            pre[i] += xcnt[f];
//            if (other != f) {
//                fcnt[other]++;
//                pre[i] += xcnt[other];
//            }
//        }
//        pre[i] += fcnt[x];
//        xcnt[x]++;
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
//    memset(fcnt, 0, sizeof(fcnt));
//    for (int i = 0; i <= n; i++) {
//        if (i >= 1) {
//            int num = arr[i];
//            for (int e = headf[num], f, other; e > 0; e = nextf[e]) {
//                f = fac[e];
//                other = num / f;
//                fcnt[f]++;
//                if (other != f) {
//                    fcnt[other]++;
//                }
//            }
//            if (num > LIMIT) {
//                for (int v = num; v <= maxv; v += num) {
//                    fcnt[v]++;
//                }
//            }
//        }
//        for (int q = headq[i]; q > 0; q = nextq[q]) {
//            int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
//            for (int j = l; j <= r; j++) {
//                ans[id] += 1LL * op * fcnt[arr[j]];
//            }
//        }
//    }
//    for (int v = 1; v <= LIMIT; v++) {
//        cnt1[0] = 0;
//        cnt2[0] = 0;
//        for (int i = 1; i <= n; i++) {
//            cnt1[i] = cnt1[i - 1] + (arr[i] == v ? 1 : 0);
//            cnt2[i] = cnt2[i - 1] + (arr[i] % v == 0 ? 1 : 0);
//        }
//        for(int i = 1; i <= cntq; i++) {
//             int x = qx[i], l = ql[i], r = qr[i], op = qop[i], id = qid[i];
//             ans[id] += 1LL * op * cnt1[x] * (cnt2[r] - cnt2[l - 1]);
//        }
//    }
//}
//
//void prepare() {
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//        maxv = max(maxv, arr[i]);
//        addFactors(arr[i]);
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
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