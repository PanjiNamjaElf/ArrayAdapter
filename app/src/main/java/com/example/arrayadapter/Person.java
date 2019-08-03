package com.example.arrayadapter;

public class Person {
 private String Name, Id, Gender, Birthday, Program;

 public Person(String name, String id, String gender, String birthday, String program) {
  Name = name;
  Id = id;
  Gender = gender;
  Birthday = birthday;
  Program = program;
 }

 public String getName() {
  return Name;
 }

 public void setName(String name) {
  Name = name;
 }

 public String getId() {
  return Id;
 }

 public void setId(String id) {
  Id = id;
 }

 public String getGender() {
  return Gender;
 }

 public void setGender(String gender) {
  Gender = gender;
 }

 public String getBirthday() {
  return Birthday;
 }

 public void setBirthday(String birthday) {
  Birthday = birthday;
 }

 public String getProgram() {
  return Program;
 }

 public void setProgram(String program) {
  Program = program;
 }
}
