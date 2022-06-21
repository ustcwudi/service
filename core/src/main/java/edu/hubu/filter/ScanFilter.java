package edu.hubu.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ScanFilter implements TypeFilter {

    private final Set<String> BeanSet = new HashSet<>();

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        String className = annotationMetadata.getClassName();
        if (className.endsWith("Controller") || className.endsWith("Service")) {
            String shortName = className.substring(className.lastIndexOf(".") + 1);
            if (BeanSet.contains(shortName)) {
                log.info("exclude " + className);
                return true;
            }
            BeanSet.add(shortName);
        }
        return false;
    }
}