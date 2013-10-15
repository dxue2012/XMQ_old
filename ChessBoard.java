/**
  A class that represents the chessboard of the game.
  @author Dyland Xue
  @version 2011-05-18
*/

import javax.swing.JOptionPane;
import java.util.ArrayList;


public class ChessBoard
{
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    private ChessJudge myJudge;
    private Piece[][] myPieces;
    private Coord start;
    private Coord des;
    private boolean chosen;
    private int turn;

    /**
      constructor with queen fan option
      @param freddie true if the player is a Queen fan, thus using special images for queens
      */
    public ChessBoard(boolean freddie)
    {
        myJudge = new ChessJudge(this);
        myPieces = new Piece[9][9];
        for(int c = 1; c < myPieces[1].length; c++)
        {
            myPieces[2][c] = new Pawn(BLACK);
            myPieces[7][c] = new Pawn(WHITE);
        }

        myPieces[1][1] = new Rook(BLACK);
        myPieces[1][8] = new Rook(BLACK);
        myPieces[8][1] = new Rook(WHITE);
        myPieces[8][8] = new Rook(WHITE);

        myPieces[1][2] = new Knight(BLACK);
        myPieces[1][7] = new Knight(BLACK);
        myPieces[8][2] = new Knight(WHITE);
        myPieces[8][7] = new Knight(WHITE);

        myPieces[1][3] = new Bishop(BLACK);
        myPieces[1][6] = new Bishop(BLACK);
        myPieces[8][3] = new Bishop(WHITE);
        myPieces[8][6] = new Bishop(WHITE);

        if(freddie == true)
        {
            myPieces[1][4] = new Queen(BLACK, this, true);
            myPieces[8][4] = new Queen(WHITE, this, true);
        }
        else
        {
            myPieces[1][4] = new Queen(BLACK, this);
            myPieces[8][4] = new Queen(WHITE, this);
        }

        myPieces[1][5] = new King(BLACK);
        myPieces[8][5] = new King(WHITE);

        for(int row = 1; row < 9; row++)
        {
            for(int col = 1; col < 9; col++)
            {
                if(myPieces[row][col] != null)
                {
                    myPieces[row][col].setBoard(this);
                }
            }
        }

        chosen = false;
        turn = WHITE;
    }

    /**
      gets the piece at coordination c
      @param c the given Coord
      @return the piece in myPieces at coord c
      */
    public Piece get(Coord c)
    {
        return myPieces[c.getRow()][c.getCol()];
    }	

    /**
      gets all white pieces in the grid
      @return an ArrayList that contains all white pieces in the grid
      */
    public ArrayList<Piece> getWhitePieces()
    {
        ArrayList<Piece> whitePieces = new ArrayList<Piece>();
        for(int r = 1; r < 9; r++)
        {
            for(int c = 1; c < 9; c++)
            {
                Coord curr = new Coord(r, c);

                if(get(curr) != null &&
                        get(curr).getColor() == WHITE)
                {
                    whitePieces.add(get(curr));
                }
            }
        }

        return whitePieces;
    }

    /**
      gets all black pieces in the grid
      @return an ArrayList that contains all black pieces in the grid
      */
    public ArrayList<Piece> getBlackPieces()
    {
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();
        for(int r = 1; r < 9; r++)
        {
            for(int c = 1; c < 9; c++)
            {
                Coord curr = new Coord(r, c);

                if(get(curr) != null &&
                        get(curr).getColor() == BLACK)
                {
                    blackPieces.add(get(curr));
                }
            }
        }

        return blackPieces;
    }

    /**
      gets all coords of all white coords in the grid
      @return an ArrayList that contains all coords of all white pieces in the grid
      */
    public ArrayList<Coord> getWhiteCoords()
    {
        ArrayList<Coord> whiteCoords = new ArrayList<Coord>();
        for(int r = 1; r < 9; r++)
        {
            for(int c = 1; c < 9; c++)
            {
                Coord curr = new Coord(r, c);

                if(get(curr) != null &&
                        get(curr).getColor() == WHITE)
                {
                    whiteCoords.add(curr);
                }
            }
        }

        return whiteCoords;
    }

    /**
      gets all coords of all black pieces in the grid
      @return an ArrayList that contains all coords of all black pieces in the grid
      */
    public ArrayList<Coord> getBlackCoords()
    {
        ArrayList<Coord> blackCoords = new ArrayList<Coord>();
        for(int r = 1; r < 9; r++)
        {
            for(int c = 1; c < 9; c++)
            {
                Coord curr = new Coord(r, c);

                if(get(curr) != null &&
                        get(curr).getColor() == BLACK)
                {
                    blackCoords.add(curr);
                }
            }
        }

        return blackCoords;
    }

    /**
      sets the piece at coordination c
      @param c the given Coord
      @param p the piece to be put in grid
      */
    public void set(Coord c, Piece p)
    {
        myPieces[c.getRow()][c.getCol()] = p;
    }

    /**
      gets status of the board
      @return true if a piece in grid is chosen, otherwise false
      */ 
    public boolean isChosen()
    {
        return chosen;
    }

    /**
      sets status of the board
      @param stat the boolean assigned to chosen; 
      true if a piece in grid is chosen, otherwise false
      */
    public void setChosen(boolean stat)
    {
        chosen = stat;
    }

    /**
      gets the starting coord of a move
      @return start
      */
    public Coord getStartingCoord()
    {
        return start;
    }

    /**
      gets the ending coord of a move
      @return des
      */
    public Coord getDestination()
    {
        return des;
    }

    /**
      sets the starting coord of a move
      @param c the coord to be set as the starting coord of a move
      */
    public void setStartingCoord(Coord c)
    {
        start = c;
    }

    /**
      sets the ending coord of a move
      @param c the coord to be set as the ending coord of a move
      */
    public void setDestination(Coord c)
    {
        des = c;
    }

    /**
      toggles; switches the current turn to move
      */
    public void next()
    {
        if(turn == WHITE)
        {
            turn = BLACK;
        }
        else
        {
            turn = WHITE;
        }
    }

    /**
      gets whose turn it is
      @return turn
      */
    public int getTurn()
    {
        return turn;
    }

    /**
      gets the chess judge
      @return myJudge
      */
    public ChessJudge getJudge()
    {
        return myJudge;
    }
}
