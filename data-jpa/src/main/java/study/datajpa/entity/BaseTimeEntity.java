package study.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class BaseTimeEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

}
