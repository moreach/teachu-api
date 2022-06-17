package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.generated.tables.User;
import ch.teachu.teachuapi.generated.tables.records.GradeRecord;
import ch.teachu.teachuapi.grade.dto.*;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.teachuapi.util.DateUtil;
import ch.teachu.teachuapi.util.DoubleHolder;
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
import static ch.teachu.teachuapi.generated.tables.SchoolClassUser.SCHOOL_CLASS_USER;
import static ch.teachu.teachuapi.generated.tables.Semester.SEMESTER;
import static ch.teachu.teachuapi.generated.tables.Subject.SUBJECT;
import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class GradeRepo extends AbstractRepo {

    private static final String CLASS_TEACHER = "class_teacher";
    private static final String SUBJECT_TEACHER = "subject_teacher";
    private static final String STUDENT = "student";

    public UUID createGrade(CreateGradeRequest request, UUID studentId) {
        UUID id = UUID.randomUUID();
        GradeRecord grade = sql().newRecord(GRADE);
        grade.setId(id);
        grade.setStudentId(studentId);
        grade.setMark(request.getMark());
        grade.setNote(request.getNote());
        grade.setExamId(request.getExamId());
        grade.store();
        return id;
    }

    public boolean studentHasGrade(UUID studentId, UUID examId) {
        return sql().fetchExists(sql().select().from(GRADE)
                .where(GRADE.STUDENT_ID.eq(studentId), GRADE.EXAM_ID.eq(examId)));
    }

    public boolean studentCanTakeExam(UUID studentId, UUID examId) {
        return sql().fetchExists(sql().select().from(EXAM)
                .join(SCHOOL_CLASS_SUBJECT).on(EXAM.SCHOOL_CLASS_SUBJECT_ID.eq(SCHOOL_CLASS_SUBJECT.ID))
                .join(SCHOOL_CLASS_USER).on(SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(SCHOOL_CLASS_USER.SCHOOL_CLASS_ID))
                .where(EXAM.ID.eq(examId), SCHOOL_CLASS_USER.USER_ID.eq(studentId)));
    }

    public void changeGrade(ChangeGradeRequest request, UUID studentId) {
        GradeRecord grade = sql().selectFrom(GRADE).where(GRADE.ID.eq(request.getId())).fetchOptional()
                .orElseThrow(() -> new NotFoundException("Grade"));
        grade.setStudentId(studentId);
        grade.setMark(request.getMark());
        grade.setNote(request.getNote());
        grade.setExamId(request.getExamId());
        grade.store();
    }

    public GradesResponse loadWithRestriction(LoadGradeRequest request) {
        List<Condition> conditions = new ArrayList<>();
        addCondition(conditions, EXAM.ID.eq(request.getExamId()), request.getExamId());
        addCondition(conditions, USER.ID.eq(request.getStudentId()), request.getStudentId());

        DoubleHolder sum = new DoubleHolder();
        DoubleHolder weightSum = new DoubleHolder();
        List<ExamGradeResponse> examGrades = sql().select(GRADE.ID, USER.FIRST_NAME, USER.LAST_NAME, GRADE.MARK, GRADE.NOTE, EXAM.WEIGHT,
                        EXAM.NAME, EXAM.DESCRIPTION, EXAM.DATE, EXAM.VIEW_DATE)
                .from(GRADE)
                .join(USER).on(GRADE.STUDENT_ID.eq(USER.ID))
                .join(EXAM).on(GRADE.EXAM_ID.eq(EXAM.ID))
                .where(conditions)
                .stream().map(record -> {
                    sum.plusSet(record.value6() * record.value4());
                    weightSum.plusSet(record.value6());
                    return new ExamGradeResponse(record.value1(), record.value7(), record.value8(), record.value6(),
                            DateUtil.toDate(record.value9()), DateUtil.toDate(record.value10()), record.value4(),
                            record.value5(), record.value2(), record.value3());
                })
                .collect(Collectors.toList());

        return new GradesResponse(sum.divide(weightSum), examGrades);
    }
}
