/**
  A class that represents coordinations used in a chessboard.
  @author Dyland Xue
  @version 2011-05-20
  */

public class Coord
{
    private int row;
    private int col;

    /**
      default constructor
      @param r row
      @param c column
      */
    public Coord(int r, int c)
    {
        row = r;
        col = c;
    }

    /**
      gets the row of this coord
      @return row
      */
    public int getRow()
    {
        return row;
    }

    /**
      gets the col of this coord
      @return col
      */
    public int getCol()
    {
        return col;
    }

    /**
      checks if two coords are the same
      @return true if rows and cols are the same, otherwise false
      */
    public boolean equals(Coord coo)
    {
        return row == coo.getRow() && col == coo.getCol();
    }

    /**
      overrides toString method
      @return a string that represents the coord in standard notation
      */
    public String toString()
    {
        String letter;
        int r = 9 - row;

        if(col == 1)
        {
            letter = "a";
        }
        else if(col == 2)
        {
            letter = "b";
        }
        else if(col == 3)
        {
            letter = "c";
        }
        else if(col == 4)
        {
            letter = "d";
        }
        else if(col == 5)
        {
            letter = "e";
        }
        else if(col == 6)
        {
            letter = "f";
        }
        else if(col == 7)
        {
            letter = "g";
        }
        else// if(col == 8)
        {
            letter = "h";
        }

        return letter + r;
    }
}
