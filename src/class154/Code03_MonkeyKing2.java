package class154;

// 猴王，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1456
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//int num[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//int fa[MAXN];
//
//void prepare() {
//    dist[0] = -1;
//    for(int i = 1; i <= n; i++) {
//        ls[i] = rs[i] = dist[i] = 0;
//        fa[i] = i;
//    }
//}
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
//    fa[ls[i]] = fa[rs[i]] = i;
//    return i;
//}
//
//int pop(int i) {
//    fa[ls[i]] = ls[i];
//    fa[rs[i]] = rs[i];
//    fa[i] = merge(ls[i], rs[i]);
//    num[i] /= 2;
//    ls[i] = rs[i] = dist[i] = 0;
//    return fa[i];
//}
//
//int fight(int x, int y) {
//    int l = find(x);
//    int r = find(y);
//    if (l == r) {
//        return -1;
//    }
//    fa[l] = fa[r] = merge(merge(pop(l), l), merge(pop(r), r));
//    return num[fa[l]];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    while (cin >> n) {
//        prepare();
//        for (int i = 1; i <= n; i++) {
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