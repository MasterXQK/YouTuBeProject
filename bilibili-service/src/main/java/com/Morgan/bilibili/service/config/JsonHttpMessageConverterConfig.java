package com.Morgan.bilibili.service.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan
 * @create 2022-10-17-12:46
 */

@Configuration
public class JsonHttpMessageConverterConfig {

    public static void main(String[] args) {
        Object o = new Object();
        List<Object> list = new ArrayList<>();
        list.add(o);
        list.add(o);
        System.out.println(list.size());
        System.out.println(JSONObject.toJSONString(list));
        // 禁止循环引用
        System.out.println(JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));

        /*
        print result
        2
        [{},{"$ref":"$[0]"}]
        [{},{}]
         */

    }
    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConvert() {
        FastJsonHttpMessageConverter fastJsonConvert = new FastJsonHttpMessageConverter();
        // new fastJsonConfig and set config
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.MapSortField,
                SerializerFeature.DisableCircularReferenceDetect
        );
        // convert 设置 config
        fastJsonConvert.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastJsonConvert);
    }
}
