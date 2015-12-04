package org.brewman.web.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This configuration will trigger component scanning from this package and down
 * the hierarchy.
 * 
 * @author ddshipl
 */
@Configuration
@ComponentScan
@PropertySource("classpath:/config/default.properties")
public class SpringConfiguration {

}
