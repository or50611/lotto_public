package com.board;
 
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
 
@ImportResource("context-scheduler.xml")
@SpringBootApplication
@MapperScan(value = {"com.board.mapper"})
public class BoardApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            
            Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
            
            sessionFactory.setMapperLocations(res);
            //한글로 올라가나? 넹
            return sessionFactory.getObject();
    }

}
