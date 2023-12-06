package edu.hubu.assist;

import edu.hubu.base.dao.MongoDao;
import edu.hubu.filter.ScanFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.io.File;
import java.io.FileInputStream;

@Slf4j
@SpringBootApplication
@ComponentScan(value = "edu.hubu", excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, value = {
        ScanFilter.class }))
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void run(String... args) throws Exception {
        File rootPath = new File("assist/data");
        log.info("init data from " + rootPath.getCanonicalPath());
        if (rootPath.exists()) {
            File[] files = rootPath.listFiles();
            assert files != null;
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".csv")) {
                    String modelName = fileName.substring(0, fileName.length() - 4);
                    @SuppressWarnings("rawtypes")
                    var dao = (MongoDao) appContext.getBean(modelName + "MongoDao");
                    var collection = dao.input(new FileInputStream(file));
                    if (collection.size() > 0)
                        log.info("import " + collection.size() + " " + modelName);
                }
            }
        }
    }
}
