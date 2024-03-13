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
//int info1[MAXN][MAXM], info2[MAXN][MAXM], info3[MAXN][MAXM], info4[MAXN][MAXM];
//int n, m;
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int x, int y, int v) {
//    int v1 = v;
//    int v2 = x * v;
//    int v3 = y * v;
//    int v4 = x * y * v;
//    for (int i = x; i <= n; i += lowbit(i)) {
//        for (int j = y; j <= m; j += lowbit(j)) {
//            info1[i][j] += v1;
//            info2[i][j] += v2;
//            info3[i][j] += v3;
//            info4[i][j] += v4;
//        }
//    }
//}
//
//int sum(int x, int y) {
//    int ans = 0;
//    for (int i = x; i > 0; i -= lowbit(i)) {
//        for (int j = y; j > 0; j -= lowbit(j)) {
//            ans += (x + 1) * (y + 1) * info1[i][j] - (y + 1) * info2[i][j] - (x + 1) * info3[i][j] + info4[i][j];
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
