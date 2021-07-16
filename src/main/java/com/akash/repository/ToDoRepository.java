package com.akash.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.akash.entity.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, Long> {

	List<ToDo> findByActiveOrderByDateDesc(boolean active);
}
 