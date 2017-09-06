package util;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:default.properties" })
public interface DefaultConfig extends Config {

    @DefaultValue("secret")
    String secretWords();

    @DefaultValue(",")
    String separator();

    @DefaultValue("[a-z]+")
    String inputValidationRegex();
}