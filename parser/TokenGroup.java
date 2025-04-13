package parser;

import java.util.HashMap;
import java.util.Map;

public class TokenGroup {
  private final Map<String, Token> tokens = new HashMap<>();

  public void add(String name, Token token) {
    tokens.put(name, token);
  }

  public Token get(String name) {
    return tokens.get(name);
  }
}
