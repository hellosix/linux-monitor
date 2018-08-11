package com.lzz;

import com.lzz.util.LinuxMonitorUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by gl49 on 2017/11/7.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LinuxMonitorUtil.start();
    }
}
