/**
 * bootstrap.jDialog v1.1
 * 基于bootstrap modal的封装
 * author:huangyi
 * create date :2015-05-26
 * update by 2017-10-18 base on bootstrap 3.3
 */
var jDialog = {
    /**
     * alert弹框
     * @param title
     * @param msg
     */
    alert : function(title,msg){
        var dialogId = 'jDialog_alert';
        var dialog = $('#'+dialogId);
        if(dialog.length > 0){
            $("#"+dialogId+"_jDialog_title",dialog).text(title);
            $("#"+dialogId+"_jDialog_body",dialog).html(msg);
        }else{
            var buttons = '<button class="btn" data-dismiss="modal" aria-hidden="true">确定</button>';
            var html = this.buildHtml(dialogId,title,msg,buttons)
            $(document.body).append(html);
        }
        $('#'+dialogId).modal('show');
    },
    /**
     * confirm弹框
     * @param title
     * @param msg
     * @param callback OK按钮的回调事件
     */
    confirm : function(title,msg,callback){
        var dialogId = 'jDialog_confirm';
        var dialog = $('#'+dialogId);
        if(dialog.length > 0){
            $("#"+dialogId+"_jDialog_title",dialog).text(title);
            $("#"+dialogId+"_jDialog_body",dialog).html(msg);
        }else{
            var buttons = '<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button><button id="'+dialogId+'_jDialog_ok" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">确定</button>';
            var html = this.buildHtml(dialogId,title,msg,buttons)
            $(document.body).append(html);
        }
        if(typeof callback ==='function'){
            $('#'+dialogId+'_jDialog_ok').bind('click',callback);
        }else{
            $('#'+dialogId+'_jDialog_ok').unbind();
        }
        $('#'+dialogId).modal('show');
    },
    /**
     * prompt弹框
     * @param title
     * @param msg
     * @param callback OK按钮的回调事件,返回true或false决定是否关掉弹出框
     */
    prompt : function(title,msg,callback){
        var dialogId = 'jDialog_prompt';
        var dialog = $('#'+dialogId);
        var body = '<p>'+msg+'</p><div><form class="form"><input type="text" class="form-control" id="'+dialogId+'_body_input" style="width:98%" /></form></div>';
        if(dialog.length > 0){
            $("#"+dialogId+"_jDialog_title",dialog).text(title);
            $("#"+dialogId+"_jDialog_body",dialog).html(body);
        }else{
            var buttons = '<button id="'+dialogId+'_jDialog_ok" class="btn btn-primary">确定</button><button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>';
            var html = this.buildHtml(dialogId,title,body,buttons)
            $(document.body).append(html);
        }
        if(typeof callback ==='function'){
            $('#'+dialogId+'_jDialog_ok').bind('click',function(){
                var data = $('#'+dialogId+'_body_input',"#"+dialogId).val();
                if(callback($('#'+dialogId),data)){
                    $('#'+dialogId).modal('hide');
                }
            });
        }else{
            $('#'+dialogId+'_jDialog_ok').unbind();
        }
        $('#'+dialogId).modal('show');
    },
    buildHtml : function(id,title,msg,buttons){
        var html = '<div id="'+id+'" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="jDilalog_title" data-backdrop="static" >' //aria-hidden="true"
            +'<div class="modal-dialog" role="document">'
            +'<div class="modal-content">'
            +'<div class="modal-header">'
            //+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'
            +' <h3 id="'+id+'_jDialog_title">'+title+'</h3>'
            +'</div>'
            + '<div class="modal-body" id="'+id+'_jDialog_body">'
            +msg
            +'</div>'
            +'<div class="modal-footer">'
            + buttons
            +'</div>'
            +'</div>';
        +'</div>';
        +'</div>';

        return html;
    },
    /**
     * 显示弹窗
     * @param dialogId 窗体ID
     * @param title 标题
     * @param body 窗口内容html
     * @param buttons 按钮
     */
    show : function(dialogId,title,body,buttons,isEscClose,onBeforeShow,isCache){
        var dialog = $('#'+dialogId);
        if(isCache){
            isCache = true;
        }
        if(dialog.length == 0 || !isCache){
            $('#'+dialogId).remove();
            var buttonsHtml = "";
            if($.isArray(buttons)){
                for(var btnIndex in buttons){
                    buttonsHtml += this.createButton(dialogId,buttons[btnIndex]);
                }
            }
            var html = this.buildHtml(dialogId,title,body,buttonsHtml)
            $(document.body).append(html);
            //绑定事件
            if($.isArray(buttons)){
                for(var btnIndex in buttons){
                    var btn = buttons[btnIndex];
                    var btnId = dialogId+"_"+btn.id;
                    if(btn.callback && typeof btn.callback == 'function'){
                        $('#'+btnId,$('#'+dialogId)).bind('click',{body:$('#'+dialogId+"_jDialog_body"),dialogId:dialogId},btn.callback);
                    }
                }
            }
        }
        if(onBeforeShow && typeof onBeforeShow == 'function'){
            $('#'+dialogId).on('shown.bs.modal',onBeforeShow);
        }
        if(isEscClose != undefined){
            $('#'+dialogId).modal({
                keyboard: isEscClose,
                show : true
            });
        }else{
            $('#'+dialogId).modal('show');
        }
    },
    buttonRef:{
        CLOSE: 'close'
    },
    /**
     *
     * @param dialogId
     * @param button
     * button : {
     * id 按钮ID,
     * ref 值为buttonRef的枚举值
     * cls 按钮样式
     * text 文字
     * callback 回调函数
     */
    createButton : function(dialogId,button){
        var buttonHtml = '<button ';
        //定义按钮样式
        buttonHtml +='class="btn';
        if(button['cls'] && typeof button.cls == 'string'){
            buttonHtml +=' '+button.cls;
        }
        buttonHtml+=' " ';
        //定义button id
        buttonHtml += ' id="'+dialogId+'_'+button.id+'" ';
        //如果是关闭按钮，添加关闭事件
        if(button.ref==this.buttonRef.CLOSE){
            buttonHtml+='data-dismiss="modal"';
        }
        buttonHtml+='>';
        buttonHtml+=button.text;
        buttonHtml+='</button>';
        return buttonHtml;
    }
};
