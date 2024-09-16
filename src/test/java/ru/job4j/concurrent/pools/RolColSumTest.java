package ru.job4j.concurrent.pools;


import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class RolColSumTest {

    @Test
    void syncSum() {
        int[][] matrix = new int[][]{{3, 4, 5},
                {5, 6, 7},
                {7, 8, 9}};
        Sums[] abc = new Sums[]{new Sums(12, 15),
                new Sums(18, 18),
                new Sums(24, 21)};
        assertThat(RolColSum.sum(matrix)).containsExactly(abc);
    }

    @Test
    void asyncSum() {
        int[][] matrix = new int[][]{{3, 4, 5},
                {5, 6, 7},
                {7, 8, 9}};
        Sums[] abc = new Sums[]{new Sums(12, 15),
                new Sums(18, 18),
                new Sums(24, 21)};
        assertThat(RolColSum.asyncSum(matrix)).containsExactly(abc);
    }
}
