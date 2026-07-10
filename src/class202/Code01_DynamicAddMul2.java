package class202;

// 动态树的加和乘，C++版
// 一共有n个点、n-1条边，每个点的初始点权都是1，所有节点组成一棵树
// 接下来有q条操作，操作类型有四种，格式如下
// 操作 + x y z   : x到y的路径上，点的权值都加上z，z是非负整数
// 操作 * x y z   : x到y的路径上，点的权值都乘上z，z是非负整数
// 操作 - x y a b : 删除原边(x,y)，加入新边(a,b)，操作后仍然是一棵树
// 操作 / x y     : x到y的路径上，查询点权累加和，对 51061 取模
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1501
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 100001;
//const int MOD = 51061;
//int n, q;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int sta[MAXN];
//bool rev[MAXN];
//
//int siz[MAXN];
//ll arr[MAXN];
//ll sum[MAXN];
//ll mulTag[MAXN];
//ll addTag[MAXN];
//
//void up(int x) {
//    siz[x] = siz[ls[x]] + siz[rs[x]] + 1;
//    sum[x] = (sum[ls[x]] + sum[rs[x]] + arr[x]) % MOD;
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
//        int tmp = ls[x];
//        ls[x] = rs[x];
//        rs[x] = tmp;
//        rev[x] = !rev[x];
//    }
//}
//
//void effect(int x, ll mul, ll add) {
//    if (x != 0) {
//        arr[x] = (arr[x] * mul + add) % MOD;
//        sum[x] = (sum[x] * mul + siz[x] * add) % MOD;
//        mulTag[x] = mulTag[x] * mul % MOD;
//        addTag[x] = (addTag[x] * mul + add) % MOD;
//    }
//}
//
//void down(int x) {
//    if (rev[x]) {
//        reverse(ls[x]);
//        reverse(rs[x]);
//        rev[x] = false;
//    }
//    if (mulTag[x] != 1 || addTag[x] != 0) {
//        effect(ls[x], mulTag[x], addTag[x]);
//        effect(rs[x], mulTag[x], addTag[x]);
//        mulTag[x] = 1;
//        addTag[x] = 0;
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
//    int siz = 0;
//    sta[++siz] = x;
//    for (int y = x; !isroot(y); y = fa[y]) {
//        sta[++siz] = fa[y];
//    }
//    while (siz != 0) {
//        down(sta[siz--]);
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
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    for (int i = 1; i <= n; i++) {
//        siz[i] = 1;
//        arr[i] = 1;
//        sum[i] = 1;
//        mulTag[i] = 1;
//        addTag[i] = 0;
//    }
//    for (int i = 1, x, y; i < n; i++) {
//        cin >> x >> y;
//        link(x, y);
//    }
//    string op;
//    int x, y, z, a, b;
//    for (int i = 1; i <= q; i++) {
//        cin >> op;
//        if (op == "+") {
//            cin >> x >> y >> z;
//            z %= MOD;
//            split(x, y);
//            effect(y, 1, z);
//        } else if (op == "*") {
//            cin >> x >> y >> z;
//            z %= MOD;
//            split(x, y);
//            effect(y, z, 0);
//        } else if (op == "-") {
//            cin >> x >> y >> a >> b;
//            cut(x, y);
//            link(a, b);
//        } else {
//            cin >> x >> y;
//            split(x, y);
//            cout << sum[y] << "\n";
//        }
//    }
//    return 0;
//}