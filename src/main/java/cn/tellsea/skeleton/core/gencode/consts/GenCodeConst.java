package cn.tellsea.skeleton.core.gencode.consts;

/**
 * 代码生成器常量
 *
 * @author Tellsea
 * @Description Created on 2019/7/13
 */
public class GenCodeConst {

    /**
     * 项目基础包
     */
    public static final String BASE_PACKAGE = "cn.tellsea.skeleton";

    /**
     * 业务基础包
     */
    public static final String BASE_BUSINESS_PACKAGE = BASE_PACKAGE + ".business";

    /**
     * 骨架基础包
     */
    public static final String BASE_SKELETON_PACKAGE = BASE_PACKAGE + ".core";

    /**
     * Model所在包
     */
    public static final String MODEL_PACKAGE = BASE_BUSINESS_PACKAGE + ".entity";

    /**
     * Mapper所在包
     */
    public static final String MAPPER_PACKAGE = BASE_BUSINESS_PACKAGE + ".mapper";

    /**
     * Service所在包
     */
    public static final String SERVICE_PACKAGE = BASE_BUSINESS_PACKAGE + ".service";

    /**
     * ServiceImpl所在包
     */
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";

    /**
     * Controller所在包
     */
    public static final String CONTROLLER_PACKAGE = BASE_BUSINESS_PACKAGE + ".controller";

    /**
     * Mapper插件基础接口的完全限定名
     */
    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.base.mapper.MyMapper";

}
