package class154;

// 罗马游戏，左偏树模版题2，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2713
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1000001;
//int num[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//int fa[MAXN];
//
//int find(int i) {
//    fa[i] = fa[i] == i ? i : find(fa[i]);
//    return fa[i];
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    if (num[i] > num[j]) {
//        int tmp = i;
//        i = j;
//        j = tmp;
//    }
//    rs[i] = merge(rs[i], j);
//    if (dist[ls[i]] < dist[rs[i]]) {
//        int tmp = ls[i];
//        ls[i] = rs[i];
//        rs[i] = tmp;
//    }
//    dist[i] = dist[rs[i]] + 1;
//    fa[ls[i]] = fa[rs[i]] = i;
//    return i;
//}
//
//int pop(int i) {
//    fa[ls[i]] = ls[i];
//    fa[rs[i]] = rs[i];
//    fa[i] = merge(ls[i], rs[i]);
//    num[i] = -1;
//    ls[i] = rs[i] = dist[i] = 0;
//    return fa[i];
//}
//
//int main() {
//    ios_base::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n; cin >> n;
//    for (int i = 1; i <= n; i++) {
//        fa[i] = i;
//        cin >> num[i];
//    }
//    int m; cin >> m;
//    for (int i = 1; i <= m; i++) {
//        string op; cin >> op;
//        if (op == "M") {
//            int x, y; cin >> x >> y;
//            if (num[x] != -1 && num[y] != -1) {
//                int l = find(x);
//                int r = find(y);
//                if (l != r) {
//                    merge(l, r);
//                }
//            }
//        } else {
//            int x; cin >> x;
//            if (num[x] == -1) {
//                cout << 0 << endl;
//            } else {
//                int ans = find(x);
//                cout << num[ans] << endl;
//                pop(ans);
//            }
//        }
//    }
//    return 0;
//}