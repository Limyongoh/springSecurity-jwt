package com.todo.settingProject.domain.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.type.TrueFalseConverter;
import org.hibernate.type.YesNoConverter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)// @createDate, @modifyDate 자동 사용하기 위해 추가
public class Base{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        @Column(name = "DEL_AT")
        @Convert(converter = YesNoConverter.class)
        protected boolean delAt;

        @CreatedDate
        @Column(name = "CREATE_DATE")
        protected LocalDateTime createDate;

        @LastModifiedDate
        @Column(name = "MODIFIED_DATE")
        protected LocalDateTime modifiedDate;

        @CreatedBy
        @Column(name = "CREATE_BY")
        protected String createBy;

        @LastModifiedBy
        @Column(name = "MODIFIED_BY")
        protected String modifiedBy;
}