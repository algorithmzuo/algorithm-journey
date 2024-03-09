package class108;

// 二维数组上范围增加、范围查询，使用树状数组的模版(C++)
// 测试链接 : https://www.luogu.com.cn/problem/P4514
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <cstdio>
//using namespace std;
//
//const int MAXN = 2050;
//const int MAXM = 2050;
//int tree1[MAXN][MAXM], tree2[MAXN][MAXM], tree3[MAXN][MAXM], tree4[MAXN][MAXM];
//int n, m;
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int x, int y, int v) {
//    for (int i = x; i <= n; i += lowbit(i)) {
//        for (int j = y; j <= m; j += lowbit(j)) {
//            tree1[i][j] += v;
//            tree2[i][j] += x * v;
//            tree3[i][j] += y * v;
//            tree4[i][j] += x * y * v;
//        }
//    }
//}
//
//int sum(int x, int y) {
//    int ans = 0;
//    for (int i = x; i > 0; i -= lowbit(i)) {
//        for (int j = y; j > 0; j -= lowbit(j)) {
//            ans += (x + 1) * (y + 1) * tree1[i][j] - (y + 1) * tree2[i][j] - (x + 1) * tree3[i][j] + tree4[i][j];
//        }
//    }
//    return ans;
//}
//
//void add(int a, int b, int c, int d, int v) {
//    add(a, b, v);
//    add(c + 1, d + 1, v);
//    add(a, d + 1, -v);
//    add(c + 1, b, -v);
//}
//
//int range(int a, int b, int c, int d) {
//    return sum(c, d) - sum(a - 1, d) - sum(c, b - 1) + sum(a - 1, b - 1);
//}
//
//int main() {
//    char op;
//    int a, b, c, d, v;
//    scanf("%s", &op);
//    scanf("%d%d", &n, &m);
//    while (scanf("%s", &op) != EOF) {
//        if (op == 'X') {
//            scanf("%d%d", &n, &m);
//        } else if (op == 'L') {
//            scanf("%d%d%d%d%d", &a, &b, &c, &d, &v);
//            add(a, b, c, d, v);
//        } else {
//            scanf("%d%d%d%d", &a, &b, &c, &d);
//            printf("%d\n", range(a, b, c, d));
//        }
//    }
//    return 0;
//}
