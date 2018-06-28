package com.forever.zhb;

public class Constants {
	
	
	//classes目录
	public static final String RESOURCE_PATH = Constants.class.getClassLoader().getResource("").getPath();
	
	//项目的根路径,war包里
	//String realPath = request.getSession().getServletContext().getRealPath("/");
	
    //ip地址
    public static String IP = "ip";
    
    //主机
    public static String HOST_NAME = "host_name";
    
    // Request 对象
    public static String REQUEST_ERROR = "errorMsg";
    
    //异常页面
    public static String ERROR_URL = "/error/error.jsp";
    
    //项目名称
    public static String TARGET_NAME = "zhb_forever";
    
    //image
    public static String IMAGE_PATH = "image";
    
    //image 最大
    public static Long IMAGE_MAX_SIZE = 10*1024*1024L;
    
    //video
    public static String VIDEO_PATH = "video";
    
    //video 最大
    public static Long VIDEO_MAX_SIZE = 50*1024*1024L;
    
    
    public static int PAGE_SIZE = 9;
    
    public static String SESSION_LOGINFODATA = "session_UserInfoData";
    
    public static String NEWSPROTO_PATH = "com.forever.zhb.proto.NewsProto$News";
    
    
    
    
    
    
    public static String TEMPLATE = "<%if(flag){%>flag:true！" + "\n"+
            "<%}else{%>flag:false<%}%> ";
    
    public static String tempHead = "<meta charset=\"utf-8\"/>" + "\n" +
            "<meta http-equiv=\"Content-Language\" content=\"zh-cn\" />" + "\n" +
            "<meta http-equiv=\"Pragma\" content=\"no-cache\">" + "\n" +
            "<meta http-equiv=\"Cache-Control\" content=\"no-cache\">" + "\n" +
            "<meta http-equiv=\"Expires\" content=\"0\"> " + "\n" +
            "<title>投票管理系统</title>" + "\n" +
            "<link href=\"/css/vote.css\" rel=\"stylesheet\">" + "\n" +
            "<link href=\"/css/t_vote.css\" rel=\"stylesheet\">" + "\n" +
            "<script src=\"/js/jquery-1.12.0.min.js\"></script>" + "\n" +
            "<script src=\"/js/jquery.validate.js\"></script>" + "\n" +
            "<script>" + "\n" +
            "$().ready(function() {" + "\n" +
            "  var params={},paramstip={};;" + "\n" +
            "   $(\".question\").each(function(i,it){" + "\n" +
            "    if($(it).hasClass('required')){" + "\n" +
            "      if($(it).attr('data-type') == \"4\" ){" + "\n" +
            "        var temp = $(it).find('.inputs').children().eq(0).children().attr('name');" + "\n" +
            "        paramstip[temp] = '这是一道必选题';" + "\n" +
            "      }else if($(it).attr('data-type') == \"5\" ){" + "\n" +
            "        var temp = $(it).find('.inputs').children().eq(2).children().attr('name');" + "\n" +
            "        var minSelect = $(it).find('.inputs').children().eq(0).val();" + "\n" +
            "        var maxSelect = $(it).find('.inputs').children().eq(1).val();" + "\n" +
            "        paramstip[temp] = '这是一道必选题，请至少选择 ' + minSelect + ' 个，最多选择 ' +  maxSelect + ' 个';" + "\n" +
            "      }else{" + "\n" +
            "        var temp = $(it).find('.inputs').children().eq(0).attr('name');" + "\n" +
            "        paramstip[temp] = '这是一道必做题';" + "\n" +
            "      } " + "\n" +
            "      params[temp] = 'required';" + "\n" +
            "    }else if($(it).hasClass('rangelength')){" + "\n" +
            "        if($(it).attr('data-type') == \"5\" ){" + "\n" +
            "          var temp = $(it).find('.inputs').children().eq(2).children().attr('name');" + "\n" +
            "          var maxSelect = $(it).find('.inputs').children().eq(1).val();" + "\n" +
            "          paramstip[temp] = '最多选择 ' +  maxSelect + ' 个';" + "\n" +
            "          params[temp] = 'rangelength';" + "\n" +
            "        }" + "\n" +
            "    }" + "\n" +
            "  })" + "\n" +
            "  console.log(paramstip);" + "\n" +
            "  console.log(params);" + "\n" +
            "  $(\"#signupForm\").validate({" + "\n" +
            "    rules: params," + "\n" +
            "    messages: paramstip," + "\n" +
            "    wrapper:\"p\"," + "\n" +
            "    errorPlacement: function(error, element) {" + "\n" +
            "      error.appendTo(element.parents('.inputs').siblings('.q_title'));" + "\n" +
            "    }," + "\n" +
            "  });" + "\n" +
            "});" + "\n" +
            "</script>";

    public static String tempContent = "<%if(group.status == \"0\" && !flag){%>投票尚未开始！" + "\n"+
            "<%}else if(group.status == \"2\" && !flag){%>投票已结束！" + "\n"+
            "  <%if(group.enableCnt){%>" + "\n"+
            "<a href=\"/vote/result_showResult.action?groupId=${group.id}\" class=\"survey_btn\" <%if(flag){%>onclick=\"return false\"<%}%>>查看结果</a>" + "\n"+
            "  <%}%>" + "\n"+
            "<%}else if(voted &&((group.type != 1)||(group.modifyResult == \"0\"))){%>您已经参加过投票了！" + "\n"+
            "  <%if(group.enableCnt){%>" + "\n"+
            "<a href=\"/vote/result_showResult.action?groupId=${group.id}\" class=\"survey_btn\" <%if(flag){%>onclick=\"return false\"<%}%>>查看结果</a>" + "\n"+
            "  <%}}else{%>" + "\n"+
            "<div id=\"bd\">" + "\n"+
            "  <div class=\"width1000\">" + "\n"+
            "    <div id=\"add_v\">" + "\n"+
            "      <form action='/vote/<%if(group.type == 0){%>normSubmit_<%}else{%>real/<%}%>submit.action' method='post' id=\"signupForm\" >" + "\n"+
            "        <input type=\"hidden\" name=\"group.id\" value=\"${group.id}\" />" + "\n"+
            "        <div class=\"add_v_mian2\" >" + "\n"+
            "          <div class=\"survey_main\">" + "\n"+
            "            <div class=\"survery_title\">" + "\n"+
            "              <div class=\"inner\">" + "\n"+
            "                <h2 class=\"title_content\"> <strong>${group.title}</strong>" + "\n"+
            "                </h2>" + "\n"+
            "              </div>" + "\n"+
            "            </div>" + "\n"+
            "            <div class=\"survey_prefix\">" + "\n"+
            "              <div class=\"inner\">" + "\n"+
            "                <div class=\"prefix_content\">" + "\n"+
            "                  <p>${group.desc}</p>" + "\n"+
            "                </div>" + "\n"+
            "              </div>" + "\n"+
            "            </div>" + "\n"+
            "            <div class=\"survey_container\">" + "\n"+
            "              <%if(items != null){%>" + "\n"+
            "              <%for(item in items){%>" + "\n"+
            "              <div class='question <%if(item.requiredQuestion == \"1\"){%>" + "\n"+
            "                required" + "\n"+
            "               <%}else if(item.type == '5'){%>" + "\n"+
            "                 rangelength" + "\n"+
            "                <%}%>" + "\n"+
            "                ' data-type=\"${item.type}\" >" + "\n"+
            "                <div class=\"inner\" <%if(item.type == '7'){%>" + "\n"+
            "                  style=\"min-height:60px;padding:10px;\"" + "\n"+
            "                  <%}%>" + "\n"+
            "                  >" + "\n"+
            "                  <div class=\"q_title\">" + "\n"+
            "                    <span class=\"title_index\">" + "\n"+
            "                      <span class=\"question_index\">${item.orderName}</span>" + "\n"+
            "                    </span>" + "\n"+
            "                    <div class=\"title_text ${item.diyCss}\">" + "\n"+
            "                      <span class=\"title_text_mian\">${item.title}</span>" + "\n"+
            "                    </div>" + "\n"+
            "                  </div>" + "\n"+
            "                  <div class=\"description\">" + "\n"+
            "                    <p class=\"description_text\">${item.desc}</p>" + "\n"+
            "                  </div>" + "\n"+
            "                  <%if(item.type != '7'){%>" + "\n"+
            "                  <div class=\"inputs clearfix\" >" + "\n"+
            
            "                   <%if(item.type == '5'){%>" + "\n"+
            "                    <%if(item.minColumn == null || item.minColumn == ''){%>" + "\n"+
            "                     <%if('1' == item.requiredQuestion){%>" + "\n"+
            "                      <input type='hidden' id='min_item_${item.id}' value='1' >" + "\n"+
            "                     <%}else{%>" + "\n"+
            "                      <input type='hidden' id='min_item_${item.id}' value='0' >" + "\n"+
            "                     <%}%>" + "\n"+
            "                    <%}else{%>" + "\n"+
            "                     <input type='hidden' id='min_item_${item.id}' value='${item.minColumn}' >" + "\n"+
            "                    <%}%>" + "\n"+
            "                    <%if(item.maxColumn == null || item.maxColumn == ''){%>" + "\n"+
            "                     <input type='hidden' id='max_item_${item.id}' value='${item.optionsCount}' >" + "\n"+
            "                    <%}else{%>" + "\n"+
            "                     <input type='hidden' id='max_item_${item.id}' value='${item.maxColumn}' >" + "\n"+
            "                    <%}%>" + "\n"+
            "                   <%}%>" + "\n"+
            
            "                    <%if(item.type == '4' || item.type == '5'){%>" + "\n"+
            "                    <%for(option in item.options){%>" + "\n"+
            "                    <div class=\"op_item <%if(item.columnCount == '1'){%>width100" + "\n"+
            "                      <%}if(item.columnCount == '2'){%>width50" + "\n"+
            "                      <%}if(item.columnCount == '3'){%>width33" + "\n"+
            "                      <%}if(item.columnCount == '4'){%>width25" + "\n"+
            "                      <%}if(item.columnCount == '5'){%>width20" + "\n"+
            "                      <%}%> \">" + "\n"+
            "                      <input class=\"survey_form_checkbox\" <%if(item.type == '4'){%>" + "\n"+
            "                      type=\"radio\"" + "\n"+
            "                      <%}else{%>" + "\n"+
            "                      type=\"checkbox\"" + "\n"+
            "                      <%}%>" + "\n"+
            "                      name=\"item_${item.id}\" id=\"option_${option.id}\" value=\"${option.id}\" <%if(null != optionVoteDetails  ){ for(voteDetail in optionVoteDetails){ if(voteDetail.optionId == option.id){%>checked<%}}}%>  >" + "\n"+
            "                      <label for=\"option_${option.id}\"> <i class=\"survey_form_ui\"></i>" + "\n"+
            "                        <p>" + "\n"+
            "                          <span>${option.title}</span>" + "\n"+
            "                          <span class=\"op_item_remark\">${option.desc}</span>" + "\n"+
            "                          <%if(option.hmInput == true){%>" + "\n"+
            "                          <span class=\"op_item_input\">" + "\n"+
            "                            <input type=\"text\" name=\"optioncontent${option.id}\" maxlength=\"${option.length}\" placeholder='${option.length}字以内' value=\"<%if(null != optionVoteDetails  ){ for(voteDetail in optionVoteDetails){ if(voteDetail.optionId == option.id){%>${voteDetail.content}<%}}}%>\"  />" + "\n"+
            "                          </span>" + "\n"+
            "                          <%}%>" + "\n"+
            "                        </p>" + "\n"+
            "                      </label>" + "\n"+
            "                    </div>" + "\n"+
            "                    <%}%>" + "\n"+
            "                    <%}else if(item.type == '3' ){%>" + "\n"+
            "                    <input class=\"survey_form_input\" type=\"text\"  maxlength=\"${item.length}\"  name=\"itemcontent${item.id}\" value=\"<%if(null != textVoteDetails){for(contentVo in textVoteDetails ){if(contentVo.itemId == item.id){%>${contentVo.content}<%}}}%>\" placeholder=\"${item.length}字以内\">" + "\n"+
            "                    <%}else{%>" + "\n"+
            "                    <textarea class=\"survey_form_input\"  name=\"itemcontent${item.id}\" rows=\"5\" cols=\"60\" maxlength=\"${item.length}\" placeholder=\"${item.length}字以内\"><%if(null != textVoteDetails){for(contentVo in textVoteDetails ){if(contentVo.itemId == item.id){%>${contentVo.content}<%}}}%></textarea>" + "\n"+
            "                    <%}%></div>" + "\n"+
            "                  <%}%></div>" + "\n"+
            "              </div>" + "\n"+
            "              <%}%>" + "\n"+
            "              <%}%></div>" + "\n"+
            "          </div></div>" + "\n"+
            "        <div class=\"bottom_sub\" >" + "\n"+
            "          <input type='submit' class=\"survey_btn survey_submit\" <%if(flag){%>disabled<%}%> value=\"提交\" />" + "\n"+
            "          <%if(group.enableCnt){%>" + "\n"+
            " <a href=\"/vote/result_showResult.action?groupId=${group.id}\" class=\"survey_btn\" <%if(flag){%>onclick=\"return false\"<%}%>>查看结果</a>" + "\n"+
            "          <%}%>" + "\n"+
            "        </div></form></div></div></div><%}%>";

}
