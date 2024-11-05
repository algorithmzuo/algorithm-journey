package class150;

// 替罪羊树的实现(C++版)
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
//#include <vector>
//#include <algorithm>
//#include <cmath>
//#include <climits>
//#include <cstring>
//
//using namespace std;
//
//const int MAXN = 100001;
//
//const double ALPHA = 0.7;
//
//int head = 0;
//int cnt = 0;
//int key[MAXN];
//int key_count[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
//int diff[MAXN];
//int collect[MAXN];
//int ci;
//
//void inorder(int i) {
//    if (i != 0) {
//        inorder(ls[i]);
//        if (key_count[i] > 0) {
//            collect[++ci] = i;
//        }
//        inorder(rs[i]);
//    }
//}
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + key_count[i];
//    diff[i] = diff[ls[i]] + diff[rs[i]] + (key_count[i] > 0 ? 1 : 0);
//}
//
//int build(int l, int r) {
//    if (l > r) {
//        return 0;
//    }
//    int m = (l + r) / 2;
//    int h = collect[m];
//    ls[h] = build(l, m - 1);
//    rs[h] = build(m + 1, r);
//    up(h);
//    return h;
//}
//
//int rebuild(int i) {
//    ci = 0;
//    inorder(i);
//    if (ci > 0) {
//        return build(1, ci);
//    } else {
//        return 0;
//    }
//}
//
//bool balance(int i) {
//    return ALPHA * diff[i] > max(diff[ls[i]], diff[rs[i]]);
//}
//
//int add(int i, int num) {
//    if (i == 0) {
//        i = ++cnt;
//        key[i] = num;
//        ls[i] = rs[i] = 0;
//        key_count[i] = size[i] = diff[i] = 1;
//    } else {
//        if (key[i] == num) {
//            key_count[i]++;
//        } else if (key[i] > num) {
//            ls[i] = add(ls[i], num);
//        } else {
//            rs[i] = add(rs[i], num);
//        }
//    }
//    up(i);
//    return balance(i) ? i : rebuild(i);
//}
//
//void add(int num) {
//    head = add(head, num);
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
//int pre(int num) {
//    int kth = getRank(num);
//    if (kth == 1) {
//        return INT_MIN;
//    } else {
//        return index(kth - 1);
//    }
//}
//
//int post(int num) {
//    int kth = getRank(num + 1);
//    if (kth == size[head] + 1) {
//        return INT_MAX;
//    } else {
//        return index(kth);
//    }
//}
//
//int remove(int i, int num) {
//    if (key[i] == num) {
//        key_count[i]--;
//    } else if (key[i] > num) {
//        ls[i] = remove(ls[i], num);
//    } else {
//        rs[i] = remove(rs[i], num);
//    }
//    up(i);
//    return balance(i) ? i : rebuild(i);
//}
//
//void remove(int num) {
//    if (getRank(num) != getRank(num + 1)) {
//        head = remove(head, num);
//    }
//}
//
//void clear() {
//    memset(key, 0, sizeof(key));
//    memset(key_count, 0, sizeof(key_count));
//    memset(ls, 0, sizeof(ls));
//    memset(rs, 0, sizeof(rs));
//    memset(size, 0, sizeof(size));
//    memset(diff, 0, sizeof(diff));
//    cnt = 0;
//    head = 0;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n;
//    cin >> n;
//    for (int i = 0; i < n; ++i) {
//        int op, x;
//        cin >> op >> x;
//        if (op == 1) {
//            add(x);
//        } else if (op == 2) {
//            remove(x);
//        } else if (op == 3) {
//            cout << getRank(x) << "\n";
//        } else if (op == 4) {
//            cout << index(x) << "\n";
//        } else if (op == 5) {
//            cout << pre(x) << "\n";
//        } else {
//            cout << post(x) << "\n";
//        }
//    }
//    clear();
//    return 0;
//}