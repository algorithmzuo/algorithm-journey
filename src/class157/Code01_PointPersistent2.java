package class157;

// 单点修改的可持久化线段树模版题1，C++版
// 给定一个长度为n的数组arr，下标1~n，原始数组认为是0号版本
// 一共有m条操作，每条操作是如下两种类型中的一种
// v 1 x y : 基于v号版本的数组，把x位置的值设置成y，生成新版本的数组
// v 2 x   : 基于v号版本的数组，打印x位置的值，生成新版本的数组和v版本一致
// 每条操作后得到的新版本数组，版本编号为操作的计数
// 1 <= n, m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3919
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//
//using namespace std;
//
//const int MAXN = 1000001;
//const int MAXT = MAXN * 23;
//int n, m;
//int arr[MAXN];
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int value[MAXT];
//int cnt = 0;
//
//int build(int l, int r) {
//    int rt = ++cnt;
//    if (l == r) {
//        value[rt] = arr[l];
//    } else {
//        int mid = (l + r) >> 1;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
//    }
//    return rt;
//}
//
//int update(int jobi, int jobv, int l, int r, int i) {
//    int rt = ++cnt;
//    ls[rt] = ls[i];
//    rs[rt] = rs[i];
//    value[rt] = value[i];
//    if (l == r) {
//        value[rt] = jobv;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = update(jobi, jobv, l, mid, ls[rt]);
//        } else {
//            rs[rt] = update(jobi, jobv, mid + 1, r, rs[rt]);
//        }
//    }
//    return rt;
//}
//
//int query(int jobi, int l, int r, int i) {
//    if (l == r) {
//        return value[i];
//    }
//    int mid = (l + r) >> 1;
//    if (jobi <= mid) {
//        return query(jobi, l, mid, ls[i]);
//    } else {
//        return query(jobi, mid + 1, r, rs[i]);
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
//    root[0] = build(1, n);
//    for (int i = 1, version, op, x, y; i <= m; i++) {
//        cin >> version >> op >> x;
//        if (op == 1) {
//            cin >> y;
//            root[i] = update(x, y, 1, n, root[version]);
//        } else {
//            root[i] = root[version];
//            cout << query(x, 1, n, root[i]) << '\n';
//        }
//    }
//    return 0;
//}