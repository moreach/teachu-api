package ch.teachu.teachuapi.student;

import ch.teachu.teachuapi.generated.tables.User;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.teachuapi.student.dto.GradeDTO;
import ch.teachu.teachuapi.student.dto.StudentSchoolClassDTO;
import ch.teachu.teachuapi.student.dto.StudentSemesterDTO;
import ch.teachu.teachuapi.student.dto.StudentSubjectDTO;
import ch.teachu.teachuapi.util.DateUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;
import static ch.teachu.teachuapi.generated.tables.Grade.GRADE;
import static ch.teachu.teachuapi.generated.tables.SchoolClass.SCHOOL_CLASS;
import static ch.teachu.teachuapi.generated.tables.SchoolClassSubject.SCHOOL_CLASS_SUBJECT;
import static ch.teachu.teachuapi.generated.tables.Semester.SEMESTER;
import static ch.teachu.teachuapi.generated.tables.Subject.SUBJECT;
import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class StudentRepo extends AbstractRepo {

    private static final String CLASS_TEACHER = "class_teacher";
    private static final String SUBJECT_TEACHER = "subject_teacher";
    private static final String STUDENT = "student";

    public List<StudentSemesterDTO> loadMarks(UUID studentId) {
        final List<StudentSemesterDTO> semesterDTOS = new ArrayList<>();
        final AverageObjectHolder<StudentSemesterDTO> currentSemester = new AverageObjectHolder<>();
        final AverageObjectHolder<StudentSchoolClassDTO> currentSchoolClass = new AverageObjectHolder<>();
        final AverageObjectHolder<StudentSubjectDTO> currentSubject = new AverageObjectHolder<>();

        User classTeacher = USER.as(CLASS_TEACHER);
        User subjectTeacher = USER.as(SUBJECT_TEACHER);
        User student = USER.as(STUDENT);

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
                        EXAM.VIEW_DATE)
                .from(GRADE)
                .join(student).on(GRADE.STUDENT_ID.eq(student.ID))
                .join(EXAM).on(GRADE.EXAM_ID.eq(EXAM.ID))
                .join(SCHOOL_CLASS_SUBJECT).on(EXAM.SCHOOL_CLASS_SUBJECT_ID.eq(SCHOOL_CLASS_SUBJECT.ID))
                .join(subjectTeacher).on(SCHOOL_CLASS_SUBJECT.TEACHER_ID.eq(subjectTeacher.ID))
                .rightJoin(SUBJECT).on(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(SUBJECT.ID))
                .join(SCHOOL_CLASS).on(SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(SCHOOL_CLASS.ID))
                .join(classTeacher).on(SCHOOL_CLASS.TEACHER_ID.eq(classTeacher.ID))
                .join(SEMESTER).on(EXAM.SEMESTER_ID.eq(SEMESTER.ID))
                .where(student.ID.eq(studentId))
                .orderBy(SEMESTER.FROM.desc(), SCHOOL_CLASS.NAME, SUBJECT.NAME, EXAM.DATE.desc())
                .forEach(grade -> {
                    GradeDTO gradeDTO = new GradeDTO(grade.value12(), grade.value15(), grade.value16(), grade.value17(),
                            DateUtil.toDate(grade.value18()),
                            DateUtil.toDate(grade.value19()),
                            grade.value13(), grade.value14());

                    if (currentSemester.isNewId(grade.value1())) {
                        StudentSemesterDTO studentSemesterDTO = new StudentSemesterDTO(grade.value2(), new ArrayList<>());
                        semesterDTOS.add(studentSemesterDTO);
                        currentSemester.newObject(studentSemesterDTO, grade.value1());
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
                        StudentSchoolClassDTO schoolClassDTO = new StudentSchoolClassDTO(grade.value4(), grade.value5(),
                                grade.value6(), 0, new ArrayList<>());
                        currentSchoolClass.newObject(schoolClassDTO, grade.value3());
                        currentSemester.get().getSchoolClasses().add(currentSchoolClass.get());
                    }

                    if (currentSubject.isNewId(grade.value7())) {
                        if (currentSubject.get() != null) {
                            currentSubject.get().setAverageMark(currentSubject.getAverage());
                            if (!newSchoolClass) {
                                currentSchoolClass.add(currentSubject.getAverage(), currentSubject.get().getWeight());
                            }
                        }
                        StudentSubjectDTO subjectDTO = new StudentSubjectDTO(grade.value8(), grade.value10(),
                                grade.value11(), grade.value9(), 0, new ArrayList<>());
                        currentSubject.newObject(subjectDTO, grade.value7());
                        currentSchoolClass.get().getSubjects().add(currentSubject.get());
                    }

                    currentSubject.add(gradeDTO.getMark(), gradeDTO.getWeight());
                    currentSubject.get().getGrades().add(gradeDTO);
                });

        if (currentSubject.get() != null) {
            currentSubject.get().setAverageMark(currentSubject.getAverage());
            currentSchoolClass.add(currentSubject.getAverage(), currentSubject.get().getWeight());
            currentSchoolClass.get().setAverageMark(currentSchoolClass.getAverage());
        }

        return semesterDTOS;
    }
}
