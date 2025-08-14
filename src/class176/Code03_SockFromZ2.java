package class176;

// 小Z的袜子，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1494
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
//const int MAXN = 50001;
//int n, m;
//int arr[MAXN];
//Query query[MAXN];
//
//int bi[MAXN];
//int cnt[MAXN];
//long long sum = 0;
//long long ans1[MAXN];
//long long ans2[MAXN];
//
//bool QueryCmp1(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return a.l < b.l;
//    }
//    return a.r < b.r;
//}
//
//bool QueryCmp2(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return a.l < b.l;
//    }
//    if ((bi[a.l] & 1) == 1) {
//        return a.r < b.r;
//    }
//    return a.r > b.r;
//}
//
//long long gcd(long long a, long long b) {
//    return b == 0 ? a : gcd(b, a % b);
//}
//
//void del(int idx) {
//    sum -= cnt[arr[idx]] * cnt[arr[idx]];
//    cnt[arr[idx]]--;
//    sum += cnt[arr[idx]] * cnt[arr[idx]];
//}
//
//void add(int idx) {
//    sum -= cnt[arr[idx]] * cnt[arr[idx]];
//    cnt[arr[idx]]++;
//    sum += cnt[arr[idx]] * cnt[arr[idx]];
//}
//
//void prepare() {
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + m + 1, QueryCmp2);
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id = query[i].id;
//        while (winl > jobl) {
//            add(--winl);
//        }
//        while (winr < jobr) {
//            add(++winr);
//        }
//        while (winl < jobl) {
//            del(winl++);
//        }
//        while (winr > jobr) {
//            del(winr--);
//        }
//        if (jobl == jobr) {
//            ans1[id] = 0;
//            ans2[id] = 1;
//        } else {
//            ans1[id] = sum - (jobr - jobl + 1);
//            ans2[id] = 1LL * (jobr - jobl + 1) * (jobr - jobl);
//            long long g = gcd(ans1[id], ans2[id]);
//            ans1[id] /= g;
//            ans2[id] /= g;
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
//        cin >> query[i].l;
//        cin >> query[i].r;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans1[i] << '/' << ans2[i] << '\n';
//    }
//    return 0;
//}