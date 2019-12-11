<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--https://getbootstrap.com/docs/4.0/examples/sticky-footer/--%>
<footer class="footer">
    <div class="container">
        <span class="text-muted"><spring:message code="app.footer"/></span>
    </div>
</footer>
<script type="text/javascript">
    const i18n = [];
    var titleEN = document.querySelector('h3').textContent.indexOf('Meals') > -1;
    var titleRU = document.querySelector('h3').textContent.indexOf('Моя еда') > -1;
    i18n["addTitle"] = (titleEN || titleRU) ? '<spring:message code="meal.add"/>' : '<spring:message code="user.add"/>';
    i18n["editTitle"] = (titleEN || titleRU) ? '<spring:message code="meal.edit"/>' : '<spring:message code="user.edit"/>';

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>