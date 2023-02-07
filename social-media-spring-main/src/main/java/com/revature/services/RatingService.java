package com.revature.services;


import com.revature.dtos.RatingDTO;
import com.revature.models.Employee;
import com.revature.models.Rating;
import com.revature.models.Tag;
import com.revature.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private EmployeeService employeeService;
    private TagService tagService;


    @Autowired
    public RatingService(RatingRepository ratingRepository, EmployeeService employeeService, TagService tagService)
    {
        this.ratingRepository = ratingRepository;
        this.employeeService = employeeService;
        this.tagService = tagService;
    }

    public Optional<List<Rating>> findByEmployee(Employee employee){
        return ratingRepository.findByEmployee(employee);
    }
    public Rating createRating(RatingDTO rDTO)
    {
        Employee emp = employeeService.getEmployeeById(rDTO.getEmployeeId());
        Tag tag1 = tagService.findById(rDTO.getTags1());
        Tag tag2 = tagService.findById(rDTO.getTags2());
        Tag tag3 = tagService.findById(rDTO.getTags3());

        Rating newRating = new Rating(emp, rDTO.getScore(), tag1, tag2, tag3);
        return ratingRepository.save(newRating);
    }

    public Rating editRating(RatingDTO rDTO, int ratingId){
        Rating rating= ratingRepository.findById(ratingId).orElse(null);
        Employee emp = employeeService.getEmployeeById(rDTO.getEmployeeId());
        if(rating==null) rating= new Rating(ratingId,emp,0,null,null,null);
        Tag tag1 = tagService.findById(rDTO.getTags1());
        Tag tag2 = tagService.findById(rDTO.getTags2());
        Tag tag3 = tagService.findById(rDTO.getTags3());
        rating.setScore(rDTO.getScore());
        rating.setTag1(tag1);
        rating.setTag2(tag2);
        rating.setTag3(tag3);
        return ratingRepository.save(rating);

    }
    public void delete(Rating rating){
        ratingRepository.delete(rating);
    }
    public List<Employee> searchEmployeesByTag(int tagId) {
        Tag tag= tagService.findById(tagId);
        List<Rating> ratings = ratingRepository.findByTag1OrTag2OrTag3(tag, tag, tag).orElse(null);
        if(ratings==null)return new ArrayList<>();
        List<Employee> employees= new ArrayList<>();
        for(Rating rating: ratings){
            employees.add(rating.getEmployee());
        }

        /* This is initializing an empty HashMap called "employeeCount". It then iterates through the list of employees and for each employee,
        it retrieves the current count of that employee from the HashMap (if it exists), or sets it to 0 if it doesn't exist in the HashMap. Finally,
        it adds 1 to the count of the employee and puts the updated count back into the HashMap with the employee as the key. So, this code is counting
        the number of occurrences of each employee in the "employees" list.*/
        Map<Employee, Integer> employeeCount = new HashMap<>();
        for (Employee employee : employees) {
            int count = employeeCount.getOrDefault(employee, 0);
            employeeCount.put(employee, count + 1);
        }
        //Maps are in the utils not iterable
        List<Map.Entry<Employee, Integer>> entries = new ArrayList<>(employeeCount.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public double getEmployeeAvgRating(int employeeId)
    {
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<Rating> ratingList = ratingRepository.findByEmployee(employee).orElse(null);
        if(ratingList==null||ratingList.size()==0)return 0.0d;
        double total = 0;
        for(int x = 0; x < ratingList.size(); x++)
        {
            total += ratingList.get(x).getScore();
        }
        return total / ratingList.size();
    }

    public List<Tag> getTop3TagsOfEmployee(int employeeId) {
        Employee employee= employeeService.getEmployeeById(employeeId);
        List<Rating> ratings = ratingRepository.findByEmployee(employee).orElse(null);
        if(ratings==null) return null;

        Map<Tag, Integer> tagCountMap = new HashMap<>();

        for (Rating rating : ratings) {
            Tag tag1 = rating.getTag1();
            if (tag1 != null) {
                tagCountMap.put(tag1, tagCountMap.getOrDefault(tag1, 0) + 1);
            }
            Tag tag2 = rating.getTag2();
            if (tag2 != null) {
                tagCountMap.put(tag2, tagCountMap.getOrDefault(tag2, 0) + 1);
            }
            Tag tag3 = rating.getTag3();
            if (tag3 != null) {
                tagCountMap.put(tag3, tagCountMap.getOrDefault(tag3, 0) + 1);
            }
        }
        /*This sorts the entries in the tagCountMap (which maps a Tag to its count) based on the values (the counts) in descending order.
        It then maps the sorted entries to only contain the keys (the Tags), and collects the result into a List<Tag>. So, sortedTags will be a
        List<Tag> containing the Tags sorted by their counts in descending order.*/
        List<Tag> sortedTags = tagCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return sortedTags.size() > 3 ? sortedTags.subList(0, 3) : sortedTags;
    }

    public List<Employee> getTop3Employees(){
        Map<Employee, Double> employeeScoreMap = new HashMap<>();
        Map<Employee, Integer> employeeCountMap = new HashMap<>();
        List<Rating> ratings= ratingRepository.findAll();

        for (Rating rating : ratings) {
            Employee employee = rating.getEmployee();
            int score = rating.getScore();
            if (!employeeScoreMap.containsKey(employee)) {
                employeeScoreMap.put(employee, (double) score);
                employeeCountMap.put(employee, 1);
            } else {
                double totalScore = employeeScoreMap.get(employee) + score;
                int count = employeeCountMap.get(employee) + 1;
                employeeScoreMap.put(employee, totalScore);
                employeeCountMap.put(employee, count);
            }
        }

        Map<Employee, Double> employeeAvgScoreMap = new HashMap<>();
        for (Map.Entry<Employee, Double> entry : employeeScoreMap.entrySet()) {
            Employee employee = entry.getKey();
            double totalScore = entry.getValue();
            int count = employeeCountMap.get(employee);
            double avgScore = totalScore / count;
            employeeAvgScoreMap.put(employee, avgScore);
        }

        List<Map.Entry<Employee, Double>> sortedEntries = new ArrayList<>(employeeAvgScoreMap.entrySet());

        /* This is sorting the entries in a Map called sortedEntries in descending order based on the value of each entry. The value of each entry is the
        average score of an employee. The sorting is performed by comparing the value of entry2 to entry1, and the sorting is done in descending order so
        that the employee with the highest average score will be first in the sorted list.*/
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        List<Employee> top3Employees = new ArrayList<>();
        for (int i = 0; i < 3 && i < sortedEntries.size(); i++) {
            top3Employees.add(sortedEntries.get(i).getKey());
        }

        return top3Employees;
    }
}
