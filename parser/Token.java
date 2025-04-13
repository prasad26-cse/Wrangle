package parser;

public class Token {
  private final String value;

  public Token(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
