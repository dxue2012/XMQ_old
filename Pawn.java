   import java.awt.*;
   import java.awt.image.*;
   import javax.swing.*;
	  
   /**
   A class that represents the chess piece Pawn.
   @author Dyland Xue
   @version 2011-05-17
   */
   
   public class Pawn implements Piece
   {
      private int color;
      private String name;
      private ImageIcon ico;
      public ChessBoard myBoard;
   	
   	/**
   	default constructor
   	@param c color of this piece
   	*/
      public Pawn(int c)
      {
         color = c;
         if(color == ChessBoard.WHITE)
         {
            name = "P";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("white_pawn.png"));
         }
         else if(color == ChessBoard.BLACK)
         {
            name = "p";
            ico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("black_pawn.png"));
         }
      }
      
   	/**
   	constructor with reference to a chessboard 
   	@param c color of this piece
   	@param cb reference to a chessboard
   	*/
      public Pawn(int c, ChessBoard cb)
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
      	
         if(myBoard.get(c2) == null)
         {
            if(ci != ct)
            {
               return false;
            }
         	
            if(color == ChessBoard.WHITE)
            {
               if(ri == 7)
               {
                  if(rt == 6 || rt == 5)
                  {
                     return true;
                  }
                  else
                  {
                     return false;
                  }
               }
               else if(ri == 2)
               {
                  myBoard.getJudge().special_promotion = true;
                  return true;
               }
               else
               {
                  if(rt == (ri - 1))
                  {
                     return true;
                  }
                  else
                  {
                     return false;
                  }
               }
            }
            else //if(color == ChessBoard.BLACK)
            {
               if(ri == 2)
               {
                  if(rt == 3 || rt == 4)
                  {
                     return true;
                  }
                  else
                  {
                     return false;
                  }
               }
               else if(ri == 7)
               {
                  myBoard.getJudge().special_promotion = true;
                  return true;
               }
               else
               {
                  if(rt == (ri + 1))
                  {
                     return true;
                  }
                  else
                  {
                     return false;
                  }
               }
            }
         }
         else //if(myBoard.get(c2) != null)
         {
            if(myBoard.get(c2).getColor() == color)
            {
               return false;
            }
            else
            {
               if(color == ChessBoard.WHITE)
               {
                  if(ri == 2 && (rt == ri - 1) && (ct == ci - 1 || ct == ci + 1))
                  {
                     myBoard.getJudge().special_promotion = true;
                     myBoard.getJudge().special_pawnTake = true;
                     return true;
                  }
                  else
                     if((rt == ri - 1) && (ct == ci - 1 || ct == ci + 1))
                     {
                        myBoard.getJudge().special_pawnTake = true;
                        return true;
                     }
                     else
                     {
                        return false;
                     }
               }
               else //if(color == ChessBoard.BLACK)
               {
                  if(ri == 7 && (rt == ri + 1) && (ct == ci - 1 || ct == ci + 1))
                  {
                     myBoard.getJudge().special_promotion = true;
                     myBoard.getJudge().special_pawnTake = true;
                     return true;
                  }
                  else
                     if((rt == ri + 1) && (ct == ci - 1 || ct == ci + 1))
                     {
                        myBoard.getJudge().special_pawnTake = true;
                        return true;
                     }
                     else
                     {
                        return false;
                     }
               }
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
         return "";
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
   	@return VALUE_P
   	*/
      public double getVal()
      {
         return XMQ.VALUE_P;
      }
   }