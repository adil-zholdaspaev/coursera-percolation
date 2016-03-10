package net.thumbtack.learning;

import edu.princeton.cs.algorithms.WeightedQuickUnionUF;

public class Percolation {

    private final int[][] grid;
    private final int n;

    private final WeightedQuickUnionUF quickUnion;

    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("'N' must be a positive number");
        }

        this.n = n;
        this.grid = new int[n][n];
        this.quickUnion = new WeightedQuickUnionUF(n * n);
    }

    public void open(final int i, final int j) {
        checkArgument(i - 1);
        checkArgument(j - 1);

        openSite(i - 1, j - 1);
    }

    public boolean isOpen(final int i, final int j) {
        checkArgument(i - 1);
        checkArgument(j - 1);

        return grid[i - 1][j - 1] == 1;
    }

    public boolean isFull(final int i, final int j) {
        checkArgument(i - 1);
        checkArgument(j - 1);

        final int index = convertIndex(i - 1, j - 1);
        for (int k = 0; k < n; k++) {
            if (quickUnion.connected(k, index)) {
                return true;
            }
        }

        return false;
    }

    public boolean percolates() {
        for (int k = 1; k <= n; k++) {
            if (isOpen(n, k) && isFull(n, k)) {
                return true;
            }
        }

        return false;
    }

    private void openSite(final int row, final int column) {
        grid[row][column] = 1;
        unionNeighbours(row, column);
    }

    private void unionNeighbours(final int row, final int column) {
        unionNeighbourOnSide(row, column, row, column - 1);
        unionNeighbourOnSide(row, column, row, column + 1);
        unionNeighbourOnSide(row, column, row - 1, column);
        unionNeighbourOnSide(row, column, row + 1, column);
    }

    private void unionNeighbourOnSide(final int row, final int column,
                                      final int rowNeightbour, final int columnNeightbour) {

        try {
            checkArgument(rowNeightbour);
            checkArgument(columnNeightbour);
        } catch (IllegalArgumentException e) {
            return;
        }

        if (isOpen(rowNeightbour, columnNeightbour)) {
            quickUnion.union(convertIndex(row, column), convertIndex(rowNeightbour, columnNeightbour));
        }
    }

    private int convertIndex(final int i, final int j) {
        return i * n + j;
    }

    private void checkArgument(final int value) {
        if (value < 0 || value >= n) {
            throw new IndexOutOfBoundsException("Value is outsize its prescribed range");
        }
    }
}
