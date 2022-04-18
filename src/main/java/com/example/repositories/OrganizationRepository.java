package com.example.repositories;

import com.example.entities.OrganizationEntity;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import javax.validation.constraints.NotBlank;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface OrganizationRepository extends PageableRepository<OrganizationEntity, Long> {
    @NonNull
    OrganizationEntity save(@NonNull @NotBlank String name);
}
