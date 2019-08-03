package ${basePackageController};

import ${basePackage}.core.base.controller.BaseController;
import ${baseBusinessPackage}.entity.${modelNameUpperCamel};
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ${modelNameUpperCamel} 控制层
 *
 * @author ${author}
 * @Description Created on ${date}
 */
@Controller
@RequestMapping("/${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller extends BaseController<${modelNameUpperCamel}> {

}
