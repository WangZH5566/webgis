<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="com.ydh.util.Constant"%>
<!DOCTYPE html>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<style type="text/css">
    .blue{
        color:blue;
    }
</style>
<html>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="widget-box" style="min-height: 522px;">
                    <div class="widget-title">
                        <ul class="nav nav-tabs" id="ul_tab">
                            <li class="active" data-type="0"><a href="javascript:;">收文列表</a></li>
                            <li class="" data-type="1"><a href="javascript:;">发文列表</a></li>
                            <li class="" data-type="2"><a href="javascript:;">文电拟制</a></li>
                        </ul>
                    </div>
                    <div id="tab_div_list" class="widget-content">
                        <input id="txt_last_time_mills" type="hidden" value="0" />
                        <input id="txt_doc_visit_path" type="hidden" value="${FILESERVER_TELEGRAM_VISITPATH}"/>
                        <c:set var="last_time_mills" value="0"/>
                        <div id="div_0" name="div_show_for_tab">
                            <div id="div_list_0" style="height:476px;overflow-y:auto;">
                                <%--<ul class="messages">
                                    <c:forEach items="${msgDtos}" var="item" varStatus="status">
                                        <c:if test="${item.receiveTimeMills>=last_time_mills}">
                                            <c:set var="last_time_mills" value="${item.receiveTimeMills}"/>
                                        </c:if>
                                        <c:if test="${login_user.id!=item.createBy}">
                                        <li id="msg_${item.receiveTimeMills}_${item.id}_${item.receiveBy}" class="left" data-id="${item.id}" data-thtmlpath="${item.thtmlpath}" data-msg="${item.msg}">
                                            <a href="#"><img src="${ctx}/image/40.gif" alt=""></a>
                                            <div class="info">
                                                <span class="arrow"></span>
                                                <div class="detail">
                                                    <span class="sender">
                                                        收到:<strong>${item.createByName}</strong>
                                                    </span>
                                                    <span class="time">${item.receiveTimeView}</span>
                                                </div>
                                                <div class="message">
                                                    <p>${item.tname}<a href="${FILESERVER_TELEGRAM_VISITPATH}/${item.docName}" target="_blank">下载</a><a href='javascript:void(0);' tel-id="${item.id}" onclick="WEBGIS.sh.forwardTel(this)">转发</a></p>
                                                </div>
                                            </div>
                                        </li>
                                        </c:if>
                                        <c:if test="${login_user.id==item.createBy}">
                                            <li id="msg_${item.receiveTimeMills}_${item.id}_${item.receiveBy}" class="right" data-id="${item.id}" data-thtmlpath="${item.thtmlpath}" data-msg="${item.msg}">
                                                <a href="#"><img src="${ctx}/image/40.gif" alt=""></a>
                                                <div class="info">
                                                    <span class="arrow"></span>
                                                    <div class="detail">
                                                    <span class="sender">
                                                        发送给:<strong>${item.receiveByName}</strong>
                                                    </span>
                                                        <span class="time">${item.receiveTimeView}</span>
                                                    </div>
                                                    <div class="message">
                                                        <p>${item.tname}<a href="${FILESERVER_TELEGRAM_VISITPATH}/${item.docName}" target="_blank">下载</a><a href='javascript:void(0);' tel-id="${item.id}" onclick="WEBGIS.sh.forwardTel(this)">转发</a></p>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>--%>
                            </div>
                            <div id="div_page_btn_0"></div>
                        </div>
                        <div id="div_1" name="div_show_for_tab">
                            <div id="div_list_1" style="height:476px;overflow-y:auto;"></div>
                            <div id="div_page_btn_1"></div>
                        </div>
                        <div id="div_2" name="div_show_for_tab">
                            <form class="form-horizontal" action="javascript:;" method="post" novalidate="novalidate">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label form-label2">文电名称:</label>
                                        <div class="col-sm-8 form-div2">
                                            <input type="text" placeholder="" class="form-control" id="txt_tn" />
                                        </div>
                                        <div class="col-sm-2 form-div2" style="line-height: 30px;">
                                            <button type="button" class="btn btn-xs btn-primary buttons" id="btn_search">&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;</button>
                                        </div>
                                    </div>
                                </div>
                                <div style="clear: both"></div>
                            </form>
                            <div id="div_list_2" style="height:426px;overflow-y:auto;">
                                <table class="table table-bordered" id="table_list">
                                    <tbody id="tbody_list">
                                    </tbody>
                                </table>
                            </div>
                            <div id="div_page_btn_2"></div>
                            <div>
                                <%--<a href="${ctx}/sh/teleSend" class="btn btn-xs btn-primary buttons">发送文电</a>--%>
                                <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_create">创建</a>
                                <a href="javascript:;" class="btn btn-xs btn-primary buttons" id="btn_delete">删除</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="widget-box">
                    <div class="widget-content">
                        <iframe id="f_cont" src="" width="100%" height="530px" seamless></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="div_send_detail" class="widget-box" style="display: none;margin:10px;">
        <div class="widget-content">
            <div id="div_detail_list"></div>
            <div id="div_detail_page_btn"></div>
        </div>
    </div>

    <div id="win_receipt_msg" style="display: none;">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content">
                    <form class="form-horizontal" id="form_receipt_msg">
                        <div class="form-group">
                            <label class="col-sm-3 control-label form-label2">回执消息:</label>
                            <div class="col-sm-8 form-div2">
                                <textarea placeholder="" style="width:245px;height:100px;resize: none;" class="form-control" id="txt_receipt_msg" name="receiptMsg" >已读回执</textarea>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div id="d_rd" style="display: none;">
        <div class="col-md-12">
            <div class="widget-box">
                <div class="widget-content">
                    <form class="form-horizontal" action="${ctx}/sm/upload" method="post" enctype="multipart/form-data" target="target_upload" id="form_submit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label form-label2">文电名称:</label>
                            <div class="col-sm-8 form-div2">
                                <input type="text" placeholder="" class="form-control" id="txt_tname" name="tname" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label form-label2">文电内容:</label>
                            <div class="col-sm-8 form-div2">
                                <input type="file" placeholder="" class="form-control" id="txt_file" name="tfile" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <iframe id="target_upload" name="target_upload" src="" style="display:none"></iframe>
    <script src="${ctx}/js/global/progressBar.js" type="text/javascript"></script>

    <script src="${ctx}/js/module/situationHandle/situationHandle.js?v=20161028002"></script>
    <script type="text/javascript">
        var tvisitPath = '${tvisitPath}';
        WEBGIS.sh.init();
    </script>
</body>
</html>