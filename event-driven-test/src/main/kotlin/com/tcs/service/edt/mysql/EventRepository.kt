package com.tcs.service.edt.mysql

import com.tcs.service.edt.model.Events
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
//@EnableJpaRepositories

interface EventRepository : CrudRepository<Events, String> {

}