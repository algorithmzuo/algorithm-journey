package class181;

// 最小化逆序对，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3521
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXT = 5000001;
//int n;
//
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int cntt;
//
//long long ans, u, v;
//
//void up(int i) {
//    siz[i] = siz[ls[i]] + siz[rs[i]];
//}
//
//int build(int jobi, int l, int r) {
//    int rt = ++cntt;
//    if (l == r) {
//        siz[rt]++;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = build(jobi, l, mid);
//        } else {
//            rs[rt] = build(jobi, mid + 1, r);
//        }
//        up(rt);
//    }
//    return rt;
//}
//
//int merge(int l, int r, int t1, int t2) {
//    if (t1 == 0 || t2 == 0) {
//        return t1 + t2;
//    }
//    if (l == r) {
//        siz[t1] += siz[t2];
//    } else {
//        u += 1LL * siz[rs[t1]] * siz[ls[t2]];
//        v += 1LL * siz[ls[t1]] * siz[rs[t2]];
//        int mid = (l + r) >> 1;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2]);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
//        up(t1);
//    }
//    return t1;
//}
//
//int dfs() {
//    int rt;
//    int val;
//    cin >> val;
//    if (val == 0) {
//        int left = dfs();
//        int right = dfs();
//        u = v = 0;
//        rt = merge(1, n, left, right);
//        ans += min(u, v);
//    } else {
//        rt = build(val, 1, n);
//    }
//    return rt;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    dfs();
//    cout << ans << '\n';
//    return 0;
//}