package com.ll.exam.spring_fileupload.article.entity;

import com.ll.exam.spring_fileupload.base.entity.BaseEntity;
import com.ll.exam.spring_fileupload.hashtag.entity.HashTag;
import com.ll.exam.spring_fileupload.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
    @ManyToOne
    private Member author;

    private String subject;

    private String content;

    public String getExtra_inputValue_hashTagContents() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("hashTags") == false) {
            return "";
        }

        List<HashTag> hashTags = (List<HashTag>) extra.get("hashTags");

        if (hashTags.isEmpty()) {
            return "";
        }

        return "#" + hashTags
                .stream()
                .map(hashTag -> hashTag.getKeyword().getContent())
                .sorted()
                .collect(Collectors.joining(" #"))
                .trim();
    }
}
