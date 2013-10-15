   import javax.swing.*;
   
	/**
	Defines the interface for classes that represents chess pieces
	@author Dyland Xue
	@version 2011-05-27
	*/
	
   public interface Piece
   {		
   	/**
   	checks if the move from c1 to c2 is possible for the piece
   	@param c1 starting coord
   	@param c2 ending coord
   	@return true if possible, otherwise false
   	*/
      public boolean soFarSoGood(Coord c1, Coord c2);
   	
   	/**
   	checks if the move from c1 to c2 satisfies chess rules or not
   	@param c1 starting coord
   	@param c2 ending coord
   	@return true if move is valid, otherwise false
   	*/
      public boolean moveIsValid(Coord c1, Coord c2);
      
   	/**
   	gets the color of this piece, either white or black
   	@return myColor
   	*/
      public int getColor();
      
   	/**
   	gets the initial of this piece, used in notation
   	@return name
   	*/
      public String getInitial();
      
   	/**
   	sets a chessboard reference
   	@param cb the chessboard this piece is in
   	*/
      public void setBoard(ChessBoard cb);
      
   	/**
   	gets the icon of this piece, used to represent itself in the GUI
   	@return ico
   	*/
      public ImageIcon getIcon();
      
      /**
      gets the value of this piece
      @return myVal
      */
      public double getVal();
   }