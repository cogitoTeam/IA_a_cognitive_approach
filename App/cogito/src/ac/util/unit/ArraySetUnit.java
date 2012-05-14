package ac.util.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ac.util.ArraySet;


public class ArraySetUnit
{
  
  @Test 
  public void testAdd()
  {
    ArraySet<Integer> set = new ArraySet<Integer>();
    set.add(1);
    set.add(2);
    assertFalse(set.add(1));
    assertTrue(set.size() == 2);
    try
    {
      set.add(1, 1);
      assertTrue(false);
    }
    catch(UnsupportedOperationException e)
    {
    }
    
    ArrayList<Integer> list = new ArrayList<Integer>();
    list.add(5);
    list.add(5);
    
    set.addAll(list);
    assertTrue(set.size() == 3);    
  }

  
}
