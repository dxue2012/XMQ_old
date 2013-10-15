/**
  A class that represents the chess piece Rook.
  @author Dyland Xue
  @version 2011-05-17
  */

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;


public class Rook implements Piece
{
    private int color;
    private String name;
    private ImageIcon ico;
    private ChessBoard myBoard;

    /**
      default constructor
      @param c color of this piece
      */
    public Rook(int c)
    {
        color = c;
        if(color == ChessBoard.WHITE)
        {
            name = "R";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("white_rook.png"));
        }
        else if(color == ChessBoard.BLACK)
        {
            name = "r";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("black_rook.png"));
        }
    }

    /**
      constructor with reference to a chessboard 
      @param c color of this piece
      @param cb reference to a chessboard
      */
    public Rook(int c, ChessBoard cb)
    {
        this(c);
        myBoard = cb;
    }

    /**
      checks if the move from c1 to c2 is possible for this piece
      @param c1 starting coord
      @param c2 ending coord
      @return true if possible, otherwise false
      */
    public boolean soFarSoGood(Coord c1, Coord c2)
    {
        int ri = c1.getRow();
        int ci = c1.getCol();
        int rt = c2.getRow();
        int ct = c2.getCol();

        if(myBoard.get(c2) != null && myBoard.get(c2).getColor() == color)
        {
            return false;
        }
        else
        {
            if(ci != ct && ri != rt)
            {
                return false;
            }
            else if(ci == ct)
            {
                int min, max;

                if(ri < rt)
                {
                    min = ri;
                    max = rt;
                }
                else
                {
                    min = rt;
                    max = ri;
                }

                for(int i = min + 1; i < max; i++)
                {
                    if(myBoard.get(new Coord(i, ci)) != null)
                    {
                        return false;
                    }
                }

                return true;
            }
            else// if(ri == rt)
            {
                int min, max;

                if(ci < ct)
                {
                    min = ci;
                    max = ct;
                }
                else
                {
                    min = ct;
                    max = ci;
                }

                for(int i = min + 1; i < max; i++)
                {
                    if(myBoard.get(new Coord(ri, i)) != null)
                    {
                        return false;
                    }
                }

                return true;
            }
        }
    }  

    /**
      checks if the move from c1 to c2 satisfies chess rules or not
      @param c1 starting coord
      @param c2 ending coord
      @return true if move is valid, otherwise false
      */
    public boolean moveIsValid(Coord c1, Coord c2)
    {
        if(!soFarSoGood(c1, c2))
        {
            return false;
        }
        else
        {
            Piece temp = myBoard.get(c2);
            myBoard.set(c2, this);
            myBoard.set(c1, null);
            myBoard.next();

            if(myBoard.getJudge().isCheck())
            {
                myBoard.set(c2, temp);
                myBoard.set(c1, this);
                myBoard.next();
                myBoard.getJudge().check = false;
                return false;
            }
            else
            {
                myBoard.set(c2, temp);
                myBoard.set(c1, this);
                myBoard.next();
                return true;
            }
        }
    }

    /**
      sets a chessboard reference
      @param cb the chessboard this piece is in
      */
    public void setBoard(ChessBoard cb)
    {
        myBoard = cb;
    }

    /**
      gets the color of this piece, either white or black
      @return myColor
      */
    public int getColor()
    {
        return color;
    }

    /**
      gets the initial of this piece, used in notation
      @return name
      */
    public String getInitial()
    {
        return "R";
    }

    /**
      gets the icon of this piece, used to represent itself in the GUI
      @return ico
      */
    public ImageIcon getIcon()
    {
        return ico;
    }

    /**
      gets the value of this piece
      @return VALUE_R
      */
    public double getVal()
    {
        return XMQ.VALUE_R;
    }  
}
