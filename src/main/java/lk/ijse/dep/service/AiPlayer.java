package lk.ijse.dep.service;

public class AiPlayer extends Player {

    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {

        /*do {
            col = (int) (Math.random() * 6);
        }
        while (!board.isLegalMove(col));*/

        col = bestMove();
        board.updateMove(col, Piece.GREEN);
        board.getBoardUI().update(col, false);
        Winner winner = board.findWinner();

        if (winner.getWinningPiece() != Piece.EMPTY) {
            board.getBoardUI().notifyWinner(winner);    //notify winner
        } else if (!board.existLegalMoves()) {          //if don't have legal move
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }
    }

    private int bestMove() {    //minimax
        boolean isUserWinning = false;
        int tiedColumn = 0;
        int col;

        for (col = 0; col < 6; ++col) {
            if (board.isLegalMove(col)) {
                int row = board.findNextAvailableSpot(col);
                board.updateMove(col, Piece.GREEN);
                int heuristicVal = minimax(0, false);
                board.updateMove(col, row, Piece.EMPTY);
                if (heuristicVal == 1) {
                    return col;
                }
                if (heuristicVal == -1) {
                    isUserWinning = true;
                } else {
                    tiedColumn = col;
                }
            }
        }

        if (isUserWinning && board.isLegalMove(tiedColumn)) {
            return tiedColumn;
        } else {
            boolean legal = false;
            do {
                col = (int) ((Math.random() * (6 - 0)) + 0);
            } while (!board.isLegalMove(col));
            return col;
        }
    }

    private int minimax(int depth, boolean maximizingPlayer) {      //minimax
        Winner winner = board.findWinner();
        if (winner.getWinningPiece() == Piece.GREEN) {
            return 1;
        } else if (winner.getWinningPiece() == Piece.BLUE) {
            return -1;
        } else if (board.existLegalMoves() && depth != 2) {
            int col;
            int row;
            int heuristicValue;
            if (!maximizingPlayer) {
                for (col = 0; col < 6; ++col) {
                    if (board.isLegalMove(col)) {
                        row = board.findNextAvailableSpot(col);
                        board.updateMove(col, Piece.BLUE);
                        heuristicValue = this.minimax(depth + 1, true);
                        board.updateMove(col, row, Piece.EMPTY);
                        if (heuristicValue == -1) {
                            return heuristicValue;
                        }
                    }
                }
            } else {
                for (col = 0; col < 6; ++col) {
                    if (board.isLegalMove(col)) {
                        row = board.findNextAvailableSpot(col);
                        board.updateMove(col, Piece.GREEN);
                        heuristicValue = minimax(depth + 1, false);
                        board.updateMove(col, row, Piece.EMPTY);
                        if (heuristicValue == 1) {
                            return heuristicValue;
                        }
                    }
                }
            }
            return 0;
        } else {
            return 0;
        }
    }
}
