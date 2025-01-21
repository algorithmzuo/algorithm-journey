package class159;

// 异或运算，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5795
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//const int MAXT = MAXN * 32;
//const int BIT = 30;
//int n, m, p;
//int x[MAXN];
//int y[MAXN];
//int root[MAXN];
//int tree[MAXT][2];
//int pass[MAXT];
//int cnt = 0;
//int rtpath[MAXN][2];
//
//int insert(int num, int i) {
//    int rt = ++cnt;
//    tree[rt][0] = tree[i][0];
//    tree[rt][1] = tree[i][1];
//    pass[rt] = pass[i] + 1;
//    for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
//        path = (num >> b) & 1;
//        i = tree[i][path];
//        cur = ++cnt;
//        tree[cur][0] = tree[i][0];
//        tree[cur][1] = tree[i][1];
//        pass[cur] = pass[i] + 1;
//        tree[pre][path] = cur;
//    }
//    return rt;
//}
//
//int maxKth(int xl, int xr, int yl, int yr, int k) {
//    for (int i = xl; i <= xr; i++) {
//        rtpath[i][0] = root[yl - 1];
//        rtpath[i][1] = root[yr];
//    }
//    int ans = 0;
//    for (int b = BIT, path, best, sum; b >= 0; b--) {
//        sum = 0;
//        for (int i = xl; i <= xr; i++) {
//            path = (x[i] >> b) & 1;
//            best = path ^ 1;
//            sum += pass[tree[rtpath[i][1]][best]] - pass[tree[rtpath[i][0]][best]];
//        }
//        for (int i = xl; i <= xr; i++) {
//            path = (x[i] >> b) & 1;
//            best = path ^ 1;
//            if (sum >= k) {
//                rtpath[i][0] = tree[rtpath[i][0]][best];
//                rtpath[i][1] = tree[rtpath[i][1]][best];
//            } else {
//                rtpath[i][0] = tree[rtpath[i][0]][path];
//                rtpath[i][1] = tree[rtpath[i][1]][path];
//            }
//        }
//        if (sum >= k) {
//            ans += 1 << b;
//        } else {
//            k -= sum;
//        }
//    }
//    return ans;
//}
//
//void prepare() {
//    for (int i = 1; i <= m; i++) {
//        root[i] = insert(y[i], root[i - 1]);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> x[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> y[i];
//    }
//    prepare();
//    cin >> p;
//    for (int i = 1, xl, xr, yl, yr, k; i <= p; i++) {
//        cin >> xl >> xr >> yl >> yr >> k;
//        cout << maxKth(xl, xr, yl, yr, k) << "\n";
//    }
//    return 0;
//}