package class178;

// 莫队二次离线入门题，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4887
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query1 {
//    int l, r, id;
//};
//
//struct Query2 {
//    int pos, id, l, r, op;
//};
//
//const int MAXN = 100001;
//const int MAXV = 1 << 14;
//int n, m, k;
//int arr[MAXN];
//int kOneArr[MAXV];
//int cntk;
//
//Query1 query1[MAXN];
//Query2 query2[MAXN << 1];
//int cntq;
//
//int bi[MAXN];
//int pre[MAXN];
//int cnt[MAXV];
//
//long long ans[MAXN];
//
//bool Cmp1(Query1 &a, Query1 &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//bool Cmp2(Query2 &a, Query2 &b) {
//    return a.pos < b.pos;
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
//void addQuery(int pos, int id, int l, int r, int op) {
//    query2[++cntq].pos = pos;
//    query2[cntq].id = id;
//    query2[cntq].l = l;
//    query2[cntq].r = r;
//    query2[cntq].op = op;
//}
//
//void prepare() {
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query1 + 1, query1 + m + 1, Cmp1);
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
//        int jobl = query1[i].l;
//        int jobr = query1[i].r;
//        int id = query1[i].id;
//        if (winr < jobr) {
//            addQuery(winl - 1, id, winr + 1, jobr, -1);
//        }
//        while (winr < jobr) {
//            ans[id] += pre[++winr];
//        }
//        if (winr > jobr) {
//            addQuery(winl - 1, id, jobr + 1, winr, 1);
//        }
//        while (winr > jobr) {
//            ans[id] -= pre[winr--];
//        }
//        if (winl > jobl) {
//            addQuery(winr, id, jobl, winl - 1, 1);
//        }
//        while (winl > jobl) {
//            ans[id] -= pre[--winl];
//        }
//        if (winl < jobl) {
//            addQuery(winr, id, winl, jobl - 1, -1);
//        }
//        while (winl < jobl) {
//            ans[id] += pre[winl++];
//        }
//    }
//    memset(cnt, 0, sizeof(cnt));
//    sort(query2 + 1, query2 + cntq + 1, Cmp2);
//    for (int pos = 0, qi = 1; pos <= n && qi <= cntq; pos++) {
//        if (pos >= 1) {
//            for (int j = 1; j <= cntk; j++) {
//                cnt[arr[pos] ^ kOneArr[j]]++;
//            }
//        }
//        for (; qi <= cntq && query2[qi].pos == pos; qi++) {
//            int id = query2[qi].id, l = query2[qi].l, r = query2[qi].r, op = query2[qi].op;
//            for (int j = l; j <= r; j++) {
//                if (j <= pos && k == 0) {
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