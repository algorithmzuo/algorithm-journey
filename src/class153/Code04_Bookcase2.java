package class153;

// 书架(C++版)
// 给定一个长度为n的排列，由数字1、2、3...n组成，实现如下五种操作
// Top s      : 数字s移动到最左边
// Bottom s   : 数字s移动到最右边
// Insert s t : 数字s位置假设为rank，现在移动到rank+t位置
// Ask s      : 查询数字s左边有多少数字
// Query s    : 查询从左往右第s位的数字
// 所有操作保证都是合法的
// 测试链接 : https://www.luogu.com.cn/problem/P2596
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <string>
//
//using namespace std;
//
//const int MAXN = 80005;
//
//int head = 0;
//int cnt = 0;
//int num[MAXN];
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
//int pos[MAXN];
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + 1;
//}
//
//int lr(int i) {
//    return rs[fa[i]] == i ? 1 : 0;
//}
//
//void rotate(int i) {
//    int f = fa[i], g = fa[f], soni = lr(i), sonf = lr(f);
//    if (soni == 1) {
//        rs[f] = ls[i];
//        if (rs[f] != 0) {
//            fa[rs[f]] = f;
//        }
//        ls[i] = f;
//    } else {
//        ls[f] = rs[i];
//        if (ls[f] != 0) {
//            fa[ls[f]] = f;
//        }
//        rs[i] = f;
//    }
//    if (g != 0) {
//        if (sonf == 1) {
//            rs[g] = i;
//        } else {
//            ls[g] = i;
//        }
//    }
//    fa[f] = i;
//    fa[i] = g;
//    up(f);
//    up(i);
//}
//
//void splay(int i, int goal) {
//    int f = fa[i], g = fa[f];
//    while (f != goal) {
//        if (g != goal) {
//            if (lr(i) == lr(f)) {
//                rotate(f);
//            } else {
//                rotate(i);
//            }
//        }
//        rotate(i);
//        f = fa[i];
//        g = fa[f];
//    }
//    if (goal == 0) {
//        head = i;
//    }
//}
//
//int find(int rank) {
//    int i = head;
//    while (i != 0) {
//        if (size[ls[i]] + 1 == rank) {
//            return i;
//        } else if (size[ls[i]] >= rank) {
//            i = ls[i];
//        } else {
//            rank -= size[ls[i]] + 1;
//            i = rs[i];
//        }
//    }
//    return 0;
//}
//
//void add(int s) {
//    num[++cnt] = s;
//    pos[s] = cnt;
//    size[cnt] = 1;
//    fa[cnt] = head;
//    rs[head] = cnt;
//    splay(cnt, 0);
//}
//
//int ask(int s) {
//    int i = pos[s];
//    splay(i, 0);
//    return size[ls[i]];
//}
//
//int query(int s) {
//    int i = find(s);
//    splay(i, 0);
//    return num[i];
//}
//
//void move(int a, int b) {
//    int l = find(a - 1);
//    int r = find(a + 1);
//    splay(l, 0);
//    splay(r, l);
//    int i = ls[r];
//    ls[r] = 0;
//    up(r);
//    up(l);
//    l = find(b - 1);
//    r = find(b);
//    splay(l, 0);
//    splay(r, l);
//    ls[r] = i;
//    fa[i] = r;
//    up(r);
//    up(l);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n, m;
//    cin >> n >> m;
//    add(0);
//    for (int i = 1, x; i <= n; i++) {
//        cin >> x;
//        add(x);
//    }
//    add(n + 1);
//    n = n + 2;
//    for (int i = 1, s, t, rank; i <= m; i++) {
//        string op;
//        cin >> op >> s;
//        rank = ask(s) + 1;
//        if (op == "Top") {
//            move(rank, 2);
//        } else if (op == "Bottom") {
//            move(rank, n - 1);
//        } else if (op == "Insert") {
//            cin >> t;
//            move(rank, rank + t);
//        } else if (op == "Ask") {
//            cout << rank - 2 << endl;
//        } else {
//            cout << query(s + 1) << endl;
//        }
//    }
//    return 0;
//}