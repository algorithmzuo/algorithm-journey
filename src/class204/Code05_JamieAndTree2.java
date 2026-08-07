package class204;

// 杰米与树，C++版
// 一共n个节点、n-1条边，所有节点组成一棵树，点有点权，初始的根为1号节点
// 接下来有q条操作，操作类型如下
// 操作 1 x     : 整棵树的根修改为x
// 操作 2 x y v : 当前根的情况下，lca(x, y)的子树中所有点权增加v
// 操作 3 x     : 当前根的情况下，打印x的子树点权累加和
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF916E
// 测试链接 : https://codeforces.com/problemset/problem/916/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 100001;
//int n, q, root;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//ll val[MAXN];
//
//int splaySize[MAXN];
//int treeSize[MAXN];
//int virSize[MAXN];
//int allVirSize[MAXN];
//
//ll treeSum[MAXN];
//ll virSum[MAXN];
//ll virAdd[MAXN];
//
//ll virTag[MAXN];
//ll splayTag[MAXN];
//
//void up(int x) {
//    splaySize[x] = splaySize[ls[x]] + splaySize[rs[x]] + 1;
//    treeSize[x] = treeSize[ls[x]] + treeSize[rs[x]] + virSize[x] + 1;
//    allVirSize[x] = allVirSize[ls[x]] + allVirSize[rs[x]] + virSize[x];
//    treeSum[x] = treeSum[ls[x]] + treeSum[rs[x]] + val[x] + virSum[x] + virSize[x] * virAdd[x];
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
//void addSplay(int x, ll v) {
//    if (x != 0) {
//        splayTag[x] += v;
//        val[x] += v;
//        treeSum[x] += v * splaySize[x];
//    }
//}
//
//void addVirtual(int x, ll v) {
//    if (x != 0) {
//        virAdd[x] += v;
//        virTag[x] += v;
//        treeSum[x] += v * allVirSize[x];
//    }
//}
//
//void down(int x) {
//    if (rev[x]) {
//        reverse(ls[x]);
//        reverse(rs[x]);
//        rev[x] = false;
//    }
//    if (splayTag[x] != 0) {
//        addSplay(ls[x], splayTag[x]);
//        addSplay(rs[x], splayTag[x]);
//        splayTag[x] = 0;
//    }
//    if (virTag[x] != 0) {
//        addVirtual(ls[x], virTag[x]);
//        addVirtual(rs[x], virTag[x]);
//        virTag[x] = 0;
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
//    up(x);
//}
//
//void insertVirtual(int f, int x) {
//    if (x != 0) {
//        addSplay(x, -virAdd[f]);
//        addVirtual(x, -virAdd[f]);
//        virSize[f] += treeSize[x];
//        virSum[f] += treeSum[x];
//    }
//}
//
//void removeVirtual(int f, int x) {
//    if (x != 0) {
//        virSize[f] -= treeSize[x];
//        virSum[f] -= treeSum[x];
//        addSplay(x, virAdd[f]);
//        addVirtual(x, virAdd[f]);
//    }
//}
//
//int access(int x) {
//    int ans = 0;
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        insertVirtual(x, rs[x]);
//        removeVirtual(x, y);
//        rs[x] = y;
//        up(x);
//        ans = x;
//    }
//    return ans;
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
//        access(y);
//        splay(y);
//        fa[x] = y;
//        insertVirtual(y, x);
//        up(y);
//    }
//}
//
//void addLcaTree(int x, int y, int v) {
//    makeroot(root);
//    access(x);
//    int xylca = access(y);
//    split(root, xylca);
//    virAdd[xylca] += v;
//    val[xylca] += v;
//    treeSum[xylca] += (virSize[xylca] + 1LL) * v;
//}
//
//ll query(int x) {
//    split(root, x);
//    return val[x] + virSum[x] + virSize[x] * virAdd[x];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    root = 1;
//    for (int i = 1; i <= n; i++) {
//        cin >> val[i];
//        splaySize[i] = 1;
//        treeSize[i] = 1;
//        treeSum[i] = val[i];
//    }
//    for (int i = 1, x, y; i < n; i++) {
//        cin >> x >> y;
//        link(x, y);
//    }
//    for (int i = 1, op, x, y, v; i <= q; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> root;
//        } else if (op == 2) {
//            cin >> x >> y >> v;
//            addLcaTree(x, y, v);
//        } else {
//            cin >> x;
//            cout << query(x) << "\n";
//        }
//    }
//    return 0;
//}