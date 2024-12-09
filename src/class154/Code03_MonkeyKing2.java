package class154;

// 猴王，左偏树模版题3，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1456
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int num[MAXN];
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//
//int find(int i) {
//    if (fa[i] != i) {
//        fa[i] = find(fa[i]);
//    }
//    return fa[i];
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    int tmp;
//    if (num[i] < num[j]) {
//        tmp = i;
//        i = j;
//        j = tmp;
//    }
//    rs[i] = merge(rs[i], j);
//    if (dist[ls[i]] < dist[rs[i]]) {
//        tmp = ls[i];
//        ls[i] = rs[i];
//        rs[i] = tmp;
//    }
//    dist[i] = dist[rs[i]] + 1;
//    fa[i] = fa[ls[i]] = fa[rs[i]] = i;
//    return i;
//}
//
//int pop(int i) {
//    fa[ls[i]] = ls[i];
//    fa[rs[i]] = rs[i];
//    fa[i] = merge(ls[i], rs[i]);
//    num[i] /= 2;
//    ls[i] = rs[i] = 0;
//    return fa[i];
//}
//
//int fight(int x, int y) {
//    int l = find(x);
//    int r = find(y);
//    return l == r ? -1 : num[merge(merge(pop(l), l), merge(pop(r), r))];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n, m;
//    while (cin >> n) {
//        for (int i = 1; i <= n; i++) {
//            fa[i] = i;
//            ls[i] = rs[i] = dist[i] = 0;
//            cin >> num[i];
//        }
//        cin >> m;
//        for (int i = 1; i <= m; i++) {
//            int x, y;
//            cin >> x >> y;
//            cout << fight(x, y) << "\n";
//        }
//    }
//    return 0;
//}