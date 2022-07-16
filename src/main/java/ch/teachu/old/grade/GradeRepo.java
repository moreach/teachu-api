//package ch.teachu.old.grade;
//
//import ch.teachu.old.grade.dto.GradeResponse;
//import ch.teachu.old.grade.dto.SchoolClassGradesResponse;
//import ch.teachu.old.grade.dto.SemesterGradesResponse;
//import ch.teachu.old.grade.dto.SubjectGradesResponse;
//import ch.teachu.old.parent.AbstractRepo;
//import ch.teachu.teachuapi.generated.tables.User;
//import ch.teachu.old.util.DateUtil;
//import ch.teachu.old.util.Holder;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;
//import static ch.teachu.teachuapi.generated.tables.Grade.GRADE;
//import static ch.teachu.teachuapi.generated.tables.SchoolClass.SCHOOL_CLASS;
//import static ch.teachu.teachuapi.generated.tables.SchoolClassSubject.SCHOOL_CLASS_SUBJECT;
//import static ch.teachu.teachuapi.generated.tables.Semester.SEMESTER;
//import static ch.teachu.teachuapi.generated.tables.Subject.SUBJECT;
//import static ch.teachu.teachuapi.generated.tables.User.USER;
//
//@Repository
//public class GradeRepo extends AbstractRepo {
//
//    private static final String CLASS_TEACHER = "class_teacher";
//    private static final String SUBJECT_TEACHER = "subject_teacher";
//    private static final String STUDENT = "student";
//
//    public List<SemesterGradesResponse> loadGrades(UUID studentId) {
//        List<SemesterGradesResponse> semesters = new ArrayList<>();
//
//        Holder<SemesterGradesResponse> currentSemester = new Holder<>();
//        Holder<SchoolClassGradesResponse> currentSchoolClass = new Holder<>();
//        Holder<SubjectGradesResponse> currentSubject = new Holder<>();
//
//        User classTeacher = USER.as(CLASS_TEACHER);
//        User subjectTeacher = USER.as(SUBJECT_TEACHER);
//        User student = USER.as(STUDENT);
//
//        // load all grades in one stream and sort it
//        sql().select(SEMESTER.ID,
//                        SEMESTER.NAME,
//                        SCHOOL_CLASS.ID,
//                        SCHOOL_CLASS.NAME,
//                        classTeacher.FIRST_NAME,
//                        classTeacher.LAST_NAME,
//                        SUBJECT.ID,
//                        SUBJECT.NAME,
//                        SUBJECT.WEIGHT,
//                        subjectTeacher.FIRST_NAME,
//                        subjectTeacher.LAST_NAME,
//                        GRADE.MARK,
//                        GRADE.NOTE,
//                        EXAM.ID,
//                        EXAM.NAME,
//                        EXAM.DESCRIPTION,
//                        EXAM.WEIGHT,
//                        EXAM.DATE,
//                        EXAM.VIEW_DATE,
//                        student.FIRST_NAME,
//                        student.LAST_NAME,
//                        student.ID)
//                .from(GRADE)
//                .join(student).on(GRADE.STUDENT_ID.eq(student.ID))
//                .join(EXAM).on(GRADE.EXAM_ID.eq(EXAM.ID))
//                .join(SCHOOL_CLASS_SUBJECT).on(EXAM.SCHOOL_CLASS_SUBJECT_ID.eq(SCHOOL_CLASS_SUBJECT.ID))
//                .join(subjectTeacher).on(SCHOOL_CLASS_SUBJECT.TEACHER_ID.eq(subjectTeacher.ID))
//                .rightJoin(SUBJECT).on(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(SUBJECT.ID))
//                .join(SCHOOL_CLASS).on(SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(SCHOOL_CLASS.ID))
//                .join(classTeacher).on(SCHOOL_CLASS.TEACHER_ID.eq(classTeacher.ID))
//                .join(SEMESTER).on(EXAM.SEMESTER_ID.eq(SEMESTER.ID))
//                .where(student.ID.eq(studentId))
//                .orderBy(SEMESTER.FROM.desc(), SCHOOL_CLASS.NAME, SUBJECT.NAME, EXAM.DATE.desc())
//                .stream().forEach(record -> {
//                    // create the grade itself
//                     GradeResponse grade = new GradeResponse(record.value15(), record.value16(), record.value17(),
//                            DateUtil.toDate(record.value18()), DateUtil.toDate(record.value19()),
//                             record.value12(), record.value13());
//
//                    // check if it is a new semester
//                    if (!currentSemester.exists() || !currentSemester.get().getSemesterId().equals(record.get(0))) {
//                        SemesterGradesResponse semesterExamsResponse = new SemesterGradesResponse(record.value1(),
//                                record.value2(), new ArrayList<>());
//
//                        semesters.add(semesterExamsResponse);
//                        currentSemester.set(semesterExamsResponse);
//                        // force the child objects to create a new object
//                        currentSchoolClass.clear();
//                        currentSubject.clear();
//                    }
//
//                    // same as semester for school class
//                    if (!currentSchoolClass.exists() || !currentSchoolClass.get().getSchoolClassId().equals(record.get(2))) {
//                        SchoolClassGradesResponse schoolClassExamsResponse = new SchoolClassGradesResponse(record.value3(),
//                                record.value4(), record.value5(), record.value6(), 0, new ArrayList<>());
//
//                        currentSchoolClass.set(schoolClassExamsResponse);
//                        currentSemester.get().getSchoolClasses().add(schoolClassExamsResponse);
//                        currentSubject.clear();
//                    }
//
//                    // same as semester for subject
//                    if (!currentSubject.exists() || !currentSubject.get().getSubjectId().equals(record.get(6))) {
//                        SubjectGradesResponse subjectExamsResponse = new SubjectGradesResponse(record.value7(),
//                                record.value8(), record.value10(), record.value11(),
//                                record.value9(), 0, new ArrayList<>());
//
//                        currentSubject.set(subjectExamsResponse);
//                        currentSchoolClass.get().getSubjects().add(subjectExamsResponse);
//                    }
//
//                    currentSubject.get().getGrades().add(grade);
//                });
//
//        return semesters;
//    }
//}
