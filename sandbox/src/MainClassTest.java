import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass {

  @Test
  public void testGetLocalNumber() {
    System.out.println("Static number getLocalNumber = " + this.number);
    Assert.assertTrue("Метод getLocalNumber не возвращает число 14!", getLocalNumber() == 14);
  }

  @Test
  public void testGetClassNumber() {
    if (getClassNumber() > 45) {
      System.out.println("Private number " + getClassNumber() + " > 45!");
    } else if (getClassNumber() == 45) {
      System.out.println("Private number " + getClassNumber() + " = 45!");
    } else if (getClassNumber() < 45) {
      System.out.println("Private number " + getClassNumber() + " < 45!");
    } else {
      Assert.fail("Not correct private number!");
    }
  }

}