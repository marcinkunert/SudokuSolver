import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SudokuSolverTest {

    @Test
    public void shouldGoToNextPosition() {
        SudokuSolver sudokuSolver = new SudokuSolver(null);
        sudokuSolver.currentX = 5;
        sudokuSolver.currentY = 5;

        sudokuSolver.goToNextPosition();

        assertThat(sudokuSolver.currentX, is(6));
        assertThat(sudokuSolver.currentY, is(5));
    }

    @Test
    public void shouldGoToNextRow() {
        SudokuSolver sudokuSolver = new SudokuSolver(null);
        sudokuSolver.currentX = 8;
        sudokuSolver.currentY = 5;

        sudokuSolver.goToNextPosition();

        assertThat(sudokuSolver.currentX, is(0));
        assertThat(sudokuSolver.currentY, is(6));
    }

    @Test
    public void shouldGoToPreviousPosition() {
        SudokuSolver sudokuSolver = new SudokuSolver(null);
        sudokuSolver.currentX = 5;
        sudokuSolver.currentY = 5;

        sudokuSolver.goToPreviousPosition();

        assertThat(sudokuSolver.currentX, is(4));
        assertThat(sudokuSolver.currentY, is(5));
    }

    @Test
    public void shouldGoToPreviousRow() {
        SudokuSolver sudokuSolver = new SudokuSolver(null);
        sudokuSolver.currentX = 0;
        sudokuSolver.currentY = 5;

        sudokuSolver.goToPreviousPosition();

        assertThat(sudokuSolver.currentX, is(8));
        assertThat(sudokuSolver.currentY, is(4));
    }

    @Test
    public void shouldAllowVerticalValue() {
        int[][] input = createEmptySudoku();
        input[0][0] = 1;
        input[0][1] = 2;
        input[0][2] = 3;
        input[0][3] = 4;
        input[0][4] = 5;
        input[0][5] = 6;
        input[0][6] = 7;
        input[0][7] = 8;

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        sudokuSolver.currentY = 0;
        boolean allowed = sudokuSolver.checkVerticalAllowed(9);

        assertThat(allowed, is(true));
    }

    @Test
    public void shouldNotAllowVerticalValue() {

        int[][] input = createEmptySudoku();
        input[0][8] = 1;

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        sudokuSolver.currentY = 0;
        boolean allowed = sudokuSolver.checkVerticalAllowed(1);

        assertThat(allowed, is(false));
    }

    @Test
    public void shouldAllowHorizontalValue() {
        int[][] input = createEmptySudoku();
        input[0][5] = 1;
        input[1][5] = 2;
        input[2][5] = 3;
        input[3][5] = 4;
        input[4][5] = 5;
        input[5][5] = 6;
        input[6][5] = 7;
        input[7][5] = 8;

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        sudokuSolver.currentX = 5;
        boolean allowed = sudokuSolver.checkVerticalAllowed(9);

        assertThat(allowed, is(true));
    }

    @Test
    public void shouldNotAllowHorizontalValue() {
        int[][] input = createEmptySudoku();
        input[8][5] = 5;

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        sudokuSolver.currentX = 5;
        boolean allowed = sudokuSolver.checkVerticalAllowed(8);

        assertThat(allowed, is(true));
    }

    @Test
    public void shouldAllowSquare() {
        int[][] input = createEmptySudoku();
        input[0][0] = 1;
        input[0][1] = 2;
        input[0][2] = 3;
        input[1][0] = 4;
        input[1][1] = 5;
        input[1][2] = 6;
        input[2][0] = 7;
        input[2][1] = 8;

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        sudokuSolver.currentX = 2;
        sudokuSolver.currentY = 2;
        boolean allowed = sudokuSolver.checkSquareAllowed(9);

        assertThat(allowed, is(true));
    }

    @Test
    public void shouldNotAllowSquare() {
        int[][] input = createEmptySudoku();
        input[0][0] = 1;
        input[0][1] = 2;
        input[0][2] = 3;
        input[1][0] = 4;
        input[1][1] = 5;
        input[1][2] = 6;
        input[2][0] = 7;
        input[2][1] = 8;

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        sudokuSolver.currentX = 2;
        sudokuSolver.currentY = 2;
        boolean allowed = sudokuSolver.checkSquareAllowed(8);

        assertThat(allowed, is(false));
    }

    @Test
    public void shouldNotAllowSquare2() {
        int[][] input = createEmptySudoku();
        input[3][3] = 1;
        input[3][4] = 2;
        input[3][5] = 3;
        input[4][3] = 4;
        input[4][4] = 5;
        input[4][5] = 6;
        input[5][3] = 7;
        input[5][4] = 8;

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        sudokuSolver.currentX = 5;
        sudokuSolver.currentY = 5;
        boolean allowed = sudokuSolver.checkSquareAllowed(8);

        assertThat(allowed, is(false));
    }

    @Test
    public void shouldSolveEmptySudoku() {

        int[][] input = createEmptySudoku();

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        int[][] result = sudokuSolver.solve();

        sudokuSolver.print(result);
    }


    @Test
    public void shouldSolveHardSudoku() {

        int[][] input = new int[][] {
                {0,0,0,0,0,8,0,0,3},
                {8,0,3,9,7,0,4,5,0},
                {0,0,0,0,2,0,0,0,0},
                {3,0,5,0,0,7,1,0,6},
                {0,0,0,0,0,0,0,0,0},
                {4,0,1,8,0,0,2,0,5},
                {0,0,0,0,8,0,0,0,0},
                {0,4,2,0,6,9,3,0,7},
                {6,0,0,5,0,0,0,0,0}};

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        int[][] result = sudokuSolver.solve();

        sudokuSolver.print(result);
    }

    @Test
    public void shouldSolveMediumSudoku() {

        int[][] input = new int[][] {
                {0,0,0,6,4,8,1,0,7},
                {9,0,1,5,0,0,4,0,3},
                {0,0,0,0,0,0,5,0,6},
                {0,0,0,0,5,0,6,0,0},
                {0,8,0,4,0,2,0,1,0},
                {0,0,2,0,7,6,0,0,0},
                {5,9,8,2,6,4,0,0,1},
                {6,1,4,0,0,0,2,5,9},
                {2,7,3,9,1,5,8,6,4}};

        SudokuSolver sudokuSolver = new SudokuSolver(input);
        int[][] result = sudokuSolver.solve();

        sudokuSolver.print(result);
    }

    private int[][] createEmptySudoku() {
        return new int[][] {{0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0}};
    }

}
