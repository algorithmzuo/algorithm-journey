package class172;

// 教主的魔法，C++版
// 给定一个长度为n的数组arr，接下来有m条操作，每条操作是如下两种类型中的一种
// 操作 A l r v : 打印arr[l..r]范围上>=v的数字个数
// 操作 M l r v : 把arr[l..r]范围上每个值都加上v
// 1 <= n <= 10^6
// 1 <= m <= 3000
// 1 <= 数组中的值 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P2801
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1000001;
//const int MAXB = 1001;
//int n, m;
//int arr[MAXN];
//int sortv[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int lazy[MAXB];
//
//void build() {
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//    }
//    for (int i = 1; i <= n; i++) {
//        sortv[i] = arr[i];
//    }
//    for (int i = 1; i <= bnum; i++) {
//        sort(sortv + bl[i], sortv + br[i] + 1);
//    }
//}
//
//int getCnt(int i, int v) {
//    v -= lazy[i];
//    int l = bl[i], r = br[i], m, ans = 0;
//    while (l <= r) {
//        m = (l + r) >> 1;
//        if (sortv[m] >= v) {
//            ans += r - m + 1;
//            r = m - 1;
//        } else {
//            l = m + 1;
//        }
//    }
//    return ans;
//}
//
//int innerQuery(int l, int r, int v) {
//    v -= lazy[bi[l]];
//    int ans = 0;
//    for (int i = l; i <= r; i++) {
//        if (arr[i] >= v) {
//            ans++;
//        }
//    }
//    return ans;
//}
//
//int query(int l, int r, int v) {
//    int ans = 0;
//    if (bi[l] == bi[r]) {
//        ans = innerQuery(l, r, v);
//    } else {
//        ans += innerQuery(l, br[bi[l]], v);
//        ans += innerQuery(bl[bi[r]], r, v);
//        for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
//            ans += getCnt(i, v);
//        }
//    }
//    return ans;
//}
//
//void innerAdd(int l, int r, int v) {
//    for (int i = l; i <= r; i++) {
//        arr[i] += v;
//    }
//    for (int i = bl[bi[l]]; i <= br[bi[l]]; i++) {
//        sortv[i] = arr[i];
//    }
//    sort(sortv + bl[bi[l]], sortv + br[bi[l]] + 1);
//}
//
//void add(int l, int r, int v) {
//    if (bi[l] == bi[r]) {
//        innerAdd(l, r, v);
//    } else {
//        innerAdd(l, br[bi[l]], v);
//        innerAdd(bl[bi[r]], r, v);
//        for (int b = bi[l] + 1; b <= bi[r] - 1; b++) {
//            lazy[b] += v;
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
//    build();
//    char op;
//    int l, r, v;
//    for (int i = 1; i <= m; i++) {
//        cin >> op >> l >> r >> v;
//        if (op == 'A') {
//            cout << query(l, r, v) << '\n';
//        } else {
//            add(l, r, v);
//        }
//    }
//    return 0;
//}