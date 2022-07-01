package com.sunfujun.tools.io.core.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author scott
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    public static <T> T parse(String content, Class<T> valueType)  {
        if (StrUtil.isEmpty(content)) {
            return null;
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static Object parse(String content)  {
        try {
            return objectMapper.readValue(content, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static String toTree(String content, String path) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(content);
        return node.path(path).asText();
    }


    public static String getJsonNodeValue(String json, String attrs)   {
        //JSON ----> JsonNode
        JsonNode node = null;
        try {
            node = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int index = attrs.indexOf('.');
        if (index == -1) {
            if (node != null) {
                if (node.isArray() && !node.isEmpty()) {
                    StringJoiner joiner = new StringJoiner(",");
                    Iterator<JsonNode> elements = node.elements();
                    while (elements.hasNext()) {
                        JsonNode score = elements.next();
                        joiner.add(score.get(attrs).asText());
                    }
                    return joiner.toString();
                }
                if (node.get(attrs) != null) {
                    return node.get(attrs).asText();
                }
            }
            return "";
        } else {
            String s1 = attrs.substring(0, index);
            String s2 = attrs.substring(index + 1);
            return getJsonNodeValue(node.path(s1).toString(), s2);
        }
    }


    public static JsonNode findJsonNode(String jsonStr, String attrs)  {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int index = attrs.indexOf('.');
        if (index == -1) {
            if (node != null) {
                if (node.isArray()) {
                    return node.get(0).get(attrs);
                } else {
                    return node.get(attrs);
                }
            }
            return null;
        } else {
            String s1 = attrs.substring(0, index);
            String s2 = attrs.substring(index + 1);
            return findJsonNode(node.path(s1).toString(), s2);
        }
    }


    public static String replaceJsonNodeValue(String jsonStr, String node, String value)  {
        ObjectNode objectNode = null;
        try {
            objectNode = (ObjectNode) objectMapper.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return replaceJsonNodeValue(objectNode, node, value);
    }

    private static String replaceJsonNodeValue(ObjectNode objectNode, String node, String value) {
        //值不能为null
        if (value != null) {
            if (node == null || node.equals("")) {
                //如果node为空，说明只有一级节点，直接替换值
                objectNode.put(node, value);
            }
            assert node != null;
            int index = node.indexOf('.');
            if (index != -1) {
                String s1 = node.substring(0, index);
                String s2 = node.substring(index + 1);
                if (s2.equals(node.substring(node.lastIndexOf(".") + 1))) {
                    //比较节点相同就替换节点值
                    JsonNode jsonNode = objectNode.get(s1);
                    if (jsonNode != null) {
                        //节点值是否为数组
                        if (jsonNode instanceof ArrayNode) {
                            //为数组
                            for (JsonNode jsonNode1 : jsonNode) {
                                ObjectNode jsonNode2 = (ObjectNode) jsonNode1;
                                jsonNode2.put(s2, value);
                            }
                        } else {
                            //不为数组
                            ObjectNode jsonNode1 = (ObjectNode) jsonNode;
                            jsonNode1.put(s2, value);
                        }
                    }
                } else {
                    //比较不相同就继续遍历
//                    int i1 = s2.lastIndexOf(".");
//                    if (i1 != -1) {
//                        s2 = s2.substring(0, i1);
//                    }
                    JsonNode path = objectNode.path(s1);
                    if (path != null) {
                        if (path instanceof ArrayNode) {
                            ArrayNode arrayNode = (ArrayNode) path;
                            for (JsonNode jsonNode : arrayNode) {
                                replaceJsonNodeValue((ObjectNode) jsonNode, s2, value);
                            }
                        } else {
                            //遍历
                            replaceJsonNodeValue((ObjectNode) objectNode.path(s1), s2, value);
                        }
                    }
                }
            }
        }
        return objectNode.toString();
    }


    public static void jsonLeaf(JsonNode node) {
        if (node.isValueNode()) {
            System.out.println(node);
            return;
        }

        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = node.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                jsonLeaf(entry.getValue());
            }
        }

        if (node.isArray()) {
            for (JsonNode jsonNode : node) {
                jsonLeaf(jsonNode);
            }
        }
    }


    public static void main(String[] args) throws JsonProcessingException {

        String json = "{\n" +
                "    \"encodeData\": \"test\",\n" +
                "    \"timestamp\": 1654824827,\n" +
                "    \"data\": {\n" +
                "        \"category\": \"JSZD\",\n" +
                "        \"baseInformation\": {\n" +
                "            \"name\": \"zml标准工时制\",\n" +
                "            \"groupId\": \"2851322945050099310\",\n" +
                "            \"ruleCode\": \"zmljiszd2021\",\n" +
                "            \"isUse\": true\n" +
                "        },\n" +
                "        \"applicableScope\": [\n" +
                "            {\n" +
                "                \"obj\": [\n" +
                "                    {\n" +
                "                        \"id\": \"2851322945050099310\",\n" +
                "                        \"key\": \"2851322945050099310\",\n" +
                "                        \"label\": \"zml公司\",\n" +
                "                        \"name\": \"zml公司\",\n" +
                "                        \"show\": false,\n" +
                "                        \"type\": \"Org\",\n" +
                "                        \"value\": \"2851322945050099310\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"dateRange\": [\n" +
                "                    \"2000-01-01\",\n" +
                "                    \"2100-01-01\"\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"rules\": [\n" +
                "            {\n" +
                "                \"code\": \"ruleCode\",\n" +
                "                \"value\": \"zmljiszd2021\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"groupId\",\n" +
                "                \"value\": \"2851322945050099310\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attRuleSelect\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attWorkRule\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"summerTimeBegin\",\n" +
                "                \"value\": \"2022-06-01\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"summerTimeEnd\",\n" +
                "                \"value\": \"2022-06-02\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attShiftGrab\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attWorkDayGrabShift\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attWeekendGrabShift\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attHolidayGrabShift\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attSpecialDayGrabShift\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attZeropointWorktypeMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNoshiftTypeMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attShiftGrabShifttype\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNoshiftCalc\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNoshiftSearchMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNoshiftFixSearchPoint\",\n" +
                "                \"value\": \"08:30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNoshiftFixSearchLen\",\n" +
                "                \"value\": \"800\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOttypeConfirm\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOtlistShiftTypeNotmatch\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOtRoundingRange\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayGenException\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOtCompareMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1Fz\",\n" +
                "                \"value\": \"60\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1Step\",\n" +
                "                \"value\": \"30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1TjMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1DjMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1QrMode\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1Rounding\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1ListExemption\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1TxBeginMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1TxjsMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1TxEndMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt1TxValidLen\",\n" +
                "                \"value\": \"5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2Fz\",\n" +
                "                \"value\": \"60\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2Step\",\n" +
                "                \"value\": \"30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2TjMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2DjMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2QrMode\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2Rounding\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2ListExemption\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2TxBeginMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2TxjsMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2TxEndMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt2TxValidLen\",\n" +
                "                \"value\": \"5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3Fz\",\n" +
                "                \"value\": \"60\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3Step\",\n" +
                "                \"value\": \"30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3TjMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3DjMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3QrMode\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3Rounding\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3ListExemption\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3TxBeginMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3TxjsMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3TxEndMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attOt3TxValidLen\",\n" +
                "                \"value\": \"5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attPunchSelectRule\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attPunchInterval\",\n" +
                "                \"value\": \"5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attPunchSelectPrefer\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attIoExemptionMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attIoExemptionMinutes\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attShiftSearchIntersectRule\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attBukaStatistic\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attPunchForgetMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attAutoPunchOnduty\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attAutoPunchOnshift\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attAutoPunchOffduty\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attAutoPunchOffshift\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCdztMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attQjcdMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attQjztMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCd0\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCd1\",\n" +
                "                \"value\": \"5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCd2\",\n" +
                "                \"value\": \"30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attZt0\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attZt1\",\n" +
                "                \"value\": \"5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attZt2\",\n" +
                "                \"value\": \"30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attWorktimeCalcOrder\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attBaseDayQuota\",\n" +
                "                \"value\": \"480\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attDayQuota\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCqgsMode\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCqgsFz\",\n" +
                "                \"value\": \"120\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCqgsStep\",\n" +
                "                \"value\": \"60\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCqgsRoundingRange\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCqgsRounding\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCqts\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attFloatOverflow\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attFloatRestFixed\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayShiftLeaveMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayNoshiftLeaveMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attChaiqMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attWorkdayChaiqCqCalc\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayChaiqCqCalc\",\n" +
                "                \"value\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayShiftChaiqMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayNoshiftChaiqMode\",\n" +
                "                \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attWorkdayQjcqCoexist\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayQjcqCoexist\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attWorkdayChaiqcqCoexist\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attNonworkdayChaiqcqCoexist\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attQjchaiqDeductOrder\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attQjchaiqCqBalance\",\n" +
                "                \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attQjchaiqCqBalanceMode\",\n" +
                "                \"value\": \"3\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"code\": \"attCalcAbsenteeism\",\n" +
                "                \"value\": \"1\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"id\": \"4599221933403145363\"\n" +
                "    },\n" +
                "    \"sign\": \"dsad8sdsadas7d98\"\n" +
                "}\n";


        JsonNode node = objectMapper.readTree(json);
        jsonLeaf(node);
    }

}
