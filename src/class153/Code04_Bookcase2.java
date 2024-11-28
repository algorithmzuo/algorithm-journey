package class153;

// 书架(C++版)
// 测试链接 : https://www.luogu.com.cn/problem/P2596
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <cstdio>
//#include <string>
//using namespace std;
//
//const int MAXN = 80001;
//
//int head = 0;
//int cnt = 0;
//int num[MAXN];
//int pos[MAXN];
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
//int n, m;
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + 1;
//}
//
//int lr(int i) {
//    return rs[fa[i]] == i ? 1 : 0;
//}
//
//void upRotate(int i) {
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
//                upRotate(f);
//            } else {
//                upRotate(i);
//            }
//        }
//        upRotate(i);
//        f = fa[i];
//        g = fa[f];
//    }
//    if (goal == 0) {
//        head = i;
//    }
//}
//
//int find(int rank) {
//    int i = head, ans = 0;
//    while (i != 0) {
//        if (size[ls[i]] + 1 == rank) {
//            ans = i;
//            break;
//        } else if (size[ls[i]] >= rank) {
//            i = ls[i];
//        } else {
//            rank -= size[ls[i]] + 1;
//            i = rs[i];
//        }
//    }
//    splay(ans, 0);
//    return ans;
//}
//
//void add(int s) {
//    int i = head;
//    while (rs[i] != 0) {
//        i = rs[i];
//    }
//    num[++cnt] = s;
//    pos[s] = cnt;
//    size[cnt] = 1;
//    fa[cnt] = i;
//    rs[i] = cnt;
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
//    return num[find(s)];
//}
//
//void disconnect(int i, int rank) {
//    splay(i, 0);
//    if (rank == 1) {
//        head = rs[head];
//        fa[head] = 0;
//    } else if (rank == n) {
//        head = ls[head];
//        fa[head] = 0;
//    } else {
//        int l = find(rank - 1);
//        splay(l, 0);
//        splay(i, l);
//        rs[l] = rs[i];
//        fa[rs[l]] = l;
//        up(l);
//    }
//    fa[i] = ls[i] = rs[i] = 0;
//}
//
//void connect(int i, int rank) {
//    if (rank == 1) {
//        rs[i] = head;
//        fa[rs[i]] = i;
//        head = i;
//        up(i);
//    } else if (rank == n) {
//        ls[i] = head;
//        fa[ls[i]] = i;
//        head = i;
//        up(i);
//    } else {
//        int r = find(rank - 1);
//        int next = find(rank);
//        splay(r, 0);
//        splay(next, r);
//        rs[r] = i;
//        fa[i] = r;
//        rs[i] = next;
//        fa[next] = i;
//        up(i);
//        up(r);
//    }
//}
//
//void move(int a, int b) {
//    int i = find(a);
//    disconnect(i, a);
//    connect(i, b);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        int x;
//        cin >> x;
//        add(x);
//    }
//    for (int i = 1; i <= m; i++) {
//        string op;
//        int s, t, small;
//        cin >> op >> s;
//        small = ask(s);
//        if (op == "Top") {
//            move(small + 1, 1);
//        } else if (op == "Bottom") {
//            move(small + 1, n);
//        } else if (op == "Insert") {
//            cin >> t;
//            move(small + 1, small + t + 1);
//        } else if (op == "Ask") {
//            cout << small << endl;
//        } else {
//            cout << query(s) << endl;
//        }
//    }
//    return 0;
//}