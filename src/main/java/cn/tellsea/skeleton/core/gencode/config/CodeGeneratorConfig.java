package cn.tellsea.skeleton.core.gencode.config;

import cn.tellsea.skeleton.core.gencode.consts.GenCodeConst;
import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成器配置
 *
 * @author Tellsea
 * @Description Created on 2019/7/13
 * 参考地址：https://blog.csdn.net/lwang_IT/article/details/89021755
 */
public class CodeGeneratorConfig {

    /**
     * 数据源配置
     */
    private static final String DATABASE_NAME = "skeleton";
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/mybatis-mapper-code?serverTimezone=UTC";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    /**
     * 模板位置
     */
    private static final String TEMPLATE_FILE_PATH = "src/main/resources/templates/generator";
    private static final String JAVA_PATH = "src/main/java";
    private static final String RESOURCES_PATH = "src/main/resources";
    /**
     * 生成的Service存放路径
     */
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(GenCodeConst.SERVICE_PACKAGE);
    /**
     * 生成的Service实现存放路径
     */
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(GenCodeConst.SERVICE_IMPL_PACKAGE);
    /**
     * 生成的Controller存放路径
     */
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(GenCodeConst.CONTROLLER_PACKAGE);
    /**
     * author
     */
    private static final String AUTHOR = "Tellsea";
    /**
     * date
     */
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());


    /**
     * 生成控制层
     *
     * @param tableName
     */
    public static void genController(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>(16);
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("baseRequestMapping", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", GenCodeConst.BASE_PACKAGE);
            data.put("baseBusinessPackage", GenCodeConst.BASE_BUSINESS_PACKAGE);
            data.put("baseSkeletonPackage", GenCodeConst.BASE_SKELETON_PACKAGE);
            data.put("basePackageController", GenCodeConst.CONTROLLER_PACKAGE);
            data.put("basePackageService", GenCodeConst.SERVICE_PACKAGE);
            data.put("basePackageModel", GenCodeConst.MODEL_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }
    }

    /**
     * 生成service接口
     *
     * @param tableName
     */
    public static void genService(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>(16);
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", GenCodeConst.BASE_BUSINESS_PACKAGE);
            data.put("baseBusinessPackage", GenCodeConst.BASE_BUSINESS_PACKAGE);
            data.put("baseSkeletonPackage", GenCodeConst.BASE_SKELETON_PACKAGE);
            data.put("basePackageService", GenCodeConst.SERVICE_PACKAGE);
            data.put("basePackageServiceImpl", GenCodeConst.SERVICE_IMPL_PACKAGE);
            data.put("basePackageModel", GenCodeConst.MODEL_PACKAGE);
            data.put("basePackageDao", GenCodeConst.MAPPER_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    /**
     * 生成Model和Mapper接口
     *
     * @param tableName
     */
    public static void genModelAndMapper(String tableName) {
        Context context = getContext();

        JDBCConnectionConfiguration jdbcConnectionConfiguration = getJdbcConnectionConfiguration();
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        context.addPluginConfiguration(getPluginConfiguration());
        context.addPluginConfiguration(getLombokPluginConfiguration());
        context.setJavaModelGeneratorConfiguration(getJavaModelGeneratorConfiguration());
        context.setSqlMapGeneratorConfiguration(getSqlMapGeneratorConfiguration());
        context.setJavaClientGeneratorConfiguration(getJavaClientGeneratorConfiguration());

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(null);
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        String modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    private static Context getContext() {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        return context;
    }

    private static JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        return jdbcConnectionConfiguration;
    }

    private static PluginConfiguration getPluginConfiguration() {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", GenCodeConst.MAPPER_INTERFACE_REFERENCE);
        // 强制生成 @Table 和 @Column
        pluginConfiguration.addProperty("forceAnnotation", "true");
        // 使用数据库注释，默认 true
        pluginConfiguration.addProperty("useMapperCommentGenerator", "true");
        return pluginConfiguration;
    }

    private static PluginConfiguration getLombokPluginConfiguration() {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("cn.tellsea.skeleton.core.gencode.plugin.LombokPlugin");
        return pluginConfiguration;
    }

    private static JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(GenCodeConst.MODEL_PACKAGE);
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        return javaModelGeneratorConfiguration;
    }

    private static SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        return sqlMapGeneratorConfiguration;
    }

    private static JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(GenCodeConst.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        return javaClientGeneratorConfiguration;
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * 查询数据库所有的表名称
     *
     * @return
     */
    public static List<String> getTableNameList() {
        List<String> list = new ArrayList<>();
        try {
            Class.forName(JDBC_DIVER_CLASS_NAME);
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            if (conn != null) {
                System.out.println("数据库连接成功");
            }
            StringBuffer sql = new StringBuffer();
            sql.append(" select table_name from information_schema.tables where table_schema = '");
            sql.append(DATABASE_NAME).append("' ");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql.toString());
            while (result.next()) {
                list.add(result.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("没有查询到表");
        }
        return list;
    }
}
