package class177;

// 秃子酋长，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P8078
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
//int n, m;
//int arr[MAXN];
//Query query[MAXN];
//int pos[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXN];
//
//int lst[MAXN + 1];
//int nxt[MAXN + 1];
//long long sum;
//long long ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r > b.r;
//}
//
//inline void del(int num) {
//    int less = lst[num], more = nxt[num];
//    if (less != 0) {
//        sum -= abs(pos[num] - pos[less]);
//    }
//    if (more != n + 1) {
//        sum -= abs(pos[more] - pos[num]);
//    }
//    if (less != 0 && more != n + 1) {
//        sum += abs(pos[more] - pos[less]);
//    }
//    nxt[less] = more;
//    lst[more] = less;
//}
//
//inline void undo(int num) {
//    nxt[lst[num]] = num;
//    lst[nxt[num]] = num;
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        pos[arr[i]] = i;
//    }
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//    }
//    sort(query + 1, query + 1 + m, QueryCmp);
//}
//
//void compute() {
//    for (int v = 1; v <= n; v++) {
//        lst[v] = v - 1;
//        nxt[v] = v + 1;
//    }
//    nxt[0] = 1;
//    lst[n + 1] = n;
//    sum = 0;
//    for (int v = 2; v <= n; v++) {
//        sum += abs(pos[v] - pos[v - 1]);
//    }
//    int winl = 1, winr = n;
//    for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
//        while (winl < bl[block]) {
//            del(arr[winl++]);
//        }
//        long long before = sum;
//        for (; qi <= m && bi[query[qi].l] == block; qi++) {
//            int jobl = query[qi].l;
//            int jobr = query[qi].r;
//            int id = query[qi].id;
//            while (winr > jobr) {
//                del(arr[winr--]);
//            }
//            long long backup = sum;
//            while (winl < jobl) {
//                del(arr[winl++]);
//            }
//            ans[id] = sum;
//            while (winl > bl[block]) {
//                undo(arr[--winl]);
//            }
//            sum = backup;
//        }
//        while (winr < n) {
//            undo(arr[++winr]);
//        }
//        sum = before;
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
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}