package ${package.value}.dao;

import org.springframework.stereotype.Repository;

import ${package.value}.dto.${bean.value};
import ${package.value}.dto.${bean.value}Example;
import com.nd.common.dao.SimpleMybatisSupport;
/**
 * 类描述:${bean.name}
 * 创建人:${creater.value}
 * 创建时间:${_now}
 * @version
 */
 
@Repository("${bean.value?uncap_first}")
public class ${bean.value}Dao extends SimpleMybatisSupport<${bean.value}, Integer, ${bean.value}Example>{
	
	@Override
    public String getMybatisMapperNamesapce() {
        return "${package.value}.dao.${bean.value}Mapper";
    }


}