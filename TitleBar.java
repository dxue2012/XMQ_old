   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   
	/**
	A class that represents the entire title bar, which includes player names,
	score and turn indicator.
	@author Dyland Xue
	@version 2011-05-24
	*/
	
   public class TitleBar
   {
      private String whiteName, blackName;
      private double whiteScore, blackScore;
   	
      private JLabel title;
      private JLabel names;
      private JLabel score;
      private JLabel whiteTurnIndicator, blackTurnIndicator;
      private JPanel myPanel;
      private JPanel tPanel;
      private JPanel indicatorPanel;
      private ChessBoard myBoard;
      
   	/**
   	constructor with players' names and scores, and reference to a chessboard
   	@param cb the chessboard being used
   	@param p1 player 1's name
   	@param p2 player 2's name
   	@param ws white's score
   	@param bs black's score
   	*/
      public TitleBar(ChessBoard cb, String p1, String p2, double ws, double bs)
      {
         myBoard = cb;
      	
      	//title chessmaster
         title = new JLabel("ChessMaster 1.0!");
         title.setFont(ChessRunner.hugeFont);
         title.setForeground(ChessRunner.ROYAL_BLUE);  
         tPanel = new JPanel();
         tPanel.add(title);
      	
      	//scores and names
         whiteScore = ws;
         blackScore = bs;
         whiteName = p1;
         blackName = p2;
         
      	//turn indicators
         whiteTurnIndicator = new JLabel("It's WHITE's turn!");
         whiteTurnIndicator.setFont(ChessRunner.visibleFont);
         whiteTurnIndicator.setForeground(ChessRunner.BROWN);
         blackTurnIndicator = new JLabel("It's BLACK's turn!");
         blackTurnIndicator.setFont(ChessRunner.invisibleFont);
         blackTurnIndicator.setForeground(ChessRunner.BROWN);
         indicatorPanel = new JPanel();
         indicatorPanel.add(whiteTurnIndicator);
         indicatorPanel.add(blackTurnIndicator);
      	
      	//name panels
         names = new JLabel(p1 + " : " + p2);
         names.setFont(ChessRunner.hugeFont);
         names.setForeground(Color.blue);
         JPanel namePanel = new JPanel();
         namePanel.add(names);
         
      	//score panels
         score = new JLabel(whiteScore + " : " + blackScore);
         score.setFont(ChessRunner.hugeFont);
         JPanel scorePanel = new JPanel();
         scorePanel.add(score);
         
      	//title + name panel
         JPanel northPanel = new JPanel();
         northPanel.setLayout(new BorderLayout());
         northPanel.add(tPanel, BorderLayout.NORTH);
         northPanel.add(namePanel, BorderLayout.SOUTH);
         
      	//mypanel
         myPanel = new JPanel();
         myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
         myPanel.add(northPanel);
         myPanel.add(indicatorPanel);
         myPanel.add(scorePanel);
      }
      
   	/**
   	gets the JPanel that contains the title bar
   	@return myPanel;
   	*/
      public JPanel getPanel()
      {
         return myPanel;
      }
     
     	/**
   	gets white's name
   	@return whiteName;
   	*/
      public String getWhiteName()
      {
         return whiteName; 
      }
      
   	/**
   	gets black's name
   	@return blackName;
   	*/
      public String getBlackName()
      {
         return blackName; 
      }
      
      public void update(double result)
      {
         whiteScore += result;
         blackScore += 1 - result;
         score.setText(whiteScore + " : " + blackScore);
      }
      
      public void switchTurn()
      {
         if(myBoard.getTurn() == ChessBoard.WHITE)
         {
            whiteTurnIndicator.setFont(ChessRunner.visibleFont);
            blackTurnIndicator.setFont(ChessRunner.invisibleFont);
         }
         else
         {
            whiteTurnIndicator.setFont(ChessRunner.invisibleFont);
            blackTurnIndicator.setFont(ChessRunner.visibleFont);
         }
      }
      
   	/**
   	displays the result of the game
   	*/
      public void getScore()
      {
         JOptionPane.showMessageDialog(null, "The score is: " + whiteScore + " : " + blackScore);
      }
      
   	/**
   	gets white's score
   	@return whiteScore;
   	*/
      public double getWhiteScore()
      {
         return whiteScore;
      }
   	
   	/**
   	gets black's score
   	@return blackScore;
   	*/
      public double getBlackScore()
      {
         return blackScore;
      }
      
   	/**
   	sets the visibility of the turn indicators
   	@param b true to be visible, false to be invisible
   	*/
      public void setTurnVisible(boolean b)
      {
         whiteTurnIndicator.setVisible(b);
         blackTurnIndicator.setVisible(b);
      }
   }