package test;


public class Gator
{
    public int ID;
    public int tagNumber;
    public String eggNestLocation;
    public String eggNestCondition;
    public String eggCollectionDate;
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
