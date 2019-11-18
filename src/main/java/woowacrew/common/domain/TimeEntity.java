package woowacrew.common.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {
    @CreatedDate
    @Column(updatable = false)
    protected Date createdDate;
    @LastModifiedDate
    protected Date lastModifiedDate;
}
