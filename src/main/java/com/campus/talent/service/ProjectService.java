package com.campus.talent.service;

import com.campus.talent.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();

    Project findById(Long id);

    void save(Project project);

    void update(Project project);

    void delete(Long id);
}