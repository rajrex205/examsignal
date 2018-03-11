package com.springapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import com.springapp.orm.hibernate.model.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionDaoImpl {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertQuestion(Question question){

        int currentQuestionCountForSubject = getQuestionCountForSubject(question.getSubjectId(),question.getExamId());
        ++currentQuestionCountForSubject;
        String sql = "insert into question " +
                "(exam_id, subject_id, question_number, question_text, marks, option_1, option_2, option_3, option_4,answer) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, new Object[]{question.getExamId(),question.getSubjectId(),currentQuestionCountForSubject, question.getQuestionText(),
                question.getMarks(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),
                question.getAnswer()
        });
    }
    public void deleteQuestion(long questionID){
        Question toBeDeletedQuestion  = fetchQuestionFromID(questionID);

        if(null != toBeDeletedQuestion){
            String sql = "update question set is_deleted=1 " +
                    "where id = ? ";

            jdbcTemplate.update(sql, new Object[]{questionID});
            sql = "update question set question_number=(question_number-1) " +
                    "where exam_id = ? and subject_id = ? " +
                    "and question_number > ? " +
                    "and is_deleted = 0";

            jdbcTemplate.update(sql, new Object[]{toBeDeletedQuestion.getExamId(),toBeDeletedQuestion.getSubjectId(),toBeDeletedQuestion.getQuestionNumber()});
        }
    }
    public void deleteAllQuestionsForExam(long examID){
        String sql = "update question set is_deleted=1 " +
                "where exam_id = ? ";

        jdbcTemplate.update(sql, new Object[]{examID});
    }
    public void deleteAllQuestionsForSubject(long subjectID){
        String sql = "update question set is_deleted=1 " +
                "where subject_id = ? and is_deleted=0";

        jdbcTemplate.update(sql, new Object[]{subjectID});
    }

    public Question fetchQuestionFromID(final long questionID){
        String query = "select id, exam_id, subject_id, question_text, marks, option_1, option_2, option_3, option_4,answer,question_number from question " +
                "where id = ? and is_deleted = 0";
        List<Question> questionList  = this.jdbcTemplate.query(query, new Object[]{questionID}, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Question question1 = new Question();
                question1.setId(rs.getLong(1));
                question1.setExamId(rs.getLong(2));
                question1.setSubjectId(rs.getLong(3));
                question1.setQuestionText(rs.getString(4));
                question1.setMarks(rs.getInt(5));
                question1.setOption1(rs.getString(6));
                question1.setOption2(rs.getString(7));
                question1.setOption3(rs.getString(8));
                question1.setOption4(rs.getString(9));
                question1.setAnswer(rs.getString(10));
                question1.setQuestionNumber(rs.getInt(11));
                return  question1;
            }});
        if(null != questionList && questionList.size()==1){
            return questionList.get(0);
        }
        return  null;
    }

    public Question fetchQuestion(int questionNumber, long examId, long subjectId){
        String query = "select id, exam_id, subject_id, question_text, marks, option_1, option_2, option_3, option_4,answer,question_number from question " +
                "where question_number = ? and exam_id= ? and subject_id = ? and is_deleted = 0";
        List<Question> questionList  = this.jdbcTemplate.query(query, new Object[]{questionNumber,examId,subjectId}, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Question question1 = new Question();
                question1.setId(rs.getLong(1));
                question1.setExamId(rs.getLong(2));
                question1.setSubjectId(rs.getLong(3));
                question1.setQuestionText(rs.getString(4));
                question1.setMarks(rs.getInt(5));
                question1.setOption1(rs.getString(6));
                question1.setOption2(rs.getString(7));
                question1.setOption3(rs.getString(8));
                question1.setOption4(rs.getString(9));
                question1.setAnswer(rs.getString(10));
                question1.setQuestionNumber(rs.getInt(11));
                return  question1;
            }});
        if(null != questionList && questionList.size()==1){
            return questionList.get(0);
        }
        return  null;
    }

    public List<Question> fetchAllQuestionForExam(final long examId){
        String query = "select id, exam_id, subject_id, question_number, question_text, marks, option_1, option_2, option_3, option_4,answer from question " +
                "where exam_id = ? and is_deleted = 0 order by subject_id,question_number";
        return this.jdbcTemplate.query(query, new Object[]{examId}, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Question question1 = new Question();
                question1.setId(rs.getLong(1));
                question1.setExamId(rs.getLong(2));
                question1.setSubjectId(rs.getLong(3));
                question1.setQuestionNumber(rs.getInt(4));
                question1.setQuestionText(rs.getString(5));
                question1.setMarks(rs.getInt(6));
                question1.setOption1(rs.getString(7));
                question1.setOption2(rs.getString(8));
                question1.setOption3(rs.getString(9));
                question1.setOption4(rs.getString(10));
                question1.setAnswer(rs.getString(11));
                return  question1;
            }});
    }

    public Question getFirstQuestionOfExamForSubject(long subjectId, long examId){
        String query = "select id, exam_id, subject_id, question_number, question_text, marks, option_1, option_2, option_3, option_4,answer from question " +
                "where subject_id = ? and exam_id = ? and is_deleted = 0 and question_number=1";
        List<Question> list = this.jdbcTemplate.query(query, new Object[]{subjectId, examId}, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Question question1 = new Question();
                question1.setId(rs.getLong(1));
                question1.setExamId(rs.getLong(2));
                question1.setSubjectId(rs.getLong(3));
                question1.setQuestionNumber(rs.getInt(4));
                question1.setQuestionText(rs.getString(5));
                question1.setMarks(rs.getInt(6));
                question1.setOption1(rs.getString(7));
                question1.setOption2(rs.getString(8));
                question1.setOption3(rs.getString(9));
                question1.setOption4(rs.getString(10));
                question1.setAnswer(rs.getString(11));
                return  question1;
            }});
        if(null != list && list.size()==1){
            return list.get(0);
        } else{
            return null;
        }
    }

    public Integer getQuestionCountForSubject(final long subjectId, final long examId){
        String query = "select count(id) from question " +
                "where subject_id = ? and exam_id = ? and is_deleted = 0";
        return this.jdbcTemplate.queryForObject(query, new Object[]{subjectId,examId}, Integer.class);
    }

    public void updateQuestionForID(Question q){
        String sql = "update question " +
                " set question_text=?, marks=?, option_1= ? ,option_2 = ? ,option_3 = ? , option_4 = ? ,answer = ? " +
                "where id = ? and is_deleted = 0";

        jdbcTemplate.update(sql, new Object[]{q.getQuestionText(),q.getMarks(),q.getOption1(),q.getOption2(),q.getOption3()
                ,q.getOption4(),q.getAnswer(),q.getId()
        });
    }

    public int questionIdCountForAdminId(final long questionId, final long adminId){
        String query = "select count(1) from question q inner join exam e on q.exam_id = e.id inner join course c on e.course_id = c.id " +
                "where c.admin_id = ? and q.id = ? and q.is_deleted = 0";
        List<Integer> count = this.jdbcTemplate.query(query, new Object[]{adminId,questionId}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return  rs.getInt(1);
            }});
        if(count != null && count.size()>0){
            return count.get(0);
        } else {
            return 0;
        }
    }

    public int getTotalMarksForExam(long scoreId){
        String query = "select sum(q.marks) from question q inner join user_exam e on e.exam_id = q.exam_id " +
                "where e.score_id = ? and q.is_deleted = 0 and e.is_deleted = 0";
        List<Integer> totalMarks = this.jdbcTemplate.query(query, new Object[]{scoreId}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                return  rs.getInt(1);
            }});
        if(totalMarks != null && totalMarks.size()>0){
            return totalMarks.get(0);
        } else {
            return 0;
        }
    }
}