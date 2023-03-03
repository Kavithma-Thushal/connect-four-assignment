package lk.ijse.dep.service;

public interface Board {

    public int NUM_OF_COLS = 6;
    public int NUM_OF_ROWS = 5;

    public BoardUI getBoardUI();

    public int findNextAvailableSpot(int col);

    public boolean isLegalMove(int col);

    public boolean existLegalMoves();

    public void updateMove(int col, Piece move);

    public void updateMove(int col, int row, Piece move);   //minimax

    public Winner findWinner();
}
