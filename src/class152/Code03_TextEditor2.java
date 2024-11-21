package class152;

// 文本编辑器，FHQ-Treap实现区间移动，C++版本
// 一开始文本为空，光标在文本开头，也就是1位置，请实现如下6种操作
// Move k     : 将光标移动到第k个字符之后，操作保证光标不会到非法位置
// Insert n s : 在光标处插入长度为n的字符串s，光标位置不变
// Delete n   : 删除光标后的n个字符，光标位置不变，操作保证有足够字符
// Get n      : 输出光标后的n个字符，光标位置不变，操作保证有足够字符
// Prev       : 光标前移一个字符，操作保证光标不会到非法位置
// Next       : 光标后移一个字符，操作保证光标不会到非法位置
// Insert操作时，字符串s中ASCII码在[32,126]范围上的字符一定有n个，其他字符请过滤掉
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <vector>
//#include <cstdlib>
//#include <cstring>
//#include <algorithm>
//using namespace std;
//
//const int MAXN = 2000001;
//
//int head = 0;
//int cnt = 0;
//char key[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int size[MAXN];
//double priority[MAXN];
//char ans[MAXN];
//int ansi;
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + 1;
//}
//
//void split(int l, int r, int i, int rank) {
//    if (i == 0) {
//        rs[l] = ls[r] = 0;
//    } else {
//        if (size[ls[i]] + 1 <= rank) {
//            rs[l] = i;
//            split(i, r, rs[i], rank - size[ls[i]] - 1);
//        } else {
//            ls[r] = i;
//            split(l, i, ls[i], rank);
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
//void inorder(int i) {
//    if (i != 0) {
//        inorder(ls[i]);
//        ans[++ansi] = key[i];
//        inorder(rs[i]);
//    }
//}
//
//int main() {
//    srand(time(0));
//    int pos = 0, len, l, m, lm, r;
//    int n;
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        char op[10];
//        cin >> op;
//        if (op[0] == 'P') {
//            pos--;
//        } else if (op[0] == 'N') {
//            pos++;
//        } else if (op[0] == 'M') {
//            cin >> pos;
//        } else if (op[0] == 'I') {
//            cin >> len;
//            split(0, 0, head, pos);
//            l = rs[0];
//            r = ls[0];
//            for (int j = 1; j <= len; j++) {
//                char ch = getchar();
//                while (ch < 32 || ch > 126) {
//                    ch = getchar();
//                }
//                key[++cnt] = ch;
//                size[cnt] = 1;
//                priority[cnt] = (double)rand() / RAND_MAX;
//                l = merge(l, cnt);
//            }
//            head = merge(l, r);
//        } else if (op[0] == 'D') {
//            cin >> len;
//            split(0, 0, head, pos + len);
//            lm = rs[0];
//            r = ls[0];
//            split(0, 0, lm, pos);
//            l = rs[0];
//            head = merge(l, r);
//        } else {
//            cin >> len;
//            split(0, 0, head, pos + len);
//            lm = rs[0];
//            r = ls[0];
//            split(0, 0, lm, pos);
//            l = rs[0];
//            m = ls[0];
//            ansi = 0;
//            inorder(m);
//            head = merge(merge(l, m), r);
//            for (int j = 1; j <= ansi; j++) {
//                cout << ans[j];
//            }
//            cout << '\n';
//        }
//    }
//    return 0;
//}