package com.wsh;

import com.wsh.zero.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {
    @Autowired
    SysUserMapper mapper;

    @Test
    public void contextLoads() {
//        Class<E> beanClass = getParameterizedType();
//        public Class<E> getParameterizedType() {
//            Class<E> beanClass;
//            Type genericSuperclass = getClass().getGenericSuperclass();
//            beanClass = getSomeType(genericSuperclass, BaseEntity.class).orElse(null);
//            Type[] genericInterfaces = getClass().getGenericInterfaces();
//            for (int i = 0; i < genericInterfaces.length && beanClass == null; i++) {
//                beanClass = getSomeType(genericInterfaces[i], BaseEntity.class).orElse(null);
//            }
//            return beanClass;
//        }
//
//        public Optional<Class<E>> getSomeType(Type genericClz, Class filter) {
//            if (genericClz instanceof ParameterizedType) {
//                ParameterizedType clzType = (ParameterizedType) genericClz;
//                Type[] actualTypeArguments = clzType.getActualTypeArguments();
//                for (Type actualTypeArgument : actualTypeArguments) {
//                    if (actualTypeArgument instanceof Class && filter.isAssignableFrom((Class) actualTypeArgument)) {
//                        return Optional.ofNullable((Class) actualTypeArgument);
//                    }
//                }
//            }
//            return Optional.empty();
//        }
    }

}
