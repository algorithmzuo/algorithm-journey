package class151;

// Treap树实现普通有序表，数据加强的测试，C++版
// 这个文件课上没有讲，测试数据加强了，而且有强制在线的要求
// 基本功能要求都是不变的，可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P6136
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <algorithm>
//#include <cstring>
//#include <random>
//#include <climits>
//
//using namespace std;
//
//const int MAXN = 2000001;
//
//int cnt = 0;
//int head = 0;
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
//int leftRotate(int i) {
//    int r = rs[i];
//    rs[i] = ls[r];
//    ls[r] = i;
//    up(i);
//    up(r);
//    return r;
//}
//
//int rightRotate(int i) {
//    int l = ls[i];
//    ls[i] = rs[l];
//    rs[l] = i;
//    up(i);
//    up(l);
//    return l;
//}
//
//int add(int i, int num) {
//    if (i == 0) {
//        key[++cnt] = num;
//        key_count[cnt] = size[cnt] = 1;
//        priority[cnt] = static_cast<double>(rand()) / RAND_MAX;
//        return cnt;
//    }
//    if (key[i] == num) {
//        key_count[i]++;
//    } else if (key[i] > num) {
//        ls[i] = add(ls[i], num);
//    } else {
//        rs[i] = add(rs[i], num);
//    }
//    up(i);
//    if (ls[i] != 0 && priority[ls[i]] > priority[i]) {
//        return rightRotate(i);
//    }
//    if (rs[i] != 0 && priority[rs[i]] > priority[i]) {
//        return leftRotate(i);
//    }
//    return i;
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
//int remove(int i, int num) {
//    if (key[i] < num) {
//        rs[i] = remove(rs[i], num);
//    } else if (key[i] > num) {
//        ls[i] = remove(ls[i], num);
//    } else {
//        if (key_count[i] > 1) {
//            key_count[i]--;
//        } else {
//            if (ls[i] == 0 && rs[i] == 0) {
//                return 0;
//            } else if (ls[i] != 0 && rs[i] == 0) {
//                i = ls[i];
//            } else if (ls[i] == 0 && rs[i] != 0) {
//                i = rs[i];
//            } else {
//                if (priority[ls[i]] >= priority[rs[i]]) {
//                    i = rightRotate(i);
//                    rs[i] = remove(rs[i], num);
//                } else {
//                    i = leftRotate(i);
//                    ls[i] = remove(ls[i], num);
//                }
//            }
//        }
//    }
//    up(i);
//    return i;
//}
//
//void remove(int num) {
//    if (getRank(num) != getRank(num + 1)) {
//        head = remove(head, num);
//    }
//}
//
//void clear() {
//    fill(key + 1, key + cnt + 1, 0);
//    fill(key_count + 1, key_count + cnt + 1, 0);
//    fill(ls + 1, ls + cnt + 1, 0);
//    fill(rs + 1, rs + cnt + 1, 0);
//    fill(size + 1, size + cnt + 1, 0);
//    fill(priority + 1, priority + cnt + 1, 0);
//    cnt = 0;
//    head = 0;
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
//    clear();
//    return 0;
//}