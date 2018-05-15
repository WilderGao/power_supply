$(function(){
	function fillMainData(stuff){
    	var ul = $(".stuff_ul");
    	var len = stuff.length;
    	var i = 0;
    	var html = '<li class="list-group-item active"><div class="stuff_box"><div class="stuff_name">材料名称</div><div class="meterialCode">材料编码</div><div class="meterialUnit">单位</div><div class="stuffmeterialPrice_num">单价</div><div class="meterialAttention">备注信息</div><div class="meterialCheck">是否业扩储备物资</div></div></li>';
    	for(;i<len;i++){
    		html += '<li class="list-group-item"><div class="stuff_box"><div class="stuff_name">';
    		html += stuff[i].meterialName + '</div><div class="meterialCode">';
    		html += stuff[i].meterialCode + '</div><div class="meterialUnit">';
    		html += stuff[i].meterialUnit + '</div><div class="stuffmeterialPrice_num">';
    		html += stuff[i].meterialPrice + '</div><div class="meterialAttention">';
    		html += stuff[i].meterialAttention + '</div><div class="meterialCheck">';
    		html += stuff[i].meterialCheck + '</div><div class="stuff_num"><input class="btn btn-primary modal_add" type="button" value="修改数量" data-id="'+ i + '">';
    		html += '</div></div></li>';
    	}
    	ul.html(html);
    	$(".modal_add").click(function(ev){
	    	$("#mymodal").modal("toggle");
	    });
    }
    var allResult = $.searchStuff(1,null);
	var result;	
	if(allResult.status!="404"){
		result = allResult.info;
		fillMainData(result);
	}else{
		fillMainData([]);
		alert("网络不佳，请刷新页面");
	}
    var selectjson = [];var dataId = '';var local = -1;
	var mainBox = $(".main_box");
	var inp = $("#count");
	$(".modal_add_btn").click(function(ev){
    	$("#mymodal").modal("toggle");
    });
    mainBox.click(function(ev){
		var target = $(ev.target);
		if(ev.target.nodeName.toLowerCase() == "input"){
			dataId = parseInt(target.attr("data-id"));
			var i = 0,len = selectjson.length;
			inp.val('');
			for(local = -1;i<len;i++){
				if(selectjson[i].meterialId == result[dataId].meterialId){
					local = i;
					inp.val(selectjson[i].num);
					break;
				}
			}
		}
	});
	inp.keydown(function(e){
		if(e.keyCode==13){
			$('#comfirm').trigger("click");
			return false;
		}
	});
	$("#comfirm").click(function(){
		var value = parseInt(inp.val());
		if(local==-1&&value>0){
			var json = {
				"meterialId":result[dataId].meterialId,
				"meterialCode":result[dataId].meterialCode,
				"meterialName":result[dataId].meterialName,
				"meterialUnit":result[dataId].meterialUnit,
				"meterialPrice":result[dataId].meterialPrice,
				"meterialCheck":result[dataId].meterialCheck,
				"meterialAttention":result[dataId].meterialAttention,
				"num":value
			}
			selectjson.push(json);
		}else if(local!=-1&&value>0){
			selectjson[local].num = value;
		}else if(local!=-1){
			selectjson.splice(local,1);
		}
	})
	function fillSeleteData(stuff){
    	var ul = $(".show_stuff_ul");
    	var len = stuff.length;
    	var i = 0;
    	var html = '<li class="list-group-item active"><div class="show_stuff_box"><div class="show_stuff_name">材料名称</div><div class="show_meterialCode">材料编码</div><div class="show_stuff_num">材料选择数量</div></div></li>';
    	for(;i<len;i++){
    		html += '<li class="list-group-item"><div class="show_stuff_box"><div class="show_stuff_name">';
    		html += stuff[i].meterialName + '</div><div class="show_meterialCode">';
    		html += stuff[i].meterialCode + '</div><div class="show_stuff_num">';
    		html += stuff[i].num + '</div></div></li>';
    	}
    	ul.html(html);
    }
    var footerBox = $(".footer");
	footerBox.click(function(ev){
		var target = $(ev.target);
		if(ev.target.nodeName.toLowerCase() == "a"){
			fillSeleteData(selectjson);
		}
	});
	var searchType = 1;	
	$("#searchType_ul").click(function(ev){
		var target = $(ev.target);
		if(ev.target.nodeName.toLowerCase() == "a"){
			var num = target.attr("data_id");
			searchType = num;
			if(num==1){
				$(".dropdown-toggle").html('按名称搜索<span class="caret"></span>');
			}else{
				$(".dropdown-toggle").html('按编号搜索<span class="caret"></span>');
			}
		}
	})
	$("#search_key_input").keydown(function(e){
		if(e.keyCode==13){
			$('#search_btn').trigger("click");
			return false;
		}
	});
	$("#search_btn").click(function(){
		var searchResult;
		// 获取搜索的关键字
		var value = $("#search_key_input").val();
		if(value==''){
			result = allResult.info;
		}else{
			// 搜索
			result = $.searchStuff(searchType,value);
			if(result.status!="404"){
				result = result.info;
				fillMainData(result);
			}else{
				fillMainData([]);
				alert("网络不佳，请稍后重试");
			}
		}
		fillMainData(result);
	})
	var name = $.Request("n");
	var district = $.Request("d");
	var batch = $.Request("b");
	var powerSupply = $.Request("po");
	var projectCode = $.Request("pr");
	var finishBtn = $("#finish");
	finishBtn.click(function(){
		var data = {
		    "projectName":name,
		    "district":district,
		    "batch":batch,
		    "powerSupply":powerSupply,
		    "projectCode":projectCode,
		    "meterials":selectjson
		}
		var res = $.createPro(data);
		exportExcel(data);
	})
	function exportExcel(data){
		var arr = [];
		var data1 = {};
		var i = 0;var len = data.meterials.length;
		for(;i<len;i++){
			data1 = {
				"区局":data.district,
				"批次":data.batch,
				"供电所":data.powerSupply,
				"项目编号":data.projectCode,
				"工程名称":data.projectName,
				"材料编码":data.meterials[i].meterialCode,
				"材料名称":data.meterials[i].meterialName,
				"单位":data.meterials[i].meterialUnit,
				"单价":data.meterials[i].meterialPrice,
				"是否业扩储备物资":data.meterials[i].meterialCheck,
				"备注信息":data.meterials[i].meterialAttention,
				"数量":data.meterials[i].num
			}
			arr.push(data1);
		}
	    var d=new Date();
    	var name = d.getTime() + String(parseInt(Math.random()*10000)) + ".xlsx";
    	var opts = [{sheetid:'One',header:true}];
    	var res = alasql('SELECT INTO XLSX("'+ name +'",?) FROM ?',
                     [opts,[arr]]);
	}
})
