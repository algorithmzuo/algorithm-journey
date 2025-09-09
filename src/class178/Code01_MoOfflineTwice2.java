package class178;

// 莫队二次离线入门题，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4887
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
//const int MAXN = 100001;
//const int MAXV = 1 << 14;
//int n, m, k;
//int arr[MAXN];
//int kOneArr[MAXV];
//int cntk;
//
//Query query[MAXN];
//
//int headq[MAXN];
//int nextq[MAXN << 1];
//int qid[MAXN << 1];
//int ql[MAXN << 1];
//int qr[MAXN << 1];
//int qop[MAXN << 1];
//int cntq;
//
//int bi[MAXN];
//int pre[MAXN];
//int cnt[MAXV];
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
//int countOne(int num) {
//    int ret = 0;
//    while (num > 0) {
//        ret++;
//        num -= num & -num;
//    }
//    return ret;
//}
//
//void addOffline(int x, int id, int l, int r, int op) {
//    nextq[++cntq] = headq[x];
//    headq[x] = cntq;
//    qid[cntq] = id;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//}
//
//void prepare() {
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
//    for (int v = 0; v < MAXV; v++) {
//        if (countOne(v) == k) {
//            kOneArr[++cntk] = v;
//        }
//    }
//}
//
//void compute() {
//    for (int i = 1; i <= n; i++) {
//        pre[i] = cnt[arr[i]];
//        for (int j = 1; j <= cntk; j++) {
//            cnt[arr[i] ^ kOneArr[j]]++;
//        }
//    }
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id = query[i].id;
//        if (winr < jobr) {
//            addOffline(winl - 1, id, winr + 1, jobr, -1);
//        }
//        while (winr < jobr) {
//            ans[id] += pre[++winr];
//        }
//        if (winr > jobr) {
//            addOffline(winl - 1, id, jobr + 1, winr, 1);
//        }
//        while (winr > jobr) {
//            ans[id] -= pre[winr--];
//        }
//        if (winl > jobl) {
//            addOffline(winr, id, jobl, winl - 1, 1);
//        }
//        while (winl > jobl) {
//            ans[id] -= pre[--winl];
//        }
//        if (winl < jobl) {
//            addOffline(winr, id, winl, jobl - 1, -1);
//        }
//        while (winl < jobl) {
//            ans[id] += pre[winl++];
//        }
//    }
//    memset(cnt, 0, sizeof(cnt));
//    for (int i = 0; i <= n; i++) {
//        if (i >= 1) {
//            for (int j = 1; j <= cntk; j++) {
//                cnt[arr[i] ^ kOneArr[j]]++;
//            }
//        }
//        for (int q = headq[i]; q > 0; q = nextq[q]) {
//            int id = qid[q], l = ql[q], r = qr[q], op = qop[q];
//            for (int j = l; j <= r; j++) {
//                if (j <= i && k == 0) {
//                    ans[id] += 1LL * op * (cnt[arr[j]] - 1);
//                } else {
//                    ans[id] += 1LL * op * cnt[arr[j]];
//                }
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> k;
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