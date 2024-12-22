package class154;

// 猴王，C++版
// 给定n只猴子的战斗力，一开始每个猴子都是独立的阵营
// 一共有m次冲突，每次冲突给定两只猴子的编号x、y
// 如果x和y在同一阵营，这次冲突停止，打印-1
// 如果x和y在不同阵营，x所在阵营的最强猴子会和y所在阵营的最强猴子进行打斗
// 打斗的结果是，两个各自阵营的最强猴子，战斗力都减半，向下取整，其他猴子战力不变
// 然后两个阵营合并，打印合并后的阵营最大战斗力
// 题目可能有多组数据，需要监控输入流直到结束
// 1 <= n, m <= 10^5
// 0 <= 猴子战斗力 <= 32768
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
//    if (num[i] < num[j]) {
//        swap(i, j);
//    }
//    rs[i] = merge(rs[i], j);
//    if (dist[ls[i]] < dist[rs[i]]) {
//        swap(ls[i], rs[i]);
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
//    ls[i] = rs[i] = dist[i] = 0;
//    return fa[i];
//}
//
//int fight(int x, int y) {
//    int a = find(x);
//    int b = find(y);
//    if (a == b) {
//        return -1;
//    }
//    int l = pop(a);
//    int r = pop(b);
//    num[a] /= 2;
//    num[b] /= 2;
//    fa[a] = fa[b] = fa[l] = fa[r] = merge(merge(l, a), merge(r, b));
//    return num[fa[a]];
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
//            cout << fight(x, y) << endl;
//        }
//    }
//    return 0;
//}