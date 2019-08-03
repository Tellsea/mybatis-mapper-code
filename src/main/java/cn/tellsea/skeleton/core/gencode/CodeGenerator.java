package cn.tellsea.skeleton.core.gencode;

import cn.tellsea.skeleton.core.gencode.config.CodeGeneratorConfig;

import java.util.Scanner;

/**
 * 代码生成工具
 *
 * @author Tellsea
 * @Description Created on 2019/7/13
 */
public class CodeGenerator {

    public static void main(String[] args) {
        System.out.println("1、单表代码生成\t 2、多表代码生成\t 3、所有表代码生成");
        System.out.println("请输入生成模式对应的序号：");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch (option) {
            case 1: {
                System.out.println("请输入表名称：");
                Scanner scanner1 = new Scanner(System.in);
                genCodeByOne(scanner1.next());
                break;
            }
            case 2: {
                System.out.println("请输入表名称，使用英文的逗号隔开");
                Scanner scanner2 = new Scanner(System.in);
                String[] name = scanner2.next().split(",");
                //genCodeByOne(name);
                System.out.println(name);
                break;
            }
            case 3: {
                genCodeByAll();
                break;
            }
            default: {
                System.out.println("输入格式错误");
            }
        }
    }

    /**
     * 根据单个表名称生成
     *
     * @param tableName
     */
    public static void genCodeByOne(String tableName) {
        CodeGeneratorConfig.genModelAndMapper(tableName);
        CodeGeneratorConfig.genService(tableName);
        CodeGeneratorConfig.genController(tableName);
    }

    /**
     * 根据多个表名称生成
     *
     * @param tableNames
     */
    public static void genCodeByMore(String... tableNames) {
        for (String tableName : tableNames) {
            genCodeByOne(tableName);
        }
    }

    /**
     * 根据数据库所有表生成
     */
    public static void genCodeByAll() {
        CodeGeneratorConfig.getTableNameList().forEach(name -> genCodeByOne(name));
    }
}
