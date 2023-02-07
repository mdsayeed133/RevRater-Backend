package com.revature.repositories;

import com.revature.models.Employee;
import com.revature.models.Rating;
import com.revature.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Optional<List<Rating>> findByEmployee(Employee employee);

   Optional<List<Rating>> findByTag1OrTag2OrTag3(Tag tag1,Tag tag2,Tag tag3);


}