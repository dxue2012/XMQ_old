/**
  A class that represents a chess judge who determines check, checkmate and other chess rules.
  @author Dyland Xue
  @version 2011-05-24
  */

import java.util.ArrayList;

public class ChessJudge
{
    public boolean special_castleKingside;
    public boolean special_castleQueenside;
    public boolean special_promotion;
    public boolean special_pawnTake;
    public boolean check;
    public boolean checkmate;
    public boolean gamePlaying;

    private ChessBoard myBoard;

    /**
      constructor with reference to a chessboard
      @param cb the chessboard being used
      */
    public ChessJudge(ChessBoard cb)
    {
        myBoard = cb;
        special_castleKingside = false;
        special_castleQueenside = false;
        special_promotion = false;
        special_pawnTake = false;
        check = false;
        checkmate = false;

        gamePlaying = true;
    }

    /**
      checks if the last move made is a check
      @return true if the last move is a check, otherwise false
      */
    public boolean isCheck()
    {
        if(myBoard.getTurn() == ChessBoard.WHITE)
        {
            Coord kingBlack = new Coord(0, 0);
            for(int r = 1; r < 9; r++)
            {
                for(int c = 1; c < 9; c++)
                {
                    Coord curr = new Coord(r, c);
                    if(myBoard.get(curr) != null &&
                            myBoard.get(curr).getColor() == ChessBoard.BLACK &&
                            myBoard.get(curr).getInitial().equals("K"))
                    {
                        kingBlack = curr;
                    }
                }
            }

            ArrayList<Piece> whitePieces = myBoard.getWhitePieces();
            ArrayList<Coord> whiteCoords = myBoard.getWhiteCoords();
            for(int i = 0; i < whitePieces.size(); i++)
            {
                if(whitePieces.get(i).soFarSoGood(whiteCoords.get(i), kingBlack))
                {
                    check = true;
                    special_pawnTake = false;
                    return true;
                }
            }

            return false;
        }
        else
        {
            Coord kingWhite = new Coord(0, 0);

            for(int r = 1; r < 9; r++)
            {
                for(int c = 1; c < 9; c++)
                {
                    Coord curr = new Coord(r, c);
                    if(myBoard.get(curr) != null &&
                            myBoard.get(curr).getColor() == ChessBoard.WHITE &&
                            myBoard.get(curr).getInitial().equals("K"))
                    {
                        kingWhite = curr;
                    }
                }
            }

            ArrayList<Piece> blackPieces = myBoard.getBlackPieces();
            ArrayList<Coord> blackCoords = myBoard.getBlackCoords();
            for(int i = 0; i < blackPieces.size(); i++)
            {
                if(blackPieces.get(i).soFarSoGood(blackCoords.get(i), kingWhite))
                {
                    check = true;
                    special_pawnTake = false;
                    return true;
                }
            }

            return false;
        }

    }

    /**
      checks if the last move made is a checkmate
      @return true if the last move is a checkmate, otherwise false
      */
    public boolean isCheckmate()
    {
        if(!check)
        {
            return false;
        }
        else
        {
            ArrayList<Piece> whitePieces = myBoard.getWhitePieces();
            ArrayList<Piece> blackPieces = myBoard.getBlackPieces();
            ArrayList<Coord> whiteCoords = myBoard.getWhiteCoords();
            ArrayList<Coord> blackCoords = myBoard.getBlackCoords();

            if(myBoard.getTurn() == ChessBoard.WHITE)
            {
                //loop over all the black pieces and check if the check is still valid
                for(int r = 1; r < 9; r++)
                {
                    for(int c = 1; c < 9; c++)
                    {
                        Coord des = new Coord(r, c);

                        for(int i = 0; i < blackPieces.size(); i++)
                        {
                            boolean originalCK = special_castleKingside;
                            boolean originalCQ = special_castleQueenside;
                            boolean originalPP = special_promotion;
                            boolean originalPT = special_pawnTake;

                            if(blackPieces.get(i).moveIsValid(blackCoords.get(i), des))
                            {
                                Piece temp = myBoard.get(des);
                                myBoard.set(des, blackPieces.get(i));
                                myBoard.set(blackCoords.get(i), null);

                                if(!isCheck())
                                {
                                    special_castleKingside = originalCK;
                                    special_castleQueenside = originalCQ;
                                    special_promotion = originalPP;
                                    special_pawnTake = originalPT;
                                    myBoard.set(des, temp);
                                    myBoard.set(blackCoords.get(i), blackPieces.get(i));
                                    return false;
                                }
                                else
                                {
                                    special_castleKingside = originalCK;
                                    special_castleQueenside = originalCQ;
                                    special_promotion = originalPP;
                                    special_pawnTake = originalPT;
                                    myBoard.set(des, temp);
                                    myBoard.set(blackCoords.get(i), blackPieces.get(i));
                                }
                            }
                        }
                    }
                }

                checkmate = true;
                return true;
            }
            else
            {
                //loop over all the white pieces and check if the check is still valid
                for(int r = 1; r < 9; r++)
                {
                    for(int c = 1; c < 9; c++)
                    {
                        Coord des = new Coord(r, c);

                        for(int i = 0; i < whitePieces.size(); i++)
                        {
                            boolean originalCK = special_castleKingside;
                            boolean originalCQ = special_castleQueenside;
                            boolean originalPP = special_promotion;
                            boolean originalPT = special_pawnTake;

                            if(whitePieces.get(i).moveIsValid(whiteCoords.get(i), des))
                            {
                                Piece temp = myBoard.get(des);

                                myBoard.set(des, whitePieces.get(i));
                                myBoard.set(whiteCoords.get(i), null);

                                if(!isCheck())
                                {
                                    special_castleKingside = originalCK;
                                    special_castleQueenside = originalCQ;
                                    special_promotion = originalPP;
                                    special_pawnTake = originalPT;
                                    myBoard.set(des, temp);
                                    myBoard.set(whiteCoords.get(i), whitePieces.get(i));
                                    return false;
                                }
                                else
                                {
                                    special_castleKingside = originalCK;
                                    special_castleQueenside = originalCQ;
                                    special_promotion = originalPP;
                                    special_pawnTake = originalPT;
                                    myBoard.set(des, temp);
                                    myBoard.set(whiteCoords.get(i), whitePieces.get(i));
                                }
                            }
                        }
                    }
                }

                checkmate = true;
                return true;
            }
        }
    }

    /**
      resets all the fields except gamePlaying
      */
    public void reset()
    {
        special_castleKingside = false;
        special_castleQueenside = false;
        special_promotion = false;
        special_pawnTake = false;
        check = false;
        checkmate = false;
    }
}
