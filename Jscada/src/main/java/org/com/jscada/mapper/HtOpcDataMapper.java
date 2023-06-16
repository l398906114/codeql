package org.com.jscada.mapper;

import org.apache.ibatis.annotations.Select;
import org.com.jscada.entity.HtOpcData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface HtOpcDataMapper extends BaseMapper<HtOpcData> {

    @Select(" select touch_code AS touchCode , instruct_code AS instructCode from ys_pro_instruct where equ_code=#{opcCode} AND product_status='生产中'")
    public Map<String, String>getTouchCodeByOpcCode(String opcCode);

}
