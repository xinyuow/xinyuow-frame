package com.xinyuow.frame.common.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis-Plus配置
 *
 * @author mxy
 * @date 2020/11/12
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * Mybatis-Plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDbType(DbType.MYSQL);
        // 开启count的join优化，只针对部分left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

        /*
            攻击SQL阻断解析器、加入解析链;
            防止小白或者恶意进行delete/update全表操作。
            注：若表配置使用了逻辑删除将正常执行删除。
         */
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

}
