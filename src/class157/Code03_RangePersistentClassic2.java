package class157;

// 范围修改的可持久化线段树，经典的方式，C++版
// 给定一个长度为n的数组arr，下标1~n，时间戳t=0，arr认为是0版本的数组
// 一共有m条操作，每条操作为如下四种类型中的一种
// C x y z : 当前时间戳t版本的数组，[x..y]范围每个数字增加z，得到t+1版本数组，并且t++
// Q x y   : 当前时间戳t版本的数组，打印[x..y]范围累加和
// H x y z : z版本的数组，打印[x..y]范围的累加和
// B x     : 当前时间戳t设置成x
// 1 <= n、m <= 10^5
// -10^9 <= arr[i] <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/SP11470
// 测试链接 : https://www.spoj.com/problems/TTM/
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
// 
//const int MAXN = 100001;
//const int MAXT = MAXN * 70;
//int n, m, t = 0;
//int arr[MAXN];
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//long long sum[MAXT];
//long long add[MAXT];
//int cnt = 0;
// 
//int clone(int i) {
//    int rt = ++cnt;
//    ls[rt] = ls[i];
//    rs[rt] = rs[i];
//    sum[rt] = sum[i];
//    add[rt] = add[i];
//    return rt;
//}
// 
//void up(int i) {
//    sum[i] = sum[ls[i]] + sum[rs[i]];
//}
// 
//void lazy(int i, long long v, int n) {
//    sum[i] += v * n;
//    add[i] += v;
//}
// 
//void down(int i, int ln, int rn) {
//    if (add[i] != 0) {
//        ls[i] = clone(ls[i]);
//        rs[i] = clone(rs[i]);
//        lazy(ls[i], add[i], ln);
//        lazy(rs[i], add[i], rn);
//        add[i] = 0;
//    }
//}
// 
//int build(int l, int r) {
//    int rt = ++cnt;
//    add[rt] = 0;
//    if (l == r) {
//        sum[rt] = arr[l];
//    } else {
//        int mid = (l + r) / 2;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
//        up(rt);
//    }
//    return rt;
//}
// 
//int addRange(int jobl, int jobr, long long jobv, int l, int r, int i) {
//    int rt = clone(i);
//    if (jobl <= l && r <= jobr) {
//        lazy(rt, jobv, r - l + 1);
//    } else {
//        int mid = (l + r) / 2;
//        down(rt, mid - l + 1, r - mid);
//        if (jobl <= mid) {
//            ls[rt] = addRange(jobl, jobr, jobv, l, mid, ls[rt]);
//        }
//        if (jobr > mid) {
//            rs[rt] = addRange(jobl, jobr, jobv, mid + 1, r, rs[rt]);
//        }
//        up(rt);
//    }
//    return rt;
//}
// 
//long long query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    }
//    int mid = (l + r) / 2;
//    down(i, mid - l + 1, r - mid);
//    long long ans = 0;
//    if (jobl <= mid) {
//        ans += query(jobl, jobr, l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//        ans += query(jobl, jobr, mid + 1, r, rs[i]);
//    }
//    return ans;
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
//    string op;
//    int x, y, z;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == "C") {
//            cin >> x >> y >> z;
//            root[t + 1] = addRange(x, y, z, 1, n, root[t]);
//            t++;
//        } else if (op == "Q") {
//            cin >> x >> y;
//            cout << query(x, y, 1, n, root[t]) << "\n";
//        } else if (op == "H") {
//            cin >> x >> y >> z;
//            cout << query(x, y, 1, n, root[z]) << "\n";
//        } else if (op == "B") {
//            cin >> x;
//            t = x;
//        }
//    }
//    return 0;
//}