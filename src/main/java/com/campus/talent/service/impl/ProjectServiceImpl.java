package com.campus.talent.service.impl;

import com.campus.talent.entity.Project;
import com.campus.talent.mapper.ProjectMapper;
import com.campus.talent.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    @Override
    public List<Project> findAll() {
        return projectMapper.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectMapper.findById(id);
    }

    @Override
    public void save(Project project) {
        projectMapper.insert(project);
    }

    @Override
    public void update(Project project) {
        projectMapper.update(project);
    }

    @Override
    public void delete(Long id) {
        projectMapper.deleteById(id);
    }
}