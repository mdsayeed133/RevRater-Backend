package com.revature.Service;

import com.revature.dtos.RatingDTO;
import com.revature.models.*;
import com.revature.repositories.RatingRepository;
import com.revature.services.EmployeeService;
import com.revature.services.RatingService;
import com.revature.services.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private EmployeeService employeeService;
    @Mock
    private TagService tagService;
    @Mock
    private RatingRepository ratingRepo;

    @InjectMocks
    private RatingService service;

    private Employee mockEmployee;
    private Employee mockEmployee2;
    private Employee mockEmployee3;
    private Employee mockEmployee4;
    private Department mockDepartment;
    private Department mockDepartment2;
    private User mockUser;
    private User mockUser2;
    private User mockUser3;
    private User mockUser4;
    private Tag mockTag1;
    private Tag mockTag2;
    private Tag mockTag3;
    private Rating mockRating;
    private Rating mockRating2;
    private Rating mockRating3;
    private Rating mockRating4;

    private List<Employee> mockEmployees;


    private int Score;

    @BeforeEach
    public void setup(){

        mockDepartment = new Department(1, "department name");
        mockDepartment2 = new Department(2, "fire department");

        mockUser = new User(0, "test@example.com", "password", "John", "Doe", Instant.now());
        mockUser2 = new User(0, "test2@example.com", "password", "John2", "Doe", Instant.now());
        mockUser3 = new User(0, "test3@example.com", "password", "John3", "Doe", Instant.now());
        mockUser4 = new User(0, "test4@example.com", "password", "John4", "Doe", Instant.now());

        mockEmployee = new Employee("Bob", "The builder is cool", mockUser, mockDepartment);
        mockEmployee2 = new Employee("Bob2", "The builder is silly", mockUser2, mockDepartment2);
        mockEmployee3 = new Employee("Bob3", "The builder is fast", mockUser3, mockDepartment2);
        mockEmployee4 = new Employee("Bob4", "The builder is a dork", mockUser4, mockDepartment);

        mockTag1 = new Tag(1, "Good");
        mockTag2 = new Tag(2, "Bad");
        mockTag3 = new Tag(3, "Average");

        mockRating = new Rating(mockEmployee2, 10, mockTag1, mockTag2, mockTag3);
        mockRating2 = new Rating(mockEmployee3, 80, mockTag1, mockTag2, mockTag3);
        mockRating3 = new Rating(mockEmployee4, 90, mockTag1, mockTag1, mockTag3);
        mockRating4 = new Rating(mockEmployee2, 10, mockTag1, mockTag2, mockTag3);

    }

    @Test
    public void createRatingTest()
    {
        RatingDTO rDTO = new RatingDTO(1, 60, 1,2,3);
        when(employeeService.getEmployeeById(rDTO.getEmployeeId())).thenReturn(mockEmployee);
        when(tagService.findById(rDTO.getTags1())).thenReturn(mockTag1);
        when(tagService.findById(rDTO.getTags2())).thenReturn(mockTag2);
        when(tagService.findById(rDTO.getTags3())).thenReturn(mockTag3);
        when(ratingRepo.save(any())).thenReturn(mockRating);

        Rating result = service.createRating(rDTO);

        assertThat(mockRating, equalTo(result));
    }

    @Test
    public void editRatingTest()
    {
        RatingDTO rDTO = new RatingDTO(1,60,1,2,3);
        when(employeeService.getEmployeeById(rDTO.getEmployeeId())).thenReturn(mockEmployee);
        when(tagService.findById(rDTO.getTags1())).thenReturn(mockTag1);
        when(tagService.findById(rDTO.getTags2())).thenReturn(mockTag2);
        when(tagService.findById(rDTO.getTags3())).thenReturn(mockTag3);
        when(ratingRepo.findById(1)).thenReturn(Optional.empty());
        when(ratingRepo.save(any(Rating.class))).thenAnswer(i -> i.getArguments()[0]);
        Rating rating = service.editRating(rDTO, 1);
        assertEquals(rDTO.getScore(), rating.getScore());
        assertEquals(mockEmployee, rating.getEmployee());
        assertEquals(mockTag1, rating.getTag1());
        assertEquals(mockTag2, rating.getTag2());
        assertEquals(mockTag3, rating.getTag3());
    }
    @Test
    public void testDelete() {
        service.delete(mockRating);
        verify(ratingRepo).delete(mockRating);
    }

    @Test
    public void searchEmployeesByTagTest() {
        List<Rating> ratings = Arrays.asList(mockRating3, mockRating2,mockRating, mockRating3);

        when(tagService.findById(mockTag1.getId())).thenReturn(mockTag1);
        when(ratingRepo.findByTag1OrTag2OrTag3(any(Tag.class), any(Tag.class), any(Tag.class))).thenReturn(Optional.of(ratings));

        List<Employee> result = service.searchEmployeesByTag(mockTag1.getId());
        List<Employee> expected = Arrays.asList(mockEmployee4, mockEmployee3);

        assertThat(result, hasItem(mockEmployee2));
        verify(tagService).findById(mockTag1.getId());
    }

    @Test
    public void testGetEmployeeAvgRating_ReturnAverage() {
        when(employeeService.getEmployeeById(mockEmployee2.getId())).thenReturn(mockEmployee2);

        List<Rating> ratings = Arrays.asList(mockRating,mockRating4);

        when(ratingRepo.findByEmployee(mockEmployee2)).thenReturn(Optional.of(ratings));

        double avg = service.getEmployeeAvgRating(mockEmployee2.getId());
        assertEquals(10.0, avg, 0.0001);
    }

    @Test
    void testGetTop3TagsOfEmployee() {
        when(employeeService.getEmployeeById(mockEmployee2.getId())).thenReturn(mockEmployee2);
        List<Rating> ratings = Arrays.asList(mockRating,mockRating4);
        when(ratingRepo.findByEmployee(mockEmployee2)).thenReturn(Optional.of(ratings));

        List<Tag> top3Tags = service.getTop3TagsOfEmployee(mockEmployee2.getId());

        // Verify the result
        assertNotNull(top3Tags);
        assertEquals(3, top3Tags.size());
    }

    @Test
    public void getTop3EmployeesTest() {
        List<Rating> ratings = Arrays.asList(mockRating,mockRating2,mockRating3,mockRating4);
        when(ratingRepo.findAll()).thenReturn(ratings);

        // call the method under test
        List<Employee> top3Employees = service.getTop3Employees();

        // assert the results
        assertNotNull(top3Employees);
        assertEquals(3, top3Employees.size());
        verify(ratingRepo).findAll();
    }

    @Test
    public void testFindByEmployee() {

        List<Rating> ratings = Arrays.asList(mockRating, mockRating2);

        when(ratingRepo.findByEmployee(mockEmployee2)).thenReturn(Optional.of(ratings));

        Optional<List<Rating>> result = service.findByEmployee(mockEmployee2);

        assertTrue(result.isPresent());
        assertEquals(ratings, result.get());
    }



}
