package class205;

// 三维偏序，C++版
// 本题就是讲解170，题目1，讲了CDQ分治的解法，这里用kdt的解法
// 一共有n个对象，每个对象有a、b、c三个属性，每个属性值的范围都是[1, k]
// f(i)表示，aj <= ai 且 bj <= bi 且 cj <= ci 且 j != i 的j的数量
// ans(d)表示，f(i) == d 的i的数量
// 打印所有的ans[d]，d的范围[0, n)
// 1 <= n <= 10^5
// 1 <= k <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3810
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct ABC {
//    int a;
//    int b;
//    int c;
//};
//
//bool ABCCmp(ABC x, ABC y) {
//    return x.a < y.a;
//}
//
//struct BC {
//    int b;
//    int c;
//};
//
//const int MAXN = 100001;
//const int MAXP = 18;
//const int INF = 1 << 30;
//int n, k, cntn;
//
//ABC abc[MAXN];
//BC bc[MAXN];
//
//int siz[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//int bmin[MAXN];
//int bmax[MAXN];
//int cmin[MAXN];
//int cmax[MAXN];
//
//int root[MAXP];
//int ans[MAXN];
//
//int first, last;
//
//void partition(int l, int r, int pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        int cur = dimension == 0 ? bc[i].b : bc[i].c;
//        if (cur == pivot) {
//            i++;
//        } else if (cur < pivot) {
//            swap(bc[first++], bc[i++]);
//        } else {
//            swap(bc[i], bc[last--]);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        int idx = l + rand() % (r - l + 1);
//        int pivot = dimension == 0 ? bc[idx].b : bc[idx].c;
//        partition(l, r, pivot, dimension);
//        if (i < first) {
//            r = first - 1;
//        } else if (i > last) {
//            l = last + 1;
//        } else {
//            break;
//        }
//    }
//}
//
//void maintain(int i) {
//    siz[i] = siz[ls[i]] + siz[rs[i]] + 1;
//    bmin[i] = min(bc[i].b, min(bmin[ls[i]], bmin[rs[i]]));
//    bmax[i] = max(bc[i].b, max(bmax[ls[i]], bmax[rs[i]]));
//    cmin[i] = min(bc[i].c, min(cmin[ls[i]], cmin[rs[i]]));
//    cmax[i] = max(bc[i].c, max(cmax[ls[i]], cmax[rs[i]]));
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    if (l == r) {
//        ls[mid] = 0;
//        rs[mid] = 0;
//    } else {
//        randSelect(l, r, mid, dimension);
//        ls[mid] = build(l, mid - 1, dimension ^ 1);
//        rs[mid] = build(mid + 1, r, dimension ^ 1);
//    }
//    maintain(mid);
//    return mid;
//}
//
//void add(int b, int c) {
//    cntn++;
//    bc[cntn].b = b;
//    bc[cntn].c = c;
//    int p = 0;
//    while (root[p] != 0) {
//        root[p++] = 0;
//    }
//    root[p] = build(cntn - (1 << p) + 1, cntn, 0);
//}
//
//int query(int b, int c, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (bmin[i] > b || cmin[i] > c) {
//        return 0;
//    }
//    if (bmax[i] <= b && cmax[i] <= c) {
//        return siz[i];
//    }
//    int ans = 0;
//    if (bc[i].b <= b && bc[i].c <= c) {
//        ans++;
//    }
//    ans += query(b, c, ls[i]);
//    ans += query(b, c, rs[i]);
//    return ans;
//}
//
//int query(int b, int c) {
//    int ans = 0;
//    for (int p = 0; p < MAXP; p++) {
//        ans += query(b, c, root[p]);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand((unsigned)time(nullptr));
//    cin >> n >> k;
//    for (int i = 1; i <= n; i++) {
//        cin >> abc[i].a >> abc[i].b >> abc[i].c;
//    }
//    sort(abc + 1, abc + n + 1, ABCCmp);
//    bmin[0] = cmin[0] = INF;
//    bmax[0] = cmax[0] = -INF;
//    for (int l = 1, r = 1; l <= n; l = ++r) {
//        while (r + 1 <= n && abc[r + 1].a == abc[l].a) {
//            r++;
//        }
//        for (int i = l; i <= r; i++) {
//            add(abc[i].b, abc[i].c);
//        }
//        for (int i = l; i <= r; i++) {
//            int cur = query(abc[i].b, abc[i].c);
//            ans[cur - 1]++;
//        }
//    }
//    for (int d = 0; d < n; d++) {
//        cout << ans[d] << "\n";
//    }
//    return 0;
//}