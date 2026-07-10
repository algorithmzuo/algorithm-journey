package class202;

// 长跑，C++版
// 一共n个点，每个点有点权，初始没有边，接下来有m条操作，格式如下
// 操作 1 x y : 点x和点y之间增加无向边，已经连通则忽略
// 操作 2 x y : 点x的点权变成y
// 操作 3 x y : 每条边需要指定方向变成单向边，希望点x到点y的过程中
//              获得最多的点权累加和，重复经过某点只获得一次点权
//              打印能获得的点权累加和最大值，如果不连通打印-1
//              指定边的方向仅影响本次操作，不会影响后续操作
// 1 <= n <= 1.5 * 10^5
// 1 <= m <= 5 * n
// 测试链接 : https://www.luogu.com.cn/problem/P10658
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 200001;
//int n, m;
//
//int father[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//ll arr[MAXN];
//ll sum[MAXN];
//ll sumOfSum[MAXN];
//
//int find(int x) {
//    if (x != father[x]) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int x) {
//    sumOfSum[x] = sumOfSum[ls[x]] + sumOfSum[rs[x]] + sum[x];
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
//    x = find(x);
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        rs[x] = y;
//        up(x);
//        fa[x] = find(fa[x]);
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
//void condense(int x, int root) {
//    if (x != 0) {
//        father[x] = root;
//        sum[root] += sum[x];
//        condense(ls[x], root);
//        condense(rs[x], root);
//    }
//}
//
//void link(int x, int y) {
//    x = find(x);
//    y = find(y);
//    if (x == y) {
//        return;
//    }
//    makeroot(x);
//    if (findroot(y) != x) {
//        fa[x] = y;
//    } else {
//        condense(rs[x], x);
//        rs[x] = 0;
//        up(x);
//    }
//}
//
//void update(int x, int y) {
//    ll delta = y - arr[x];
//    arr[x] = y;
//    x = find(x);
//    makeroot(x);
//    sum[x] += delta;
//    up(x);
//}
//
//ll query(int x, int y) {
//    x = find(x);
//    y = find(y);
//    if (findroot(x) != findroot(y)) {
//        return -1;
//    }
//    split(x, y);
//    return sumOfSum[y];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        cin >> arr[i];
//        sum[i] = arr[i];
//        sumOfSum[i] = arr[i];
//    }
//    for (int i = 1, op, x, y; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 1) {
//            link(x, y);
//        } else if (op == 2) {
//            update(x, y);
//        } else {
//            cout << query(x, y) << "\n";
//        }
//    }
//    return 0;
//}