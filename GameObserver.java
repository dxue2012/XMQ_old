   import javax.swing.*;
   import java.util.ArrayList;

/**
A class that represents a game observer who records game results and notation.
@author Dyland Xue
@version 2011-05-24
*/

   public class GameObserver
   {
      private JScrollPane myScroller;
      private JTextArea myText;
      private JPanel myPanel;
      private ArrayList<String> myLines;
      private int numCount;
      private ChessBoard myBoard;
   	
   	/**
   	constructor with reference to a chessboard
   	@param cb the chessboard being used
   	*/
      public GameObserver(ChessBoard cb)
      {
         myBoard = cb;
         numCount = 1;
         myLines = new ArrayList<String>();
      	
         myText = new JTextArea(20, 10);
         myText.setEditable(false);
         myText.setFont(ChessRunner.visibleFont);
         myScroller = new JScrollPane(myText);
         myScroller.setVerticalScrollBarPolicy
            (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
         myScroller.setHorizontalScrollBarPolicy
            (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);      
      		
         myPanel = new JPanel();
         myPanel.add(myScroller);
      }
      
   	/**
   	records the last move made
   	@param turn either ChessBoard.WHITE or ChessBoard.BLACK
   	@param p the piece moved
   	@param start the starting coord of the move
   	@param des the ending coord of the move
   	*/
      public void takeNotes(int turn, Piece p, Coord start, Coord des)
      {
         String note = "";
         if(turn == ChessBoard.WHITE)
         {
            if(myBoard.getJudge().special_castleKingside == true)
            {
               note = numCount + ". " + "0-0" + " ";
            }
            else if(myBoard.getJudge().special_castleQueenside == true)
            {
               note = numCount + ". " + "0-0-0";
            }
            else if(myBoard.getJudge().special_promotion == true)
            {
               note = numCount + ". " + des + "=" + myBoard.get(des).getInitial();
            }
            else if(myBoard.getJudge().special_pawnTake == true)
            {
               note = numCount + ". " + start.toString().substring(0, 1) + des;
            }
            else
            {
               note = numCount + ". " + p.getInitial() + des;
            } 
            
            if(myBoard.getJudge().checkmate)
            {
               note += "++";
            }  
            else if(myBoard.getJudge().check)
            {
               note += "+";
            }
         	
            note += " ";
         }
         else
         {
            if(myBoard.getJudge().special_castleKingside == true)
            {
               note = "0-0";
            }
            else if(myBoard.getJudge().special_castleQueenside == true)
            {
               note = "0-0-0";
            }
            else if(myBoard.getJudge().special_promotion == true)
            {
               note = des + "=" + myBoard.get(des).getInitial();
            }
            else if(myBoard.getJudge().special_pawnTake == true)
            {
               note = "" + start.toString().substring(0, 1) + des;
            }
            else
            {
               note = p.getInitial() + des;
            }
            
            if(myBoard.getJudge().check)
            {
               note += "+";
            }  
            else if(myBoard.getJudge().checkmate)
            {
               note += "++";
            }
         	
            note += "\n";
            numCount++;
         }
         
         myLines.add(note);
         myText.append(note);
      }
   	
   	/**
   	gets the JPanel that contains the notation
   	@return myPanel
   	*/
      public JPanel getPanel()
      {
         return myPanel;
      }
      
   	/**
   	gets the string list that contains the notation
   	@return myLines
   	*/
      public ArrayList<String> getText()
      {
         return myLines;
      }
      
   	/**
   	records the result of the game
   	@param r string that contains the result of the game
   	*/
      public void setResult(String r)
      {
         r = "\n" + r;
         myLines.add(r);
         myText.append(r);
      }
   }