package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class StreamExamples {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employees = objectMapper.readValue(new File("C:\\LB11\\src\\main\\resources\\data.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));


        System.out.println("==== Лямбда выражения ====");

        Predicate<Employee> isYoung = employee -> employee.getAge() < 30;
        System.out.println("Young Employees:");
        employees.stream().filter(isYoung).forEach(System.out::println);

        Function<Employee, String> getJob = Employee::getJob;
        System.out.println("\nJobs of Employees:");
        employees.stream().map(getJob).forEach(System.out::println);

        Consumer<Employee> printEmployee = System.out::println;
        System.out.println("\nAll Employees:");
        employees.forEach(printEmployee);

        Supplier<Employee> employeeSupplier = () -> new Employee("Intern", 20000, 7, "Москва", 2023, 22);
        System.out.println("\nNew Employee: " + employeeSupplier.get());

        BiConsumer<Employee, Integer> updateSalary = (employee, increment) -> {
            employee.setSalary(employee.getSalary() + increment);
            System.out.println("Updated Salary of Employee ID " + employee.getId() + ": " + employee.getSalary());
        };
        System.out.println("\nUpdating salary of first employee:");
        updateSalary.accept(employees.get(0), 5000);
        updateSalary.accept(employees.get(1), 3000);


        Comparator<Employee> salaryComparator = Comparator.comparingInt(Employee::getSalary);
        System.out.println("\nEmployees sorted by salary:");
        employees.stream().sorted(salaryComparator).forEach(System.out::println);


        System.out.println("\n==== Stream API ====");
        List<Employee> filteredByCity = employees.stream()
                .filter(e -> e.getCity().equals("Прага"))
                .collect(Collectors.toList());
        System.out.println("Filtered Employees in Prague: " + filteredByCity);

        List<Employee> sortedBySalary = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary))
                .collect(Collectors.toList());
        System.out.println("Employees sorted by salary: " + sortedBySalary);

        List<Employee> limitedEmployees = employees.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Limited Employees: " + limitedEmployees);

        List<String> jobTitles = employees.stream()
                .map(Employee::getJob)
                .collect(Collectors.toList());
        System.out.println("Job Titles: " + jobTitles);

        List<Employee> topYoungEmployees = employees.stream()
                .filter(e -> e.getAge() < 25 && e.getCity().equals("Москва")) // замените "Москва" на нужный город
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .limit(10)
                .collect(Collectors.toList());
        System.out.println("Top Young Employees: " + topYoungEmployees);

        long countHighSalaryByJob = employees.stream()
                .filter(e -> e.getSalary() > 50000 && e.getJob().equals("Оператор call-центра")) // замените "Оператор call-центра" на нужную профессию
                .count();
        System.out.println("Count of Operators with Salary > 50k: " + countHighSalaryByJob);

        OptionalInt maxSalaryInAgeRange = employees.stream()
                .filter(e -> e.getCity().equals("Прага") && e.getAge() >= 30 && e.getAge() <= 40) // замените "Прага" на нужный город и уточните возраст
                .mapToInt(Employee::getSalary)
                .max();
        maxSalaryInAgeRange.ifPresent(salary -> System.out.println("Max Salary in Prague, Age 30-40: " + salary));

        OptionalInt minAgeHighSalary = employees.stream()
                .filter(e -> e.getCity().equals("Прага") && e.getSalary() > 100000)
                .mapToInt(Employee::getAge)
                .min();
        minAgeHighSalary.ifPresent(age -> System.out.println("Min Age in Prague with Salary > 100k: " + age));

        Map<String, List<Employee>> groupByJob = employees.stream()
                .collect(Collectors.groupingBy(Employee::getJob));
        System.out.println("Grouped by Job: " + groupByJob);

        Map<String, Long> groupCount = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()));
        System.out.println("Count by City: " + groupCount);

        Map<String, Optional<Integer>> maxSalaryByCity = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCity,
                        Collectors.mapping(Employee::getSalary,
                                Collectors.reducing(Integer::max))));
        System.out.println("Max Salary by City: " + maxSalaryByCity);
        
        Map<String, Double> avgSalaryByJob = employees.stream()
                .collect(Collectors.groupingBy(Employee::getJob,
                        Collectors.averagingInt(Employee::getSalary)));
        System.out.println("Average Salary by Job: " + avgSalaryByJob);
    }
}