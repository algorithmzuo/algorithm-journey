package class167;

// 最小异或查询，C++版
// 一共有q条操作，每种操作是如下三种类型中的一种
// 操作 1 x : 黑板上写上一个数字x，同一种数字可以出现多次
// 操作 2 x : 将一个x从黑板上擦掉，操作前保证至少有一个x在黑板上
// 操作 3   : 打印任意两个数的最小异或值，操作前保证黑板上至少有两个数
// 1 <= q <= 3 * 10^5
// 0 <= x <= 2^30
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc308_g
// 测试链接 : https://atcoder.jp/contests/abc308/tasks/abc308_g
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10000001;
//const int BIT = 29;
//const int INF = 1 << 30;
//
//int fa[MAXN];
//int tree[MAXN][2];
//int pass[MAXN];
//int cnt = 1;
//
//int mineor[MAXN];
//int only[MAXN];
//
//int insert(int x, int changeCnt) {
//    int cur = 1;
//    pass[cur] += changeCnt;
//    for (int b = BIT, path; b >= 0; b--) {
//        path = (x >> b) & 1;
//        if (tree[cur][path] == 0) {
//            tree[cur][path] = ++cnt;
//            fa[tree[cur][path]] = cur;
//        }
//        cur = tree[cur][path];
//        pass[cur] += changeCnt;
//    }
//    return cur;
//}
//
//void compute(int x, int changeCnt) {
//    int bottom = insert(x, changeCnt);
//    mineor[bottom] = pass[bottom] >= 2 ? 0 : INF;
//    only[bottom] = pass[bottom] == 1 ? x : 0;
//    for (int i = fa[bottom], l, r; i > 0; i = fa[i]) {
//        l = tree[i][0];
//        r = tree[i][1];
//        if (pass[i] < 2) {
//            mineor[i] = INF;
//        } else if ((l != 0) ^ (r != 0)) {
//            mineor[i] = l > 0 ? mineor[l] : mineor[r];
//        } else if (max(pass[l], pass[r]) == 1) {
//            mineor[i] = only[l] ^ only[r];
//        } else {
//            mineor[i] = min(mineor[l], mineor[r]);
//        }
//        if (pass[l] + pass[r] == 1) {
//        	only[i] = pass[l] == 1 ? only[l] : only[r];
//        } else {
//        	only[i] = 0;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int q;
//    cin >> q;
//    for (int i = 1, op, x; i <= q; i++) {
//        cin >> op;
//        if (op == 3) {
//            cout << mineor[1] << '\n';
//        } else {
//            cin >> x;
//            if (op == 1) {
//                compute(x, 1);
//            } else {
//                compute(x, -1);
//            }
//        }
//    }
//    return 0;
//}