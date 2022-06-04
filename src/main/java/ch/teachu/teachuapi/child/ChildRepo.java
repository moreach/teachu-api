package ch.teachu.teachuapi.child;

import ch.teachu.teachuapi.child.dto.ChildResponse;
import ch.teachu.teachuapi.child.dto.OutlineChildDTO;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.teachuapi.grade.GradeRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.ParentStudent.PARENT_STUDENT;
import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class ChildRepo extends AbstractRepo {

    protected final GradeRepo gradeRepo;

    public ChildRepo(GradeRepo gradeRepo) {
        this.gradeRepo = gradeRepo;
    }

    public List<OutlineChildDTO> findChildren(UUID parentId) {
        return sql().select(USER.ID.as(OutlineChildDTO.ID),
                USER.FIRST_NAME.as(OutlineChildDTO.FIRST_NAME),
                USER.LAST_NAME.as(OutlineChildDTO.LAST_NAME))
                .from(USER)
                .join(PARENT_STUDENT)
                .on(USER.ID.eq(PARENT_STUDENT.STUDENT_ID))
                .where(PARENT_STUDENT.PARENT_ID.eq(parentId))
                .fetchInto(OutlineChildDTO.class);
    }

    public Optional<ChildResponse> findChild(UUID parentId, UUID studentId) {
        if (!isParentOfChild(parentId, studentId)) {
            return Optional.empty();
        }

        ChildResponse childResponse = loadBaseData(parentId, studentId);
        childResponse.setMarks(gradeRepo.loadGrades(studentId));
        return Optional.of(childResponse);
    }

    protected boolean isParentOfChild(UUID parentId, UUID childId) {
        return sql().fetchExists(sql().select()
                .from(PARENT_STUDENT)
                .where(PARENT_STUDENT.STUDENT_ID.eq(childId)
                        .and(PARENT_STUDENT.PARENT_ID.eq(parentId))));
    }

    protected ChildResponse loadBaseData(UUID parentId, UUID childId) {
        return sql().select(USER.EMAIL.as(ChildResponse.EMAIL),
                        USER.FIRST_NAME.as(ChildResponse.FIRST_NAME),
                        USER.LAST_NAME.as(ChildResponse.LAST_NAME),
                        USER.BIRTHDAY.as(ChildResponse.BIRTHDAY),
                        USER.SEX.as(ChildResponse.SEX),
                        USER.CITY.as(ChildResponse.CITY),
                        USER.POSTAL_CODE.as(ChildResponse.POSTAL_CODE),
                        USER.STREET.as(ChildResponse.STREET),
                        USER.PHONE.as(ChildResponse.PHONE),
                        USER.PROFILE_IMG.as(ChildResponse.PROFILE_IMAGE))
                .from(USER)
                .join(PARENT_STUDENT)
                .on(PARENT_STUDENT.STUDENT_ID.eq(childId))
                .where(USER.ID.eq(childId)
                        .and(PARENT_STUDENT.PARENT_ID.eq(parentId)))
                .fetchOneInto(ChildResponse.class);
    }
}
