package com.campus.talent.controller;

import com.campus.talent.dto.Result;
import com.campus.talent.entity.Project;
import com.campus.talent.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    /** 获取所有项目 */
    @GetMapping
    public Result<List<Project>> findAll() {
        return Result.success(projectService.findAll());
    }

    /** 获取项目详情 */
    @GetMapping("/{id}")
    public Result<Project> findById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        if (project == null) {
            return Result.fail("项目不存在");
        }
        return Result.success(project);
    }

    /** 新增项目 */
    @PostMapping
    public Result<Void> save(@RequestBody Project project) {
        projectService.save(project);
        return Result.success();
    }

    /** 更新项目 */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id,
                               @RequestBody Project project) {
        project.setId(id);
        projectService.update(project);
        return Result.success();
    }

    /** 删除项目 */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return Result.success();
    }
}