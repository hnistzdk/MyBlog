var jboltBody=$("body");
var jboltWindow=$(window);
var jboltWindowWidth=jboltWindow.width();
var jboltWindowHeight=jboltWindow.height();

/**
 * 刷新当前页
 * @returns
 */
function reloadCurrentPage(){
	var url=self.location.href;
	/*if(url.indexOf("globalconfig")!=-1){
		self.location.href="admin";
	}else{*/
	history.go(0);
//	}
}
/**
 * 刷新主区域
 * @returns
 */
function refreshPjaxContainer(ele){
	if(ele){
		var eleObj=getRealJqueryObject(ele);
		if(isOk(eleObj)){
			disposeTooltip(eleObj);
		}
	}
	if(self!=top){
		reloadCurrentPage();
	}
// 	else{
// //		var withTabs=isWithtabs();
// 		if(jboltWithTabs){
// 			refreshCurrentTabContent();
// 		}else{
//
// 			var iframe=mainPjaxContainer.find("iframe.jbolt_main_iframe");
// 			if(isOk(iframe)){
// 				iframe.attr("src",iframe.attr("src"));
// 			}else{
// 				if(isIE()){
// 					reloadCurrentPage();
// 				}else{
// 					$.pjax.reload(mainPjaxContainerId);
// 				}
// 			}
// 		}
// 	}
}

/**
 * 判断数据是否是正确有值的数据
 */
var isOk=function(obj){
	if(!obj){return false;}
	if(isArray(obj)){
		return obj.length>0;
	}
	var type=typeof(obj);
	var result=false;
	switch (type) {
		case "object":
			if(obj.length==undefined){
				result = Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && JSON.stringify(obj)!='{}';
			}else{
				result = obj.length&&obj.length>0;
			}
			break;
		case "string":
			result=(obj!=""&&obj!="0"&&obj.length>0);
			break;
		case "number":
			result=obj>0;
			break;
		case "boolean":
			result=obj;
			break;
		case "function":
			result=true;
			break;
		case "undefined":
			result=false;
			break;

		default:
			result=(obj.length&&obj.length>0);
			break;
	}
	return result;
}

function isDOM(item){
	var isdom=false;
	try{
		item.is(":hidden");
	}catch(ex){
		isdom=true;
	}
	return isdom;
}
/**
 * 得到真实的jquery object
 * @param ele
 * @returns
 */
function getRealJqueryObject(ele){
	if(!ele){
		return null;
	}
	var type=typeof(ele);
	var eleObj=null;
	if(type=="string"){
		var startChar=ele.charAt(0);
		if(startChar=='#'||startChar=='.'){
			eleObj=$(ele);
		}else{
			eleObj=$("#"+ele);
		}
	}else if(type=="object"){
		if(isDOM(ele)){
			eleObj=$(ele);
		}else{
			eleObj=ele;
		}
	}
	return eleObj;
}

/**
 * 获取一个指定ele的父元素jquery对象
 * @param parentEle
 * @returns
 */
function getRealParentJqueryObject(parentEle){
	var parent=getRealJqueryObject(parentEle);
	return parent?parent:jboltBody;
}
/**
 * 取消tooltip
 * @param ele
 * @returns
 */
function disposeTooltip(ele){
	if(ele[0].hasAttribute("tooltip")||ele[0].hasAttribute("data-tooltip")){
		ele.tooltip("dispose");
		ele.tooltip({ boundary: 'window',container:"body"});
	}
}

/**
 * 判断对象是否为array
 * @param obj
 * @returns {Boolean}
 */
function isArray(obj){
	return $.isArray(obj);
}

function isEmpty(something){
	return (something=="undefined"||something==null||something==""||something==false);
}

function isNotEmpty(something){
	return ((something!=null&&something!="undefined"&&something!="")||something==true);
}

//弹出dialog类库
var DialogUtil={
	initBy:function(cssSelector,parentEle){
		var parent=getRealParentJqueryObject(parentEle);
		if(!isOk(parent)){return false;}
		var that=this;
		parent.on("click",cssSelector,function(e){
			e.preventDefault();
			e.stopPropagation();
			that.openBy(this);
			/*var target=trigger.data("target");
            if(target=="parent"){
                parent.DialogUtil.openBy(trigger);
            }else if(target=="outparent"){
                parent.parent.DialogUtil.openBy(trigger);
            }else{
                that.openBy(trigger);
            }*/
			return false;
		});
	},
	openBy:function(ele){
		var action=getRealJqueryObject(ele);
		if(!isOk(action)){LayerMsgBox.alert("DialogUtil.openBy(ele)参数异常",2);return false;}
		disposeTooltip(action);
		var target=action.data("target");
		if(target){
			action.data("target","").attr("data-target","");
			if(target=="parent"){
				parent.DialogUtil.openBy(action);
				action.data("parent","parent");
			}else if(target=="outparent"){
				parent.parent.DialogUtil.openBy(action);
			}
			action.data("target",target).attr("data-target",target);
			return false;
		}



		var url=action.data("url");
		if(!url){
			url=action.attr("href");
			if(url=="javascript:void(0)"){
				url=null;
			}
		}
		var contentid=action.data("contentid");
		var content=action.data("content");
		if(!url&&!contentid&&!content){LayerMsgBox.alert("没有设置dialog的加载URL或者内容", 2); return false;}
		var checkHandler=action.data("check-handler");
		if(checkHandler){
			var checkResult;
			var exeCheck_handler=eval(checkHandler);
			if(exeCheck_handler&&typeof(exeCheck_handler)=="function"){
				checkResult=exeCheck_handler(action);
				if(typeof(checkResult)=="boolean" && checkResult==false){
					return false;
				}
				if(typeof(checkResult)!="boolean"){
					if(isArray(checkResult)){
						url=url+checkResult.join(",");
					}else{
						url=url+checkResult;
					}
				}
			}
		}
		var title=action.data("dialog-title")||action.data("title");
		var handler=action.data("handler");
		var closeHandler=action.data("close-handler");
		var dialog_area=action.data("area");
		var w="800px";
		var h="500px";
		if(dialog_area){
			var area=dialog_area.split(",");
			var ww=area[0];
			var hh=area[1];
			if(ww.indexOf("px")!=-1||ww.indexOf("%")!=-1){
				w=ww;
			}else{
				w=ww+"px";
			}
			if(hh.indexOf("px")!=-1||hh.indexOf("%")!=-1){
				h=hh;
			}else{
				h=hh+"px";
			}
		}
		if(w.indexOf("%")==-1&&w.indexOf("px")!=-1){
			var pw=Number(w.replace("px",""));
			if(pw>jboltWindowWidth){
				w=(jboltWindowWidth-10)+"px";
			}
		}
		if(h.indexOf("%")==-1&&h.indexOf("px")!=-1){
			var ph=Number(h.replace("px",""));
			if(ph>jboltWindowHeight){
				h=(jboltWindowHeight-10)+"px";
			}
		}
		var dialog_scroll=action.data("scroll");
		if(dialog_scroll==undefined||dialog_scroll=="undefined"||(typeof(dialog_scroll)=="string"&&dialog_scroll!="no")||(typeof(dialog_scroll)=="boolean"&&dialog_scroll==true)){
			dialog_scroll="yes";
		}else{
			dialog_scroll="no";
		}
		var fs=action.data("fs");
		if(fs&&(fs=="true"||fs==true)){
			dialog_scroll="yes";
		}
		//close dialog and refresh parent
		var cdrfp=action.data("cdrfp");
		if(cdrfp==undefined||cdrfp=="undefined"||cdrfp==""){
			cdrfp=false;
		}
		var portalId=action.data("portalid");
		if(!portalId){
			portalId=action.data("portal");
		}
		var btn=action.data("btn");
		var action_id=action.attr("id");
		if(!action_id){
			action_id="dialogbtn_"+randomId();
			action.attr("id",action_id);
		}
		var btnAlign=action.data("btn-align");
		var shade=action.data("shade");
		var shadeClose=action.data("shadeclose");
		this.openNewDialog({
			ele:action,
			title:title,
			width:w,
			height:h,
			url:url,
			shade:shade,
			shadeClose:shadeClose,
			scroll:dialog_scroll,
			btn:btn,
			btnAlign:btnAlign,
			handler:handler,
			closeHandler:closeHandler,
			cdrfp:cdrfp,
			fs:fs,
			portalId:portalId,
			contentId:contentid,
			content:content
		});
	},openNewDialog:function(options){
		if(options.fs){
			options.width="100%";
			options.height="100%";
		}else{
			if(options.width){
				if(options.width.indexOf("%")==-1&&options.width.indexOf("px")==-1){
					options.width=options.width+"px";
				}
			}else{
				options.width="800px";
			}
			if(options.height){
				if(options.height.indexOf("%")==-1&&options.height.indexOf("px")==-1){
					options.height=options.height+"px";
				}
			}else{
				options.width="500px";
			}
		}
		// var btn=[];
		// var dbtn=options.btn;
		// if(!dbtn||dbtn=="yes"||dbtn=="true"){
		// 	btn=["确定", '关闭'];
		// }else if(dbtn&&dbtn=="no"){
		// 	btn=[];
		// }else if(dbtn&&dbtn=="close"){
		// 	btn=["确定", '关闭'];
		// }else if(dbtn&&dbtn.indexOf(",")!=-1){
		// 	btn=dbtn.split(",");
		// }
		var type=2;
		if(!(options.url)&&(options.contentId||options.content)){
			type=1;
		}
		var content="";
		if(type==1){
			if(options.contentId){
				content=$("#"+options.contentId).html();
			}else if(options.content){
				if(options.content=="this"){
					content=options.ele.html();
					content='<div class="p-3 text-break">'+content+'</div>';
				}else{
					content='<div class="p-3 text-break">'+options.content+'</div>';
				}
			}
		}else {
			// var url=actionUrl(options.url);
			var url=options.url;
			if(isOk(options.ele)){
				// url=processEleUrlByLinkOtherParamEle(options.ele,url,false);
				if(!url){
					return false;
				}
				// url=processJBoltTableEleUrlByLinkColumn(options.ele,url);
				if(!url){
					return false;
				}
			}
			// url=processUrlRqType(url,"dialog");
			content=[url,options.scroll];
		}

		shade=(options.shade!=undefined||options.shade=="")?options.shade:0.3;
		shadeClose=(options.shadeClose!=undefined)?options.shadeClose:false;
		var btn_align=options.btnAlign?options.btnAlign:"right";
		var btnAlign='r';
		switch (btn_align) {
			case "left":
				btnAlign='l';
				break;
			case "center":
				btnAlign='c';
				break;
			case "right":
				btnAlign='r';
				break;
		}
		var that=this;
		var lindex=layer.open({
			type: type,
			title: options.title,
			shadeClose:shadeClose,
			shade: shade,
			maxmin:true,
			area: [options.width, options.height],
			content:content,
			// btn:btn,
			btnAlign:btnAlign,
			success:function(obj,index){
//					  if(type==2){
//						  var iframeWin = window[$(".layui-layer-iframe").find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
////						  iframeWin.focus();
//					  }
			},
			yes:function(index,layero){
				//点击确定按钮处理
				if(type==2){
					var iframeWin = window[$(".layui-layer-iframe").find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					console.log('iframeWin',iframeWin)
					iframeWin.submitThisForm();
				}
			},end:function(){
				if(options.exeOkHandler){return false;}
				if(options.cdrfp){
					if(options.closeHandler){
						var handlerType=typeof(options.closeHandler);
						if(handlerType=="string"){
							that.exeBindHandler(options.closeHandler,options);
						}else if(handlerType=="function"){
							options.closeHandler();
						}
					}else{
						refreshPjaxContainer();
					}
				}else{
					if(options.closeHandler){
						var handlerType=typeof(options.closeHandler);
						if(handlerType=="string"){
							that.exeBindHandler(options.closeHandler,options);
						}else if(handlerType=="function"){
							options.closeHandler();
						}
					}
				}
			}
		});

		var layObj=$("#layui-layer"+lindex);
		// if(dbtn&&dbtn=="close"){
		// 	layObj.find("a.layui-layer-btn0").hide();
		// }
		// if(dbtn&&dbtn=="no"){
		// 	layObj.find(".layui-layer-btn").remove();
		// }

//			  layObj.data("trigger-actionid",options.ele.attr("id"));
		layObj.data("trigger-action",options.ele);
		if(options.ele&&options.ele.data("in-editable-td")){
			layObj.data("link-editable-td",options.ele.closest("td"));
		}
	},
	getCurrent:function(){
		return jboltBody.find(".layui-layer").first();
	},
	getByIndex:function(index){
		return jboltBody.find("#layui-layer"+index);
	},getCurrentTriggerEle:function(){
		var layerDialog=this.getCurrent();
		if(isOk(layerDialog)){
			return layerDialog.data("trigger-action");
//				 var triggerId = layerDialog.data("trigger-actionid");
//				 if(triggerId){
//					 return jboltBody.find("#"+triggerId);
//				 }
		}
		return null;
	},exeBindHandler:function(handler,options){
		if(handler=="refreshPortal"){
			if(options.portalId){
				LayerMsgBox.success("操作成功",500,function(){
					$("#"+options.portalId).ajaxPortal(true);
				});
			}else{
				LayerMsgBox.alert("没有配置data-portalid",2);
			}

		}else if(handler=="jboltTablePageToFirst"){
			loadJBoltPlugin(['jbolttable'], function(){
				jboltTablePageToFirst(options.ele);
			});
		}else if(handler=="jboltTablePageToLast"){
			loadJBoltPlugin(['jbolttable'], function(){
				jboltTablePageToLast(options.ele);
			});
		}else if(handler=="refreshJBoltTable"||handler=="jboltTableRefresh"){
			loadJBoltPlugin(['jbolttable'], function(){
				refreshJBoltTable(options.ele);
			});
		}else if(handler=="refreshJBoltMainTable"||handler=="jboltMainTableRefresh"){
			loadJBoltPlugin(['jbolttable'], function(){
				refreshJBoltMainTable(options.ele);
			});
		}else if(handler=="refreshCurrentAjaxPortal"){
			refreshCurrentAjaxPortal(options.ele);
		}else if(handler=="removeJBoltTableCheckedTr"){
			loadJBoltPlugin(['jbolttable'], function(){
				removeJBoltTableCheckedTr(options.ele,false);
			});
		}else if(handler=="jboltTableRemoveCheckedRow"){
			loadJBoltPlugin(['jbolttable'], function(){
				jboltTableRemoveCheckedRow(options.ele,false);
			});
		}else{
			var exe_handler=eval(handler);
			if(exe_handler&&typeof(exe_handler)=="function"){
				exe_handler();
			}
		}
	}
}

//layer msg模块封装
var LayerMsgBox={
	alert:function(msg,icon,handler){
		if(icon){
			layer.alert(msg,{icon:icon}, function(index){
				if(handler){
					handler();
				}
				layer.close(index);
			});
		}else{
			layer.alert(msg, function(index){
				if(handler){
					handler();
				}
				layer.close(index);
			});
		}

	},
	confirm:function(msg,handler,cancelHandler){
		layer.confirm(msg, {icon: 3, title:'请选择'}, function(index){
			if(handler){
				handler();
			}
			layer.close(index);
		},function(index){
			if(cancelHandler){
				cancelHandler();
			}
			layer.close(index);
		});
	},
	/**
	 * 弹出成功信息,并执行回调方法
	 * @param msg
	 * @param time
	 * @param handler
	 */
	success:function(msg,time,handler){
		if(!msg){msg="操作成功";}
		if(!time){time=1000;}
		var index=layer.msg(msg,{time:time,icon:1},function(){
			if(handler){
				handler();
			}
		});
		return index;
	},


	/**
	 * 弹出Error,并执行回调方法
	 * @param msg
	 * @param time
	 */
	error:function(msg,time,handler){
		if(!msg){msg="错误";}
		if(!time){time=1500;}
		var index=layer.msg(msg,{time:time,icon:2},function(){
			if(handler){
				handler();
			}
		});
		return index;
	},
	prompt:function(title,defaultMsg,handler,type){
		if(type==undefined){
			type=2;
		}
		var i=layer.prompt({title: title,value:(defaultMsg?defaultMsg:""),formType: type}, function(text, index){
			if(handler){
				handler(index,text);
			}
		});
		return i;
	},
	/**
	 * 弹出进度
	 * @param msg
	 * @param time
	 */
	loading:function(msg,time,handler){
		if(!msg){msg="执行中...";}
		var index=null;
		time=(time?time:10000);
		if(time){
			index=layer.msg(msg,{time:time,icon:16,shade:0.3},function(){
				if(handler){
					handler();
				}
			});
		}else{
			index=layer.msg(msg,{icon:16});
		}
		return index;
	},
	close:function(index){
		layer.close(index);
	},
	closeAll:function(type){
		if(type){
			layer.closeAll(type);
		}else{
			layer.closeAll();
		}
	},
	closeLoading:function(){
		setTimeout(function(){
			layer.closeAll('dialog'); //关闭加载层
		}, 500);
	},
	closeLoadingNow:function(){
		layer.closeAll('dialog'); //关闭加载层
	},
	load:function(type,time){
		var index=null;
		if(time){
			index=layer.load(type,{time:time});
		}else{
			index=layer.load(type);
		}
		return index;
	},
	closeLoad:function(){
		setTimeout(function(){
			layer.closeAll('loading'); //关闭加载层
		}, 200);
	},
	closeLoadNow:function(){
		layer.closeAll('loading'); //关闭加载层
	}

}
