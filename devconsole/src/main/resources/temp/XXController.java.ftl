package ${package.value}.ctrl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import com.nd.common.base.BaseController;
import ${package.value}.dto.${bean.value};
import ${package.value}.dto.${bean.value}Example;
import ${package.value}.service.I${bean.value}Service;
import com.nd.common.json.ResponseBean;
import com.nd.common.json.ResponseState;
import com.nd.common.util.Page;
import com.nd.common.util.ObjectUtils;

/**
 * 类描述:${bean.name}
 * 创建人:${creater.value}
 * 创建时间:${_now}
 * @version
 */
@Controller
@RequestMapping("/admin/${bean.value?uncap_first}")
public class ${bean.value}Controller extends BaseController{
	private final static Logger logger = Logger.getLogger(${bean.value}Controller.class);
	
	@Resource
    I${bean.value}Service ${bean.value?uncap_first}Service;//${bean.name}
	/**
	 * 获取${bean.name}列表
	 * @param record
	 
	 
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "",method=RequestMethod.GET)
	public @ResponseBody
	Object getListPage(${bean.value} record,Page page,HttpServletRequest request) {
		int total = 0;
		List<${bean.value}> list = new ArrayList<${bean.value}>();
		try{
			/*	查询条件    */
			${bean.value}Example example = new ${bean.value}Example();
			
			//统计数量
			total = ${bean.value?uncap_first}Service.countByExample(example);
			if(total>0){
				list = ${bean.value?uncap_first}Service.getList(example);
			}
		}catch (Exception e) {
			logger.error("error", e);
		}
		return new ResponseBean<${bean.value}>(total,list);
	}
	
	/**
	 * 插入记录
	 * @param record
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "",method=RequestMethod.POST)
	public @ResponseBody
	Object insert(${bean.value} record,HttpServletRequest request) {
		ResponseState state = new ResponseState(false,"");
		try{			
			boolean isSuccess = ${bean.value?uncap_first}Service.insert(record);
			if(isSuccess){//成功
				state.setSuccess(true);
				state.setMsg("");
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("error", e);
		}
		return state;
	}
	/**
	 * 删除记录
	 * @param record
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}",method=RequestMethod.DELETE)
	public @ResponseBody
	Object delete(@PathVariable("id") Integer id,HttpServletRequest request) {
		ResponseState state = new ResponseState(false,"");
		try{
			if(ObjectUtils.isNotEmpty(id)){
				boolean isSuccess = ${bean.value?uncap_first}Service.delete(id);
				if(isSuccess){//删除成功
					state.setSuccess(true);
					state.setMsg("");
				}
			}	
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("error", e);
		}
		return state;
	}
	/**
	 * 更新
	 * @param record
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}",method=RequestMethod.PUT)
	public @ResponseBody
	Object update(@RequestBody ${bean.value} record,HttpServletRequest request) {
		ResponseState state = new ResponseState(false,"");
		try{			
			boolean isSuccess = ${bean.value?uncap_first}Service.update(record);
			if(isSuccess){//删除成功
				state.setSuccess(true);
				state.setMsg("");
			}
		}catch (Exception e) {
			logger.error("error", e);
		}
		return state;
	}

}
