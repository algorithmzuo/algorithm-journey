package class172;

// 文本编辑器，块状链表实现，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 3000001;
//const int BLEN = 3001;
//const int BNUM = (MAXN / BLEN) << 1;
//
//char blocks[BNUM][BLEN];
//int nxt[BNUM];
//int siz[BNUM];
//int pool[BNUM];
//int psiz = 0;
//
//char str[MAXN];
//
//void prepare() {
//    for (int i = 1; i < BNUM; i++) pool[i] = i;
//    psiz = BNUM - 1;
//    siz[0] = 0;
//    nxt[0] = -1;
//}
//
//int assign() {
//    return pool[psiz--];
//}
//
//void recycle(int i) {
//    pool[++psiz] = i;
//}
//
//int bi, pi;
//
//void find(int pos) {
//    int curb = 0;
//    while (curb != -1 && pos > siz[curb]) {
//        pos -= siz[curb];
//        curb = nxt[curb];
//    }
//    bi = curb;
//    pi = pos;
//}
//
//void link(int curb, int nextb, int nextLen, char* src, int srcPos) {
//    nxt[nextb] = nxt[curb];
//    nxt[curb] = nextb;
//    siz[nextb] = nextLen;
//    memcpy(blocks[nextb], src + srcPos, nextLen);
//}
//
//void merge(int curb, int nextb) {
//    memcpy(blocks[curb] + siz[curb], blocks[nextb], siz[nextb]);
//    siz[curb] += siz[nextb];
//    nxt[curb] = nxt[nextb];
//    recycle(nextb);
//}
//
//void split(int curb, int pos) {
//    if (curb == -1 || pos == siz[curb]) return;
//    int nextb = assign();
//    link(curb, nextb, siz[curb] - pos, blocks[curb], pos);
//    siz[curb] = pos;
//}
//
//void maintain() {
//    for (int curb = 0, nextb; curb != -1; curb = nxt[curb]) {
//        nextb = nxt[curb];
//        while (nextb != -1 && siz[curb] + siz[nextb] <= BLEN) {
//            merge(curb, nextb);
//            nextb = nxt[curb];
//        }
//    }
//}
//
//void insert(int pos, int len) {
//    find(pos);
//    split(bi, pi);
//    int curb = bi, newb, done = 0;
//    while (done + BLEN <= len) {
//        newb = assign();
//        link(curb, newb, BLEN, str, done);
//        done += BLEN;
//        curb = newb;
//    }
//    if (len > done) {
//        newb = assign();
//        link(curb, newb, len - done, str, done);
//    }
//    maintain();
//}
//
//void erase(int pos, int len) {
//    find(pos);
//    split(bi, pi);
//    int curb = bi;
//    int nextb = nxt[curb];
//    while (nextb != -1 && len > siz[nextb]) {
//        len -= siz[nextb];
//        recycle(nextb);
//        nextb = nxt[nextb];
//    }
//    if (nextb != -1) {
//        split(nextb, len);
//        recycle(nextb);
//        nxt[curb] = nxt[nextb];
//    } else {
//        nxt[curb] = -1;
//    }
//    maintain();
//}
//
//void get(int pos, int len) {
//    find(pos);
//    int curb = bi;
//    pos = pi;
//    int got = (len < siz[curb] - pos) ? len : (siz[curb] - pos);
//    memcpy(str, blocks[curb] + pos, got);
//    curb = nxt[curb];
//    while (curb != -1 && got + siz[curb] <= len) {
//        memcpy(str + got, blocks[curb], siz[curb]);
//        got += siz[curb];
//        curb = nxt[curb];
//    }
//    if (curb != -1 && got < len) {
//        memcpy(str + got, blocks[curb], len - got);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n;
//    cin >> n;
//    int pos = 0, len;
//    char op[10];
//    prepare();
//    for (int i = 1; i <= n; i++) {
//        cin >> op;
//        if (op[0] == 'P') {
//            pos--;
//        } else if (op[0] == 'N') {
//            pos++;
//        } else if (op[0] == 'M') {
//            cin >> pos;
//        } else if (op[0] == 'I') {
//            cin >> len;
//            for (int j = 0; j < len; ) {
//                char ch = cin.get();
//                if (32 <= ch && ch <= 126) {
//                    str[j++] = ch;
//                }
//            }
//            insert(pos, len);
//        } else if (op[0] == 'D') {
//            cin >> len;
//            erase(pos, len);
//        } else {
//            cin >> len;
//            get(pos, len);
//            cout.write(str, len);
//            cout.put('\n');
//        }
//    }
//    return 0;
//}