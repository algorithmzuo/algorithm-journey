package class157;

// 单点修改的可持久化线段树模版题2，C++版
// 给定一个长度为n的数组arr，下标1~n，一共有m条查询
// 每条查询 l r k : 打印arr[l..r]中第k小的数字
// 1 <= n、m <= 2 * 10^5
// 0 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3834
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXT = MAXN * 22;
//int n, m, s;
//int arr[MAXN];
//int sorted[MAXN];
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int size[MAXT];
//int cnt;
//
//int kth(int num) {
//    int left = 1, right = s, mid, ans = 0;
//    while (left <= right) {
//        mid = (left + right) / 2;
//        if (sorted[mid] <= num) {
//            ans = mid;
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return ans;
//}
//
//int build(int l, int r) {
//    int rt = ++cnt;
//    size[rt] = 0;
//    if (l < r) {
//        int mid = (l + r) / 2;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
//    }
//    return rt;
//}
//
//int insert(int jobi, int l, int r, int i) {
//    int rt = ++cnt;
//    ls[rt] = ls[i];
//    rs[rt] = rs[i];
//    size[rt] = size[i] + 1;
//    if (l < r) {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//            ls[rt] = insert(jobi, l, mid, ls[rt]);
//        } else {
//            rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
//        }
//    }
//    return rt;
//}
//
//int query(int jobk, int l, int r, int u, int v) {
//    if (l == r) {
//        return l;
//    }
//    int lsize = size[ls[v]] - size[ls[u]];
//    int mid = (l + r) / 2;
//    if (lsize >= jobk) {
//        return query(jobk, l, mid, ls[u], ls[v]);
//    } else {
//        return query(jobk - lsize, mid + 1, r, rs[u], rs[v]);
//    }
//}
//
//void prepare() {
//    cnt = 0;
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    s = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[s] != sorted[i]) {
//            sorted[++s] = sorted[i];
//        }
//    }
//    root[0] = build(1, s);
//    for (int i = 1, x; i <= n; i++) {
//        x = kth(arr[i]);
//        root[i] = insert(x, 1, s, root[i - 1]);
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
//    prepare();
//    for (int i = 1, l, r, k, rank; i <= m; i++) {
//        cin >> l >> r >> k;
//        rank = query(k, 1, s, root[l - 1], root[r]);
//        cout << sorted[rank] << '\n';
//    }
//    return 0;
//}