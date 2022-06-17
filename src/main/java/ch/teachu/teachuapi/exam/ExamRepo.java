package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.exam.dto.*;
import ch.teachu.teachuapi.generated.tables.User;
import ch.teachu.teachuapi.generated.tables.records.ExamRecord;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.teachuapi.util.DateUtil;

import ch.teachu.teachuapi.util.Holder;
import org.jooq.Condition;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;
import static ch.teachu.teachuapi.generated.tables.Grade.GRADE;
import static ch.teachu.teachuapi.generated.tables.SchoolClass.SCHOOL_CLASS;
import static ch.teachu.teachuapi.generated.tables.SchoolClassSemester.SCHOOL_CLASS_SEMESTER;
import static ch.teachu.teachuapi.generated.tables.SchoolClassSubject.SCHOOL_CLASS_SUBJECT;
import static ch.teachu.teachuapi.generated.tables.Semester.SEMESTER;
import static ch.teachu.teachuapi.generated.tables.Subject.SUBJECT;
import static ch.teachu.teachuapi.generated.tables.User.USER;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.val;

@Repository
public class ExamRepo extends AbstractRepo {

    private static final String CLASS_TEACHER = "class_teacher";
    private static final String SUBJECT_TEACHER = "subject_teacher";
    private static final String STUDENT = "student";

    public List<SemesterExamsResponse> loadExamsByTeacher(UUID teacherId) {
        User subjectTeacher = USER.as(SUBJECT_TEACHER);
        User student = USER.as(STUDENT);
        return loadExams(subjectTeacher.ID.eq(teacherId), subjectTeacher, student);
    }

    public List<SemesterExamsResponse> loadExamsByStudent(UUID studentId) {
        User subjectTeacher = USER.as(SUBJECT_TEACHER);
        User student = USER.as(STUDENT);
        return loadExams(student.ID.eq(studentId), subjectTeacher, student);
    }

    private List<SemesterExamsResponse> loadExams(Condition condition, User subjectTeacher, User student) {
        List<SemesterExamsResponse> semesters = new ArrayList<>();

        Holder<SemesterExamsResponse> currentSemester = new Holder<>();
        Holder<SchoolClassExamsResponse> currentSchoolClass = new Holder<>();
        Holder<SubjectExamsResponse> currentSubject = new Holder<>();
        Holder<ExamResponse> currentExam = new Holder<>();

        User classTeacher = USER.as(CLASS_TEACHER);

        // load all grades in one stream and sort it
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
                        EXAM.ID,
                        EXAM.NAME,
                        EXAM.DESCRIPTION,
                        EXAM.WEIGHT,
                        EXAM.DATE,
                        EXAM.VIEW_DATE,
                        student.FIRST_NAME,
                        student.LAST_NAME,
                        student.ID)
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
                .stream().forEach(record -> {
                    // create the grade itself
                    GradeResponse grade = new GradeResponse((UUID) record.get(11), (UUID) record.get(22), (Double) Objects.requireNonNull(record.get(12)),
                            (String) record.get(13), (String) record.get(20), (String) record.get(21));

                    // check if it is a new semester
                    if (!currentSemester.exists() || !currentSemester.get().getSemesterId().equals(record.get(0))) {
                        SemesterExamsResponse semesterExamsResponse = new SemesterExamsResponse((UUID) record.get(0),
                                (String) record.get(1), new ArrayList<>());

                        semesters.add(semesterExamsResponse);
                        currentSemester.set(semesterExamsResponse);
                        // force the child objects to create a new object
                        currentSchoolClass.clear();
                        currentSubject.clear();
                        currentExam.clear();
                    }

                    // same as semester for school class
                    if (!currentSchoolClass.exists() || !currentSchoolClass.get().getSchoolClassId().equals(record.get(2))) {
                        SchoolClassExamsResponse schoolClassExamsResponse = new SchoolClassExamsResponse((UUID) record.get(2),
                                (String) record.get(3), (String) record.get(4), (String) record.get(5), 0, new ArrayList<>());

                        currentSchoolClass.set(schoolClassExamsResponse);
                        currentSemester.get().getSchoolClasses().add(schoolClassExamsResponse);
                        currentSubject.clear();
                        currentExam.clear();
                    }

                    // same as semester for subject
                    if (!currentSubject.exists() || !currentSubject.get().getSubjectId().equals(record.get(6))) {
                        SubjectExamsResponse subjectExamsResponse = new SubjectExamsResponse((UUID) record.get(6),
                                (String) record.get(7), (String) record.get(9), (String) record.get(10),
                                (Double) Objects.requireNonNull(record.get(8)), 0, new ArrayList<>());

                        currentSubject.set(subjectExamsResponse);
                        currentSchoolClass.get().getSubjects().add(subjectExamsResponse);
                        currentExam.clear();
                    }

                    // same as semester for exam
                    if (!currentExam.exists() || !currentExam.get().getId().equals(record.get(14))) {
                        ExamResponse examResponse = new ExamResponse((UUID) record.get(14), (String) record.get(15),
                                (String) record.get(16), (Double) Objects.requireNonNull(record.get(17)),
                                DateUtil.toDate((LocalDate) Objects.requireNonNull(record.get(18))),
                                DateUtil.toDate((LocalDate) Objects.requireNonNull(record.get(19))), 0, new ArrayList<>());
                        currentSubject.get().getExams().add(examResponse);
                        currentExam.set(examResponse);
                    }

                    currentExam.get().getGrades().add(grade);
                });

        return semesters;
    }

    public void createExam(CreateExamRequest request) {
        ExamRecord examRecord = sql().newRecord(EXAM);
        examRecord.setId(UUID.randomUUID());
        examRecord.setName(request.getName());
        examRecord.setDescription(request.getDescription());
        examRecord.setWeight(request.getWeight());
        examRecord.setDate(DateUtil.toLocalDate(request.getDate()));
        examRecord.setViewDate(DateUtil.toLocalDate(request.getViewDate()));
        examRecord.setSemesterId(request.getSemesterId());
        examRecord.setSchoolClassSubjectId(computeSchoolClassSubjectId(request.getSubjectId(), request.getSchoolClassId()));
        examRecord.store();
    }

    private UUID computeSchoolClassSubjectId(UUID subjectId, UUID schoolClassId) {
        return sql().select(SCHOOL_CLASS_SUBJECT.ID)
                .from(SCHOOL_CLASS_SUBJECT)
                .where(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(subjectId),
                        SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(schoolClassId))
                .fetchOneInto(UUID.class);
    }

    public boolean hasSchoolClassSubjectAndSemester(UUID schoolClassId, UUID subjectId, UUID semesterId) {
        return sql().fetchExists(sql().select().from(SCHOOL_CLASS)
                .join(SCHOOL_CLASS_SEMESTER).on(SCHOOL_CLASS.ID.eq(SCHOOL_CLASS_SEMESTER.SCHOOL_CLASS_ID))
                .join(SCHOOL_CLASS_SUBJECT).on(SCHOOL_CLASS.ID.eq(SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID))
                .where(SCHOOL_CLASS.ID.eq(schoolClassId), SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(subjectId), SCHOOL_CLASS_SEMESTER.SEMESTER_ID.eq(semesterId)));
    }

    public boolean examUsed(UUID examId) {
        return sql().fetchExists(sql().selectFrom(GRADE).where(GRADE.EXAM_ID.eq(examId)));
    }

    public void changeExam(ChangeExamRequest request) {
        int result = sql().update(EXAM)
                .set(EXAM.NAME, request.getName())
                .set(EXAM.DESCRIPTION, request.getDescription())
                .set(EXAM.WEIGHT, request.getWeight())
                .set(EXAM.DATE, DateUtil.toLocalDate(request.getDate()))
                .set(EXAM.VIEW_DATE, DateUtil.toLocalDate(request.getViewDate()))
                .where(EXAM.ID.eq(request.getId()))
                .execute();

        if (result == 0) {
            throw new NotFoundException("Exam");
        }
    }
}
