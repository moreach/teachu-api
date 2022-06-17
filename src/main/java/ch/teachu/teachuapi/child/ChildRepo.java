package ch.teachu.teachuapi.child;

import ch.teachu.teachuapi.child.dto.ChildResponse;
import ch.teachu.teachuapi.child.dto.OutlineChildResponse;
import ch.teachu.teachuapi.exam.ExamRepo;
import ch.teachu.teachuapi.parent.AbstractRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.ParentStudent.PARENT_STUDENT;
import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class ChildRepo extends AbstractRepo {

    protected final ExamRepo gradeRepo;

    public ChildRepo(ExamRepo gradeRepo) {
        this.gradeRepo = gradeRepo;
    }

    public List<OutlineChildResponse> findChildren(UUID parentId) {
        return sql().select(USER.ID.as(OutlineChildResponse.ID),
                USER.FIRST_NAME.as(OutlineChildResponse.FIRST_NAME),
                USER.LAST_NAME.as(OutlineChildResponse.LAST_NAME))
                .from(USER)
                .join(PARENT_STUDENT)
                .on(USER.ID.eq(PARENT_STUDENT.STUDENT_ID))
                .where(PARENT_STUDENT.PARENT_ID.eq(parentId))
                .fetchInto(OutlineChildResponse.class);
    }

    public ChildResponse loadBaseData(UUID parentId, UUID childId) {
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

    public boolean isParentOfChild(UUID parentId, UUID childId) {
        return sql().fetchExists(sql().select()
                .from(PARENT_STUDENT)
                .where(PARENT_STUDENT.STUDENT_ID.eq(childId)
                        .and(PARENT_STUDENT.PARENT_ID.eq(parentId))));
    }
}
