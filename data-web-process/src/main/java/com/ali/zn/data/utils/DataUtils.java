package com.ali.zn.data.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class DataUtils {

    @Bean
    public SourceConf getSource() {
        return new SourceConf();
    }



}
