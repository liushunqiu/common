package com.liushunqiu.redisson.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.redisson")
public class RedissonProperties {
    private ConfigFile configFile = new ConfigFile();

    public class ConfigFile {

        /** json格式配置文件*/
        private String json;

        /** yaml格式配置文件*/
        private String yaml;

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String getYaml() {
            return yaml;
        }

        public void setYaml(String yaml) {
            this.yaml = yaml;
        }
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }

}
