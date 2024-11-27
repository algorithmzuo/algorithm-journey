package class152;

// FHQ-Treap，使用词频压缩，C++版
// 实现一种结构，支持如下操作，要求单次调用的时间复杂度O(log n)
// 1，增加x，重复加入算多个词频
// 2，删除x，如果有多个，只删掉一个
// 3，查询x的排名，x的排名为，比x小的数的个数+1
// 4，查询数据中排名为x的数
// 5，查询x的前驱，x的前驱为，小于x的数中最大的数，不存在返回整数最小值
// 6，查询x的后继，x的后继为，大于x的数中最小的数，不存在返回整数最大值
// 所有操作的次数 <= 10^5
// -10^7 <= x <= +10^7
// 测试链接 : https://www.luogu.com.cn/problem/P3369
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
//const int MAXN = 100001;
//int head = 0;
//int cnt = 0;
//int key[MAXN];
//int key_count[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
//double priority[MAXN];
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + key_count[i];
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
//int find(int i, int num) {
//    if (i == 0) {
//        return 0;
//    }
//    if (key[i] == num) {
//        return i;
//    } else if (key[i] > num) {
//        return find(ls[i], num);
//    } else {
//        return find(rs[i], num);
//    }
//}
//
//void changeCount(int i, int num, int change) {
//    if (key[i] == num) {
//        key_count[i] += change;
//    } else if (key[i] > num) {
//        changeCount(ls[i], num, change);
//    } else {
//        changeCount(rs[i], num, change);
//    }
//    up(i);
//}
//
//void add(int num) {
//    if (find(head, num) != 0) {
//        changeCount(head, num, 1);
//    } else {
//        split(0, 0, head, num);
//        key[++cnt] = num;
//        key_count[cnt] = size[cnt] = 1;
//        priority[cnt] = (double)rand() / RAND_MAX;
//        head = merge(merge(rs[0], cnt), ls[0]);
//    }
//}
//
//void remove(int num) {
//    int i = find(head, num);
//    if (i != 0) {
//        if (key_count[i] > 1) {
//            changeCount(head, num, -1);
//        } else {
//            split(0, 0, head, num);
//            int lm = rs[0];
//            int r = ls[0];
//            split(0, 0, lm, num - 1);
//            int l = rs[0];
//            head = merge(l, r);
//        }
//    }
//}
//
//int small(int i, int num) {
//    if (i == 0) {
//        return 0;
//    }
//    if (key[i] >= num) {
//        return small(ls[i], num);
//    } else {
//        return size[ls[i]] + key_count[i] + small(rs[i], num);
//    }
//}
//
//int getRank(int num) {
//    return small(head, num) + 1;
//}
//
//int index(int i, int x) {
//    if (size[ls[i]] >= x) {
//        return index(ls[i], x);
//    } else if (size[ls[i]] + key_count[i] < x) {
//        return index(rs[i], x - size[ls[i]] - key_count[i]);
//    }
//    return key[i];
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
//    int n;
//    cin >> n;
//    for (int i = 1, op, x; i <= n; i++) {
//        cin >> op >> x;
//        if (op == 1) {
//            add(x);
//        } else if (op == 2) {
//            remove(x);
//        } else if (op == 3) {
//            cout << getRank(x) << endl;
//        } else if (op == 4) {
//            cout << index(x) << endl;
//        } else if (op == 5) {
//            cout << pre(x) << endl;
//        } else {
//            cout << post(x) << endl;
//        }
//    }
//    return 0;
//}
