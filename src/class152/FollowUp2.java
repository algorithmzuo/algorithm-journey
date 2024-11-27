package class152;

// FHQ-Treap实现普通有序表，不用词频压缩，数据加强的测试，C++版
// 这个文件课上没有讲，测试数据加强了，而且有强制在线的要求
// 基本功能要求都是不变的，可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P6136
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <cstdio>
//#include <cstdlib>
//#include <cstring>
//#include <algorithm>
//#include <climits>
//using namespace std;
//
//const int MAXN = 2000001;
//int head = 0;
//int cnt = 0;
//int key[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
//double priority[MAXN];
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + 1;
//}
//
//void split(int l, int r, int i, int num) {
//    if (i == 0) {
//        rs[l] = ls[r] = 0;
//    } else {
//        if (key[i] <= num) {
//            rs[l] = i;
//            split(i, r, rs[i], num);
//        } else {
//            ls[r] = i;
//            split(l, i, ls[i], num);
//        }
//        up(i);
//    }
//}
//
//int merge(int l, int r) {
//    if (l == 0 || r == 0) {
//        return l + r;
//    }
//    if (priority[l] >= priority[r]) {
//        rs[l] = merge(rs[l], r);
//        up(l);
//        return l;
//    } else {
//        ls[r] = merge(l, ls[r]);
//        up(r);
//        return r;
//    }
//}
//
//void add(int num) {
//    split(0, 0, head, num);
//    key[++cnt] = num;
//    size[cnt] = 1;
//    priority[cnt] = (double)rand() / RAND_MAX;
//    head = merge(merge(rs[0], cnt), ls[0]);
//}
//
//void remove(int num) {
//    split(0, 0, head, num);
//    int lm = rs[0];
//    int r = ls[0];
//    split(0, 0, lm, num - 1);
//    int l = rs[0];
//    int m = ls[0];
//    head = merge(merge(l, merge(ls[m], rs[m])), r);
//}
//
//int getRank(int num) {
//    split(0, 0, head, num - 1);
//    int ans = size[rs[0]] + 1;
//    head = merge(rs[0], ls[0]);
//    return ans;
//}
//
//int index(int i, int x) {
//    if (size[ls[i]] >= x) {
//        return index(ls[i], x);
//    } else if (size[ls[i]] + 1 < x) {
//        return index(rs[i], x - size[ls[i]] - 1);
//    } else {
//        return key[i];
//    }
//}
//
//int index(int x) {
//    return index(head, x);
//}
//
//int pre(int i, int num) {
//    if (i == 0) {
//        return INT_MIN;
//    }
//    if (key[i] >= num) {
//        return pre(ls[i], num);
//    } else {
//        return max(key[i], pre(rs[i], num));
//    }
//}
//
//int pre(int num) {
//    return pre(head, num);
//}
//
//int post(int i, int num) {
//    if (i == 0) {
//        return INT_MAX;
//    }
//    if (key[i] <= num) {
//        return post(rs[i], num);
//    } else {
//        return min(key[i], post(ls[i], num));
//    }
//}
//
//int post(int num) {
//    return post(head, num);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand(time(0));
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