package cn.tellsea.skeleton.business.controller;

import cn.tellsea.skeleton.core.base.controller.BaseController;
import cn.tellsea.skeleton.business.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User 控制层
 *
 * @author Tellsea
 * @Description Created on 2019/08/03
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User> {

}
