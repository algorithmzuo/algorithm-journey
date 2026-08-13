package class204;

// 首都，C++版
// 初始有n个互不连通的城市，一共有m条操作，操作类型如下
// 操作 A x y : 在两个不同国家的城市x和y之间连边，两个国家合并
// 操作 Q x   : 打印城市x所在国家的首都
// 操作 Xor   : 打印当前所有国家首都编号的异或和
// 一个国家的道路构成树，首都是到其他城市距离总和最小的城市
// 如果有多个首都，选择编号最小的城市
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4299
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int father[MAXN];
//int vir[MAXN];
//int sum[MAXN];
//
//int xorsum;
//
//int find(int x) {
//    if (x != father[x]) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int x) {
//    sum[x] = sum[ls[x]] + sum[rs[x]] + vir[x] + 1;
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
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        vir[x] += sum[rs[x]];
//        vir[x] -= sum[y];
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
//        access(y);
//        splay(y);
//        fa[x] = y;
//        vir[y] += sum[x];
//        up(y);
//    }
//}
//
//int newCenter(int a, int b) {
//    split(a, b);
//    int half = sum[b] >> 1;
//    int lpass = 0;
//    int rpass = 0;
//    int ans = n + 1;
//    int cur = b;
//    while (cur != 0) {
//        down(cur);
//        int lsiz = sum[ls[cur]] + lpass;
//        int rsiz = sum[rs[cur]] + rpass;
//        if (lsiz <= half && rsiz <= half) {
//            ans = min(ans, cur);
//        }
//        if (lsiz < rsiz) {
//            lpass += sum[ls[cur]] + vir[cur] + 1;
//            cur = rs[cur];
//        } else {
//            rpass += sum[rs[cur]] + vir[cur] + 1;
//            cur = ls[cur];
//        }
//    }
//    splay(ans);
//    return ans;
//}
//
//void road(int x, int y) {
//    int a = find(x);
//    int b = find(y);
//    link(x, y);
//    int cur = newCenter(a, b);
//    father[cur] = father[a] = father[b] = cur;
//    xorsum ^= a ^ b ^ cur;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        sum[i] = 1;
//        father[i] = i;
//        xorsum ^= i;
//    }
//    string op;
//    int x, y;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == "A") {
//            cin >> x >> y;
//            road(x, y);
//        } else if (op == "Q") {
//            cin >> x;
//            cout << find(x) << "\n";
//        } else {
//            cout << xorsum << "\n";
//        }
//    }
//    return 0;
//}