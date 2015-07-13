/**
 * Compares 2 input Gator objects according to their tag number first
 * If they are equal, compares the ID number next
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

package test;

import java.util.*;

public class GatorComparator implements Comparator<Gator>
{
    @Override
    public int compare(Gator gator1, Gator gator2)
    {
        int ID1 = gator1.ID;
        int ID2 = gator2.ID;
        
        int tag1 = gator1.tagNumber;
        int tag2 = gator2.tagNumber;
        
        int tagCompare = tag1 - tag2;
        if(tagCompare == 0)
        {
            return ID1 - ID2;
        }
        return tagCompare;
    }
}
