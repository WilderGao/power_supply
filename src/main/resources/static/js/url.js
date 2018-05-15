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
		}
	});
})(jQuery);

(function($) {
	$.extend({
		localurl:function(){
	    	return "http://localhost:8080/";
	    },
	    createPro:function(json){
	        var xhr = $.ajax({
				url: $.localurl()+'project/build',
				type: "POST",
				contentType:'application/json',
				data: JSON.stringify(json),
				async: false,
				success: function(x){
				}
			});
			return xhr.responseJSON;
	    },
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
	    getPro:function(id){
			var url = $.UrlUpdateParams($.localurl()+"project/detail","projectId",id)
			var xhr = $.ajax({
				url: url,
				type: "GET",
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    getAllPro:function(){
			var xhr = $.ajax({
				url: $.localurl() + "project/get",
				type: "GET",
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    getDe:function(id){
			var url = $.UrlUpdateParams($.localurl()+"device/detail","deviceId",id);
			var xhr = $.ajax({
				url: url,
				type: "GET",
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
	    getAllDe:function(){
			var xhr = $.ajax({
				url: $.localurl() + "device/get",
				type: "GET",
				async: false
			});
			var result = JSON.parse(xhr.responseText);
			return result;
	    },
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
	    }
	});
})(jQuery);