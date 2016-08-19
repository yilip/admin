/*
 * 用于后台管理系统公共javascript功能
 */
$.fn.navTree = function(data){
	var navStr = '', navArr = [], dlength = data.length;
	// 创建nav列表
	if(dlength <= 8){
		$(this).css({"width":"1000px", "margin":"0 auto"});
	}
	for(var i=0;i<dlength;i++){
		navArr.push('<li class="pli" id="'+data[i]["id"]+'" onclick=navHref(\"'+data[i]["id"]+'\")>'+data[i]["text"]);
		navArr.push('</li>');
	}
	navStr = navArr.join("");
	$(this).html(navStr);
};
/**
 * @param text
 *            confirm传入的展示文字
 * @param fn
 *            confirm传入的点击确定的方法
 * @param param1
 *            confirm传入的fn方法的参数
 * @param url
 *            用于删除数据的url
 */
function confirm(text, fn, param1, url){
	// 动态生成confirm
	if($("#condiv").attr("id")){
		$("#condiv").remove();
	}
	var conArr = [], conStr = '';
	conArr.push('<div id="condiv" class="condiv">');
	conArr.push(text);
	conArr.push('<div><a class="button" id="con_sure">确定</a><a class="button" id="con_canel">取消</a></div>');
	conArr.push('</div>');
	conStr = conArr.join("");
	$("body").append(conStr);
	var divL = $(window).width()/2 - $("#condiv").width(), divT = $(window).height()/2 -$("#condiv").height();
	$("#condiv").css({"left":+divL+"px", "top":+divT+"px"}).show();
	// confirm按钮事件
	$("#con_sure").click(function(){
		$("#con_canel").click();
		if(url){
			fn(param1, url);
		}else{
			fn(param1);
		}
	});
	$("#con_canel").click(function(){
		$("#condiv").fadeOut();
	});
}
// 单一提示文字框
function alertfn(text){
	if($("#condiv").attr("id")){
		$("#condiv").remove();
	}
	// 动态生成alert
	var alertStr = '<div id="condiv" class="condiv" style="width:240px;">' + text + '<div><a class="button" id="alertBtn">知道了</a></div>';
	$("body").append(alertStr);
	var divL = ($(window).width() - $("#condiv").width())/2, divT = ($(window).height() - $("#condiv").height())/2;
	$("#condiv").css({"left":+divL+"px", "top":+divT+"px"}).show();
	$("#alertBtn").click(function(){
		$("#condiv").fadeOut();
	});
}
/**
 * @param id1
 *            展示列表的id
 * @param id2
 *            要查询的form的id
 */
function queryToGrid(id1, id2) {
	$("#" + id1).datagrid("reload",serializeObject($("#" + id2)));
}

function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
				if (o[this['name']]) {
					o[this['name']] = o[this['name']] + "," + this['value'];
				} else {
					o[this['name']] = this['value'];
				}
			});
	return o;
};
// 获取项目所在省份
function getProvice(p, c){
	$.ajax({
		url: path + "/area/getProvince.html",
		type: "post",
		dataType: "json",
		success: function(data){
			getLoanTypeFn(p, data["rows"],"city");
			// 级联修改标的子类型
			// getCitys(data["rows"][0]["id"], c);
		},
		error: function(){
			console.log("获取项目所在省份异常");
		}
	});
}
// 根据省份获取城市
function getCitys(id, cid,callback){
	$.ajax({
		url: path + "/area/getCity.html",
		type: "post",
		dataType: "json",
		data: {"pid": id},
		success: function(data){
			getLoanTypeFn(cid, data["rows"],"city");
			if (callback) {
				callback();
			}
		},
		error: function(){
			console.log("获取项目所在城市异常");
		}
	});
}
/**
 * @param id:
 *            要填充的select的id
 * @param data：
 *            填充的select的html内容
 * @param type:
 *            要显示省份和城市的类型
 */
function getLoanTypeFn(id, data, type){
	var loanStr = '', loanArr = [];
	loanArr.push('<option value="">-=请选择=-</option>');
	for(var i=0,tlength=data.length;i<tlength;i++){
		if(type){
			loanArr.push('<option value="'+data[i]["id"]+'">'+data[i]["name"]+'</option>');
		}else{
			loanArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
		}
	}
	loanStr = loanArr.join("");
	$("#" + id).html(loanStr);
}
/**
 * 获取担保公司、入驻商户数据
 * 
 * @param id:
 *            要展示数据的select
 * @param url：
 *            获取请求的url
 */
function getSelectData(id, url){
	$.ajax({
		url: path + url,
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["rows"], comStr = '<option value="">-=请选择=-</option>';
			for(var i=0,ilength=data.length;i<ilength;i++){
				comStr += '<option value="'+data[i]["userId"]+'">'+data[i]["comName"]+'</option>';
			}
			$("#" + id).html(comStr);
		},
		error: function(){
			console.log("获取商户数据异常");
		}
	});
}
/**
 * 跟据Cookie名字获取值
 * 
 * @param c_name
 * @returns String
 */
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1)
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}

// 删除cookies
function delCookie(name) 
{ 
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
    var cval=getCookie(name); 
    if(cval!=null) 
        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 

function getValidateStr(fromId){
	var rules='rules: {';
	var messages='messages: { ';
	$('#'+fromId+' .x-form-item').each(function(){
		rules+=$(this).find('input').attr('name')+': "required",';
		messages+=$(this).find('input').attr('name')+': "请填写'+$(this).find('label').text().substring(0,$(this).find('label').text().length-1)+'",';
	});
	rules=rules.substring(0,rules.length-1)+'}';
	messages=messages.substring(0,messages.length-1)+'}';
	alert(rules+','+messages+',');
}

/**
 * 
 * @param x
 *            日期
 * @param y
 *            "yyyy-MM-dd hh:mm:ss" || "yyyy-M-d h:m:s"
 * @returns
 */
function date2str(x,y) { 
	var z = {M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()}; 
	y = y.replace(/(M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-2)}); 
	return y.replace(/(y+)/g,function(v) {return x.getFullYear().toString().slice(-v.length)}); 
}

if ($.fn.combobox) {
	$.extend($.fn.combobox.defaults,{height:26});
	$.extend($.fn.validatebox.defaults.rules, {  
		selectValueRequired: {  
			validator: function(value,param){  
				return $.trim($(param[0]).combobox('getValue')) != '';  
			},  
			message: '请填写{1}'
		}  
	}); 
}

/**
 * 发送验证码倒计时
 * 
 * @param inputId
 */
function sendAuthCode(inputId){
	var time=60;
	var timer = window.setInterval(function(){
		time--;
		if (time==0) {
			window.clearInterval(timer);
			$('#'+inputId).next().removeAttr("disabled");
			$('#'+inputId).next().removeAttr("style");
			$('#'+inputId).next().val('获取验证码');
		}else if(time > 0){
			$('#'+inputId).next().attr("disabled", "disabled");
			$('#'+inputId).next().css({
				'cursor':'default'
			});
			// 设置disabled属性
			$('#'+inputId).next().val(time+' 秒后重新获取');
		}
	},1000);
}

$(function() {
	document.onkeydown = function(e) {
		var theEvent = window.event || e;
		var code = theEvent.keyCode || theEvent.which;
		if (code == 13) {
			$('#searchBtn').click();
		}
	};
});

function checkNum(e,obj) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0 || k == 46) {
    	
    } else {
        if (window.event) {
            window.event.returnValue = false;
        } else {
            e.preventDefault(); //for firefox 
        }
    }
}

function getMask(msg){
	return $('<div class="loading" style="border:solid 2px #95B8E7;">'+msg+'</div>').dialog({title: null,shadow:false,modal: true,style:{'border':'none','background':'none'}});
}

function closeUploadImages(iframeUrl){
	$('.uploadIframe').each(function(){
		if (iframeUrl.indexOf($(this).attr('src'))>-1) {
			$(this).dialog('close');
			return false;
		}
	});
}

/**
 *  判断变量是否为空或者'undefined'或者''
 * @param obj
 * @returns {Boolean}
 */
function isNull(obj){
	if(null == obj || obj == undefined || 'undefined' == obj || '' == obj){
		return true;
	}
	return false;
}

function commonExportExcel(excelId,params){
	var paramsObj={};
	if (params) {
		paramsObj=params;
	}
	paramsObj.excelId =excelId;
	$('#list_search').form('submit', {
		url : path+'/exportExcel.html',
		queryParams: paramsObj,
		success : function(data) {
			
		}
	});
}

function getXml(columns){
	var excelText='<report-header>';
	for ( var i = 0; i < columns[0].length; i++) {
		var field =columns[0][i].field;
		if(field=='id'||field=='view'){
			continue;
		} else if (field.length > 4 && (field.toLowerCase().lastIndexOf('date')==field.length-4 || field.toLowerCase().lastIndexOf('time')==field.length-4) ) {
			excelText+='<column name="'+columns[0][i].title+'" mapping="'+columns[0][i].field+'" datatype="date" format="yyyy-MM-dd HH:mm:ss"/>';
		}else{
			excelText+='<column name="'+columns[0][i].title+'" mapping="'+columns[0][i].field+'"/>';
		}
	}
	excelText+='</report-header>';
	alert(excelText);
}


function createWebUploader(pickId,showId,setValueId,sForm){
	var uploader=WebUploader.create({
		auto: true,
	    pick: {
	        id: '#'+pickId,
	        multiple: false,
	        label: '点击选择图片'
	    },
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    },
	    // swf文件路径
	    swf:path+'/js/common/webuploader/Uploader.swf',
	    chunked: true,
	    compress :false,
	    server: path+'/uploadImage.html',
	    fileNumLimit: 300,
	    fileSizeLimit: 5 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json.state == 'SUCCESS') {
            	$('#'+showId+' .filelist').html('<img src="'+fileUrl+json.url+'" style="width:200px; height:200px;"/>');
            	$('#'+sForm+' input[name="'+setValueId+'"]').val(json.url);
            } else {
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}
//扩展Date的format方法   
Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
        "q+": Math.floor((this.getMonth() + 3) / 3),  
        "S": this.getMilliseconds()  
    }  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}
