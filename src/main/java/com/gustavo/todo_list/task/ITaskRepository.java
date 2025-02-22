package com.gustavo.todo_list.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{

    public List<TaskModel> findByIdUser(UUID idUser);
}
