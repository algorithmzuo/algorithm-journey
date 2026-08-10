package class204;

// 须臾幻境，C++版
// 无向图中有n个点、m条无向边，边的编号是1~m
// 每条询问的格式为 l r，表示保留编号[l, r]的边，打印有多少个连通块
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 1 <= q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5385
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//const int MAXT = 10000001;
//const int INF = 1000000001;
//int n, m, q, t;
//
//int eu[MAXN];
//int ev[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int val[MAXN];
//int minv[MAXN];
//
//int root[MAXN];
//int tl[MAXT];
//int tr[MAXT];
//int num[MAXT];
//int cntt;
//
//void up(int x) {
//    minv[x] = min(val[x], min(minv[ls[x]], minv[rs[x]]));
//}
//
//bool isroot(int x) {
//    return ls[fa[x]] != x && rs[fa[x]] != x;
//}
//
//int lr(int x) {
//    return ls[fa[x]] == x ? 0 : 1;
//}
//
//void reverse(int x) {
//    if (x != 0) {
//        swap(ls[x], rs[x]);
//        rev[x] = !rev[x];
//    }
//}
//
//void down(int x) {
//    if (rev[x]) {
//        reverse(ls[x]);
//        reverse(rs[x]);
//        rev[x] = false;
//    }
//}
//
//void rotate(int x) {
//    int f = fa[x], g = fa[f];
//    if (lr(x) == 0) {
//        ls[f] = rs[x];
//        if (ls[f] != 0) {
//            fa[ls[f]] = f;
//        }
//        rs[x] = f;
//    } else {
//        rs[f] = ls[x];
//        if (rs[f] != 0) {
//            fa[rs[f]] = f;
//        }
//        ls[x] = f;
//    }
//    if (!isroot(f)) {
//        if (lr(f) == 0) {
//            ls[g] = x;
//        } else {
//            rs[g] = x;
//        }
//    }
//    fa[f] = x;
//    fa[x] = g;
//    up(f);
//    up(x);
//}
//
//void splay(int x) {
//    int size = 0;
//    sta[++size] = x;
//    for (int y = x; !isroot(y); y = fa[y]) {
//        sta[++size] = fa[y];
//    }
//    while (size != 0) {
//        down(sta[size--]);
//    }
//    while (!isroot(x)) {
//        int f = fa[x];
//        if (!isroot(f)) {
//            if (lr(x) == lr(f)) {
//                rotate(f);
//            } else {
//                rotate(x);
//            }
//        }
//        rotate(x);
//    }
//    up(x);
//}
//
//void access(int x) {
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        rs[x] = y;
//        up(x);
//    }
//}
//
//void makeroot(int x) {
//    access(x);
//    splay(x);
//    reverse(x);
//}
//
//int findroot(int x) {
//    access(x);
//    splay(x);
//    down(x);
//    while (ls[x] != 0) {
//        x = ls[x];
//        down(x);
//    }
//    splay(x);
//    return x;
//}
//
//void split(int x, int y) {
//    makeroot(x);
//    access(y);
//    splay(y);
//}
//
//void link(int x, int y) {
//    makeroot(x);
//    if (findroot(y) != x) {
//        fa[x] = y;
//    }
//}
//
//void cut(int x, int y) {
//    makeroot(x);
//    if (findroot(y) == x && fa[y] == x && ls[y] == 0 && rs[x] == y) {
//        fa[y] = rs[x] = 0;
//        up(x);
//    }
//}
//
//int pathMin(int x, int y) {
//    split(x, y);
//    return minv[y];
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    int rt = ++cntt;
//    tl[rt] = tl[i];
//    tr[rt] = tr[i];
//    num[rt] = num[i];
//    if (l == r) {
//        num[rt] += jobv;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            tl[rt] = add(jobi, jobv, l, mid, tl[i]);
//        } else {
//            tr[rt] = add(jobi, jobv, mid + 1, r, tr[i]);
//        }
//        num[rt] = num[tl[rt]] + num[tr[rt]];
//    }
//    return rt;
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return num[i];
//    }
//    int ans = 0;
//    int mid = (l + r) >> 1;
//    if (jobl <= mid) {
//        ans += query(jobl, jobr, l, mid, tl[i]);
//    }
//    if (jobr > mid) {
//        ans += query(jobl, jobr, mid + 1, r, tr[i]);
//    }
//    return ans;
//}
//
//void prepare() {
//    for (int i = 0; i <= n; i++) {
//        val[i] = minv[i] = INF;
//    }
//    for (int i = 1; i <= m; i++) {
//        val[n + i] = minv[n + i] = i;
//    }
//    for (int i = 1; i <= m; i++) {
//        int x = eu[i];
//        int y = ev[i];
//        if (x == y) {
//            root[i] = root[i - 1];
//        } else {
//            root[i] = add(i, 1, 1, m, root[i - 1]);
//            if (findroot(x) == findroot(y)) {
//                int e = pathMin(x, y);
//                cut(eu[e], n + e);
//                cut(ev[e], n + e);
//                root[i] = add(e, -1, 1, m, root[i]);
//            }
//            link(x, n + i);
//            link(y, n + i);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q >> t;
//    for (int i = 1; i <= m; i++) {
//        cin >> eu[i] >> ev[i];
//    }
//    prepare();
//    for (int i = 1, lastAns = 0, l, r; i <= q; i++) {
//        cin >> l >> r;
//        if (t > 0) {
//            l = (l + t * lastAns) % m + 1;
//            r = (r + t * lastAns) % m + 1;
//        }
//        if (l > r) {
//            swap(l, r);
//        }
//        lastAns = n - query(l, r, 1, m, root[r]);
//        cout << lastAns << "\n";
//    }
//    return 0;
//}