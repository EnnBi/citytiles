package com.akash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akash.entity.ToDo;
import com.akash.repository.ToDoRepository;

@Controller
@RequestMapping("/todo")
public class ToDoController {

	@Autowired
	ToDoRepository todoRepository;
	
	@GetMapping
	public ResponseEntity<?> view(){
		return ResponseEntity.ok(todoRepository.findByActiveOrderByDateDesc(true));
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ToDo toDo){
		toDo.setActive(true);
		return ResponseEntity.ok(todoRepository.save(toDo));
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		ToDo toDo=todoRepository.findById(id).orElse(null);
		if(toDo!=null){
			toDo.setActive(false);
		}
		todoRepository.save(toDo);
		return ResponseEntity.ok().build();
	}

}
