package class153;

// 文艺平衡树，Splay实现范围翻转，C++版本
// 长度为n的序列，下标从1开始，一开始序列为1, 2, ..., n
// 接下来会有k个操作，每个操作给定l，r，表示从l到r范围上的所有数字翻转
// 做完k次操作后，从左到右打印所有数字
// 1 <= n, k <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3391
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//
//using namespace std;
//
//const int MAXN = 100005;
//
//int head = 0;
//int cnt = 0;
//int num[MAXN];
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//int si;
//int ans[MAXN];
//int ai;
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
//void down(int i) {
//    if (rev[i]) {
//        rev[ls[i]] = !rev[ls[i]];
//        rev[rs[i]] = !rev[rs[i]];
//        int tmp = ls[i];
//        ls[i] = rs[i];
//        rs[i] = tmp;
//        rev[i] = false;
//    }
//}
//
//int find(int rank) {
//    int i = head;
//    while (i != 0) {
//        down(i);
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
//void add(int x) {
//    num[++cnt] = x;
//    size[cnt] = 1;
//    fa[cnt] = head;
//    rs[head] = cnt;
//    splay(cnt, 0);
//}
//
//void reverse(int l, int r) {
//    int i = find(l - 1);
//    int j = find(r + 1);
//    splay(i, 0);
//    splay(j, i);
//    rev[ls[rs[head]]] = !rev[ls[rs[head]]];
//}
//
//void inorder(int i) {
//    if (i != 0) {
//        down(i);
//        inorder(ls[i]);
//        ans[++ai] = num[i];
//        inorder(rs[i]);
//    }
//}
//
//void inorder() {
//    si = 0;
//    int i = head;
//    while (si != 0 || i != 0) {
//        if (i != 0) {
//            down(i);
//            sta[++si] = i;
//            i = ls[i];
//        } else {
//            i = sta[si--];
//            ans[++ai] = num[i];
//            i = rs[i];
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n, m;
//    cin >> n >> m;
//    add(0);
//    for (int i = 1; i <= n; i++) {
//        add(i);
//    }
//    add(0);
//    for (int i = 1, x, y; i <= m; i++) {
//        cin >> x >> y;
//        reverse(x + 1, y + 1);
//    }
//    ai = 0;
//    // inorder(head);
//    inorder();
//    for (int i = 2; i < ai; i++) {
//        cout << ans[i] << " ";
//    }
//    cout << endl;
//    return 0;
//}