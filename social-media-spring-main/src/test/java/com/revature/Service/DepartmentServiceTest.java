package com.revature.Service;

import com.revature.models.Department;
import com.revature.repositories.DepartmentRepository;
import com.revature.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void GetDepartmentByIdTest(){
        Department mockDepartment = new Department(1, "HR");
        when(departmentRepository.findById(mockDepartment.getId())).thenReturn(Optional.of(mockDepartment));

        Department result = departmentService.getDepartmentById(1);

        assertThat(result, equalTo(mockDepartment));
    }


}
