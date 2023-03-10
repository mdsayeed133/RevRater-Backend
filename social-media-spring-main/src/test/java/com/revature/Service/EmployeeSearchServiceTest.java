package com.revature.Service;

import com.revature.dtos.SearchRequest;
import com.revature.models.Employee;
import com.revature.services.EmployeeSearchService;
import com.revature.services.EmployeeService;
import com.revature.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeSearchServiceTest {
    @Mock
    private EmployeeService employeeService;
    @Mock
    private RatingService ratingService;
    @InjectMocks
    private EmployeeSearchService employeeSearchService;

    private Employee mockEmployee1= Mockito.mock(Employee.class);
    private Employee mockEmployee2= Mockito.mock(Employee.class);
    private Employee mockEmployee3= Mockito.mock(Employee.class);

    @Test
    void blankSearch_ReturnsEmptyList(){
        SearchRequest searchRequest= new SearchRequest();
        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result.isEmpty(), equalTo(true));
    }

    @Test
    void searchWithAllFields(){
        SearchRequest searchRequest = new SearchRequest(" ",1,1);
        List<Employee> listByName= Arrays.asList(mockEmployee1,mockEmployee2);
        List<Employee> listByTag= Arrays.asList(mockEmployee1,mockEmployee3);
        List<Employee> listByDepartment= Arrays.asList(mockEmployee1,mockEmployee3,mockEmployee2);
        when(employeeService.getEmployeeByName(" ")).thenReturn(listByName);
        when(employeeService.getEmployeeByDepartment(1)).thenReturn(listByDepartment);
        when(ratingService.searchEmployeesByTag(1)).thenReturn(listByTag);

        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result, hasItem(mockEmployee1));
        assertThat(result, not(hasItem(mockEmployee2)));
        assertThat(result, not(hasItem(mockEmployee3)));
    }

    @Test
    void searchWithOnlyDepartment(){
        SearchRequest searchRequest = new SearchRequest(null,1,0);
        List<Employee> listByDepartment= Arrays.asList(mockEmployee1,mockEmployee3,mockEmployee2);
        when(employeeService.getEmployeeByDepartment(1)).thenReturn(listByDepartment);

        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result, equalTo(listByDepartment));
    }
    @Test
    void searchWithOnlyTag(){
        SearchRequest searchRequest = new SearchRequest(null,0,1);
        List<Employee> listByTag= Arrays.asList(mockEmployee1,mockEmployee3);
        when(ratingService.searchEmployeesByTag(1)).thenReturn(listByTag);

        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result, equalTo(listByTag));
    }

    @Test
    void searchWithOnlyName(){
        SearchRequest searchRequest = new SearchRequest(" ",0,0);
        List<Employee> listByName= Arrays.asList(mockEmployee1,mockEmployee2);
        when(employeeService.getEmployeeByName(" ")).thenReturn(listByName);

        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result, equalTo(listByName));
    }

    @Test
    void searchWithNameAndDepartment(){
        SearchRequest searchRequest = new SearchRequest(" ",1,0);
        List<Employee> listByName= Arrays.asList(mockEmployee1,mockEmployee2);
        List<Employee> listByDepartment= Arrays.asList(mockEmployee1,mockEmployee3,mockEmployee2);
        when(employeeService.getEmployeeByName(" ")).thenReturn(listByName);
        when(employeeService.getEmployeeByDepartment(1)).thenReturn(listByDepartment);

        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result, hasItem(mockEmployee1));
        assertThat(result, hasItem(mockEmployee2));
        assertThat(result, not(hasItem(mockEmployee3)));
    }

    @Test
    void searchWithNameAndTag(){
        SearchRequest searchRequest = new SearchRequest(" ",0,1);
        List<Employee> listByName= Arrays.asList(mockEmployee1,mockEmployee2);
        List<Employee> listByTag= Arrays.asList(mockEmployee1,mockEmployee3);
        when(employeeService.getEmployeeByName(" ")).thenReturn(listByName);
        when(ratingService.searchEmployeesByTag(1)).thenReturn(listByTag);

        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result, hasItem(mockEmployee1));
        assertThat(result, not(hasItem(mockEmployee2)));
        assertThat(result, not(hasItem(mockEmployee3)));
    }

    @Test
    void searchWithTagAndDepartment(){
        SearchRequest searchRequest = new SearchRequest(null,1,1);
        List<Employee> listByTag= Arrays.asList(mockEmployee1,mockEmployee3);
        List<Employee> listByDepartment= Arrays.asList(mockEmployee1,mockEmployee3,mockEmployee2);
        when(employeeService.getEmployeeByDepartment(1)).thenReturn(listByDepartment);
        when(ratingService.searchEmployeesByTag(1)).thenReturn(listByTag);

        List<Employee> result= employeeSearchService.combinedEmployeeSearch(searchRequest);
        assertThat(result, hasItem(mockEmployee1));
        assertThat(result, hasItem(mockEmployee3));
        assertThat(result, not(hasItem(mockEmployee2)));
    }

}
