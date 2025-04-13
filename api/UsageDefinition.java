package api;

import java.util.LinkedHashMap;
import java.util.Map;

public final class UsageDefinition {
  private final String directiveName;
  private final LinkedHashMap<String, Argument> arguments;

  private UsageDefinition(String directiveName, LinkedHashMap<String, Argument> arguments) {
    this.directiveName = directiveName;
    this.arguments = arguments;
  }

  public String getDirectiveName() {
    return directiveName;
  }

  public Map<String, Argument> getArguments() {
    return arguments;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(directiveName);
    for (Map.Entry<String, Argument> entry : arguments.entrySet()) {
      Argument arg = entry.getValue();
      sb.append(' ');
      if (arg.optional == Optional.TRUE) {
        sb.append("[").append(entry.getKey()).append("]");
      } else {
        sb.append(":").append(entry.getKey());
      }
    }
    return sb.toString();
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public static final class Builder {
    private final String name;
    private final LinkedHashMap<String, Argument> arguments = new LinkedHashMap<>();

    public Builder(String name) {
      this.name = name;
    }

    public Builder define(String name) {
      return define(name, Optional.FALSE);
    }

    public Builder define(String name, Optional optional) {
      arguments.put(name, new Argument(name, optional));
      return this;
    }

    public UsageDefinition build() {
      return new UsageDefinition(name, arguments);
    }
  }

  public static final class Argument {
    private final String name;
    private final Optional optional;

    public Argument(String name, Optional optional) {
      this.name = name;
      this.optional = optional;
    }

    public String getName() {
      return name;
    }

    public Optional getOptional() {
      return optional;
    }
  }

  // Enum defined at bottom as requested
  public enum Optional {
    TRUE,
    FALSE
  }
}
