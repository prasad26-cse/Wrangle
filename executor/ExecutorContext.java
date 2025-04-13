package executor;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple context class for use in directives.
 */
public class ExecutorContext {
  private String contextName;
  private Map<String, String> properties = new HashMap<>();

  public ExecutorContext() {
  }

  public ExecutorContext(String contextName, Map<String, String> properties) {
    this.contextName = contextName;
    this.properties = properties;
  }

  public String getContextName() {
    return contextName;
  }

  public void setContextName(String contextName) {
    this.contextName = contextName;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public String getProperty(String key) {
    return properties.getOrDefault(key, null);
  }

  public void addProperty(String key, String value) {
    properties.put(key, value);
  }
}
