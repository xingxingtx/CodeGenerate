<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basePackage}.mapper.${bigClassName}Mapper">
    <resultMap id="${smallClassName}ResultMap" type="${basePackage}.model.${bigClassName}">
        <id column="id" property="id" jdbcType="BIGINT" />
    <#list genericFieldList as genericField>
        <result column="${genericField}" property="${genericField}" />
    </#list>
    <#list association as item>
        <association property="${item.property}" columnPrefix="${item.columnPrefix}"
                     javaType="${item.javaType}">
            <!-- 对象的关联 -->
            <id property="id" column="id"></id>
        </association>
    </#list>

    </resultMap>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
    ${insert}
    </insert>

    <delete id="delete">
    ${delete}
    </delete>

    <update id="update">
    ${update}
    </update>

    <select id="select" resultMap="${smallClassName}ResultMap">
    ${selectByPrimaryKey}
    </select>


</mapper>