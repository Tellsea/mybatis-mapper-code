package cn.tellsea.skeleton.core.gencode.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Lombok 定制化插件
 *
 * @author Tellsea
 * @Description Created on 2019/7/13
 * 参考文章：https://blog.csdn.net/qq_41958793/article/details/89020093
 * 插件定制：https://www.kumapai.com/open/3076-mybatis-generator-lombok-plugin/1-0-0
 */
@SuppressWarnings("unused")
public class LombokPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 实体类的import
        topLevelClass.addImportedType("java.io.Serializable");
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addImportedType("lombok.AllArgsConstructor");

        // 实体类的注解
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@AllArgsConstructor");

        // 实体类的注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * 实体类");
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author Tellsea");
        topLevelClass.addJavaDocLine(" * @Description Created on " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        topLevelClass.addJavaDocLine(" */");

        // 接口
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // Mapper文件的注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * Mapper 接口");
        interfaze.addJavaDocLine(" * ");
        interfaze.addJavaDocLine(" * @author Tellsea");
        interfaze.addJavaDocLine(" * @Description Created on " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        interfaze.addJavaDocLine(" */");
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        return false;
    }
}
