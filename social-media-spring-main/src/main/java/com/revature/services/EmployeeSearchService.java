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

        List<Employee> listByName;
        List<Employee> listByTag;
        List<Employee> listByDepartment;

        if(searchRequest.getTagId()==0 && searchRequest.getDepartmentId()==0&&(searchRequest.getName()==null||searchRequest.getName().equals("")))
            return new ArrayList<>();

        if(searchRequest.getTagId()==0 && searchRequest.getDepartmentId()==0){
            listByName= employeeService.getEmployeeByName(searchRequest.getName());
            return listByName;}
        if((searchRequest.getName()==null||searchRequest.getName().equals(""))&& searchRequest.getDepartmentId()==0){
            listByTag= ratingService.searchEmployeesByTag(searchRequest.getTagId());
            return listByTag;}
        if(searchRequest.getTagId()==0 && (searchRequest.getName()==null||searchRequest.getName().equals(""))){
            listByDepartment= employeeService.getEmployeeByDepartment(searchRequest.getDepartmentId());
            return listByDepartment;}

        if(searchRequest.getTagId()==0){
            listByName= employeeService.getEmployeeByName(searchRequest.getName());
            listByDepartment= employeeService.getEmployeeByDepartment(searchRequest.getDepartmentId());
            return listByName.stream().filter(listByDepartment::contains).collect(Collectors.toList());}
        if(searchRequest.getDepartmentId()==0){
            listByName= employeeService.getEmployeeByName(searchRequest.getName());
            listByTag= ratingService.searchEmployeesByTag(searchRequest.getTagId());
            return listByName.stream().filter(listByTag::contains).collect(Collectors.toList());}
        if(searchRequest.getName()==null||searchRequest.getName().equals("")){
            listByTag= ratingService.searchEmployeesByTag(searchRequest.getTagId());
            listByDepartment= employeeService.getEmployeeByDepartment(searchRequest.getDepartmentId());
            return listByTag.stream().filter(listByDepartment::contains).collect(Collectors.toList());}

        listByName= employeeService.getEmployeeByName(searchRequest.getName());
        listByTag= ratingService.searchEmployeesByTag(searchRequest.getTagId());
        listByDepartment= employeeService.getEmployeeByDepartment(searchRequest.getDepartmentId());

        return listByName.stream()
                .filter(listByDepartment::contains).filter(listByTag::contains)
                .collect(Collectors.toList());
    }
}
