package org.example.restspringbootudemy.services.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        public static <O, D> D parseObject(O origin, Class<D> destination) {
            return mapper.map(origin, destination);
        }

        public static <O, D> List<D> parseList(List<O> origin, Class<D> destination) {
            List<D> destinationObjs = new ArrayList<>();
            for (O o : origin) {
                destinationObjs.add(mapper.map(o, destination));
            }
            return destinationObjs;
        }

}
