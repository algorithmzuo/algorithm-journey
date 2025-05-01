package class167;

// 最小异或查询，C++版
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
//int eor[MAXN];
//int num[MAXN];
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
//    eor[bottom] = (pass[bottom] >= 2 ? 0 : INF);
//    num[bottom] = (pass[bottom] == 1 ? x : 0);
//    for (int i = fa[bottom], l, r; i > 0; i = fa[i]) {
//        l = tree[i][0];
//        r = tree[i][1];
//        if (pass[i] < 2) {
//            eor[i] = INF;
//        } else if ((l != 0) ^ (r != 0)) {
//            eor[i] = (l > 0 ? eor[l] : eor[r]);
//        } else if (max(pass[l], pass[r]) == 1) {
//            eor[i] = num[l] ^ num[r];
//        } else {
//            eor[i] = min(eor[l], eor[r]);
//        }
//        if (pass[l] + pass[r] == 1) {
//        	num[i] = (pass[l] == 1 ? num[l] : num[r]);
//        } else {
//        	num[i] = 0;
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
//            cout << eor[1] << '\n';
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