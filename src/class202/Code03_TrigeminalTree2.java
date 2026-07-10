package class202;

// 三叉神经树，C++版
// 节点有两种类型，细胞节点编号范围[1, n]，信号节点编号范围[n+1, 3*n+1]
// 每个信号节点没有儿子节点，每个细胞节点固定有三个儿子节点
// 给定每个细胞节点的三个儿子节点编号，每个儿子可能是细胞节点，或者信号节点
// 每个信号节点拥有自身的值，是0或者1，初始值会给定，该值向上传递给父节点
// 每个细胞节点没有自身的值，根据三个儿子的信号，决定向上传递的值
// 三个儿子节点中，1多就向上传递1，0多就向上传递0
// 一共q条操作，每次给定一个信号节点的编号，表示它的值发生了翻转
// 题目保证1号节点是整棵树的根，每次操作执行完，都打印1号节点的输出
// 1 <= n、q <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4332
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXT = MAXN * 3;
//int n, q;
//
//int parent[MAXT];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//bool tag[MAXN];
//int sta[MAXN];
//
//int cell[MAXN];
//int sign[MAXT];
//
//int end1[MAXN];
//int end2[MAXN];
//
//int que[MAXT];
//int degree[MAXN];
//
//int ans;
//
//void up(int x) {
//    end1[x] = end1[rs[x]];
//    if (end1[x] == 0 && cell[x] != 1) {
//        end1[x] = x;
//    }
//    if (end1[x] == 0) {
//        end1[x] = end1[ls[x]];
//    }
//    end2[x] = end2[rs[x]];
//    if (end2[x] == 0 && cell[x] != 2) {
//        end2[x] = x;
//    }
//    if (end2[x] == 0) {
//        end2[x] = end2[ls[x]];
//    }
//}
//
//void effect(int x) {
//    if (x != 0) {
//        cell[x] ^= 3;
//        swap(end1[x], end2[x]);
//        tag[x] = !tag[x];
//    }
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
//void down(int x) {
//    if (tag[x]) {
//        effect(ls[x]);
//        effect(rs[x]);
//        tag[x] = false;
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
//void change(int x) {
//    sign[x] ^= 1;
//    int delta = sign[x] == 1 ? 1 : -1;
//    x = parent[x];
//    access(x);
//    splay(x);
//    int stop = delta == 1 ? end1[x] : end2[x];
//    if (stop != 0) {
//        splay(stop);
//        effect(rs[stop]);
//        cell[stop] += delta;
//        up(stop);
//    } else {
//        effect(x);
//        ans ^= 1;
//    }
//}
//
//int get01(int x) {
//    if (x <= n) {
//        return cell[x] <= 1 ? 0 : 1;
//    } else {
//        return sign[x];
//    }
//}
//
//void topo() {
//    for (int i = 1; i <= n; i++) {
//        degree[i] = 3;
//    }
//    int l = 1, r = 0;
//    for (int i = n + 1; i <= 3 * n + 1; i++) {
//        que[++r] = i;
//    }
//    while (l <= r) {
//        int x = que[l++];
//        if (x <= n) {
//            up(x);
//        }
//        int p = parent[x];
//        if (p != 0) {
//            cell[p] += get01(x);
//            if (--degree[p] == 0) {
//                que[++r] = p;
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, x; i <= n; i++) {
//        for (int j = 1; j <= 3; j++) {
//            cin >> x;
//            parent[x] = i;
//            if (x <= n) {
//                fa[x] = i;
//            }
//        }
//    }
//    for (int i = n + 1; i <= 3 * n + 1; i++) {
//        cin >> sign[i];
//    }
//    topo();
//    ans = get01(1);
//    cin >> q;
//    for (int i = 1, x; i <= q; i++) {
//        cin >> x;
//        change(x);
//        cout << ans << "\n";
//    }
//    return 0;
//}