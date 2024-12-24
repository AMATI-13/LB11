package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
    private String job;
    private int salary;
    private int id;
    private String city;
    private int year;
    private int age;

    public Employee(@JsonProperty("job") String job,
                    @JsonProperty("salary") int salary,
                    @JsonProperty("id") int id,
                    @JsonProperty("city") String city,
                    @JsonProperty("year") int year,
                    @JsonProperty("age") int age) {
        this.job = job;
        this.salary = salary;
        this.id = id;
        this.city = city;
        this.year = year;
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public int getSalary() {
        return salary;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public int getYear() {
        return year;
    }

    public int getAge() {
        return age;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "job='" + job + '\'' +
                ", salary=" + salary +
                ", id=" + id +
                ", city='" + city + '\'' +
                ", year=" + year +
                ", age=" + age +
                '}';
    }
}