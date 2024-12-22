package class154;

// 派遣，C++版
// 一共有n个忍者，每个忍者有上级编号、工资、能力，三个属性
// 输入保证，任何忍者的上级编号 < 这名忍者的编号，1号忍者是整棵忍者树的头
// 你一共有m的预算，可以在忍者树上随意选一棵子树，然后在这棵子树上挑选忍者
// 你选择某棵子树之后，不一定要选子树头的忍者，只要不超过m的预算，可以随意选择子树上的忍者
// 最终收益 = 雇佣人数 * 子树头忍者的能力，返回能取得的最大收益是多少
// 1 <= n <= 10^5           1 <= m <= 10^9
// 1 <= 每个忍者工资 <= m     1 <= 每个忍者领导力 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1552
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//int leader[MAXN];
//long long cost[MAXN];
//long long ability[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//int fa[MAXN];
//int siz[MAXN];
//long long sum[MAXN];
//
//int find(int i) {
//    return fa[i] = (fa[i] == i ? i : find(fa[i]));
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    if (cost[i] < cost[j]) {
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
//void prepare() {
//    dist[0] = -1;
//    for (int i = 1; i <= n; i++) {
//        ls[i] = rs[i] = dist[i] = 0;
//        siz[i] = 1;
//        sum[i] = cost[i];
//        fa[i] = i;
//    }
//}
//
//long long compute() {
//    long long ans = 0;
//    int p, psize, h, hsize;
//    long long hsum, psum;
//    for (int i = n; i >= 1; i--) {
//        h = find(i);
//        hsize = siz[h];
//        hsum = sum[h];
//        while (hsum > m) {
//            pop(h);
//            hsize--;
//            hsum -= cost[h];
//            h = find(i);
//        }
//        ans = max(ans, (long long)hsize * ability[i]);
//        if (i > 1) {
//            p = find(leader[i]);
//            psize = siz[p];
//            psum = sum[p];
//            fa[p] = fa[h] = merge(p, h);
//            siz[fa[p]] = psize + hsize;
//            sum[fa[p]] = psum + hsum;
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> leader[i] >> cost[i] >> ability[i];
//    }
//    prepare();
//    cout << compute() << endl;
//    return 0;
//}