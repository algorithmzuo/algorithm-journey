package class160;

// 线段树套线段树，C++版
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=1823
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXH = 101;
//const int MAXA = 1001;
//int n = 1000, m;
//int tree[MAXH << 2][MAXA << 2];
//
//void ybuild(int yl, int yr, int xi, int yi) {
//    tree[xi][yi] = -1;
//    if (yl < yr) {
//        int mid = (yl + yr) >> 1;
//        ybuild(yl, mid, xi, yi << 1);
//        ybuild(mid + 1, yr, xi, yi << 1 | 1);
//    }
//}
//
//void build(int xl, int xr, int xi) {
//    ybuild(0, n, xi, 1);
//    if (xl < xr) {
//        int mid = (xl + xr) >> 1;
//        build(xl, mid, xi << 1);
//        build(mid + 1, xr, xi << 1 | 1);
//    }
//}
//
//void yupdate(int joby, int jobv, int yl, int yr, int xi, int yi) {
//    if (yl == yr) {
//        tree[xi][yi] = max(tree[xi][yi], jobv);
//    } else {
//        int mid = (yl + yr) >> 1;
//        if (joby <= mid) {
//            yupdate(joby, jobv, yl, mid, xi, yi << 1);
//        } else {
//            yupdate(joby, jobv, mid + 1, yr, xi, yi << 1 | 1);
//        }
//        tree[xi][yi] = max(tree[xi][yi << 1], tree[xi][(yi << 1) | 1]);
//    }
//}
//
//void update(int jobx, int joby, int jobv, int xl, int xr, int xi) {
//    yupdate(joby, jobv, 0, n, xi, 1);
//    if (xl < xr) {
//        int mid = (xl + xr) >> 1;
//        if (jobx <= mid) {
//            update(jobx, joby, jobv, xl, mid, xi << 1);
//        } else {
//            update(jobx, joby, jobv, mid + 1, xr, xi << 1 | 1);
//        }
//    }
//}
//
//int yquery(int jobyl, int jobyr, int yl, int yr, int xi, int yi) {
//    if (jobyl <= yl && yr <= jobyr) {
//        return tree[xi][yi];
//    }
//    int mid = (yl + yr) >> 1;
//    int ans = -1;
//    if (jobyl <= mid) {
//        ans = max(ans, yquery(jobyl, jobyr, yl, mid, xi, yi << 1));
//    }
//    if (jobyr > mid) {
//        ans = max(ans, yquery(jobyl, jobyr, mid + 1, yr, xi, (yi << 1) | 1));
//    }
//    return ans;
//}
//
//int query(int jobxl, int jobxr, int jobyl, int jobyr, int xl, int xr, int xi) {
//    if (jobxl <= xl && xr <= jobxr) {
//        return yquery(jobyl, jobyr, 0, n, xi, 1);
//    }
//    int mid = (xl + xr) >> 1;
//    int ans = -1;
//    if (jobxl <= mid) {
//        ans = max(ans, query(jobxl, jobxr, jobyl, jobyr, xl, mid, xi << 1));
//    }
//    if (jobxr > mid) {
//        ans = max(ans, query(jobxl, jobxr, jobyl, jobyr, mid + 1, xr, (xi << 1) | 1));
//    }
//    return ans;
//}
//
//int main() {
//	scanf("%d", &m);
//	while(m != 0) {
//        build(100, 200, 1);
//        for (int i = 0; i < m; i++) {
//        	char op[2];
//            scanf("%s", op);
//            if (op[0] == 'I') {
//                int a;
//                double bd, cd;
//                scanf("%d %lf %lf", &a, &bd, &cd);
//                int b = (int)(bd * 10);
//                int c = (int)(cd * 10);
//                update(a, b, c, 100, 200, 1);
//            } else {
//                int a, b;
//                double cd, dd;
//                scanf("%d %d %lf %lf", &a, &b, &cd, &dd);
//                int c = (int)(cd * 10);
//                int d = (int)(dd * 10);
//                int xl = min(a, b);
//                int xr = max(a, b);
//                int yl = min(c, d);
//                int yr = max(c, d);
//                int ans = query(xl, xr, yl, yr, 100, 200, 1);
//                if (ans == -1) {
//                    printf("-1\n");
//                } else {
//                    printf("%.1f\n", ans / 10.0);
//                }
//            }
//        }
//        scanf("%d", &m);
//	}
//    return 0;
//}