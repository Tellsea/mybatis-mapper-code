package cn.tellsea.skeleton.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类
 * 
 * @author Tellsea
 * @Description Created on 2019/08/03
 */
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 用户ID
     */
    @Column(name = "id")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 盐值
     */
    @Column(name = "salt")
    private String salt;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 手机
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 性别 1男 2女
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 状态 0 锁定 1有效
     */
    @Column(name = "status")
    private Boolean status;
}