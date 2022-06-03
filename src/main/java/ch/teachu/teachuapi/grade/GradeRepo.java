package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.generated.tables.User;
import ch.teachu.teachuapi.generated.tables.records.GradeRecord;
import ch.teachu.teachuapi.grade.dto.*;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.teachuapi.util.DateUtil;
import org.jooq.Condition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;
import static ch.teachu.teachuapi.generated.tables.Grade.GRADE;
import static ch.teachu.teachuapi.generated.tables.SchoolClass.SCHOOL_CLASS;
import static ch.teachu.teachuapi.generated.tables.SchoolClassSubject.SCHOOL_CLASS_SUBJECT;
import static ch.teachu.teachuapi.generated.tables.Semester.SEMESTER;
import static ch.teachu.teachuapi.generated.tables.Subject.SUBJECT;
import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class GradeRepo extends AbstractRepo {

    private static final String CLASS_TEACHER = "class_teacher";
    private static final String SUBJECT_TEACHER = "subject_teacher";
    private static final String STUDENT = "student";

    public List<SemesterGradesDTO> loadStudentGrades(UUID studentId) {
        User subjectTeacher = USER.as(SUBJECT_TEACHER);
        User student = USER.as(STUDENT);
        return loadGrades(subjectTeacher, student, student.ID.eq(studentId));
    }

    public List<SemesterGradesDTO> loadTeacherGrades(UUID teacherId) {
        User subjectTeacher = USER.as(SUBJECT_TEACHER);
        User student = USER.as(STUDENT);
        return loadGrades(subjectTeacher, student, subjectTeacher.ID.eq(teacherId));
    }

    private List<SemesterGradesDTO> loadGrades(User subjectTeacher, User student, Condition condition) {
        final List<SemesterGradesDTO> semesterDTOS = new ArrayList<>();
        final AverageObjectHolder<SemesterGradesDTO> currentSemester = new AverageObjectHolder<>();
        final AverageObjectHolder<SchoolClassGradesDTO> currentSchoolClass = new AverageObjectHolder<>();
        final AverageObjectHolder<SubjectGradesDTO> currentSubject = new AverageObjectHolder<>();
        final AverageObjectHolder<StudentGradesDTO> currentStudent = new AverageObjectHolder<>();

        User classTeacher = USER.as(CLASS_TEACHER);

        sql().select(SEMESTER.ID,
                        SEMESTER.NAME,
                        SCHOOL_CLASS.ID,
                        SCHOOL_CLASS.NAME,
                        classTeacher.FIRST_NAME,
                        classTeacher.LAST_NAME,
                        SUBJECT.ID,
                        SUBJECT.NAME,
                        SUBJECT.WEIGHT,
                        subjectTeacher.FIRST_NAME,
                        subjectTeacher.LAST_NAME,
                        GRADE.ID,
                        GRADE.MARK,
                        GRADE.NOTE,
                        EXAM.NAME,
                        EXAM.DESCRIPTION,
                        EXAM.WEIGHT,
                        EXAM.DATE,
                        EXAM.VIEW_DATE,
                        student.ID,
                        student.FIRST_NAME,
                        student.LAST_NAME)
                .from(GRADE)
                .join(student).on(GRADE.STUDENT_ID.eq(student.ID))
                .join(EXAM).on(GRADE.EXAM_ID.eq(EXAM.ID))
                .join(SCHOOL_CLASS_SUBJECT).on(EXAM.SCHOOL_CLASS_SUBJECT_ID.eq(SCHOOL_CLASS_SUBJECT.ID))
                .join(subjectTeacher).on(SCHOOL_CLASS_SUBJECT.TEACHER_ID.eq(subjectTeacher.ID))
                .rightJoin(SUBJECT).on(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(SUBJECT.ID))
                .join(SCHOOL_CLASS).on(SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(SCHOOL_CLASS.ID))
                .join(classTeacher).on(SCHOOL_CLASS.TEACHER_ID.eq(classTeacher.ID))
                .join(SEMESTER).on(EXAM.SEMESTER_ID.eq(SEMESTER.ID))
                .where(condition)
                .orderBy(SEMESTER.FROM.desc(), SCHOOL_CLASS.NAME, SUBJECT.NAME, EXAM.DATE.desc())
                .forEach(grade -> {
                    ExamGradeDTO examGradeDTO = new ExamGradeDTO(grade.value12(), grade.value15(), grade.value16(), grade.value17(),
                            DateUtil.toDate(grade.value18()),
                            DateUtil.toDate(grade.value19()),
                            grade.value13(), grade.value14());

                    if (currentSemester.isNewId(grade.value1())) {
                        SemesterGradesDTO semesterGradesDTO = new SemesterGradesDTO(grade.value2(), new ArrayList<>());
                        semesterDTOS.add(semesterGradesDTO);
                        currentSemester.newObject(semesterGradesDTO, grade.value1());
                        currentSchoolClass.forceNew();
                        currentSubject.forceNew();
                    }

                    boolean newSchoolClass = false;
                    if (currentSchoolClass.isNewId(grade.value3())) {
                        if (currentSchoolClass.get() != null) {
                            currentSubject.get().setAverageMark(currentSubject.getAverage());
                            currentSchoolClass.add(currentSubject.getAverage(), currentSubject.get().getWeight());
                            currentSchoolClass.get().setAverageMark(currentSchoolClass.getAverage());
                            newSchoolClass = true;
                        }
                        SchoolClassGradesDTO schoolClassDTO = new SchoolClassGradesDTO(grade.value4(), grade.value5(),
                                grade.value6(), 0, new ArrayList<>());
                        currentSchoolClass.newObject(schoolClassDTO, grade.value3());
                        currentSemester.get().getSchoolClasses().add(currentSchoolClass.get());
                    }

                    boolean newSubject = false;
                    if (currentSubject.isNewId(grade.value7())) {
                        if (currentSubject.get() != null) {
                            currentStudent.get().setAverageMark(currentStudent.getAverage());
                            currentSubject.add(currentStudent.getAverage(), 1);
                            currentSubject.get().setAverageMark(currentSubject.getAverage());
                            newSubject = true;
                            if (!newSchoolClass) {
                                currentSubject.add(currentStudent.getAverage(), 1);
                            }
                        }
                        SubjectGradesDTO subjectDTO = new SubjectGradesDTO(grade.value8(), grade.value10(),
                                grade.value11(), grade.value9(), 0, new ArrayList<>());
                        currentSubject.newObject(subjectDTO, grade.value7());
                        currentSchoolClass.get().getSubjects().add(currentSubject.get());
                    }

                    if (currentStudent.isNewId(grade.value20())) {
                        if (currentStudent.get() != null) {
                            currentStudent.get().setAverageMark(currentStudent.getAverage());
                            if (!newSubject) {
                                currentStudent.add(currentStudent.getAverage(), 1);
                            }
                        }
                        StudentGradesDTO gradesDTO = new StudentGradesDTO(grade.value20(), grade.value21(),
                                grade.value22(), 0, new ArrayList<>());
                        currentStudent.newObject(gradesDTO, grade.value20());
                        currentSubject.get().getStudents().add(currentStudent.get());
                    }

                    currentStudent.add(examGradeDTO.getMark(), examGradeDTO.getWeight());
                    currentStudent.get().getGrades().add(examGradeDTO);
                });

        if (currentSubject.get() != null) {
            currentSubject.get().setAverageMark(currentSubject.getAverage());
            currentSchoolClass.add(currentSubject.getAverage(), currentSubject.get().getWeight());
            currentSchoolClass.get().setAverageMark(currentSchoolClass.getAverage());
        }

        return semesterDTOS;
    }

    public void createGrade(CreateGradeRequest request) {
        GradeRecord grade = sql().newRecord(GRADE);
        grade.setStudentId(request.getStudentId());
        grade.setMark(request.getMark());
        grade.setNote(request.getNote());
        grade.setExamId(request.getExamId());
        grade.store();
    }

    public void changeGrade(ChangeGradeRequest request) {
        GradeRecord grade = sql().selectFrom(GRADE).where(GRADE.ID.eq(request.getId())).fetchOptional()
                .orElseThrow(() -> new NotFoundException("Grade"));
        grade.setStudentId(request.getStudentId());
        grade.setMark(request.getMark());
        grade.setNote(request.getNote());
        grade.setExamId(request.getExamId());
        grade.store();
    }

    public List<GradeDTO> loadByExam(UUID examId) {
        return sql().select(GRADE.ID, USER.FIRST_NAME, USER.LAST_NAME, GRADE.MARK, GRADE.NOTE)
                .from(GRADE)
                .join(USER).on(GRADE.STUDENT_ID.eq(USER.ID))
                .where(GRADE.EXAM_ID.eq(examId))
                .stream().map(record -> new GradeDTO(record.value1(), record.value2(), record.value3(), record.value4(), record.value5()))
                .collect(Collectors.toList());
    }
}
