package com.example.batch5.service;

import com.example.batch5.dto.ResultDto;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;
import java.util.stream.Collectors;

public class ResultServiceImpl implements ResultService {

    // Type 到投注类型的映射
    private static final Map<String, String> typeMap = new HashMap<>();
    static {
        typeMap.put("1", "単勝");
        typeMap.put("2", "複勝");
        typeMap.put("3", "応援馬券");
        typeMap.put("4", "枠連");
        typeMap.put("5", "馬連");
        typeMap.put("6", "馬単");
        typeMap.put("7", "ワイド");
        typeMap.put("8", "3連複");
        typeMap.put("9", "3連単");
    }

    @Override
    public List<ResultDto> findAllUsers() {
        // 假设从数据库或其他地方获取数据，这里使用模拟数据
        List<ResultDto> resultDtoList = findData();

        // 按 type 分组
        Map<String, List<ResultDto>> listMap = resultDtoList.stream()
                .collect(Collectors.groupingBy(ResultDto::getType));

        // 存储每个 type 的投票统计
        Map<String, Map<String, Long>> betStatistics = new HashMap<>();

        // 统计每个 type 下的 bet 字段组合出现次数
        listMap.forEach((type, typeList) -> {
            Map<String, Long> typeBetCount = typeList.stream()
                    .flatMap(dto -> {
                        Set<String> betSet = new HashSet<>();
                        // 遍历所有 bet 字段，值为 1 时加入组合
                        for (int i = 1; i <= 54; i++) {
                            String betField = "bet" + String.format("%04d", i);
                            String betValue = getBetValue(dto, betField);

                            // 只统计值为 "1" 的 bet 字段
                            if ("1".equals(betValue)) {
                                betSet.add(betField);
                            }
                        }
                        return betSet.stream();  // 返回符合条件的 bet 字段
                    })
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));  // 统计每个 bet 组合出现次数

            // 将统计结果存入 betStatistics
            betStatistics.put(type, typeBetCount);
        });

        // 输出统计结果
        betStatistics.forEach((type, statistics) -> {
            String betType = typeMap.get(type);  // 获取对应的投注类型名称
            if (betType != null) {
                System.out.println("Type: " + betType + " 总投票数=" + statistics.values().stream().mapToLong(Long::longValue).sum());
                statistics.forEach((betCombination, count) -> {
                    System.out.println(betCombination + ": " + count);
                });
                System.out.println();  // 添加空行分隔不同的 type
            }
        });

        return null;  // 返回值可以根据需求调整，或者返回统计结果
    }

    // 模拟从数据库查询的结果
    private List<ResultDto> findData() {
        ResultDto dto1 = new ResultDto();
        dto1.setType("1");  // 単勝
        dto1.setBet0101("1");
        dto1.setBet0102("0");

        ResultDto dto2 = new ResultDto();
        dto2.setType("1");  // 単勝
        dto2.setBet0101("1");

        ResultDto dto3 = new ResultDto();
        dto3.setType("2");  // 複勝
        dto3.setBet0201("1");
        dto3.setBet0210("1");

        ResultDto dto4 = new ResultDto();
        dto4.setType("2");  // 複勝
        dto4.setBet0113("0");
        dto4.setBet0218("1");

        ResultDto dto5 = new ResultDto();
        dto5.setType("3");  // 応援馬券
        dto5.setBet0101("1");
        dto5.setBet0202("1");

        ResultDto dto6 = new ResultDto();
        dto6.setType("3");  // 応援馬券
        dto6.setBet0101("1");
        dto6.setBet0213("1");
        dto6.setBet0318("1");

        ResultDto dto7 = new ResultDto();
        dto7.setType("4");  // 枠連
        dto7.setBet0103("1");
        dto7.setBet0213("1");

        ResultDto dto8 = new ResultDto();
        dto8.setType("4");  // 枠連
        dto8.setBet0113("1");
        dto8.setBet0218("1");

        return Arrays.asList(dto1, dto2, dto3, dto4, dto5, dto6, dto7, dto8);
    }

    /**
     * 获取指定 bet 字段的值
     */
    private String getBetValue(ResultDto dto, String betField) {
        try {
            // 使用反射获取指定字段的值
            Field field = ResultDto.class.getDeclaredField(betField);
            field.setAccessible(true);  // 设置为可访问
            return (String) field.get(dto);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
