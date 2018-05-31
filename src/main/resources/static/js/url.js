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
	    	return "http://120.77.38.183/";
	    },
	    createPro:function(json){
	    	// console.log(json)
	        var xhr = $.ajax({
				url: $.localurl()+'project/build',
				type: "POST",
				contentType:'application/json',
				data: JSON.stringify(json),
				async: false,
				success: function(x){
					console.log(x)
                    window.location.href = x.info;
				}
			});
			// var result = JSON.parse(xhr.responseText);
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
            return xhr.responseJSON;
	    },
	    // 获取某个工程列表
	    getPro:function(id){
			var url = $.UrlUpdateParams($.localurl()+"project/detail","projectId",id)
			var xhr = $.ajax({
				url: url,
				type: "GET",
				async: false
			});
            return xhr.responseJSON;
	    },
	    // 获取工程列表
	    getAllPro:function(){
			var xhr = $.ajax({
				url: $.localurl() + "project/get",
				type: "GET",
				async: false
			});
			// console.log(xhr);
            return xhr.responseJSON;
	    },
	    // 获取某个设备列表
	    getDe:function(id){
			var url = $.UrlUpdateParams($.localurl()+"device/detail","deviceId",id);
			var xhr = $.ajax({
				url: url,
				type: "GET",
				async: false
			});
            return xhr.responseJSON;
	    },
	    // 获取设备列表
	    getAllDe:function(){
			var xhr = $.ajax({
				url: $.localurl() + "device/get",
				type: "GET",
				async: false
			});
            return xhr.responseJSON;
	    },
	    export:function(json){
	    	// console.log(json)
			var xhr = $.ajax({
				url: $.localurl() + "device/export",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});
			// console.log(xhr)
            return xhr.responseJSON;
	    },
	    addDevice:function(json){
	    	// console.log(json)
			var xhr = $.ajax({
				url: $.localurl() + "meterial/adddevice",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});
			// console.log(xhr)
            return xhr.responseJSON;
	    },
	    addMaterial:function(json){
	    	console.log(json)
			var xhr = $.ajax({
				url: $.localurl() + "meterial/addmaterial",
				type: "POST",
				contentType: 'application/json;charset=UTF-8',
				async: false,
				data: JSON.stringify(json)
			});
			// console.log(xhr)
            return xhr.responseJSON;
	    },
	    exportPro:function(id){
	    	// console.log(json)
			var xhr = $.ajax({
				url: $.localurl() + "project/export?id=" + id,
				type: "GET",
				contentType: 'application/json;charset=UTF-8',
				async: false
			});
			// console.log(xhr)
            return xhr.responseJSON;
	    },
	});
})(jQuery);