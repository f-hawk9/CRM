<%--
  Created by IntelliJ IDEA.
  User: jerry
  Date: 2020/2/15
  Time: 6:09 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<nav>
    <ul class="pagination">
        <c:if test="${searchInfo.page == 1}">
            <li class="disabled">
                <a href="javascript:void(0);">上一页</a>
            </li>
        </c:if>

        <c:if test="${searchInfo.page > 1}">
            <li>
                <a onclick="prePage()" href="javascript:void(0);">上一页</a>
            </li>
        </c:if>


        <!--显示的页码-->
        <c:forEach begin="1" end="${totalPage}" varStatus="status" >
            <c:if test="${status.index == searchInfo.page}">
                <li class="active">
                    <a href="#"><span class="sr-on¬ly">${status.index}</span></a>
                </li>
            </c:if>
            <c:if test="${status.index != searchInfo.page}">
                <li>
                    <a href="javascript:void(0);" onclick="toPage(${status.index})">${status.index}</a>
                </li>
            </c:if>
        </c:forEach>

        <c:if test="${searchInfo.page == totalPage}">
            <li class="disabled">
                <a href="javascript:void(0);">下一页</a>
            </li>
        </c:if>
        <c:if test="${searchInfo.page < totalPage}">
            <li>
                <a onclick="nextPage()" href="javascript:void(0);">下一页</a>
            </li>
        </c:if>
    </ul>
</nav>
<script>
    function nextPage() {
        $("#page").val(parseInt($("#page").val()) + 1)
        $("#form").submit()
    }

    function prePage() {
        $("#page").val(parseInt($("#page").val()) - 1)
        $("#form").submit()
    }
    function toPage(page) {
        $("#page").val(page)
        $("#form").submit()
    }
</script>
</body>
</html>
