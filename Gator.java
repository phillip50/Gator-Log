/**
 * Gator object to replicate entries in the gator database
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

package test;

public class Gator
{
    public int ID;
    public String tagNumber;
    public String eggNestLocation;
    public String eggCollectionDate;
    public String eggNumber;
    public String eggLength;
    public String eggWeight;
    public String hatchYear;
    public String gender;
    public String umbilical;
    public String date;
    public String from;
    public String to;
    public String bellySize;
    public String length;
    public String weight;
    public String recipe;
    public String code;
    public String vaccinate;
    public String comments;
    public String harvested;
    
    public Gator()
    {
        
    }
    
    @Override
    public String toString()
    {
        return "Entry Number: " + this.ID + ", Tag: " + this.tagNumber + ", Date: " + this.date;
    }
}
