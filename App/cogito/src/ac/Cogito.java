/*****************
 * @author william
 * @date 31-Mar-2012
 *****************/

package ac;

public class Cogito
{

  static final boolean BOOSTRAP = false;
  static final boolean THINK = false;

  /* MAIN */
  public static void main(String[] args)
  {

    // create agent(s)
    AC agent = new AC();

    if (BOOSTRAP)
      {
        agent.bootstrap();
        System.exit(0);
      }

    if (THINK)
      {
        agent.think();
        System.exit(0);
      }

    // main loop
    agent.run();

    System.out.println("Stopped");
  }
}
