package class148;

// AVL树的实现(C++版)
// 实现一种结构，支持如下操作，要求单次调用的时间复杂度O(log n)
// 1，增加一个x，支持重复加入
// 2，删除一个x，只删掉一个
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
//#include <algorithm>
//#include <climits>
//#include <cstring>
//
//using namespace std;
//
//const int MAXN = 100001;
//
//int cnt = 0;
//int head = 0;
//int key[MAXN];
//int height[MAXN];
//int leftChild[MAXN];
//int rightChild[MAXN];
//int key_count[MAXN];
//int size[MAXN];
//
//void up(int i) {
//    size[i] = size[leftChild[i]] + size[rightChild[i]] + key_count[i];
//    height[i] = max(height[leftChild[i]], height[rightChild[i]]) + 1;
//}
//
//int leftRotate(int i) {
//    int r = rightChild[i];
//    rightChild[i] = leftChild[r];
//    leftChild[r] = i;
//    up(i);
//    up(r);
//    return r;
//}
//
//int rightRotate(int i) {
//    int l = leftChild[i];
//    leftChild[i] = rightChild[l];
//    rightChild[l] = i;
//    up(i);
//    up(l);
//    return l;
//}
//
//int maintain(int i) {
//    int lh = height[leftChild[i]];
//    int rh = height[rightChild[i]];
//    if (lh - rh > 1) {
//        if (height[leftChild[leftChild[i]]] >= height[rightChild[leftChild[i]]]) {
//            i = rightRotate(i);
//        } else {
//            leftChild[i] = leftRotate(leftChild[i]);
//            i = rightRotate(i);
//        }
//    } else if (rh - lh > 1) {
//        if (height[rightChild[rightChild[i]]] >= height[leftChild[rightChild[i]]]) {
//            i = leftRotate(i);
//        } else {
//            rightChild[i] = rightRotate(rightChild[i]);
//            i = leftRotate(i);
//        }
//    }
//    return i;
//}
//
//int addNode(int i, int num) {
//    if (i == 0) {
//        key[++cnt] = num;
//        key_count[cnt] = size[cnt] = height[cnt] = 1;
//        return cnt;
//    }
//    if (key[i] == num) {
//        key_count[i]++;
//    } else if (key[i] > num) {
//        leftChild[i] = addNode(leftChild[i], num);
//    } else {
//        rightChild[i] = addNode(rightChild[i], num);
//    }
//    up(i);
//    return maintain(i);
//}
//
//void add(int num) {
//    head = addNode(head, num);
//}
//
//int getRank(int i, int num) {
//    if (i == 0) {
//        return 0;
//    }
//    if (key[i] >= num) {
//        return getRank(leftChild[i], num);
//    } else {
//        return size[leftChild[i]] + key_count[i] + getRank(rightChild[i], num);
//    }
//}
//
//int getRank(int num) {
//    return getRank(head, num) + 1;
//}
//
//int removeMostLeft(int i, int mostLeft) {
//    if (i == mostLeft) {
//        return rightChild[i];
//    } else {
//        leftChild[i] = removeMostLeft(leftChild[i], mostLeft);
//        up(i);
//        return maintain(i);
//    }
//}
//
//int removeNode(int i, int num) {
//    if (key[i] < num) {
//        rightChild[i] = removeNode(rightChild[i], num);
//    } else if (key[i] > num) {
//        leftChild[i] = removeNode(leftChild[i], num);
//    } else {
//        if (key_count[i] > 1) {
//            key_count[i]--;
//        } else {
//            if (leftChild[i] == 0 && rightChild[i] == 0) {
//                return 0;
//            } else if (leftChild[i] != 0 && rightChild[i] == 0) {
//                i = leftChild[i];
//            } else if (leftChild[i] == 0 && rightChild[i] != 0) {
//                i = rightChild[i];
//            } else {
//                int mostLeft = rightChild[i];
//                while (leftChild[mostLeft] != 0) {
//                    mostLeft = leftChild[mostLeft];
//                }
//                rightChild[i] = removeMostLeft(rightChild[i], mostLeft);
//                leftChild[mostLeft] = leftChild[i];
//                rightChild[mostLeft] = rightChild[i];
//                i = mostLeft;
//            }
//        }
//    }
//    up(i);
//    return maintain(i);
//}
//
//void remove(int num) {
//    if (getRank(num) != getRank(num + 1)) {
//        head = removeNode(head, num);
//    }
//}
//
//int getIndex(int i, int x) {
//    if (size[leftChild[i]] >= x) {
//        return getIndex(leftChild[i], x);
//    } else if (size[leftChild[i]] + key_count[i] < x) {
//        return getIndex(rightChild[i], x - size[leftChild[i]] - key_count[i]);
//    }
//    return key[i];
//}
//
//int getIndex(int x) {
//    return getIndex(head, x);
//}
//
//int pre(int i, int num) {
//    if (i == 0) {
//        return INT_MIN;
//    }
//    if (key[i] >= num) {
//        return pre(leftChild[i], num);
//    } else {
//        return max(key[i], pre(rightChild[i], num));
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
//        return post(rightChild[i], num);
//    } else {
//        return min(key[i], post(leftChild[i], num));
//    }
//}
//
//int post(int num) {
//    return post(head, num);
//}
//
//void clear() {
//    memset(key + 1, 0, cnt * sizeof(int));
//    memset(height + 1, 0, cnt * sizeof(int));
//    memset(leftChild + 1, 0, cnt * sizeof(int));
//    memset(rightChild + 1, 0, cnt * sizeof(int));
//    memset(key_count + 1, 0, cnt * sizeof(int));
//    memset(size + 1, 0, cnt * sizeof(int));
//    cnt = 0;
//    head = 0;
//}
//
//int main() {
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
//            cout << getIndex(x) << endl;
//        } else if (op == 5) {
//            cout << pre(x) << endl;
//        } else {
//            cout << post(x) << endl;
//        }
//    }
//    clear();
//    return 0;
//}