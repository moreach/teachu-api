package ch.teachu.teachuapi.lookup;

import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.lookup.dto.*;
import ch.teachu.teachuapi.parent.AbstractRepo;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
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
import static org.jooq.impl.DSL.*;

@Repository
public class LookupRepo extends AbstractRepo {

    public LookupResponse lookupSemesters(LookupRequest lookupRequest) {
        return lookup(lookupRequest, SEMESTER, SEMESTER.ID, SEMESTER.NAME);
    }

    public LookupResponse lookupSchoolClass(LookupSchoolClassRequest lookupRequest, UUID teacherId) {
        if (lookupRequest.isOnlyLoadOwnSchoolClasses()) {
            return lookup(lookupRequest, SCHOOL_CLASS, SCHOOL_CLASS.ID, SCHOOL_CLASS.NAME, SCHOOL_CLASS.TEACHER_ID.eq(teacherId));
        }
        return lookup(lookupRequest, SCHOOL_CLASS, SCHOOL_CLASS.ID, SCHOOL_CLASS.NAME);
    }

    public LookupResponse lookupSubjects(SubjectLookupRequest lookupRequest, UUID teacherId) {
        List<Condition> conditionList = new ArrayList<>();
        if (lookupRequest.isOnlyLoadOwnSubjects()) {
            addCondition(conditionList, SCHOOL_CLASS_SUBJECT.TEACHER_ID.eq(teacherId), teacherId);
        }
        addCondition(conditionList, SCHOOL_CLASS_SUBJECT.SCHOOL_CLASS_ID.eq(lookupRequest.getSchoolClass()), lookupRequest.getSchoolClass());
        conditionList.addAll(defaultLookupConditions(lookupRequest, SUBJECT.ID, SUBJECT.NAME));

        return new LookupResponse(
                sql().selectDistinct(SUBJECT.ID, SUBJECT.NAME)
                        .from(SUBJECT)
                        .join(SCHOOL_CLASS_SUBJECT).on(SCHOOL_CLASS_SUBJECT.SUBJECT_ID.eq(SUBJECT.ID))
                        .where(conditionList)
                        .stream().map(this::mapLookup)
                        .collect(Collectors.toList()
        ));
    }

    private LookupResponse lookup(LookupRequest request, TableImpl<?> table, TableField<?, UUID> idField, TableField<?, String> nameField, Condition... conditions) {
        List<Condition> conditionList = new ArrayList<>(Arrays.asList(conditions));
        conditionList.addAll(defaultLookupConditions(request, idField, nameField));

        return new LookupResponse(
                sql().select(idField, nameField)
                .from(table)
                .where(conditionList)
                .stream().map(this::mapLookup)
                .collect(Collectors.toList()));
    }

    public LookupResponse lookupStudent(LookupStudentRequest request) {
        List<Condition> conditions = new ArrayList<>(List.of(USER.ROLE.eq(UserRole.STUDENT)));
        conditions.addAll(defaultLookupConditions(request, USER.ID, concat(USER.FIRST_NAME, val(" "), USER.LAST_NAME)));
        addCondition(conditions, SCHOOL_CLASS_USER.SCHOOL_CLASS_ID.eq(request.getSchoolClassId()), request.getSchoolClassId());
        addCondition(conditions, notTakenExamCondition(request.getNotTakenExamId()), request.getNotTakenExamId());
        return new LookupResponse(
                sql().selectDistinct(USER.ID, concat(USER.FIRST_NAME, val(" "), USER.LAST_NAME))
                        .from(USER)
                        .leftJoin(SCHOOL_CLASS_USER).on(SCHOOL_CLASS_USER.USER_ID.eq(USER.ID))
                        .where(conditions)
                        .stream().map(this::mapLookup)
                        .collect(Collectors.toList()));
    }

    private Condition notTakenExamCondition(UUID notTakenExamId) {
        return sql().select(count())
                .from(GRADE)
                .where(GRADE.STUDENT_ID.eq(USER.ID).and(GRADE.EXAM_ID.eq(notTakenExamId))).eq(sql().select(val(0)));
    }

    private List<Condition> defaultLookupConditions(LookupRequest request, Field<UUID> idField, Field<String> nameField) {
        List<Condition> conditionList = new ArrayList<>();
        addCondition(conditionList, idField.eq(request.getId()), request.getId());
        addCondition(conditionList, nameField.likeIgnoreCase(concat(inline("%"), val(request.getSearch()), inline("%"))), request.getSearch());
        return conditionList;
    }

    private LookupDTO mapLookup(Record2<UUID, String> result) {
        return new LookupDTO(result.value1(), result.value2());
    }
}
