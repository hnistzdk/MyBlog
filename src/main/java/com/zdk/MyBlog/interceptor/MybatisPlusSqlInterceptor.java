package com.zdk.MyBlog.interceptor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author zdk
 * @date 2021/12/12 19:15
 */
@Intercepts({
        @Signature(type = Executor.class,method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class,method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class , CacheKey.class , BoundSql.class }),
        @Signature(type = Executor.class,method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisPlusSqlInterceptor implements Interceptor {
    private  static  final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusSqlInterceptor.class);
        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            System.out.println("Executor Plugin 拦截 :"+invocation.getMethod());
            Object[] queryArgs = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) queryArgs[0];
            //获取 ParamMap
//            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) queryArgs[1];
            Object param = queryArgs[1];
            // 获取SQL
            BoundSql boundSql = mappedStatement.getBoundSql(param);
            String sql = boundSql.getSql();
            LOGGER.debug("==> ORIGIN SQL: "+sql);
            long startTime = System.currentTimeMillis();
            Configuration configuration = mappedStatement.getConfiguration();
            String sqlId = mappedStatement.getId();

            Object proceed = invocation.proceed();
            long endTime=System.currentTimeMillis();
            long time = endTime - startTime;
            printSqlLog(configuration,boundSql,sqlId,time);
            return proceed;
        }

        public static void printSqlLog(Configuration configuration, BoundSql boundSql, String sqlId, long time){
            Object parameterObject = boundSql.getParameterObject();
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            String sql= boundSql.getSql().replaceAll("[\\s]+", " ");
            StringBuffer sb=new StringBuffer("==> PARAM:");
            if (parameterMappings.size()>0 && parameterObject!=null){
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    sql = sql.replaceFirst("\\?", parameterObject.toString());
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    for (ParameterMapping parameterMapping : parameterMappings) {
                        String propertyName = parameterMapping.getProperty();
                        if (metaObject.hasGetter(propertyName)) {
                            Object obj = metaObject.getValue(propertyName);
                            String parameterValue = obj.toString();
                            sql = sql.replaceFirst("\\?", parameterValue);
                            sb.append(parameterValue).append("(").append(obj.getClass().getSimpleName()).append("),");
                        } else if (boundSql.hasAdditionalParameter(propertyName)) {
                            Object obj = boundSql.getAdditionalParameter(propertyName);
                            String parameterValue = obj.toString();
                            sql = sql.replaceFirst("\\?", parameterValue);
                            sb.append(parameterValue).append("(").append(obj.getClass().getSimpleName()).append("),");
                        }
                    }
                }
                sb.deleteCharAt(sb.length()-1);
            }
            LOGGER.debug("==> SQL:"+sql);
            LOGGER.debug(sb.toString());
            LOGGER.debug("==> SQL TIME:"+time+" ms");
        }
}
