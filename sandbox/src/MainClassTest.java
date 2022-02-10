import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass {

  @Test
  public void testGetLocalNumber() {
    System.out.println("Static number getLocalNumber = " + this.number);
    Assert.assertEquals("Метод getLocalNumber не возвращает число 14!", 14, getLocalNumber());
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

  @Test
  public void testGetClassString() {
    if (getClassString().contains("hello")) {
      System.out.println(getClassString() + " contains 'hello'");
    } else if (getClassString().contains("Hello")) {
      System.out.println(getClassString() + " contains 'Hello'");
    } else {
      Assert.fail("String '" + getClassString() + "' does not contain 'hello' or 'Hello'");
    }
  }

}