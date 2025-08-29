package com.startnow.blog.util;

import com.startnow.blog.model.ArticleSection;
import com.startnow.blog.model.ArticleContentBlock;
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

            List<AttributeValue> contentBlocks = section.getContent().stream().map(block -> {
                Map<String, AttributeValue> blockMap = new HashMap<>();
                blockMap.put("type", AttributeValue.builder().s(block.type).build());
                if (block.text != null) {
                    blockMap.put("text", AttributeValue.builder().s(block.text).build());
                }
                if (block.items != null) {
                    List<AttributeValue> itemValues = block.items.stream()
                            .map(item -> AttributeValue.builder().s(item).build())
                            .collect(Collectors.toList());
                    blockMap.put("items", AttributeValue.builder().l(itemValues).build());
                }
                if (block.src != null) {
                    blockMap.put("src", AttributeValue.builder().s(block.src).build());
                }
                if (block.alt != null) {
                    blockMap.put("alt", AttributeValue.builder().s(block.alt).build());
                }
                return AttributeValue.builder().m(blockMap).build();
            }).collect(Collectors.toList());

            sectionMap.put("content", AttributeValue.builder().l(contentBlocks).build());
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

            List<ArticleContentBlock> contentBlocks = new ArrayList<>();
            for (AttributeValue blockValue : sectionMap.get("content").l()) {
                Map<String, AttributeValue> blockMap = blockValue.m();

                List<String> items = null;
                if (blockMap.containsKey("items")) {
                    items = blockMap.get("items").l().stream().map(av -> av.s())
                            .collect(Collectors.toList());
                }

                ArticleContentBlock block = ArticleContentBlock.builder()
                        .type(blockMap.get("type").s())
                        .text(blockMap.containsKey("text") ? blockMap.get("text").s() : null)
                        .items(items)
                        .src(blockMap.containsKey("src") ? blockMap.get("src").s() : null)
                        .alt(blockMap.containsKey("alt") ? blockMap.get("alt").s() : null).build();
                contentBlocks.add(block);
            }

            ArticleSection section = ArticleSection.builder().id(sectionMap.get("id").s())
                    .title(sectionMap.get("title").s()).content(contentBlocks).build();
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
