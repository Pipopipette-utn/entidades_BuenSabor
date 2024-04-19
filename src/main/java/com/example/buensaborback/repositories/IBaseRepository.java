package com.example.buensaborback.repositories;
import com.example.buensaborback.domain.entities.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface IBaseRepository<E extends Base, Id extends Serializable> extends JpaRepository<E, Id> {
}