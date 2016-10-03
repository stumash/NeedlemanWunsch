public class NeedlemanWunschMain {

    public static void main(String[] args) {
        //TODO: get user input for argStrings

        initialize(argString1, argString2);
        fillNwMatrix();
    }

    public static final int GAP_SCORE = -2;
    public static final String SCORE_MATRIX_COL_AND_ROW_NAMES = "CTAG";
    public static final int[][] SCORE_MATRIX =  {{2,1,-1,-1},
                                                 {1,2,-1,-1},
                                                 {-1,-1,2,1},
                                                 {-1,-1,1,2}};
    public static String argString1;
    public static String argString2;
    public static NWCell[][] nwm; // Needleman-Wunsch score and traceback matrix


    /**
     * Create empty traceback/score matrix given two Strings
     */
    public static void initialize(String a, String b) {
        nwm = new NWCell[a.length() + 1][b.length() + 1];
    }

    /**
     * Fill traceback/score matrix matrix
     */
    public static void fillNwMatrix() {
        for (int i = 0; i < nwm.length; i++) {
            for (int j = 0; j < nwm[0].length; j++) {
                nwm[i][j] = getCellScoreAndTracebackDirection(i,j);
            }
        }
    }

    /**
     * Calculate the score and traceback direction for the cell using a recurrence relation
     */
    public static NWCell getCellScoreAndTracebackDirection(int i , int j) {
        NWCell up = (i - 1 < 0)? null: nwm[i-1][j];
        NWCell left = (j - 1 < 0)? null: nwm[i][j-1];
        NWCell diag = (j - 1 < 0 || i - 1 < 0)? null: nwm[i-1][j-1];
        NWCell max = new NWCell(Integer.MIN_VALUE, Direction.DONE);

        if (diag != null) {
            // charAt(i - 1) because the index in nwm for a certain letter is one over since the col/row names start with a gap
            max = new NWCell(diag.num + scoreMatrix(argString1.charAt(i-1), argString2.charAt(j-1)), Direction.DIAG);
        }
        if (up != null && up.num + GAP_SCORE > max.num) {
            max = new NWCell(up.num + GAP_SCORE, Direction.UP);
        }
        if (left != null && left.num + GAP_SCORE > max.num) {
            max = new NWCell(left.num + GAP_SCORE, Direction.LEFT);
        }

        if (max.whereto == Direction.DONE) {
            max.num = 0;
        }
        return max;
    }

    private static Integer scoreMatrix(char a, char b) {
        int i = SCORE_MATRIX_COL_AND_ROW_NAMES.indexOf(a);
        int j = SCORE_MATRIX_COL_AND_ROW_NAMES.indexOf(b);
        return SCORE_MATRIX[i][j];
    }

    /**
     * Perform traceback
     */
    public static void traceback() {

    }
}

/**
 * A cell in the Needleman-Wunsch score and traceback matrix
 */
class NWCell {
    Integer num;
    Direction whereto;

    NWCell(Integer num, Direction whereto) {
        this.num = num;
        this.whereto = whereto;
    }
}

/**
 * some useful constants for the NWCell class
 */
enum Direction {
    UP, LEFT, DIAG, DONE;
}
