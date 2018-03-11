package com.springapp.mvc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewExams {
    private Map<String,List<SelectExamView>> exams = null;  // CourseName Like IIT/MBA and each value is a list of applicable exams
    public ViewExams(){
        exams = new HashMap<>();
    }

    public Map<String, List<SelectExamView>> getExams() {
        return exams;
    }
}
