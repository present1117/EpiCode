package com.epi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author translated from c++ by Blazheev Alexander
 */
public class FindKthElementInTwoSortedArraysTemplate {
    // @include
    public static int find_kth_in_two_sorted_arrays(List<Integer> A,
                                      List<Integer> B,
                                      int k) {
        // Lower bound of elements we will choose in A.
        int b = Math.max(0, k - B.size());
        // Upper bound of elements we will choose in A.
        int t = Math.min(A.size(), k);

        while (b < t) {
            int x = b + ((t - b) >> 1);
            int A_x_1 = (x <= 0 ? Integer.MIN_VALUE : A.get(x - 1));
            int A_x = (x >= A.size() ? Integer.MAX_VALUE : A.get(x));
            int B_k_x_1 = (k - x <= 0 ? Integer.MIN_VALUE : B.get(k - x - 1));
            int B_k_x = (k - x >= B.size() ? Integer.MAX_VALUE : B.get(k - x));

            if (A_x < B_k_x_1) {
                b = x + 1;
            } else if (A_x_1 > B_k_x) {
                t = x - 1;
            } else {
                // B[k - x - 1] <= A[x] && A[x - 1] < B[k - x].
                return Math.max(A_x_1, B_k_x_1);
            }
        }

        int A_b_1 = b <= 0 ? Integer.MIN_VALUE : A.get(b - 1);
        int B_k_b_1 = k - b - 1 < 0 ? Integer.MIN_VALUE : B.get(k - b - 1);
        return Math.max(A_b_1, B_k_b_1);
    }
    // @exclude

    private static <T extends Comparable<T>> T check_answer(List<T> A, List<T> B, int k) {
        int i = 0, j = 0, count = 0;
        T ret = null;
        while ((i < A.size() || j < B.size()) && count < k) {
            if (i < A.size() && j < B.size()) {
                if (A.get(i).compareTo(B.get(j)) < 0) {
                    ret = A.get(i);
                    ++i;
                } else {
                    ret = B.get(j);
                    ++j;
                }
            } else if (i < A.size()) {
                ret = A.get(i);
                ++i;
            } else {
                ret = B.get(j);
                ++j;
            }
            ++count;
        }
        return ret;
    }

    private static void small_test() {
        // AA: handwritten test
        ArrayList<Integer> A0 = new ArrayList<Integer>();
        ArrayList<Integer> B0 = new ArrayList<Integer>();
        A0.add(0);
        A0.add(1);
        A0.add(2);
        A0.add(3);
        B0.add(0);
        B0.add(1);
        B0.add(2);
        B0.add(3);
        assert(0 == find_kth_in_two_sorted_arrays(A0, B0, 1));
        assert(0 == find_kth_in_two_sorted_arrays(A0, B0, 2));
        assert(1 == find_kth_in_two_sorted_arrays(A0, B0, 3));
        assert(1 == find_kth_in_two_sorted_arrays(A0, B0, 4));
        assert(2 == find_kth_in_two_sorted_arrays(A0, B0, 5));
    }

    public static void main(String[] args) {
        small_test();
        Random r = new Random();
        // Random test 10000 times.
        for (int times = 0; times < 10000; ++times) {
            ArrayList<Integer> A = new ArrayList<Integer>();
            ArrayList<Integer> B = new ArrayList<Integer>();
            int n, m, k;
            if (args.length == 2) {
                n = Integer.parseInt(args[0]);
                m = Integer.parseInt(args[1]);
                k = r.nextInt(n + m) + 1;
            } else if (args.length == 3) {
                n = Integer.parseInt(args[0]);
                m = Integer.parseInt(args[1]);
                k = Integer.parseInt(args[2]);
            } else {
                n = r.nextInt(10000) + 1;
                m = r.nextInt(10000) + 1;
                k = r.nextInt(n + m) + 1;
            }
            for (int i = 0; i < n; ++i) {
                A.add(r.nextInt(100000));
            }
            for (int i = 0; i < m; ++i) {
                B.add(r.nextInt(100000));
            }
            Collections.sort(A);
            Collections.sort(B);
            /*System.out.println(A);
            System.out.println(B);*/
            int ans = find_kth_in_two_sorted_arrays(A, B, k);
            System.out.println(k + "th = " + ans);
            assert(ans == check_answer(A, B, k));
        }
    }
}
