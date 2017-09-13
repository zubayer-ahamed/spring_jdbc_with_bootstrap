package com.coderslab.dao;

import com.coderslab.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class studentDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Student> getAllStudent() {
        String sql = "select * from student";
        List<Student> students = jdbcTemplate.query(sql, new StudentMapper());
        return students;
    }

    public Student getStudent(int id) {
        String sql = "select * from student where id=?";
        Student student = (Student) jdbcTemplate.queryForObject(sql, new Object[]{id}, new StudentMapper());
        return student;
    }

    public static class StudentMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setAge(rs.getInt("age"));
            return student;
        }

    }

    public boolean createStudent(Student student) {
        String sql = "insert into student (name, age) values (?, ?)";
        int value = jdbcTemplate.update(sql, new Object[]{student.getName(), student.getAge()});
        if (value > 0) {
            return true;
        }
        return false;
    }

    public boolean updateStudent(Student student) {
        String sql = "update student set name=?, age=? where id=?";
        int value = jdbcTemplate.update(sql, new Object[]{student.getName(), student.getAge(), student.getId()});
        if (value > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        String sql = "delete from student where id=?";
        int value = jdbcTemplate.update(sql, new Object[]{id});
        if (value > 0) {
            return true;
        }
        return false;
    }

}
