//获取URL的参数和设置参数
(function($) {
	$.extend({
		Request: function(m) {
			var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
			var value = sValue ? sValue[1] : sValue;
			return decodeURIComponent(value);
		},
		UrlUpdateParams: function(url, name, value) {
			var r = url;
			if (r != null && r != 'undefined' && r != "") {
				value = encodeURIComponent(value);
				var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
				var tmp = name + "=" + value;
				if (url.match(reg) != null) {
					r = url.replace(eval(reg), tmp);
				} else {
					if (url.match("[\?]")) {
						r = url + "&" + tmp;
					} else {
						r = url + "?" + tmp;
					}
				}
			}
			return r;
		},
		save: function(key,value){
			sessionStorage.setItem(key,value);
		},
		search: function(key){
			var value = sessionStorage.getItem(key);
			// console.log(value);
			return value;
		},
		delete: function(key){
			sessionStorage.removeItem(key);
		},
		clear: function(){
			sessionStorage.clear();
		}
	});
})(jQuery);

		
		
(function($) {
	$.extend({
		localurl:function(){
	    	return "http://localhost/";
	    },
	    // 修改历史工程材料数量
	    updateNum:function(json){
			var xhr = $.ajax({
				url: $.localurl() + "project/updatenum",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 获取历史工程
	    getAllPro:function(){
			var xhr = $.ajax({
				url: $.localurl() + "project/get",
				type: "GET",
				async: false
			});
			// console.log(xhr);
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 获取工程详情
	    getProDetail:function(id){
			var url = $.UrlUpdateParams($.localurl()+"project/detail","projectId",id)
			var xhr = $.ajax({
				url: url,
				type: "GET",
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 导出历史工程
	    exportPro:function(json, id){
			var xhr = $.ajax({
				url: $.localurl() + "project/export",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});

			console.log(xhr);
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 新建工程
	    createPro:function(json){
	        var xhr = $.ajax({
				url: $.localurl()+'project/build',
				type: "POST",
				contentType:'application/json',
				data: JSON.stringify(json),
				async: false,
				success: function(x){
					console.log(x)
				}
			});
			return xhr.responseJSON;
	    },
	    // 查找材料
	    searchStuff:function(type,key){
	    	var json;
	    	if(type==1){
	    		json = {
				    'meterialName':key,
				    'meterialCode':null
				}
	    	}else{
	    		json = {
				    "meterialName":null,
				    "meterialCode":key
				}
	    	}
			var xhr = $.ajax({
				url: $.localurl()+ "meterial/fuzzy",
				type: "get",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: json,
				success: function(x){
					console.log("ok");
				},
				error:function(er){
					return {
						"status":404
					}
				}
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 获取设备列表
	    getAllDe:function(){
			var xhr = $.ajax({
				url: $.localurl() + "device/get",
				type: "GET",
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 获取某个设备详情
	    getDeviceDetail:function(id){
			var url = $.UrlUpdateParams($.localurl()+"device/detail","deviceId",id);
			var xhr = $.ajax({
				url: url,
				type: "GET",
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 导出设备Excel
	    export:function(json){
			var xhr = $.ajax({
				url: $.localurl() + "device/export",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 添加设备
	    addDevice:function(json){
			var xhr = $.ajax({
				url: $.localurl() + "device/adddevice",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 添加材料
	    addMaterial:function(json){
			var xhr = $.ajax({
				url: $.localurl() + "meterial/addmaterial",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});
			// console.log(xhr)
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 删除某个用户已经选择的设备
	    deleteDevice:function(sessionId,deviceId){
	    	// console.log(json)
			var xhr = $.ajax({
				url: $.localurl() + "device/delete/" + sessionId + "/" + deviceId,
				type: "GET",
				contentType: 'application/json;charset=UTF-8',
				async: false
			});
			// console.log(xhr)
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 显示已经添加的所有信息
	    showAllMe:function(sessionId){
	    	// console.log(json)
			var xhr = $.ajax({
				url: $.localurl() + "meterial/show/" + sessionId,
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 获取已经添加的所有设备的信息
	    getSelectedDevice:function(sessionId){
			var xhr = $.ajax({
				url: $.localurl() + "device/selected/" + sessionId,
				type: "GET",
				contentType: 'application/json;charset=UTF-8',
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 获取已经添加的材料
	    getSelectedMa:function(sessionId){
			var xhr = $.ajax({
				url: $.localurl() + "meterial/selected/" + sessionId,
				type: "GET",
				contentType: 'application/json;charset=UTF-8',
				async: false
			});
			// console.log(xhr)
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    // 删除已经添加的材料
	    deleteSelectedMa:function(sessionId, meterialId){
	    	var xhr = $.ajax({
				url: $.localurl() + "meterial/delete/" + sessionId + "/" + meterialId,
				type: "GET",
				contentType: 'application/json;charset=UTF-8',
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    }
	});
})(jQuery);