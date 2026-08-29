package class206;

// 三维偏序，替罪羊树的方式重构，C++版
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
//const int INF = 1 << 30;
//int n, k;
//
//ABC abc[MAXN];
//
//int cntkdt;
//int root;
//BC arr[MAXN];
//int siz[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int bmin[MAXN];
//int bmax[MAXN];
//int cmin[MAXN];
//int cmax[MAXN];
//
//double ALPHA = 0.7;
//int collect[MAXN];
//int collectSiz;
//int top;
//int topFather;
//int topSide;
//int topDimension;
//int ans[MAXN];
//
//bool BCmp(int x, int y) {
//    return arr[x].b < arr[y].b;
//}
//
//bool CCmp(int x, int y) {
//    return arr[x].c < arr[y].c;
//}
//
//int init(int qb, int qc) {
//    cntkdt++;
//    arr[cntkdt].b = qb;
//    arr[cntkdt].c = qc;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    siz[cntkdt] = 1;
//    bmin[cntkdt] = bmax[cntkdt] = qb;
//    cmin[cntkdt] = cmax[cntkdt] = qc;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    siz[i] = siz[ls[i]] + siz[rs[i]] + 1;
//    bmin[i] = min(arr[i].b, min(bmin[ls[i]], bmin[rs[i]]));
//    bmax[i] = max(arr[i].b, max(bmax[ls[i]], bmax[rs[i]]));
//    cmin[i] = min(arr[i].c, min(cmin[ls[i]], cmin[rs[i]]));
//    cmax[i] = max(arr[i].c, max(cmax[ls[i]], cmax[rs[i]]));
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    if (dimension == 0) {
//        nth_element(collect + l, collect + mid, collect + r + 1, BCmp);
//    } else {
//        nth_element(collect + l, collect + mid, collect + r + 1, CCmp);
//    }
//    int rt = collect[mid];
//    ls[rt] = build(l, mid - 1, dimension ^ 1);
//    rs[rt] = build(mid + 1, r, dimension ^ 1);
//    maintain(rt);
//    return rt;
//}
//
//bool balance(int i) {
//    return ALPHA * siz[i] >= max(siz[ls[i]], siz[rs[i]]);
//}
//
//void dfs(int i) {
//    if (i != 0) {
//        collect[++collectSiz] = i;
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//void rebuild() {
//    if (top != 0) {
//        collectSiz = 0;
//        dfs(top);
//        int rt = build(1, collectSiz, topDimension);
//        if (topFather == 0) {
//            root = rt;
//        } else if (topSide == 1) {
//            ls[topFather] = rt;
//        } else {
//            rs[topFather] = rt;
//        }
//    }
//}
//
//void add(int insertNode, int u, int fa, int side, int dimension) {
//    if (u == 0) {
//        if (fa == 0) {
//            root = insertNode;
//        } else if (side == 1) {
//            ls[fa] = insertNode;
//        } else {
//            rs[fa] = insertNode;
//        }
//    } else {
//        int insertd = dimension == 0 ? arr[insertNode].b : arr[insertNode].c;
//        int ud = dimension == 0 ? arr[u].b : arr[u].c;
//        if (insertd <= ud) {
//            add(insertNode, ls[u], u, 1, dimension ^ 1);
//        } else {
//            add(insertNode, rs[u], u, 2, dimension ^ 1);
//        }
//        maintain(u);
//        if (!balance(u)) {
//            top = u;
//            topFather = fa;
//            topSide = side;
//            topDimension = dimension;
//        }
//    }
//}
//
//void add(int qb, int qc) {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init(qb, qc);
//    add(insertNode, root, 0, 0, 0);
//    rebuild();
//}
//
//int query(int qb, int qc, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (bmin[i] > qb || cmin[i] > qc) {
//        return 0;
//    }
//    if (bmax[i] <= qb && cmax[i] <= qc) {
//        return siz[i];
//    }
//    int ans = 0;
//    if (arr[i].b <= qb && arr[i].c <= qc) {
//        ans++;
//    }
//    ans += query(qb, qc, ls[i]);
//    ans += query(qb, qc, rs[i]);
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
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
//            int cur = query(abc[i].b, abc[i].c, root);
//            ans[cur - 1]++;
//        }
//    }
//    for (int d = 0; d < n; d++) {
//        cout << ans[d] << "\n";
//    }
//    return 0;
//}