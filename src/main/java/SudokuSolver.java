import java.util.Arrays;

public class SudokuSolver {

    protected int currentX = 0;
    protected int currentY = 0;

    protected int[][] inputBoard;
    protected int[][] result;

    protected int[][] counters = new int[9][9];

    public SudokuSolver(int[][] inputBoard) {
        this.inputBoard = inputBoard;
        result = deepCopy(inputBoard);

        for(int i=0;i<counters.length;i++) {
            for(int j=0;j<counters[i].length;j++) {
                counters[i][j] = 1;
            }
        }
    }

    public int[][] solve() {

        while (currentX <= 8 && currentY <= 8) {

            if (counters[8][8] == 9) {
                throw new RuntimeException("No possible solutions");
            }

            if(inputBoard[currentX][currentY] != 0) {
                goToNextPosition();
                continue;
            }

            int newValue = counters[currentX][currentY];

//            System.out.println(currentX + ", " + currentY + "   value: " + newValue);

            boolean allowed = checkIfAllowed(newValue, result);

            if (allowed) {
                result[currentX][currentY] = newValue;
                goToNextPosition();
            } else {
                if (counters[currentX][currentY] < 9) {
                    counters[currentX][currentY]++;
                } else {
                    while (counters[currentX][currentY] >= 9 || inputBoard[currentX][currentY] != 0) {
                        counters[currentX][currentY]=0;

                        if(inputBoard[currentX][currentY] == 0) {
                            result[currentX][currentY]=0;
                        }

                        goToPreviousPosition();
                    }
                    counters[currentX][currentY]++;
                }
            }
        }

        return result;
    }

    protected void goToPreviousPosition() {
        currentX--;
        if (currentX == -1) {
            currentX = 8;
            currentY--;
        }

        if(currentX <0 || currentY < 0) {
            print(result);
            throw new IllegalStateException("currentX: " + currentX + ", currentY: " + currentY);
        }
    }

    protected void goToNextPosition() {
        currentX++;
        if (currentX % 9 == 0) {
           currentX = 0;
           currentY++;
        }

//        if(currentX > 8 || currentY > 8) {
//            print(result);
//            throw new IllegalStateException("currentX: " + currentX + ", currentY: " + currentY);
//        }
    }

    private boolean checkIfAllowed(int value, int[][] board) {
        return checkHorizontalAllowed(value) &&
                checkVerticalAllowed(value) &&
                checkSquareAllowed(value);

    }

    protected boolean checkHorizontalAllowed(int value) {
        for (int i = 0; i < 9; i++) {
            if (i != currentX) {
                if (result[i][currentY] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean checkVerticalAllowed(int value) {
        for (int i = 0; i < 9; i++) {
            if (i != currentY) {
                if (result[currentX][i] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean checkSquareAllowed(int value) {

        int startX = (currentX/3)*3;
        int startY = (currentY/3)*3;

        for(int i = startX; i<startX+3; i++) {
            for(int j = startY; j<startY+3; j++) {
                if(i != currentX || j != currentY) {
                    if(result[i][j] == value)
                        return false;
                }
            }
        }

        return true;
    }


    public int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
            // For Java versions prior to Java 6 use the next:
            // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }

    public void print(int[][] array) {

        for(int i=0;i<array.length;i++) {
            for(int j=0;j<array[i].length;j++) {
                System.out.print(array[i][j] + ", ");
            }
            System.out.println();
        }

    }

}
