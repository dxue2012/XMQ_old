   import java.awt.*;
   import java.awt.image.*;
   import javax.swing.*;  
	
	/**
   A class that represents the chess piece Knight.
   @author Dyland Xue
   @version 2011-05-17
   */
   
   public class Knight implements Piece
   {
      private int color;
      private String name;
      private ImageIcon ico;
      private ChessBoard myBoard;
   	
   	/**
   	default constructor
   	@param c color of this piece
   	*/
      public Knight(int c)
      {
         color = c;
         if(color == ChessBoard.WHITE)
         {
            name = "N";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("white_knight.png"));
         }
         else if(color == ChessBoard.BLACK)
         {
            name = "n";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("black_knight.png"));
         }
      }
   	
   	/**
   	constructor with reference to a chessboard 
   	@param c color of this piece
   	@param cb reference to a chessboard
   	*/
      public Knight(int c, ChessBoard cb)
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
            return
               ((rt == ri + 2 || rt == ri - 2) && (ct == ci + 1 || ct == ci - 1))
               ||
               ((rt == ri + 1 || rt == ri - 1) && (ct == ci + 2 || ct == ci - 2));
               //rt == ri +- 2 and ct == ci +- 1 
         		//or
         		//rt == ri +- 1 and ct == ci +-2
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
         return "N";
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
   	@return VALUE_N
   	*/
      public double getVal()
      {
         return XMQ.VALUE_N;
      }
   }