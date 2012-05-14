package ac.util;

import java.util.LinkedList;
import java.util.Collection;
import java.util.Set;

public class LinkedSet<E> extends LinkedList<E> implements Set<E>
{

  @Override
  public boolean add(E e)
  {
    if(this.contains(e))
      return false;
    
    return super.add(e);
  }

  @Override
  public void add(int index, E element)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends E> c)
  {
    //@FIXME test inutile ??
    boolean isChanged = false;

    for(E e: c)
    {
      if(!this.contains(e))
      {
        this.add(e);
        isChanged = true;
      }
    }
    
    return isChanged;
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c)
  {
    throw new UnsupportedOperationException();
  }
  
  
  
  

}
