package org.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.project.dao.TaskDAO;
import org.project.dao.PersonDAO;
import org.project.models.Task;
import org.project.models.Person;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private final TaskDAO taskDAO;
    private final PersonDAO personDAO;

    @Autowired
    public TasksController(TaskDAO TaskDAO, PersonDAO personDAO) {
        this.taskDAO = TaskDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("tasks", taskDAO.index());
        model.addAttribute("localDate", LocalDate.now());
        return "tasks/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("task", taskDAO.show(id));

        Optional<Person> taskOwner = taskDAO.getTaskOwner(id);

        if (taskOwner.isPresent())
            model.addAttribute("owner", taskOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        model.addAttribute("localDate", LocalDate.now());

        return "tasks/show";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task Task) {
        return "tasks/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("task") @Valid Task Task,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tasks/new";

        taskDAO.save(Task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskDAO.show(id));
        return "tasks/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "tasks/edit";

        taskDAO.update(id, task);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        taskDAO.delete(id);
        return "redirect:/tasks";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        taskDAO.release(id);
        return "redirect:/tasks/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        taskDAO.assign(id, selectedPerson);
        return "redirect:/tasks/" + id;
    }
}