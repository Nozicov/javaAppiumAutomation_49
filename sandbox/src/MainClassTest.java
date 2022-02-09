import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass {

  @Test
  public void testNumber() {
    Assert.assertTrue("Метод getLocalNumber не возвращает число 14!", getLocalNumber() == 14);
  }

}