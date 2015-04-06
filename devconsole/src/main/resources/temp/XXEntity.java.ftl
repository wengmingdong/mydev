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
public class ${bean.value} {
	
<#list fields as field>
	/**
	* ${field.comment}
	*/
 	private ${field.fieldTypeStr} ${field.fieldName} 
</#list>

<#list fields as field>
	public void set${field.fieldName?cap_first}(${field.fieldTypeStr} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
	}
	
	public ${field.fieldTypeStr} get${field.fieldName?cap_first}() {
		return this.${field.fieldName};
	}
</#list>	
}