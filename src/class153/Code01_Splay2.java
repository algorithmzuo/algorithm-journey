package class153;

// Splay树的实现，不用词频压缩，C++版
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
//#include <climits>
//
//using namespace std;
//
//const int MAXN = 100001;
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
//    int n;
//    cin >> n;
//    for (int i = 0, op, x; i < n; i++) {
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