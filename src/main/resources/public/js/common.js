/**
 * Created by lzz on 17/5/2.
 */

window.HOST_URL = "http://" + window.location.host + "/";
window.STATIC_URL = window.HOST_URL;
window.JSTPL_URL = window.STATIC_URL + "jstpl/";

$(document).on("click", ".step-button", function(){
    var width = $(this).data("step-width");
    $("#step-bar").css("width", width);
});

$(document).on("click", ".click-selected-button", function(){
    $(".click-selected-button").removeClass("selected");
    $(this).addClass("selected");
});

$(document).on("click", ".last-click-button", function(){
    $(".last-click-button").removeClass("selected");
    $(this).addClass("selected");
    var contentTag = $(this).data("tag");
    var key = get_last_click_key();
    set_cookie(key, contentTag);
});
// 初始化页面
function last_click(){
    // 如果包含 #params 就直接跳转到该页面
    var target = window.location.hash;
    if( target ){
        $(target).trigger("click");
        return;
    }

    var key = get_last_click_key();
    var old_contentTag = get_cookie(key);
    if( typeof(old_contentTag) == "undefined" || old_contentTag == null ){
        $(".last-click-button.selected").trigger("click");
        return;
    }
    $(".last-click-button").each(function(i){
        var contentTag = $(this).data("tag");
        if( contentTag === old_contentTag ){
            $(this).trigger("click");
        }
    });
}
function last_selected_click(){
    $(".last-click-button.selected").trigger("click");
}

function get_last_click_key(){
    var key = window.location.pathname;
    var paths = key.split("/");
    var cookie_key = "";
    for(var i in paths){
        if( paths[i] ){
            cookie_key += paths[i] + "_";
        }
    }
    return cookie_key + "last_click_button";
}

$(document).on("click", ".tab-button,.tab-top-button", function(){
    var group = $(this).data("group");
    $("[data-group='" + group + "']").removeClass("active");
    $(this).addClass("active");
});

function refresh(flag, msg){
    var str = "";
    if( msg ){
        str = msg;
    }
    $(".layui-layer-close").trigger("click");
    if( flag ){
        sparrow_win.msg( "success " + str, {icon:6},function(){
           $(".last-click-button.selected").trigger("click");
        });
    }else{
        sparrow_win.msg( "fail " + str, {icon:5},function(){
           $(".last-click-button.selected").trigger("click");
        });
    }
}

/**
 * 设置 uuid
 */
function set_cookie(key, value)
{
    var Days = 360;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = key+"="+ escape ( value ) + ";expires=" + exp.toGMTString() + ";path=/";
}

function clear_cookie(){
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i =  keys.length; i--;){
            remove_cookie(keys[i]);
        }
    }
}

function remove_cookie(key){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=get_cookie(key);
    if(cval!=null){
        document.cookie = key+"="+ escape ( cval ) + ";expires=" + exp.toGMTString() + ";path=/";
    }
}

/**
 * 获取 cookie
 * @param name
 * @returns {null}
 */
function get_cookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}


function decimal(num,v){
    var vv = Math.pow(10,v);
    return Math.round(num*vv)/vv;
}

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(str.indexOf('{')>-1){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            console.log(e);
            return false;
        }
    }else if(typeof str == 'object'){
        return true;
    }
    return false;
}

function syntaxHighlightRedisResult(content){
    if( !content ){
        return;
    }
    var res = "";
    if( isJSON(content) ){
        res = syntaxHighlight( content );
    }else{
        var arr = content.split(",");
        var cls = 'string';
        for( var i in arr ){
            var tmps = arr[i].split("=");
            if( tmps.length == 2 ){
                res += '<span class="key">' + tmps[0] + '</span>';
                res += '=';
                res += '<span class="string">' + tmps[1] + '</span><br>';
            }else{
                res += '<span class="redis-result-item ' + cls + '">' + arr[i] + '</span><br>';
            }
        }
    }
    return res;
}


function syntaxHighlightLine(content){
    if( !content ){
        return;
    }
    var arr = content.split("\n");
    var res = "";
    var cls = 'number';
    for( var i in arr ){
        var tmps = arr[i].split(":");
        if( tmps.length == 2 ){
            res += '<span class="key">' + tmps[0] + '</span>';
            res += ':';
            res += '<span class="string">' + tmps[1] + '</span>';
        }else{
            res += '<span class="' + cls + '">' + arr[i] + '</span>';
        }
    }
    return res;
}

function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&');
    json = json.replace(/</g, '<');
    json = json.replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}

$(document).on("click", ".double-button", function(){
    $(".double-button").removeClass("double-button-selected");
    $(this).addClass("double-button-selected");
});

