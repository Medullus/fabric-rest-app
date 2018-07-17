package com.medullus.fabricrestapi.dao.users;

import com.medullus.fabricrestapi.domains.dto.users.UserRegister;
import com.mhc.fabric.client.models.SampleUser;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;

public class UsersDaoMapper {
    private static Mapper mapper;

    public static UserRegister mapSampleUserToUserRegister(SampleUser sampleUser) {
        mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(getBuilder())
                .build();

        UserRegister userRegister = mapper.map(sampleUser, UserRegister.class);
        return userRegister;
    }

    private static BeanMappingBuilder getBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(SampleUser.class, UserRegister.class)
                        .fields("name", "userName")
                        .fields("enrollmentSecret", "secret");
            }
        };
    }
}
