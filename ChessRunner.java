/**
  The class that runs the program.
  @author Dyland Xue
  @version 2011-05-25
  */

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;  

import sun.audio.*;
import javazoom.jl.player.Player;
//javazoom is imported from an external jar file named "jl1.0.jar"
//references: http://www.javazoom.net/javalayer/sources.html
//documentation: http://www.javazoom.net/javalayer/docs/docs1.0/index.html	

public class ChessRunner
{
    public static final int APP_WIDTH = 1024;
    public static final int APP_HEIGHT = 1024;
    public static final int ROW = 9;
    public static final int COL = 9;
    public static final Font invisibleFont = new Font("InvisibleFont", Font.PLAIN, 0);
    public static final Font visibleFont = new Font("Helvetica", Font.BOLD, 20);
    public static final Font hugeFont = new Font("Helvetica", Font.BOLD, 40);
    public static final Color ROYAL_BLUE = new Color(0, 34, 102);
    public static final Color BROWN = new Color(166, 42, 42);

    private Player winWhite, winBlack, check, checkmate;

    //private XMQ myXMQ;
    private JFrame myFrame;
    private JPanel buttonPanel, notationPanel, gameOptionPanel, appPanel, export;
    private JButton myExport, myReset, resignWhite, resignBlack, drawOffer, help;
    private JButton[][] myButtons;
    private boolean myFreddie;  

    private ChessBoard myBoard;
    private TitleBar myTitle;
    private GameObserver myObserver;

    /**
      default constructor
      @param a player 1's name
      @param b palyer 2's name
      @param whiteScore player 1's score
      @param blackScore player 2's score
      @param freddie a queen fan option; equals true if player is a queen fan, otherwise false
      */
    public ChessRunner
        (String a, String b, double whiteScore, double blackScore, boolean freddie)
        {
            myFreddie = freddie;
            //initializing the audio files
            try{
                check = new Player(new BufferedInputStream
                        (new FileInputStream("check.mp3")));
                checkmate = new Player(new BufferedInputStream
                        (new FileInputStream("checkmate.mp3")));
                winWhite = new Player(new BufferedInputStream
                        (new FileInputStream("white_win.mp3")));
                winBlack = new Player(new BufferedInputStream
                        (new FileInputStream("black_win.mp3")));
            } 
            catch (Exception e)
            {
                System.out.println("Audio file does not exist!");
            }

            //initializing the chessboard
            myBoard = new ChessBoard(myFreddie);
            myButtons = new JButton[9][9];

            for(int r = 1; r < ROW; r++)
            {
                for(int c = 1; c < COL; c++)
                {
                    ImageIcon ico;
                    if(myBoard.get(new Coord(r, c)) != null)
                    {
                        ico = myBoard.get(new Coord(r, c)).getIcon();
                        myButtons[r][c] = new JButton("" + r + c,ico);
                    }
                    else
                    {
                        myButtons[r][c] = new JButton("" + r + c);
                    }

                    myButtons[r][c].addActionListener(new BoardListener());
                    myButtons[r][c].setPreferredSize(new Dimension(90, 90));
                    myButtons[r][c].setFont(invisibleFont);

                    if((r + c) % 2 == 1)
                    {
                        myButtons[r][c].setOpaque(true);
                    }
                }
            }

            buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(8,8));
            for(int r = 1; r < ROW; r++)
            {
                for(int c = 1; c < COL; c++)
                {
                    buttonPanel.add(myButtons[r][c]);
                }
            }

            //initializing game observer
            myObserver = new GameObserver(myBoard);

            //initializing AI
            //myXMQ = new XMQ(ChessBoard.BLACK, XMQ.EASY, myBoard);

            //initializing the title
            myTitle = new TitleBar(myBoard, a, b, whiteScore, blackScore);

            //gameOptionPanel
            resignWhite = new JButton("White Resigns");
            resignWhite.setPreferredSize(new Dimension(180, 30));
            resignWhite.setFont(visibleFont);
            resignWhite.addActionListener(new gameOptionListener());

            drawOffer = new JButton("Offer a draw");
            drawOffer.setPreferredSize(new Dimension(180, 30));
            drawOffer.setFont(visibleFont);
            drawOffer.addActionListener(new gameOptionListener());

            resignBlack = new JButton("Black Resigns");
            resignBlack.setPreferredSize(new Dimension(180, 30));
            resignBlack.setFont(visibleFont);
            resignBlack.addActionListener(new gameOptionListener());

            help = new JButton("Help");
            help.setPreferredSize(new Dimension(180, 30));
            help.setFont(visibleFont);
            help.addActionListener(new helpListener());

            gameOptionPanel = new JPanel();
            gameOptionPanel.add(help);
            gameOptionPanel.add(resignWhite);
            gameOptionPanel.add(drawOffer);
            gameOptionPanel.add(resignBlack);

            //notationPanel
            JLabel myNotes = new JLabel("Notation");
            myNotes.setFont(hugeFont);
            myNotes.setForeground(BROWN);
            JPanel notes = new JPanel();
            notes.add(myNotes);
            myExport = new JButton("export");
            myExport.setFont(hugeFont);
            myExport.addActionListener(new ExportListener());
            export = new JPanel();
            export.add(myExport);
            myExport.setEnabled(false);

            notationPanel = new JPanel();
            notationPanel.setLayout(new BoxLayout(notationPanel, BoxLayout.Y_AXIS));
            notationPanel.add(notes);
            notationPanel.add(myObserver.getPanel());
            notationPanel.add(export);

            //reset Button
            myReset = new JButton("Reset");
            myReset.setFont(hugeFont);
            myReset.setForeground(BROWN);
            myReset.addActionListener(new ResetListener());

            //appPanel
            appPanel = new JPanel();
            appPanel.setLayout(new BorderLayout());
            appPanel.add(myTitle.getPanel(), BorderLayout.NORTH);
            appPanel.add(buttonPanel, BorderLayout.CENTER);
            appPanel.add(notationPanel, BorderLayout.EAST);
            appPanel.add(gameOptionPanel, BorderLayout.SOUTH);
            appPanel.add(myReset, BorderLayout.WEST);

            //initializing the frame
            myFrame = new JFrame("2D Chess Game!");
            myFrame.setSize(APP_WIDTH, APP_HEIGHT);
            myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myFrame.getContentPane().add(appPanel); 
        }

    /**
      displays the GUI
      */
    public void display()
    {
        myFrame.pack();
        myFrame.setVisible(true);
    }

    /**
      resets the board, game option panel, and notation panel, 
      while keeping track of the scores
      */
    public void refresh()
    {
        if(myBoard.getJudge().gamePlaying == false)
        {
            myExport.setEnabled(true);
            resignWhite.setEnabled(false);
            resignBlack.setEnabled(false);
            drawOffer.setEnabled(false);
            myTitle.setTurnVisible(false);
            myTitle.getScore();
        }
        else
        {
            myExport.setEnabled(false);
            resignWhite.setEnabled(true);
            resignBlack.setEnabled(true);
            drawOffer.setEnabled(true);
            myTitle.setTurnVisible(true);
        }
    }

    public static void main(String[] args)
    {
        JOptionPane.showMessageDialog(null, "Welcome to 2D chess game!");
        String n1 = "";
        String n2 = "";
        while(n1 == null || n1.equals(""))
        {
            n1 = JOptionPane.showInputDialog(null, "Player 1, what is your name?");
        }
        while(n2 == null || n2.equals(""))
        {
            n2 = JOptionPane.showInputDialog(null, "Player 2, what is your name?");
        }

        int freddie = JOptionPane.showConfirmDialog(null, "BTW, are you a Queen fan?");
        boolean fr = false;
        if(freddie == JOptionPane.YES_OPTION)
        {
            fr = true;
        }

        ChessRunner myGame = new ChessRunner(n1, n2, 0, 0, fr);
        myGame.display();

        JOptionPane.showMessageDialog(null, "Enjoy!");
    }

    private class BoardListener implements ActionListener
    {
        /**
          handles a button event
          */
        public void actionPerformed(ActionEvent e)
        {
            if(myBoard.getJudge().gamePlaying == true)
            {
                int row, col;
                String myHit = e.getActionCommand();   
                row = Integer.parseInt(myHit) / 10;
                col = Integer.parseInt(myHit) % 10;
                Coord chosenCoord = new Coord(row, col);

                //destination chosen
                if(myBoard.isChosen())
                {
                    Coord start = myBoard.getStartingCoord();
                    myButtons[start.getRow()][start.getCol()].setBackground(Color.white);

                    if(chosenCoord != myBoard.getStartingCoord())
                    {
                        String result = "";

                        myBoard.setDestination(chosenCoord);
                        Coord des = chosenCoord;
                        Piece curr = myBoard.get(start);

                        if(curr.moveIsValid(start, des))
                        {
                            myButtons[row][col].setIcon
                                (myButtons[start.getRow()][start.getCol()].getIcon());
                            myButtons[start.getRow()][start.getCol()].setIcon(null);
                            myBoard.set(des, myBoard.get(start));
                            myBoard.set(start, null);

                            if(myBoard.getJudge().special_castleKingside == true)
                            {
                                if(curr.getColor() == ChessBoard.WHITE)
                                {
                                    myButtons[8][6].setIcon
                                        (myButtons[8][8].getIcon());
                                    myButtons[8][8].setIcon(null);
                                    myBoard.set(new Coord(8, 6), myBoard.get(new Coord(8, 8)));
                                    myBoard.set(new Coord(8, 8), null);
                                }
                                else
                                {
                                    myButtons[1][6].setIcon
                                        (myButtons[1][8].getIcon());
                                    myButtons[1][8].setIcon(null);
                                    myBoard.set(new Coord(1, 6), myBoard.get(new Coord(1, 8)));
                                    myBoard.set(new Coord(1, 8), null);
                                }
                            }
                            else if(myBoard.getJudge().special_castleQueenside == true)
                            {
                                if(curr.getColor() == ChessBoard.WHITE)
                                {
                                    myButtons[8][4].setIcon
                                        (myButtons[8][1].getIcon());
                                    myButtons[8][1].setIcon(null);
                                    myBoard.set(new Coord(8, 4), myBoard.get(new Coord(8, 1)));
                                    myBoard.set(new Coord(8, 1), null);
                                }
                                else
                                {
                                    myButtons[1][4].setIcon
                                        (myButtons[1][1].getIcon());
                                    myButtons[1][1].setIcon(null);
                                    myBoard.set(new Coord(1, 4), myBoard.get(new Coord(1, 1)));
                                    myBoard.set(new Coord(1, 1), null);
                                }
                            }
                            else if(myBoard.getJudge().special_promotion == true)
                            {
                                Piece[] opts = 
                                {new Queen(curr.getColor(), myBoard, myFreddie), 
                                    new Rook(curr.getColor(), myBoard),
                                    new Bishop(curr.getColor(), myBoard), 
                                    new Knight(curr.getColor(), myBoard)};
                                String[] options = {"Queen", "Rook", "Bishop", "Knight"};
                                int choice = JOptionPane.showOptionDialog(null, "Promote to:",
                                        null, JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE, null, options, "Queen");

                                myBoard.set(des, opts[choice]);
                                myButtons[row][col].setIcon
                                    (opts[choice].getIcon());
                            }

                            if(myBoard.getJudge().isCheck())
                            {
                                if(myBoard.getJudge().isCheckmate())
                                {
                                    try
                                    {
                                        checkmate.play();
                                    } 
                                    catch(Exception ioe)
                                    {
                                        System.out.println(ioe);
                                    }
                                    try
                                    {
                                        checkmate = new Player(new BufferedInputStream
                                                (new FileInputStream("checkmate.mp3")));
                                    } 
                                    catch(Exception ioe)
                                    {
                                        System.out.println(ioe);
                                    }

                                    JOptionPane.showMessageDialog(null, "CHECKMATE!");
                                    if(myBoard.getTurn() == ChessBoard.WHITE)
                                    {
                                        try
                                        {
                                            winWhite.play();
                                        } 
                                        catch(Exception ioe)
                                        {
                                            System.out.println(ioe);
                                        }

                                        try
                                        {
                                            winWhite = new Player(new BufferedInputStream
                                                    (new FileInputStream("white_win.mp3")));
                                        } 
                                        catch(Exception ioe)
                                        {
                                            System.out.println(ioe);
                                        }

                                        JOptionPane.showMessageDialog(null, "Good Game! White wins.");
                                        myTitle.update(1);
                                        result = "1 - 0";
                                    }
                                    else
                                    {
                                        try
                                        {
                                            winBlack.play();
                                        } 
                                        catch(Exception ioe)
                                        {
                                            System.out.println(ioe);
                                        }

                                        try
                                        {
                                            winBlack = new Player(new BufferedInputStream
                                                    (new FileInputStream("black_win.mp3")));
                                        } 
                                        catch(Exception ioe)
                                        {
                                            System.out.println(ioe);
                                        }

                                        JOptionPane.showMessageDialog(null, "Good Game! Black wins.");
                                        myTitle.update(0);
                                        result = "0 - 1";
                                    }

                                    myBoard.getJudge().gamePlaying = false;
                                }
                                else //it is a check
                                {
                                    //play check sound
                                    try
                                    {
                                        check.play();
                                    } 
                                    catch(Exception xxe)
                                    {
                                        System.out.println("check does not exist.");
                                    }
                                    try{
                                        check = new Player(new BufferedInputStream
                                                (new FileInputStream("check.mp3")));
                                    } 
                                    catch (Exception ioe)
                                    {
                                        System.out.println("Audio file does not exist!");
                                    }

                                    JOptionPane.showMessageDialog(null, "CHECK!");
                                }
                            }

                            myObserver.takeNotes
                                (myBoard.getTurn(), curr, start, des);

                            if(!myBoard.getJudge().gamePlaying)
                            {
                                myObserver.setResult(result);
                            }

                            myBoard.getJudge().reset();
                            myBoard.next();
                            myTitle.switchTurn();
                        }
                    }

                    myBoard.setChosen(false);
                }
                else//the piece to move is chosen
                {
                    if(myBoard.get(chosenCoord) != null && 
                            myBoard.get(chosenCoord).getColor() == myBoard.getTurn())
                    {
                        myBoard.setStartingCoord(chosenCoord);
                        myBoard.setChosen(true);

                        myButtons[row][col].setBackground(Color.red);
                        myButtons[row][col].setOpaque(true);
                    }
                }

                refresh();
            }

        }//action ends
    }

    private class gameOptionListener implements ActionListener
    {
        /**
          handles a gameOption event 
          */
        public void actionPerformed(ActionEvent e)
        {
            if(myBoard.getJudge().gamePlaying == true)
            {
                String message = e.getActionCommand();

                int confirm = JOptionPane.showConfirmDialog
                    (null, message.toLowerCase() + "?");

                if(message.equals("White Resigns"))
                {   
                    if(confirm == JOptionPane.YES_OPTION)
                    {
                        myBoard.getJudge().gamePlaying = false;
                        JOptionPane.showMessageDialog(null, "White resigns. Good game!");
                        myTitle.update(0);
                        try
                        {
                            winBlack.play();
                        } 
                        catch(Exception ioe)
                        {
                            System.out.println(ioe);
                        }      	
                        try
                        {
                            winBlack = new Player(new BufferedInputStream
                                    (new FileInputStream("black_win.mp3")));
                        } 
                        catch(Exception ioe)
                        {
                            System.out.println(ioe);
                        }

                        myObserver.setResult("0 - 1");
                    }
                }
                else if(message.equals("Black Resigns"))
                {   
                    if(confirm == JOptionPane.YES_OPTION)
                    {
                        myBoard.getJudge().gamePlaying = false;
                        JOptionPane.showMessageDialog(null, "Black resigns. Good game!");
                        myTitle.update(1);
                        try
                        {
                            winWhite.play();
                        } 
                        catch(Exception ioe)
                        {
                            System.out.println(ioe);
                        }   	
                        try
                        {
                            winWhite = new Player(new BufferedInputStream
                                    (new FileInputStream("white_win.mp3")));
                        } 
                        catch(Exception ioe)
                        {
                            System.out.println(ioe);
                        }

                        myObserver.setResult("1 - 0");
                    }
                }
                else if(message.equals("Offer a draw"))
                {
                    if(confirm == JOptionPane.YES_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Draw offered.");
                        if(JOptionPane.showConfirmDialog
                                (null, "Do you accept the draw offer?") == JOptionPane.YES_OPTION)
                        {
                            JOptionPane.showMessageDialog(null, "Draw accepted. Good game.");
                            myTitle.update(0.5);   
                            myObserver.setResult("1/2 - 1/2");
                            myBoard.getJudge().gamePlaying = false;
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Draw declined. Game resumes.");
                        }
                    }
                }

                refresh();
            }
        }
    }

    private class ExportListener implements ActionListener
    {
        /**
          handles an export request
          */
        public void actionPerformed(ActionEvent e)
        {
            ArrayList<String> myLines = myObserver.getText();
            PrintWriter pr = null;  

            try
            {
                FileWriter input = new FileWriter
                    (myTitle.getWhiteName() + "-" +  myTitle.getBlackName() + ".txt");
                pr = new PrintWriter(input);
            }
            catch(IOException ioe)
            {
                System.out.println("Error creating file!");
                System.exit(0);     		
            }

            for(String s : myLines)
            {
                pr.print(s);
            }

            pr.close();

            JOptionPane.showMessageDialog(null, "Notation exported.");
        }
    }

    private class ResetListener implements ActionListener
    {
        /**
          handles a reset request
          */
        public void actionPerformed(ActionEvent e)
        {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure to reset the board?");
            if(choice == JOptionPane.YES_OPTION)
            {
                ChessRunner myGame = new ChessRunner
                    (myTitle.getWhiteName(), myTitle.getBlackName(), myTitle.getWhiteScore(), myTitle.getBlackScore(), myFreddie);
                myGame.display();
            }
        }
    }

    private class helpListener implements ActionListener
    {
        /**
          displays help document
          */
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(null, "Go to http://en.wikipedia.org/wiki/Chess for rules!");
        }
    }
}
