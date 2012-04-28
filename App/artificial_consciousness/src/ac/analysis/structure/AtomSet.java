/**
* Copyright (C) 2011 Thibaut Marmin
* Copyright (c) 2011 Clément Sipieter
*
* This file is part of N2P.
*
* N2P is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License.
*
* N2P is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with N2P. If not, see <http://www.gnu.org/licenses/>.
*/

package ac.analysis.structure;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Extends functionality to an ArrayList of atoms
 *
 */
@SuppressWarnings("serial")
public class AtomSet extends ArrayList<Atom>
{
  
  // ************************************************************************
  // METHODS
  // ************************************************************************
  
  /**
   * Constructor
   */
  public AtomSet()
  {
    super();
  }
  
  /**
   * Constructor
   * @param initialCapacity
   */
  public AtomSet(int initialCapacity)
  {
    super(initialCapacity);
  }
  
  /**
* Constructor
*
* @param string
* in text form :
* "atom1;atom2;...atomk"
*/
  public AtomSet(String string)
  {
    super();

    StringTokenizer st = new StringTokenizer(string, ";");
    while (st.hasMoreTokens())
    {
      String s = st.nextToken(); 
      this.add(new Atom(s));
    }
  }
  
 
 
}