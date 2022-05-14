package edu.hubu.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hubu.generator.define.Model;
import edu.hubu.generator.method.CamelCaseMethod;
import edu.hubu.generator.method.HyphenCaseMethod;
import edu.hubu.generator.method.JavaTypeMethod;
import edu.hubu.generator.method.MethodTypeMethod;
import edu.hubu.generator.method.QueryTypeMethod;
import edu.hubu.generator.method.ScriptTypeMethod;
import edu.hubu.generator.method.UnderScoreCaseMethod;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generator {

    private static Map<String, Model> getModelMap(String module) {
        // make sure directories exists
        new File("../core/src/main/java/edu/hubu/auto").delete();
        new File("../core/src/main/java/edu/hubu/auto").mkdir();
        new File("../core/src/main/java/edu/hubu/auto/model").mkdir();
        new File("../core/src/main/java/edu/hubu/auto/dao").mkdir();
        new File("../core/src/main/java/edu/hubu/auto/controller").mkdir();
        new File("../core/src/main/java/edu/hubu/auto/request").mkdir();
        new File("../core/src/main/java/edu/hubu/auto/request/query").mkdir();
        new File("../core/src/main/java/edu/hubu/auto/request/builder").mkdir();
        // get models
        Map<String, Model> map = new HashMap<>();
        if (!module.equals("core")) {
            map = getModelMap("core");
        }
        try {
            File path = new File("../" + module + "/define/");
            if (path.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                File[] fileArray = path.listFiles();
                assert fileArray != null;
                for (File file : fileArray) {
                    if (file.getName().endsWith(".json")) {
                        String fileName = file.getName();
                        String modelName = fileName.substring(0, fileName.length() - 5);
                        Model model = mapper.readValue(file, Model.class);
                        model.setName(modelName);
                        if (map.containsKey(modelName)) {
                            map.get(modelName).getFields().addAll(model.getFields());
                        } else {
                            map.put(modelName, model);
                            String folderName = new UnderScoreCaseMethod().exec(modelName);
                            new File("../web/src/pages/main/base" + folderName + "/index.d.ts").mkdir();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * usage: gradle :generator:bootRun --args='MODULE_NAME'
     */
    public static void main(String[] args) {
        String module = args[0];
        try {
            Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            configuration.setDirectoryForTemplateLoading(new File("templates/"));
            Map<String, Object> map = new HashMap<>();
            map.put("module", module);
            map.put("c", new CamelCaseMethod());
            map.put("u", new UnderScoreCaseMethod());
            map.put("h", new HyphenCaseMethod());
            map.put("jt", new JavaTypeMethod());
            map.put("st", new ScriptTypeMethod());
            map.put("mt", new MethodTypeMethod());
            map.put("qt", new QueryTypeMethod());
            Map<String, Model> modelMap = getModelMap(module);
            modelMap.forEach((modelName, model) -> {
                try {
                    map.put("model", model);
                    System.out.println("generate model " + modelName);
                    String javaDir = "../core/src/main/java/edu/hubu/auto/";
                    Template template = configuration.getTemplate("java/model.ftl", "UTF-8");
                    File docFile = new File(javaDir + "model/" + modelName + ".java");
                    Writer out = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(docFile), StandardCharsets.UTF_8));
                    template.process(map, out);
                    template = configuration.getTemplate("java/dao.ftl", "UTF-8");
                    docFile = new File(javaDir + "dao/" + modelName + "MongoDao.java");
                    out = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(docFile), StandardCharsets.UTF_8));
                    template.process(map, out);
                    template = configuration.getTemplate("java/controller.ftl", "UTF-8");
                    docFile = new File(javaDir + "controller/" + modelName + "Controller.java");
                    out = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(docFile), StandardCharsets.UTF_8));
                    template.process(map, out);
                    template = configuration.getTemplate("java/request_query.ftl", "UTF-8");
                    docFile = new File(javaDir + "request/query/" + modelName + "Query.java");
                    out = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(docFile), StandardCharsets.UTF_8));
                    template.process(map, out);
                    template = configuration.getTemplate("java/request_builder.ftl", "UTF-8");
                    docFile = new File(javaDir + "request/builder/" + modelName + "Builder.java");
                    out = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(docFile), StandardCharsets.UTF_8));
                    template.process(map, out);
                    String jsDir = "../web/src/pages/admin/" + new UnderScoreCaseMethod().exec(modelName);
                    new File(jsDir).mkdir();
                    template = configuration.getTemplate("js/index.ftl", "UTF-8");
                    docFile = new File(jsDir + "/index.d.ts");
                    out = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(docFile), StandardCharsets.UTF_8));
                    template.process(map, out);
                    template = configuration.getTemplate("js/table.ftl", "UTF-8");
                    docFile = new File(jsDir + "/table.tsx");
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile),
                            StandardCharsets.UTF_8));
                    template.process(map, out);
                    template = configuration.getTemplate("js/columns.ftl", "UTF-8");
                    docFile = new File(jsDir + "/columns.tsx");
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile),
                            StandardCharsets.UTF_8));
                    template.process(map, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
