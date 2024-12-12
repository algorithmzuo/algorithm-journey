package class154;

// 断罪者，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4971
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 2000001;
//int t, w, n, m;
//long long k;
//long long num[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//int fa[MAXN];
//
//int find(int i) {
//    return fa[i] == i ? i : (fa[i] = find(fa[i]));
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    int tmp;
//    if (num[i] < num[j] || (num[i] == num[j] && i > j)) {
//        tmp = i; i = j; j = tmp;
//    }
//    rs[i] = merge(rs[i], j);
//    if (dist[ls[i]] < dist[rs[i]]) {
//        tmp = ls[i]; ls[i] = rs[i]; rs[i] = tmp;
//    }
//    dist[i] = dist[rs[i]] + 1;
//    fa[ls[i]] = fa[rs[i]] = i;
//    return i;
//}
//
//void reduce(int i, long long v) {
//    num[i] = max(num[i] - v, 0LL);
//    int l = find(i);
//    fa[ls[i]] = ls[i];
//    fa[rs[i]] = rs[i];
//    int r = merge(ls[i], rs[i]);
//    ls[i] = rs[i] = dist[i] = 0;
//    fa[i] = merge(l, r);
//}
//
//void prepare() {
//    dist[0] = -1;
//    for (int i = 1; i <= n; i++) {
//        ls[i] = rs[i] = dist[i] = 0;
//        fa[i] = i;
//    }
//}
//
//long long compute() {
//    long long ans = 0;
//    long long mx = 0;
//    for (int i = 1; i <= n; i++) {
//        if (fa[i] == i) {
//            ans += num[i];
//            if (num[i] > mx) mx = num[i];
//        }
//    }
//    if (w == 2) {
//        ans -= mx;
//    } else if (w == 3) {
//        ans += mx;
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(NULL);
//    cin >> t >> w >> k;
//    for(int i = 1; i <= t; i++) {
//        cin >> n >> m;
//        prepare();
//        for (int j = 1; j <= n; j++) {
//            cin >> num[j];
//        }
//        for (int j = 1, op, a, b; j <= m; j++) {
//            cin >> op >> a;
//            if (op == 2) {
//                reduce(a, num[a]);
//            } else if (op == 3) {
//                cin >> b;
//                reduce(find(a), b);
//            } else {
//                cin >> b;
//                int l = find(a);
//                int r = find(b);
//                if (l != r) {
//                    merge(l, r);
//                }
//            }
//        }
//        long long ans = compute();
//        if (ans == 0) {
//            cout << "Gensokyo " << ans << endl;
//        } else if (ans > k) {
//            cout << "Hell " << ans << endl;
//        } else {
//            cout << "Heaven " << ans << endl;
//        }
//    }
//    return 0;
//}