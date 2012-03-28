/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;


public class FrontEnd 
{
    /* SINGLETON */
    
    private static FrontEnd instance = null;
    
    public static FrontEnd getInstance()
    {
        if(instance == null)
            instance = new FrontEnd();
        return instance;
    }
    
    private FrontEnd()
    {
    }

          
            
    /* ATTRIBUTES */
    
    private Actuator actuator = new Actuator();
    private Sensor sensor = new Sensor();
    
    
    
    /* METHODS */

    

}
