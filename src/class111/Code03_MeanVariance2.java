package class111;

// 平均数和方差
// 给定一个长度为n的数组arr，进行m次操作，操作分为三种类型
// 操作1 : arr数组中[l, r]范围上每个数字加上k，k为double类型
// 操作2 : 查询arr数组中[l, r]范围上所有数字的平均数，返回double类型
// 操作3 : 查询arr数组中[l, r]范围上所有数字的方差，返回double类型
// 测试链接 : https://www.luogu.com.cn/problem/P1471
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <cstdio>
//using namespace std;
//
//const int MAXN = 100001;
//double arr[MAXN];
//double sum1[MAXN << 2];
//double lazy1[MAXN << 2];
//double sum2[MAXN << 2];
//double lazy2[MAXN << 2];
//
//void up(int rt) {
//  sum1[rt] = sum1[rt << 1] + sum1[rt << 1 | 1];
//  sum2[rt] = sum2[rt << 1] + sum2[rt << 1 | 1];
//}
//
//void build(int l, int r, int rt) {
//    if (l == r) {
//        sum1[rt] = arr[l];
//        sum2[rt] = arr[l] * arr[l];
//    } else {
//        int mid = (l + r) / 2;
//        build(l, mid, rt << 1);
//        build(mid + 1, r, rt << 1 | 1);
//        up(rt);
//    }
//    lazy1[rt] = 0;
//    lazy2[rt] = 0;
//}
//
//void down(int rt, int ln, int rn) {
//    if (lazy1[rt] != 0 || lazy2[rt] != 0) {
//        lazy2[rt << 1] += lazy2[rt];
//        sum2[rt << 1] += sum1[rt << 1] * lazy2[rt] * 2 + lazy2[rt] * lazy2[rt] * ln;
//        lazy2[rt << 1 | 1] += lazy2[rt];
//        sum2[rt << 1 | 1] += sum1[rt << 1 | 1] * lazy2[rt] * 2 + lazy2[rt] * lazy2[rt] * rn;
//        lazy2[rt] = 0;
//        lazy1[rt << 1] += lazy1[rt];
//        sum1[rt << 1] += lazy1[rt] * ln;
//        lazy1[rt << 1 | 1] += lazy1[rt];
//        sum1[rt << 1 | 1] += lazy1[rt] * rn;
//        lazy1[rt] = 0;
//    }
//}
//
//void add(int jobl, int jobr, double jobv, int l, int r, int rt) {
//    if (jobl <= l && r <= jobr) {
//        lazy2[rt] += jobv;
//        sum2[rt] += sum1[rt] * jobv * 2 + jobv * jobv * (r - l + 1);
//        lazy1[rt] += jobv;
//        sum1[rt] += jobv * (r - l + 1);
//    } else {
//        int mid = (l + r) >> 1;
//        down(rt, mid - l + 1, r - mid);
//        if (jobl <= mid) add(jobl, jobr, jobv, l, mid, rt << 1);
//        if (jobr > mid) add(jobl, jobr, jobv, mid + 1, r, rt << 1 | 1);
//        up(rt);
//   }
//}
//
//double query(double sum[], int jobl, int jobr, int l, int r, int rt) {
//    if (jobl <= l && r <= jobr) {
//        return sum[rt];
//    }
//    int mid = (l + r) >> 1;
//    down(rt, mid - l + 1, r - mid);
//    double ans = 0;
//    if (jobl <= mid) ans += query(sum, jobl, jobr, l, mid, rt << 1);
//    if (jobr > mid) ans += query(sum, jobl, jobr, mid + 1, r, rt << 1 | 1);
//    return ans;
//}
//
//int main() {
//    int n, m;
//    scanf("%d %d", &n, &m);
//    for (int i = 1; i <= n; i++) {
//        scanf("%lf", &arr[i]);
//    }
//    build(1, n, 1);
//    for (int i = 1; i <= m; i++) {
//        int op, jobl, jobr;
//        scanf("%d", &op);
//        if (op == 1) {
//            double jobv;
//            scanf("%d %d %lf", &jobl, &jobr, &jobv);
//            add(jobl, jobr, jobv, 1, n, 1);
//        } else if (op == 2) {
//            scanf("%d %d", &jobl, &jobr);
//            double ans = query(sum1, jobl, jobr, 1, n, 1) / (jobr - jobl + 1);
//            printf("%.4f\n", ans);
//        } else {
//            scanf("%d %d", &jobl, &jobr);
//            double a = query(sum1, jobl, jobr, 1, n, 1);
//            double b = query(sum2, jobl, jobr, 1, n, 1);
//            double size = jobr - jobl + 1;
//            double ans = b / size - (a / size) * (a / size);
//            printf("%.4f\n", ans);
//        }
//    }
//    return 0;
//}
