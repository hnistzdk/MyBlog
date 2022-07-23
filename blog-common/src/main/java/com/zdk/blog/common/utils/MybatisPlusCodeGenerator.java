//package com.zdk.MyBlog.utils;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.po.TableFill;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.ArrayList;
//
///**
// * @author zdk
// * @date 2021/8/12 11:40
// */
//public class MybatisPlusCodeGenerator {
//    public static void main(String[] args) {
//        AutoGenerator generator = new AutoGenerator();
//        //配置生成策略
//
//        //1.全局配置
//        GlobalConfig config = new GlobalConfig();
//        //获取当前目录
//        String projectPath = System.getProperty("user.dir");
//        config.setOutputDir(projectPath+"/src/main/java");
//        config.setAuthor("zdk");
//        //设置生成后是否打开对应文件夹
//        config.setOpen(false);
//        //设置生成是否覆盖原来的文件
//        config.setFileOverride(false);
//        //去掉生成的service的I前缀
//        config.setServiceName("%sService");
//        config.setEntityName("%s");
//        config.setServiceImplName("%sServiceImpl");
//        config.setIdType(IdType.AUTO);
//        config.setDateType(DateType.ONLY_DATE);
//        config.setSwagger2(false);
//        generator.setGlobalConfig(config);
//
//        //2.配置数据源
//        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//        dataSourceConfig.setDbType(DbType.MYSQL);
//        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/blog?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf8");
//        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
//        dataSourceConfig.setUsername("root");
//        dataSourceConfig.setPassword("root");
//        generator.setDataSource(dataSourceConfig);
//
//        //3.包的配置
//        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setParent("com.zdk");
//        packageConfig.setModuleName("genCode");
//        packageConfig.setEntity("pojo");
////        packageConfig.setMapper("mapper");
////        packageConfig.setService("service");
////        packageConfig.setServiceImpl("service.serviceImpl");
////        packageConfig.setController("controller");
//        generator.setPackageInfo(packageConfig);
//
//        //4.策略配置
//        StrategyConfig strategyConfig = new StrategyConfig();
//        //设置要(读取)映射的表
//        strategyConfig.setInclude("blog_permission","blog_role","blog_role_permission");
//        //设置要去掉的表的前缀
//        strategyConfig.setTablePrefix("blog_");
//        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
//        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
//        //自动lombok
//        strategyConfig.setEntityLombokModel(true);
//        //设置逻辑删除
//        strategyConfig.setLogicDeleteFieldName("deleted");
//        //设置自动填充
////        TableFill tableInsert = new TableFill("create_time", FieldFill.INSERT);
////        TableFill tableUpdate = new TableFill("update_time", FieldFill.INSERT_UPDATE);
////        ArrayList<TableFill> tableFills = new ArrayList<>();
////        tableFills.add(tableInsert);
////        tableFills.add(tableUpdate);
////        strategyConfig.setTableFillList(tableFills);
//        //乐观锁
////        strategyConfig.setVersionFieldName("version");
//        //设置controller的驼峰命名格式
//        strategyConfig.setRestControllerStyle(true);
//        //请求会变成localhost:8080/hello_id_2
//        strategyConfig.setControllerMappingHyphenStyle(true);
//        generator.setStrategy(strategyConfig);
//
//        //执行生成
//        generator.execute();
//    }
//}
