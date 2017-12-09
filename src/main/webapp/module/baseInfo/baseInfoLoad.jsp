<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tags.jsp" %>
<%@ include file="/common/vars.jsp" %>
<html>
<body>
    <c:choose>
        <c:when test="${mt eq 0}">
            <table class="table table-bordered" id="table_list" style="width: 1240px;">
                <thead>
                <tr>
                    <th style="width: 60px;">序号</th>
                    <th style="width: 85px;">名称</th>
                    <th style="width: 85px;">弦号</th>
                    <th style="width: 115px;">隶属单位</th>
                    <th style="width: 140px;">最大航速（节）</th>
                    <th style="width: 140px;">续航能力（海里）</th>
                    <th style="width: 140px;">满载排水量（吨）</th>
                    <th style="width: 140px;">标准排水量（吨）</th>
                    <th style="width: 140px;">生产研制单位</th>
                    <th style="width: 110px;">服役日期</th>
                    <th style="width: 85px;">操作</th>
                </tr>
                </thead>
                <tbody id="tbody_list">
                <c:forEach var="item" items="${dtos}" varStatus="st">
                    <tr id="${item.id}">
                        <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                        <td>${item.infoName}</td>
                        <td>${item.infoCode}</td>
                        <td>${item.belongUnit}</td>
                        <td style="text-align: center;">${item.maxSpeed}</td>
                        <td style="text-align: center;">${item.endurance}</td>
                        <td style="text-align: center;">${item.maxDisplacement}</td>
                        <td style="text-align: center;">${item.standardDisplacement}</td>
                        <td>${item.developmentUnit}</td>
                        <td style="text-align: center;">${item.serviceDate}</td>
                        <td style="text-align: center;">
                            <a href="${ctx}/baseInfo/baseInfo/addOrEditBaseInfo?mt=${item.mainType}&ti=${item.typeId}&id=${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${mt eq 1}">
            <table class="table table-bordered" id="table_list" style="width: 1075px;">
                <thead>
                <tr>
                    <th style="width: 60px;">序号</th>
                    <th style="width: 85px;">编号</th>
                    <th style="width: 115px;">隶属单位</th>
                    <th style="width: 170px;">最大航速（公里/时）</th>
                    <th style="width: 140px;">作战半径（公里）</th>
                    <th style="width: 170px;">最大起飞重量（吨）</th>
                    <th style="width: 140px;">生产研制单位</th>
                    <th style="width: 110px;">服役日期</th>
                    <th style="width: 85px;;">操作</th>
                </tr>
                </thead>
                <tbody id="tbody_list">
                <c:forEach var="item" items="${dtos}" varStatus="st">
                    <tr id="${item.id}">
                        <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                        <td>${item.infoCode}</td>
                        <td>${item.belongUnit}</td>
                        <td style="text-align: center;">${item.maxSpeed}</td>
                        <td style="text-align: center;">${item.fightRadius}</td>
                        <td style="text-align: center;">${item.maxTakeOffWeight}</td>
                        <td>${item.developmentUnit}</td>
                        <td style="text-align: center;">${item.serviceDate}</td>
                        <td style="text-align: center;">
                            <a href="${ctx}/baseInfo/baseInfo/addOrEditBaseInfo?mt=${item.mainType}&ti=${item.typeId}&id=${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${mt eq 2 or mt eq 3 or mt eq 4 or mt eq 6}">
            <table class="table table-bordered" id="table_list">
                <thead>
                <tr>
                    <th style="width: 10%;">序号</th>
                    <th style="width: 20%;">名称</th>
                    <th style="width: 20%;">地址</th>
                    <th style="width: 35%;">经纬度</th>
                    <th style="width: 15%;">操作</th>
                </tr>
                </thead>
                <tbody id="tbody_list">
                <c:forEach var="item" items="${dtos}" varStatus="st">
                    <tr id="${item.id}">
                        <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                        <td>${item.infoName}</td>
                        <td>${item.address}</td>
                        <td>${item.longitudeAndLatitude}</td>
                        <td style="text-align: center;">
                            <a href="${ctx}/baseInfo/baseInfo/addOrEditBaseInfo?mt=${item.mainType}&ti=${item.typeId}&id=${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${mt eq 5}">
            <table class="table table-bordered" id="table_list">
                <thead>
                <tr>
                    <th style="width: 8%;">序号</th>
                    <th style="width: 10%;">型号</th>
                    <th style="width: 10%;">生产研制单位</th>
                    <th style="width: 10%;">服役日期</th>
                    <th style="width: 10%;">技术状态</th>
                    <th style="width: 10%;">转进时间（小时）</th>
                    <th style="width: 10%;">装载时间（小时）</th>
                    <th style="width: 10%;">性能</th>
                    <th style="width: 10%;">操作</th>
                </tr>
                </thead>
                <tbody id="tbody_list">
                <c:forEach var="item" items="${dtos}" varStatus="st">
                    <tr id="${item.id}">
                        <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                        <td>${item.infoCode}</td>
                        <td>${item.developmentUnit}</td>
                        <td style="text-align: center;">${item.serviceDate}</td>
                        <td style="text-align: center;">${item.technologySituation}级</td>
                        <td style="text-align: center;">${item.switchTime}</td>
                        <td style="text-align: center;">${item.loadTime}</td>
                        <td>${item.performance}</td>
                        <td style="text-align: center;">
                            <a href="${ctx}/baseInfo/baseInfo/addOrEditBaseInfo?mt=${item.mainType}&ti=${item.typeId}&id=${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${mt eq 7}">
            <table class="table table-bordered" id="table_list">
                <thead>
                <tr>
                    <th style="width: 12%;">序号</th>
                    <th style="width: 22%;">型号</th>
                    <th style="width: 22%;">专业</th>
                    <th style="width: 22%;">数量</th>
                    <th style="width: 22%;">操作</th>
                </tr>
                </thead>
                <tbody id="tbody_list">
                <c:forEach var="item" items="${dtos}" varStatus="st">
                    <tr id="${item.id}">
                        <td style="text-align: center;">${(pageNo-1)*pageSize+(st.index+1)}</td>
                        <td>${item.infoCode}</td>
                        <td>${item.majorName}</td>
                        <td style="text-align: center;">${item.count}</td>
                        <td style="text-align: center;">
                            <a href="${ctx}/baseInfo/baseInfo/addOrEditBaseInfo?mt=${item.mainType}&ti=${item.typeId}&id=${item.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-id="${item.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise></c:otherwise>
    </c:choose>
</body>
</html>