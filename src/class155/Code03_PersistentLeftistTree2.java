package class155;

// 可持久化左偏树的实现，利用对数器验证正确性，C++版

//#include <iostream>
//#include <vector>
//#include <queue>
//#include <algorithm>
//#include <cstdlib>
//#include <ctime>
//
//using namespace std;
//
//const int MAXN = 10000;
//const int MAXV = 100000;
//const int MAXT = 2000001;
//
//int rt[MAXN];
//int num[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int dist[MAXT];
//int siz[MAXT];
//int cnt = 0;
//
//int init(int v) {
//    num[++cnt] = v;
//    ls[cnt] = rs[cnt] = dist[cnt] = 0;
//    return cnt;
//}
//
//int clone(int i) {
//    num[++cnt] = num[i];
//    ls[cnt] = ls[i];
//    rs[cnt] = rs[i];
//    dist[cnt] = dist[i];
//    return cnt;
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    if (num[i] > num[j]) {
//        swap(i, j);
//    }
//    int h = clone(i);
//    rs[h] = merge(rs[h], j);
//    if (dist[ls[h]] < dist[rs[h]]) {
//        swap(ls[h], rs[h]);
//    }
//    dist[h] = dist[rs[h]] + 1;
//    return h;
//}
//
//int pop(int i) {
//    if (ls[i] == 0 && rs[i] == 0) {
//        return 0;
//    }
//    if (ls[i] == 0 || rs[i] == 0) {
//        return clone(ls[i] + rs[i]);
//    }
//    return merge(ls[i], rs[i]);
//}
//
//void treeAdd(int x, int y, int i) {
//    rt[i] = merge(rt[x], init(y));
//    siz[rt[i]] = siz[rt[x]] + 1;
//}
//
//void treeMerge(int x, int y, int i) {
//    if (rt[x] == 0 && rt[y] == 0) {
//        rt[i] = 0;
//    } else if (rt[x] == 0 || rt[y] == 0) {
//        rt[i] = clone(rt[x] + rt[y]);
//    } else {
//        rt[i] = merge(rt[x], rt[y]);
//    }
//    siz[rt[i]] = siz[rt[x]] + siz[rt[y]];
//}
//
//void treePop(int x, int i) {
//    if (siz[rt[x]] == 0) {
//        rt[i] = 0;
//    } else {
//        rt[i] = pop(rt[x]);
//        siz[rt[i]] = siz[rt[x]] - 1;
//    }
//}
//
//vector<priority_queue<int, vector<int>, greater<int>>> verify;
//
//void verifyAdd(int x, int y) {
//    priority_queue<int, vector<int>, greater<int>> pre = verify[x];
//    vector<int> tmp;
//    while (!pre.empty()) {
//        tmp.push_back(pre.top());
//        pre.pop();
//    }
//    priority_queue<int, vector<int>, greater<int>> cur;
//    for (int number : tmp) {
//        cur.push(number);
//    }
//    cur.push(y);
//    verify.push_back(cur);
//}
//
//void verifyMerge(int x, int y) {
//    priority_queue<int, vector<int>, greater<int>> h1 = verify[x];
//    priority_queue<int, vector<int>, greater<int>> h2 = verify[y];
//    vector<int> tmp;
//    priority_queue<int, vector<int>, greater<int>> cur;
//    while (!h1.empty()) {
//        int number = h1.top();
//        h1.pop();
//        tmp.push_back(number);
//        cur.push(number);
//    }
//    for (int number : tmp) {
//        h1.push(number);
//    }
//    tmp.clear();
//    while (!h2.empty()) {
//        int number = h2.top();
//        h2.pop();
//        tmp.push_back(number);
//        cur.push(number);
//    }
//    for (int number : tmp) {
//        h2.push(number);
//    }
//    verify.push_back(cur);
//}
//
//void verifyPop(int x) {
//    priority_queue<int, vector<int>, greater<int>> pre = verify[x];
//    priority_queue<int, vector<int>, greater<int>> cur;
//    if (pre.empty()) {
//        verify.push_back(cur);
//    } else {
//        int top = pre.top();
//        pre.pop();
//        vector<int> tmp;
//        while (!pre.empty()) {
//            tmp.push_back(pre.top());
//            pre.pop();
//        }
//        for (int number : tmp) {
//            pre.push(number);
//            cur.push(number);
//        }
//        pre.push(top);
//        verify.push_back(cur);
//    }
//}
//
//bool check(int i) {
//    int h1 = rt[i];
//    priority_queue<int, vector<int>, greater<int>> h2 = verify[i];
//    if (siz[h1] != h2.size()) {
//        return false;
//    }
//    bool ans = true;
//    vector<int> tmp;
//    while (!h2.empty()) {
//        int o1 = num[h1];
//        h1 = pop(h1);
//        int o2 = h2.top();
//        h2.pop();
//        tmp.push_back(o2);
//        if (o1 != o2) {
//            ans = false;
//            break;
//        }
//    }
//    for (int v : tmp) {
//        h2.push(v);
//    }
//    return ans;
//}
//
//int main() {
//    cout << "test begin" << endl;
//    dist[0] = -1;
//    rt[0] = siz[0] = 0;
//    verify.emplace_back(priority_queue<int, vector<int>, greater<int>>());
//    srand(time(nullptr));
//    for (int i = 1, op, x, y; i < MAXN; i++) {
//        op = i == 1 ? 1 : (rand() % 3 + 1);
//        x = rand() % i;
//        if (op == 1) {
//            y = rand() % MAXV;
//            treeAdd(x, y, i);
//            verifyAdd(x, y);
//        } else if (op == 2) {
//            do {
//                y = rand() % i;
//            } while (y == x);
//            treeMerge(x, y, i);
//            verifyMerge(x, y);
//        } else {
//            treePop(x, i);
//            verifyPop(x);
//        }
//        if (!check(i)) {
//            cout << "err!" << endl;
//        }
//    }
//    for (int i = 1; i < MAXN; i++) {
//        if (!check(i)) {
//            cout << "err!" << endl;
//        }
//    }
//    cout << "test finish" << endl;
//    return 0;
//}