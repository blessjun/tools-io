package cn.com.coho.tools.io.core.util;

import cn.com.coho.tools.io.model.entity.SysSign;
import cn.com.coho.tools.io.model.entity.SysSignRule;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: scott
 * @date 2018/09/13
 */
public class HttpClientUtil {




    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);


    public static String generateSign(String value, Map<String, Object> param) throws JsonProcessingException {
        SysSign sysSign = JsonUtil.parse(value, SysSign.class);
        assert sysSign != null;
        List<SysSignRule> rules = sysSign.getRule().stream().sorted(Comparator.comparing(SysSignRule::getOrder)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        String sign;
        rules.forEach(rule->{
            if (rule.getIsParam()){
                if (param.containsKey(rule.getValue())){
                    rule.setValue(String.valueOf(param.get(rule.getValue())));
                }
            }
            stringBuilder.append(rule.getValue());
        });
        log.info("签名前string:{}", stringBuilder);
        sign = SecureUtil.md5(stringBuilder.toString());
        if (Constants.SHA_1.equals(sysSign.getType())){
            sign = SecureUtil.sha1(stringBuilder.toString());
        }else if (Constants.SHA_256.equals(sysSign.getType())){
            sign = SecureUtil.sha256(stringBuilder.toString());
        }
        if (sysSign.getFormat() == 1){
            sign = sign.toUpperCase(Locale.ROOT);
        }else if(sysSign.getFormat() == 2){
            sign = sign.toLowerCase(Locale.ROOT);
        }
        return sign;
    }

}
