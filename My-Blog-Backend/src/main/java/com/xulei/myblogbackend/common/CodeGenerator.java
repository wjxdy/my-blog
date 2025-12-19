package com.xulei.myblogbackend.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        // 数据库连接配置（请确认库名和表存在）
        String url = "jdbc:mysql://8.140.63.245:3306/my-blog?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=false";
        String username = "root";
        String password = "xuleixulei";

        // 必须指定要生成的表名（替换为你的实际表名，如"user","article"）
        String[] tables = {"map_info"}; // 例如：{"book", "user"}

        FastAutoGenerator.create(url, username, password)
                // 3. 全局配置
                .globalConfig(builder -> {
                    builder.author("xulei")
                            //.enableSwagger() // 可选，需 swagger 依赖
                            .outputDir(System.getProperty("user.dir") + "/src/main/java");
                })
                // 4. 包配置（父包需已存在）
                .packageConfig(builder -> {
                    builder.parent("com.xulei.myblogbackend") // 项目实际父包
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            // Mapper XML 输出路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    System.getProperty("user.dir") + "/src/main/resources/mapper"));
                })

                // 6. 策略配置（生成规则）
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            // 实体类策略
                            .entityBuilder()
                            .enableLombok() // 开启 Lombok
                            .enableChainModel() // 链式调用
                            // Controller 策略
                            .controllerBuilder()
                            .enableRestStyle() // RestController
                            .enableHyphenStyle() // 连字符命名（/user-info）
                            // Service 策略
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // XXXService
                            .formatServiceImplFileName("%sServiceImpl") // XXXServiceImpl
                            // Mapper 策略
                            .mapperBuilder()
                            .enableBaseResultMap() // 生成 ResultMap
                            .enableBaseColumnList(); // 生成 ColumnList
                })
                // 7. 模板引擎（Velocity）
                .templateEngine(new VelocityTemplateEngine())
                // 8. 执行生成
                .execute();
    }
}