package ${packagePath};

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.dSync.model.DSyncBaseHandler;
import com.backinfile.dSync.model.DSyncException;
import com.backinfile.dSync.model.Mode;

public class ${handlerClassName} extends DSyncBaseHandler {
	private Board root;

	public ${handlerClassName}(Mode mode) {
		super(mode);
		root = new ${rootClassName}();
		root.init();
		put(root);
		root.sync();
	}

	public Board getRoot() {
		return root;
	}

	@Override
	protected DSyncBase newDSyncInstance(String typeName) {
		switch (typeName) {
<#list structs as struct>
		case ${struct.className}.TypeName:
			return new ${struct.className}();
</#list>
		default:
			return null;
		}
	}

<#list structs as struct>
	public static class ${struct.className} extends DSyncBase {
		public static final String TypeName = "${struct.className}";
		
<#list struct.fields as field>
		private ${field.typeName} ${field.name};
</#list>

		public static class K {
<#list struct.fields as field>
			public static final String ${field.name} = "${field.name}";
</#list>
		}

		private ${struct.className}() {
		}

		public static ${struct.className} newInstance(${handlerClassName} _handler) {
			if (_handler.mode == Mode.Client) {
				throw new DSyncException("Client模式下，不能创建DSync数据对象");
			}
			${struct.className} _struct = new ${struct.className}();
			_struct.init();
			if (_handler.mode == Mode.Server) {
				_handler.put(_struct);
			}
			return _struct;
		}

		@Override
		protected void init() {
<#list struct.fields as field>
			${field.name} = ${field.defaultValue};
</#list>
		}

		@Override
		protected void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
<#list struct.fields as field>
<#if field.array>
<#if field.baseType>
			jsonObject.put(K.${field.name}, JSONObject.toJSONString(${field.name}));
<#else>
			jsonObject.put(K.${field.name}, toJSONString(${field.name}));
</#if>
<#else>
<#if field.baseType>
			jsonObject.put(K.${field.name}, ${field.name});
<#else>
			jsonObject.put(K.${field.name}, ${field.name}.get_dSync_id());
</#if>
</#if>
</#list>
		}

		@Override
		protected void applyRecord(JSONObject jsonObject) {
<#list struct.fields as field>
<#if field.array>
<#if field.baseType>
			${field.name} = JSONObject.parseArray(jsonObject.getString(K.${field.name}), ${field.largeTypeName}.class);
<#else>
			${field.name} = fromJSONString(jsonObject.getString(K.${field.name}));
</#if>
<#else>	
<#if field.baseType>
			${field.name} = jsonObject.get${field.longTypeName}(K.${field.name});
<#else>
			${field.name} = (${field.typeName}) handler.get(jsonObject.getLongValue(K.${field.name}));
</#if>
</#if>
</#list>
		}
	}
</#list>
}

