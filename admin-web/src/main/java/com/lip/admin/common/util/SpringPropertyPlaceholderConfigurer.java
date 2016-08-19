package com.lip.admin.common.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SpringPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  /**
   * 
   */
  public SpringPropertyPlaceholderConfigurer() {
    super();
  }

  public Properties getProperties() throws IOException {
    return this.mergeProperties();
  }

}