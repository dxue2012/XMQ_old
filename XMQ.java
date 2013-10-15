   import java.util.ArrayList;

/**
The smartest chess engine ever--XMQ
@author Dyland Xue
@version 2011-12-14
*/

   public class XMQ
   {
      public static final int EASY = 3;
      public static final int MEDIUM = 6;
      public static final int HARD = 9;
      public static final double VALUE_P = 1;
      public static final double VALUE_N = 3;
      public static final double VALUE_B = 3;
      public static final double VALUE_R = 5;
      public static final double VALUE_Q = 9;
      public static final double VALUE_K = 200;
   	
   	/**
   	difficulty, also the depth of the search tree
   	*/
      private int myDiff;
      private int myColor;
      private ChessBoard myBoard;
      public XMQ myOpp;
   
      public XMQ (int c, int diff, ChessBoard cb)
      {
         myColor = c;
         myDiff = diff;
         myBoard = cb;
         
         int oppColor;
         if(c == ChessBoard.WHITE)
         {
            oppColor = ChessBoard.BLACK;
         }
         else
         {
            oppColor = ChessBoard.WHITE;
         }
      	
         myOpp = new XMQ(oppColor, diff, cb);
         myOpp.myOpp = this;
      }
      
      public double evaluate()
      {
         double result = 0;
            
         for(int r = 1; r < 9; r++)
         {
            for(int c = 1; c < 9; c++)
            {
               Piece myPiece = myBoard.get(new Coord(r, c));
               if(myPiece != null)
               {
                  if(myPiece.getColor() == myColor)
                  {
                     result += myPiece.getVal();
                  }
                  else
                  {
                     result -= myPiece.getVal();
                  }
               }
            }
         }
         
         return result;
      }
      
   	/**
   	loops over all possible moves, evaluates all possible outcomes and chooses the best one
   	*/
      public Coord[] act()
      {
         if(myBoard == null)
         {
            return null;
         }
         else
         {
         	/////////////////////////////////
         	/*******************************/
            return null;//searchRecur(myDiff);
         }
      }
      
      public double searchRecur(int depth)
      {
         Coord bestMoveStart = new Coord(0, 0);
         Coord bestMoveDes = new Coord(0, 0);
         Coord[] bestMove= new Coord[2];
         bestMove[0] = bestMoveStart;
         bestMove[1] = bestMoveDes;
         int myDepth = depth - 1;
      	
         ArrayList<Piece> myPieces = new ArrayList<Piece>();
         ArrayList<Coord> myCoords = new ArrayList<Coord>();
         ArrayList<Coord> myDes = new ArrayList<Coord>();
         ArrayList<Double> moveScores = new ArrayList<Double>();  
         double best = Integer.MIN_VALUE;
      	
      	//loops over all pieces
         for(int r = 1; r < 9; r++)
         {
            for(int c = 1; c < 9; c++)
            {
               Piece myPiece = myBoard.get(new Coord(r, c));
               if(myPiece != null && myPiece.getColor() == myColor)
               {
                  myPieces.add(myPiece);
                  myCoords.add(new Coord(r, c));
               }
            }
         }
         
      	//loops over all possible moves
         for(int r = 1; r < 9; r++)
         {
            for(int c = 1; c < 9; c++)
            {
               Coord des = new Coord(r, c);
               for(int i = 0; i < myPieces.size(); i++)
               {
                  boolean originalCK = myBoard.getJudge().special_castleKingside;
                  boolean originalCQ = myBoard.getJudge().special_castleQueenside;
                  boolean originalPP = myBoard.getJudge().special_promotion;
                  boolean originalPT = myBoard.getJudge().special_pawnTake;
                        
                  if(myPieces.get(i).moveIsValid(myCoords.get(i), des))
                  {
                     myDes.add(des);
                     
                     Piece temp = myBoard.get(des);
                     myBoard.set(des, myPieces.get(i));
                     myBoard.set(myCoords.get(i), null);
                     
                     if(myDepth == 0)
                     {
                        moveScores.add(evaluate());
                     }
                     else
                     {
                        moveScores.add(myOpp.searchRecur(myDepth - 1));
                     }
                     
                     myBoard.getJudge().special_castleKingside = originalCK;
                     myBoard.getJudge().special_castleQueenside = originalCQ;
                     myBoard.getJudge().special_promotion = originalPP;
                     myBoard.getJudge().special_pawnTake = originalPT;
                     myBoard.set(des, temp);
                     myBoard.set(myCoords.get(i), myPieces.get(i));
                  }
               }
            }
         }
      	
         for(int i = 0; i < moveScores.size(); i++)
         {
            if(moveScores.get(i) > best)
            {
               best = moveScores.get(i);
               bestMoveStart = myCoords.get(i);
               bestMoveDes = myDes.get(i);
            }
         }
         
         System.out.println(bestMoveStart);
      	
         return best;
      }
   }