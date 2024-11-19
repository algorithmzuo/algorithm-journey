package class152;

// 可持久化有序表，FHQ-Treap实现，不用词频统计，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3835
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <cstdio>
//#include <cstdlib>
//#include <cstring>
//#include <algorithm>
//#include <climits>
//
//using namespace std;
//
//const int MAXN = 500001;
//
//int cnt = 0;
//int head[MAXN];
//int key[MAXN * 50];
//int ls[MAXN * 50];
//int rs[MAXN * 50];
//int size[MAXN * 50];
//double priority[MAXN * 50];
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + 1;
//}
//
//int copy(int i) {
//    ++cnt;
//    key[cnt] = key[i];
//    ls[cnt] = ls[i];
//    rs[cnt] = rs[i];
//    size[cnt] = size[i];
//    priority[cnt] = priority[i];
//    return cnt;
//}
//
//void split(int l, int r, int i, int num) {
//    if (i == 0) {
//        rs[l] = ls[r] = 0;
//    } else {
//        i = copy(i);
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
//        l = copy(l);
//        rs[l] = merge(rs[l], r);
//        up(l);
//        return l;
//    } else {
//        r = copy(r);
//        ls[r] = merge(l, ls[r]);
//        up(r);
//        return r;
//    }
//}
//
//void add(int v, int i, int num) {
//    split(0, 0, i, num);
//    ++cnt;
//    key[cnt] = num;
//    size[cnt] = 1;
//    priority[cnt] = (double)rand() / RAND_MAX;
//    head[v] = merge(merge(rs[0], cnt), ls[0]);
//    ls[0] = rs[0] = 0;
//}
//
//void remove(int v, int i, int num) {
//    split(0, 0, i, num);
//    int lm = rs[0];
//    int r = ls[0];
//    ls[0] = rs[0] = 0;
//    split(0, 0, lm, num - 1);
//    int m = ls[0];
//    int l = rs[0];
//    ls[0] = rs[0] = 0;
//    head[v] = merge(merge(l, merge(ls[m], rs[m])), r);
//}
//
//int small(int i, int num) {
//    if (i == 0) {
//        return 0;
//    }
//    if (key[i] >= num) {
//        return small(ls[i], num);
//    } else {
//        return size[ls[i]] + 1 + small(rs[i], num);
//    }
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
//int pre(int i, int num) {
//    if (i == 0) {
//        return INT_MIN + 1;
//    }
//    if (key[i] >= num) {
//        return pre(ls[i], num);
//    } else {
//        return max(key[i], pre(rs[i], num));
//    }
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
//int main() {
//	srand(time(0));
//    int n;
//    cin >> n;
//    for (int i = 1; i <= n; ++i) {
//        int version, op, x;
//        cin >> version >> op >> x;
//        head[i] = head[version];
//        if (op == 1) {
//            add(i, head[i], x);
//        } else if (op == 2) {
//            remove(i, head[i], x);
//        } else if (op == 3) {
//            cout << small(head[i], x) + 1 << "\n";
//        } else if (op == 4) {
//            cout << index(head[i], x) << "\n";
//        } else if (op == 5) {
//            cout << pre(head[i], x) << "\n";
//        } else if (op == 6) {
//            cout << post(head[i], x) << "\n";
//        }
//    }
//    return 0;
//}