package cn.tellsea.skeleton.core.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基类实体
 *
 * @author Tellsea
 * @Description Created on 2019/7/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    /**
     * 为什么要 Base 层？
     *
     * 1、写一些实体类公共属性
     * 2、例如前端是 Bootstrap-table 作为数据展示列表，则有公共属性：offset，limit 等
     * 3、Layui 的表格也是有公共属性 page，limit 等
     */

    private int offset;

    private int limit;
}
