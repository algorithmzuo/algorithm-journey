import java.util.*;

class s {

    public static void main(String[] args) {
        int[] nums1 = {4, 3, 1, 2};
        int[] nums2 = {2, 4, 9, 5};
        int[][] queries = {{4, 1}, {1, 3}, {2, 5}};
        s s = new s();
        s.maximumSumQueries(nums1, nums2, queries);
    }
    void print(int[][] mat) {
        for (var i : mat) {
            for (var j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length, m = queries.length;

        int[][] keys = new int[n][2];
        for (int i = 0; i < n; ++i)
            keys[i] = new int[]{nums1[i], nums2[i]};

        // 对y离散化后，规模是m+n，树状数组不会爆内存
        int[] copy = new int[m + n];
        int p = 0;
        for (int[] k : keys)
            copy[p++] = k[1];
        for (int[] q : queries)
            copy[p++] = q[1];
        for (int[] k : keys) {
            k[1] = Arrays.binarySearch(copy, k[1]) + 1;
        }
        for (int[] q : queries) {
            q[1] = Arrays.binarySearch(copy, q[1]) + 1;
        }

        // 对x降序排序，为了保证输出的顺序，queries数组要对下标进行排序
        var ids = new Integer[m];
        for (int i = 0; i < m; ++i)
            ids[i] = i;
        Arrays.sort(ids, (a, b) -> queries[b][0] - queries[a][0]);
        Arrays.sort(keys, (a, b) -> b[0] - a[0]);

        BIT bit = new BIT(m + n);
        int ptr = 0;
        int[] ans = new int[m];
        Arrays.fill(ans, -1);

        for (int id : ids) {
            // 保证keys的第一维大于等于queries的第一维
            while (ptr < n && keys[ptr][0] >= queries[id][0]) {
                // 在树状数组中更新，值为x_k + y_k，下标为y_k
                bit.update(keys[ptr][1], keys[ptr][0] + copy[keys[ptr][1] - 1]);
                ++ptr;
            }
            // 第一维已经得到保证了，现在需要从大于等于y_q的所有y_k中，找到x_k + y_k最大的，也就是利用树状数组求后缀最大值
            ans[id] = bit.query(queries[id][1]);
        }

        return ans;
    }
}

class BIT {
    private int[] tree;
    private int n;

    public BIT(int _n) {
        tree = new int[_n + 1];
        Arrays.fill(tree, -1);
        n = _n;
    }

    private static int lowbit(int x) {
        return x & (-x);
    }

    public int query(int x) {
        int ans = -1;
        while (x <= n) {
            ans = Math.max(ans, tree[x]);
            x += lowbit(x);
        }
        return ans;
    }

    public void update(int x, int val) {
        while (x > 0) {
            tree[x] = Math.max(tree[x], val);
            x -= lowbit(x);
        }
    }
}
