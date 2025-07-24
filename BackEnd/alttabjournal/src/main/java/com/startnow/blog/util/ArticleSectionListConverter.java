package com.startnow.blog.util;

import com.startnow.blog.model.ArticleSection;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleSectionListConverter implements AttributeConverter<ArrayList<ArticleSection>> {

    @Override
    public AttributeValue transformFrom(ArrayList<ArticleSection> sections) {
        if (sections == null) {
            return AttributeValue.builder().l(List.of()).build();
        }

        List<AttributeValue> sectionValues = sections.stream().map(section -> {
            Map<String, AttributeValue> sectionMap = new HashMap<>();
            sectionMap.put("id", AttributeValue.builder().s(section.getId()).build());
            sectionMap.put("title", AttributeValue.builder().s(section.getTitle()).build());
            sectionMap.put("content", AttributeValue.builder().s(section.getContent()).build());
            return AttributeValue.builder().m(sectionMap).build();
        }).collect(Collectors.toList());

        return AttributeValue.builder().l(sectionValues).build();
    }

    @Override
    public ArrayList<ArticleSection> transformTo(AttributeValue attributeValue) {
        if (attributeValue == null || attributeValue.l() == null) {
            return new ArrayList<>();
        }

        ArrayList<ArticleSection> sections = new ArrayList<>();
        for (AttributeValue sectionValue : attributeValue.l()) {
            Map<String, AttributeValue> sectionMap = sectionValue.m();
            ArticleSection section = new ArticleSection(sectionMap.get("id").s(),
                    sectionMap.get("title").s(), sectionMap.get("content").s());
            sections.add(section);
        }

        return sections;
    }

    @Override
    public EnhancedType<ArrayList<ArticleSection>> type() {
        return new EnhancedType<ArrayList<ArticleSection>>() {};
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.L;
    }
}

