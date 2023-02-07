package com.revature.services;

import com.revature.dtos.SearchRequest;
import com.revature.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class EmployeeSearchService {

    private EmployeeService employeeService;
    private RatingService ratingService;
    @Autowired
    public EmployeeSearchService(EmployeeService employeeService, RatingService ratingService) {
        this.employeeService = employeeService;
        this.ratingService = ratingService;
    }

    public List<Employee> combinedEmployeeSearch(SearchRequest searchRequest){

        if(searchRequest.getTagId()==0 && searchRequest.getDepartmentId()==0&&(searchRequest.getName()==null||searchRequest.getName().equals("")))
            return new ArrayList<>();

        List<Employee> listByName= employeeService.getEmployeeByName(searchRequest.getName());
        List<Employee> listByTag= ratingService.searchEmployeesByTag(searchRequest.getTagId());
        List<Employee> listByDepartment= employeeService.getEmployeeByDepartment(searchRequest.getDepartmentId());

        if(searchRequest.getTagId()==0 && searchRequest.getDepartmentId()==0)
            return listByName;
        if((searchRequest.getName()==null||searchRequest.getName().equals(""))&& searchRequest.getDepartmentId()==0)
            return listByTag;
        if(searchRequest.getTagId()==0 && (searchRequest.getName()==null||searchRequest.getName().equals("")))
            return listByDepartment;

        if(searchRequest.getTagId()==0)
            return listByName.stream().filter(listByDepartment::contains).collect(Collectors.toList());
        if(searchRequest.getDepartmentId()==0)
            return listByName.stream().filter(listByTag::contains).collect(Collectors.toList());
        if(searchRequest.getName()==null||searchRequest.getName().equals(""))
            return listByTag.stream().filter(listByDepartment::contains).collect(Collectors.toList());
        return listByName.stream()
                .filter(listByDepartment::contains).filter(listByTag::contains)
                .collect(Collectors.toList());
    }
}
