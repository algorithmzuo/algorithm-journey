package class172;

// 文本编辑器，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 3000001;
//const int BLEN = 4001;
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
//    int cur = 0;
//    while (cur != -1 && pos > siz[cur]) {
//        pos -= siz[cur];
//        cur = nxt[cur];
//    }
//    bi = cur;
//    pi = pos;
//}
//
//void flush(int cur, int nextb, int tailLen, char* src, int srcPos) {
//    nxt[nextb] = nxt[cur];
//    nxt[cur] = nextb;
//    siz[nextb] = tailLen;
//    memcpy(blocks[nextb], src + srcPos, tailLen);
//}
//
//void merge(int cur, int nextb) {
//    memcpy(blocks[cur] + siz[cur], blocks[nextb], siz[nextb]);
//    siz[cur] += siz[nextb];
//    nxt[cur] = nxt[nextb];
//    recycle(nextb);
//}
//
//void split(int cur, int pos) {
//    if (cur == -1 || pos == siz[cur]) return;
//    int nextb = assign();
//    flush(cur, nextb, siz[cur] - pos, blocks[cur], pos);
//    siz[cur] = pos;
//}
//
//void maintain() {
//    for (int cur = 0, nextb; cur != -1; cur = nxt[cur]) {
//        nextb = nxt[cur];
//        while (nextb != -1 && siz[cur] + siz[nextb] <= BLEN) {
//            merge(cur, nextb);
//            nextb = nxt[cur];
//        }
//    }
//}
//
//void insert(int pos, int len) {
//    find(pos);
//    split(bi, pi);
//    int cur = bi, newb, done = 0;
//    while (done + BLEN <= len) {
//        newb = assign();
//        flush(cur, newb, BLEN, str, done);
//        done += BLEN;
//        cur = newb;
//    }
//    if (len > done) {
//        newb = assign();
//        flush(cur, newb, len - done, str, done);
//    }
//    maintain();
//}
//
//void erase(int pos, int len) {
//    find(pos);
//    split(bi, pi);
//    int cur = bi;
//    int nextb = nxt[cur];
//    while (nextb != -1 && len > siz[nextb]) {
//        len -= siz[nextb];
//        recycle(nextb);
//        nextb = nxt[nextb];
//    }
//    if (nextb != -1) {
//        split(nextb, len);
//        recycle(nextb);
//        nxt[cur] = nxt[nextb];
//    } else {
//        nxt[cur] = -1;
//    }
//    maintain();
//}
//
//void get(int pos, int len) {
//    find(pos);
//    int cur = bi;
//    pos = pi;
//    int got = (len < siz[cur] - pos) ? len : (siz[cur] - pos);
//    memcpy(str, blocks[cur] + pos, got);
//    cur = nxt[cur];
//    while (cur != -1 && got + siz[cur] <= len) {
//        memcpy(str + got, blocks[cur], siz[cur]);
//        got += siz[cur];
//        cur = nxt[cur];
//    }
//    if (cur != -1 && got < len) {
//        memcpy(str + got, blocks[cur], len - got);
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