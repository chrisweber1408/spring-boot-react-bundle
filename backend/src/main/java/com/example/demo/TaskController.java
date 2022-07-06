package com.example.demo;

import com.example.demo.user.MyUser;
import com.example.demo.user.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final MyUserService myUserService;


    @PostMapping("/kanban")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTask(@RequestBody Task task, Principal principal){
        MyUser user = myUserService.findByUsername(principal.getName()).orElseThrow();
        task.setUserId(user.getId());
        taskService.addOneTaskToDo(task);
    }
    @GetMapping("/kanban")
    public List<Task> getAllTasksById(Principal principal){
        MyUser user = myUserService.findByUsername(principal.getName()).orElseThrow();
        return taskService.listAllTasksById(user.getId());
    }
    @DeleteMapping("/kanban/{id}")
    public void deleteTask(@PathVariable String id){
        taskService.deleteOneTaskById(id);
    }
    @GetMapping("/kanban/{id}")
    public Optional<Task> getTask(@PathVariable String id){
        return taskService.getOneTask(id);
    }

    @PutMapping("/kanban")
    public void editTask(@RequestBody Task task){
        taskService.editOneTask(task);
    }
    @PutMapping("/kanban/next")
    public void nextStatus(@RequestBody Task task){
        taskService.nextStatusOfTask(task);
    }
    @PutMapping("/kanban/prev")
    public void prevStatus(@RequestBody Task task){
        taskService.prevStatusOfTask(task);
    }

}
