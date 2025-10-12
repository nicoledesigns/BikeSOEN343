package com.soen343.tbd.infrastructure.persistence.mapper;

import com.soen343.tbd.domain.model.ids.UserId;
import com.soen343.tbd.domain.model.user.Operator;
import com.soen343.tbd.domain.model.user.Rider;
import com.soen343.tbd.domain.model.user.User;
import com.soen343.tbd.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity to Domain
    @Mapping(target = "userId", expression = "java(new com.soen343.tbd.domain.model.ids.UserId(entity.getId()))")
    default User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserId userId = new UserId(entity.getId());

        if ("RIDER".equals(entity.getRole())) {
            return new Rider(
                userId,
                entity.getFullName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAddress(),
                entity.getUsername(),
                entity.getCreatedAt(),
                null
            );
        } else if ("OPERATOR".equals(entity.getRole())) {
            return new Operator(
                userId,
                entity.getFullName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAddress(),
                entity.getUsername(),
                entity.getCreatedAt()
            );
        }

        throw new IllegalArgumentException("Unknown role: " + entity.getRole());
    }

    // Domain to Entity
    @Mapping(target = "id", expression = "java(domain.getUserId() != null ? domain.getUserId().value() : null)")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    UserEntity toEntity(User domain);
}
