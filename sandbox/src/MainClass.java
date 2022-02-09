public class MainClass {

  public int number = 14;

  public int getLocalNumber() {
    System.out.println("Static number getLocalNumber = " + this.number);
    return this.number;
  }

}