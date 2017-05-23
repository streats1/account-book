<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 해더 //최상단 메뉴 -->
<c:import url="/WEB-INF/views/include/main_top.jsp" />
<div class="container">
	<div class="row mt">
		
		<!-- 왼쪽공통영역 -->
		<div class="col-lg-2">
			<c:import url="/WEB-INF/views/mypage/left_menu/profile.jsp" />
		</div>
		
		<!-- 메인컨텐츠영역 (그래프) -->
		<div class="col-lg-10">
			<ul class="nav nav-tabs">
                <li class="active"><a href="#import" data-toggle="tab">수입</a>
                </li>
                <li><a href="#export" data-toggle="tab">지출</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="import">
                	<div class="flot-chart">
                        <div class="flot-chart-content" id="flot-line-chart-multi"></div>
                    </div>
                </div>
                <div class="tab-pane fade" id="export">
                	<div class="flot-chart">
                        <div class="flot-chart-content" id="flot-line-chart-multi"></div>
                    </div>
                </div>
            </div>
		</div>
		<!-- /메인컨텐츠영역 (그래프) -->
	</div>
</div>

<!-- 푸터영역 -->
<c:import url="/WEB-INF/views/include/main_bottom.jsp" />