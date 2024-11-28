package class153;

// Splay树实现普通有序表，不用词频压缩，数据加强的测试，C++版
// 这个文件课上没有讲，测试数据加强了，而且有强制在线的要求
// 基本功能要求都是不变的，可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P6136
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <vector>
//#include <algorithm>
//#include <climits>
//
//using namespace std;
//
//const int MAXN = 2000001;
//
//int head = 0;
//int cnt = 0;
//int key[MAXN];
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
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
//void add(int num) {
//    key[++cnt] = num;
//    size[cnt] = 1;
//    if (head == 0) {
//        head = cnt;
//    } else {
//        int f = 0, i = head, son = 0;
//        while (i != 0) {
//            f = i;
//            if (key[i] <= num) {
//                son = 1;
//                i = rs[i];
//            } else {
//                son = 0;
//                i = ls[i];
//            }
//        }
//        if (son == 1) {
//            rs[f] = cnt;
//        } else {
//            ls[f] = cnt;
//        }
//        fa[cnt] = f;
//        splay(cnt, 0);
//    }
//}
//
//int getRank(int num) {
//    int i = head, last = head;
//    int ans = 0;
//    while (i != 0) {
//        last = i;
//        if (key[i] >= num) {
//            i = ls[i];
//        } else {
//            ans += size[ls[i]] + 1;
//            i = rs[i];
//        }
//    }
//    splay(last, 0);
//    return ans + 1;
//}
//
//int index(int x) {
//    int i = find(x);
//    splay(i, 0);
//    return key[i];
//}
//
//int pre(int num) {
//    int i = head, last = head;
//    int ans = INT_MIN;
//    while (i != 0) {
//        last = i;
//        if (key[i] >= num) {
//            i = ls[i];
//        } else {
//            ans = max(ans, key[i]);
//            i = rs[i];
//        }
//    }
//    splay(last, 0);
//    return ans;
//}
//
//int post(int num) {
//    int i = head, last = head;
//    int ans = INT_MAX;
//    while (i != 0) {
//        last = i;
//        if (key[i] <= num) {
//            i = rs[i];
//        } else {
//            ans = min(ans, key[i]);
//            i = ls[i];
//        }
//    }
//    splay(last, 0);
//    return ans;
//}
//
//void remove(int num) {
//    int kth = getRank(num);
//    if (kth != getRank(num + 1)) {
//        int i = find(kth);
//        splay(i, 0);
//        if (ls[i] == 0) {
//            head = rs[i];
//        } else if (rs[i] == 0) {
//            head = ls[i];
//        } else {
//            int j = find(kth + 1);
//            splay(j, i);
//            ls[j] = ls[i];
//            fa[ls[j]] = j;
//            up(j);
//            head = j;
//        }
//        if (head != 0) {
//            fa[head] = 0;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n, m, lastAns = 0, ans = 0;
//    cin >> n;
//    cin >> m;
//    for (int i = 1, num; i <= n; i++) {
//        cin >> num;
//        add(num);
//    }
//    for (int i = 1, op, x; i <= m; i++) {
//        cin >> op >> x;
//        x ^= lastAns;
//        if (op == 1) {
//            add(x);
//        } else if (op == 2) {
//            remove(x);
//        } else if (op == 3) {
//            lastAns = getRank(x);
//            ans ^= lastAns;
//        } else if (op == 4) {
//            lastAns = index(x);
//            ans ^= lastAns;
//        } else if (op == 5) {
//            lastAns = pre(x);
//            ans ^= lastAns;
//        } else {
//            lastAns = post(x);
//            ans ^= lastAns;
//        }
//    }
//    cout << ans << endl;
//    return 0;
//}