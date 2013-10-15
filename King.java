   import java.awt.*;
   import java.awt.image.*;
   import javax.swing.*;
   	
	/**
   A class that represents the chess piece King.
   @author Dyland Xue
   @version 2011-05-18
   */
   
   public class King implements Piece
   {
      private int color;
      private ImageIcon ico;
      private String name;
      private ChessBoard myBoard;
   	
   	/**
   	default constructor
   	@param c color of this piece
   	*/
      public King(int c)
      {
         color = c;
         if(color == ChessBoard.WHITE)
         {
            name = "K";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("white_king.png"));
         }
         else if(color == ChessBoard.BLACK)
         {
            name = "k";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("black_king.png"));
         }
      }
   	
   	/**
   	constructor with reference to a chessboard 
   	@param c color of this piece
   	@param cb reference to a chessboard
   	*/
      public King(int c, ChessBoard cb)
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
         //checks if the target square is occupied by a piece of its own color
         {
            return false;
         }
         //white castle kingside
         else if(color == ChessBoard.WHITE && 
         	c1.equals(new Coord(8, 5)) && 
         	c2.equals(new Coord(8, 7)) &&
            myBoard.get(new Coord(8, 6)) == null &&
            myBoard.get(new Coord(8, 7)) == null &&
         	myBoard.get(new Coord(8, 8)).getColor() == ChessBoard.WHITE &&
         	myBoard.get(new Coord(8, 8)) instanceof Rook)
         {
            myBoard.getJudge().special_castleKingside = true;
            return true;
         }
         //black castle kingside
         else if(color == ChessBoard.BLACK && 
         	c1.equals(new Coord(1, 5)) &&
         	c2.equals(new Coord(1, 7)) &&
         	myBoard.get(new Coord(1, 6)) == null &&
            myBoard.get(new Coord(1, 7)) == null &&
         	myBoard.get(new Coord(1, 8)).getColor() == ChessBoard.BLACK &&
         	myBoard.get(new Coord(1, 8)) instanceof Rook)
         {
            myBoard.getJudge().special_castleKingside = true;
            return true;
         }
         //white castle queenside
         else if(color == ChessBoard.WHITE && 
         	c1.equals(new Coord(8, 5)) && 
         	c2.equals(new Coord(8, 3)) &&
            myBoard.get(new Coord(8, 4)) == null &&
            myBoard.get(new Coord(8, 3)) == null &&
         	myBoard.get(new Coord(8, 2)) == null &&
         	myBoard.get(new Coord(8, 1)).getColor() == ChessBoard.WHITE &&
         	myBoard.get(new Coord(8, 1)) instanceof Rook)
         {
            myBoard.getJudge().special_castleQueenside = true;
            return true;
         }
         //black castle queenside
         else if(color == ChessBoard.BLACK && 
         	c1.equals(new Coord(1, 5)) &&
         	c2.equals(new Coord(1, 3)) &&
         	myBoard.get(new Coord(1, 4)) == null &&
            myBoard.get(new Coord(1, 3)) == null &&
         	myBoard.get(new Coord(1, 2)) == null &&
         	myBoard.get(new Coord(1, 1)).getColor() == ChessBoard.BLACK &&
         	myBoard.get(new Coord(1, 1)) instanceof Rook)
         {
            myBoard.getJudge().special_castleQueenside = true;
            return true;
         }
         else
         {
            double difC = Math.abs((double)(ci - ct));
            double difR = Math.abs((double)(ri - rt));
               
            return (difC < 2 && difR < 2);
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
         return "K";
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
   	@return VALUE_K
   	*/
      public double getVal()
      {
         return XMQ.VALUE_K;
      }
   }